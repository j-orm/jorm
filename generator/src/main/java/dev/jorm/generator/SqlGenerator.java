package dev.jorm.generator;

import dev.jorm.core.model.SchemaModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SqlGenerator {

    private final SchemaModel schema;
    private final File outputDirectory;
    private final String dialect;

    public SqlGenerator(SchemaModel schema, File outputDirectory) {
        this.schema = schema;
        this.outputDirectory = outputDirectory;
        
        String db = "postgresql";
        if (schema.config() != null) {
            for (SchemaModel.ConfigEntry entry : schema.config()) {
                if ("database".equals(entry.key())) {
                    db = entry.value();
                }
            }
        }
        this.dialect = db;
    }

    public void generate() throws IOException {
        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new IOException("Could not create migrations directory: " + outputDirectory.getPath());
        }

        StringBuilder sql = new StringBuilder();

        boolean isPostgres = "postgresql".equalsIgnoreCase(dialect);
        boolean isMysql = "mysql".equalsIgnoreCase(dialect);
        String quote = isMysql ? "`" : "\"";

        // 1. Generate ENUMs (if supported by dialect, e.g., PostgreSQL)
        if (isPostgres) {
            for (SchemaModel.EnumModel enumModel : schema.enums()) {
                sql.append("CREATE TYPE ").append(quote).append(enumModel.name()).append(quote).append(" AS ENUM (\n");
                for (int i = 0; i < enumModel.values().size(); i++) {
                    sql.append("    '").append(enumModel.values().get(i)).append("'");
                    if (i < enumModel.values().size() - 1) {
                        sql.append(",");
                    }
                    sql.append("\n");
                }
                sql.append(");\n\n");
            }
        }

        // 2. Generate TABLES
        for (SchemaModel.EntityModel entityModel : schema.models()) {
            sql.append("CREATE TABLE ").append(quote).append(entityModel.name()).append(quote).append(" (\n");

            // Filter out relationship fields (arrays or complex types that are not Enums and not Native)
            List<SchemaModel.FieldModel> physicalFields = entityModel.fields().stream()
                    .filter(f -> !f.isArray() && (isNativeType(f.type()) || isEnumType(f.type())))
                    .toList();

            for (int i = 0; i < physicalFields.size(); i++) {
                SchemaModel.FieldModel field = physicalFields.get(i);
                sql.append("    ").append(quote).append(field.name()).append(quote).append(" ").append(mapToSqlType(field, isMysql));

                // Process Attributes (e.g., @id, @unique)
                for (SchemaModel.AttributeModel attr : field.attributes()) {
                    if (attr.name().equals("id")) {
                        sql.append(" PRIMARY KEY");
                    } else if (attr.name().equals("unique")) {
                        sql.append(" UNIQUE");
                    }
                }

                if (!field.isOptional() && !isPrimaryKey(field)) {
                    sql.append(" NOT NULL");
                }

                if (i < physicalFields.size() - 1) {
                    sql.append(",");
                }
                sql.append("\n");
            }

            sql.append(");\n\n");
        }

        // Write migration file
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filename = timestamp + "_init.sql";
        File migrationFile = new File(outputDirectory, filename);

        Files.writeString(Path.of(migrationFile.getPath()), sql.toString());
        System.out.println("SQL migration script generated successfully: " + migrationFile.getPath());
    }

    private boolean isPrimaryKey(SchemaModel.FieldModel field) {
        return field.attributes().stream().anyMatch(attr -> attr.name().equals("id"));
    }

    private boolean isNativeType(String type) {
        return type.equals("String") || type.equals("Int") || type.equals("Float") 
                || type.equals("Boolean") || type.equals("DateTime");
    }

    private boolean isEnumType(String type) {
        return schema.enums().stream().anyMatch(e -> e.name().equals(type));
    }

    private String mapToSqlType(SchemaModel.FieldModel field, boolean isMysql) {
        boolean isAutoIncrement = field.attributes().stream().anyMatch(attr -> attr.name().equals("autoincrement"));

        if (isEnumType(field.type())) {
            if (isMysql) {
                SchemaModel.EnumModel enumModel = schema.enums().stream()
                        .filter(e -> e.name().equals(field.type()))
                        .findFirst().orElseThrow();
                String values = enumModel.values().stream()
                        .map(v -> "'" + v + "'")
                        .collect(Collectors.joining(", "));
                return "ENUM(" + values + ")";
            } else {
                return field.type(); // Postgres uses the custom type name
            }
        }

        if (isMysql) {
            return switch (field.type()) {
                case "String" -> "VARCHAR(255)";
                case "Int" -> isAutoIncrement ? "INT AUTO_INCREMENT" : "INT";
                case "Float" -> "DOUBLE";
                case "Boolean" -> "TINYINT(1)";
                case "DateTime" -> "DATETIME";
                default -> "VARCHAR(255)";
            };
        } else {
            return switch (field.type()) {
                case "String" -> "VARCHAR(255)";
                case "Int" -> isAutoIncrement ? "SERIAL" : "INTEGER";
                case "Float" -> "DOUBLE PRECISION";
                case "Boolean" -> "BOOLEAN";
                case "DateTime" -> "TIMESTAMP";
                default -> "VARCHAR(255)";
            };
        }
    }
}
