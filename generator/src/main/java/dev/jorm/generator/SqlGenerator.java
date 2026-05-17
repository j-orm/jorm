package dev.jorm.generator;

import dev.jorm.parser.SchemaModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlGenerator {

    private final SchemaModel schema;
    private final File outputDirectory;

    public SqlGenerator(SchemaModel schema, File outputDirectory) {
        this.schema = schema;
        this.outputDirectory = outputDirectory;
    }

    public void generate() throws IOException {
        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new IOException("Could not create migrations directory: " + outputDirectory.getPath());
        }

        StringBuilder sql = new StringBuilder();

        // 1. Generate ENUMs (if supported by dialect, e.g., PostgreSQL)
        for (SchemaModel.EnumModel enumModel : schema.enums()) {
            sql.append("CREATE TYPE ").append(enumModel.name()).append(" AS ENUM (\n");
            for (int i = 0; i < enumModel.values().size(); i++) {
                sql.append("    '").append(enumModel.values().get(i)).append("'");
                if (i < enumModel.values().size() - 1) {
                    sql.append(",");
                }
                sql.append("\n");
            }
            sql.append(");\n\n");
        }

        // 2. Generate TABLES
        for (SchemaModel.EntityModel entityModel : schema.models()) {
            sql.append("CREATE TABLE ").append(entityModel.name()).append(" (\n");

            // Filter out relationship fields (arrays or complex types) that shouldn't be physical columns
            java.util.List<SchemaModel.FieldModel> physicalFields = entityModel.fields().stream()
                    .filter(f -> !f.isArray() && isNativeType(f.type()))
                    .toList();

            for (int i = 0; i < physicalFields.size(); i++) {
                SchemaModel.FieldModel field = physicalFields.get(i);
                sql.append("    ").append(field.name()).append(" ").append(mapToSqlType(field));

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

    private String mapToSqlType(SchemaModel.FieldModel field) {
        boolean isAutoIncrement = field.attributes().stream().anyMatch(attr -> attr.name().equals("autoincrement"));

        // Base PostgreSQL dialect for MVP
        return switch (field.type()) {
            case "String" -> "VARCHAR(255)";
            case "Int" -> isAutoIncrement ? "SERIAL" : "INTEGER";
            case "Float" -> "DOUBLE PRECISION";
            case "Boolean" -> "BOOLEAN";
            case "DateTime" -> "TIMESTAMP";
            default -> field.type(); // Assumes it is an Enum that was already created above
        };
    }
}
