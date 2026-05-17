package dev.jorm.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import dev.jorm.core.model.SchemaModel;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaGenerator {

    private final SchemaModel schema;
    private final File outputDirectory;
    private final String packageName;

    public JavaGenerator(SchemaModel schema, File outputDirectory, String packageName) {
        this.schema = schema;
        this.outputDirectory = outputDirectory;
        this.packageName = packageName;
    }

    public void generate() throws IOException {
        generateEnums();
        generateRecords();
    }

    private void generateEnums() throws IOException {
        for (SchemaModel.EnumModel enumModel : schema.enums()) {
            TypeSpec.Builder enumBuilder = TypeSpec.enumBuilder(enumModel.name())
                    .addModifiers(Modifier.PUBLIC);

            for (String value : enumModel.values()) {
                enumBuilder.addEnumConstant(value);
            }

            JavaFile javaFile = JavaFile.builder(packageName, enumBuilder.build())
                    .indent("    ")
                    .build();

            javaFile.writeTo(outputDirectory);
        }
    }

    private void generateRecords() throws IOException {
        // JavaPoet 1.13.0 does not support TypeSpec.recordBuilder directly.
        // We will generate records using standard string templates.
        
        File packageDir = new File(outputDirectory, packageName.replace(".", "/"));
        if (!packageDir.exists()) {
            packageDir.mkdirs();
        }

        for (SchemaModel.EntityModel entityModel : schema.models()) {
            StringBuilder builder = new StringBuilder();
            builder.append("package ").append(packageName).append(";\n\n");
            
            // Basic imports
            builder.append("import java.time.LocalDateTime;\n");
            builder.append("import java.util.List;\n\n");
            
            builder.append("public record ").append(entityModel.name()).append("(\n");
            
            for (int i = 0; i < entityModel.fields().size(); i++) {
                SchemaModel.FieldModel field = entityModel.fields().get(i);
                String javaType = mapTypeToString(field);
                
                builder.append("    ").append(javaType).append(" ").append(field.name());
                if (i < entityModel.fields().size() - 1) {
                    builder.append(",");
                }
                builder.append("\n");
            }
            
            builder.append(") {}\n");
            
            File javaFile = new File(packageDir, entityModel.name() + ".java");
            Files.writeString(Path.of(javaFile.getPath()), builder.toString());
        }
    }

    private String mapTypeToString(SchemaModel.FieldModel field) {
        String baseType = switch (field.type()) {
            case "String" -> "String";
            case "Int" -> "Integer";
            case "Float" -> "Float";
            case "Boolean" -> "Boolean";
            case "DateTime" -> "LocalDateTime";
            default -> field.type(); // Enum or Relation
        };

        if (field.isArray()) {
            return "List<" + baseType + ">";
        }
        
        return baseType;
    }
}
