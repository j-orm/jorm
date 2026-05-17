package dev.jorm.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import dev.jorm.core.model.SchemaModel;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClientGenerator {

    private final SchemaModel schema;
    private final File outputDirectory;
    private final String packageName;

    public ClientGenerator(SchemaModel schema, File outputDirectory, String packageName) {
        this.schema = schema;
        this.outputDirectory = outputDirectory;
        this.packageName = packageName;
    }

    public void generate() throws IOException {
        for (SchemaModel.EntityModel entityModel : schema.models()) {
            String clientClassName = entityModel.name() + "Client";
            ClassName modelClass = ClassName.get(packageName, entityModel.name());
            
            TypeSpec.Builder clientBuilder = TypeSpec.classBuilder(clientClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            ClassName queryExecutorClass = ClassName.get("dev.jorm.db", "QueryExecutor");

            // QueryExecutor field
            clientBuilder.addField(FieldSpec.builder(queryExecutorClass, "executor", Modifier.PRIVATE, Modifier.FINAL).build());

            // Constructor
            MethodSpec constructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(queryExecutorClass, "executor")
                    .addStatement("this.executor = executor")
                    .build();
            clientBuilder.addMethod(constructor);

            // Mapper
            MethodSpec mapper = generateMapper(entityModel, modelClass);
            clientBuilder.addMethod(mapper);

            // Internal classes (Filters and Builders)
            TypeSpec stringFilter = generateStringFilter();
            clientBuilder.addType(stringFilter);

            TypeSpec intFilter = generateIntFilter();
            clientBuilder.addType(intFilter);

            TypeSpec whereBuilder = generateWhereBuilder(entityModel);
            clientBuilder.addType(whereBuilder);

            TypeSpec dataBuilder = generateDataBuilder(entityModel);
            clientBuilder.addType(dataBuilder);
            
            TypeSpec includeBuilder = generateIncludeBuilder(entityModel);
            clientBuilder.addType(includeBuilder);

            // findMany method without filters
            MethodSpec findMany = MethodSpec.methodBuilder("findMany")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ParameterizedTypeName.get(ClassName.get(List.class), modelClass))
                    .addStatement("String sql = $S", "SELECT * FROM " + entityModel.name())
                    .addStatement("return executor.executeQuery(sql, this::mapRow)")
                    .build();
            clientBuilder.addMethod(findMany);

            // findMany method with filters (Fluent API)
            MethodSpec findManyFluent = MethodSpec.methodBuilder("findMany")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.bestGuess("WhereBuilder")), "consumer")
                    .returns(ParameterizedTypeName.get(ClassName.get(List.class), modelClass))
                    .addStatement("WhereBuilder builder = new WhereBuilder()")
                    .addStatement("consumer.accept(builder)")
                    .addStatement("String sql = $S + String.join($S, builder.conditions)", "SELECT * FROM " + entityModel.name() + " WHERE ", " AND ")
                    .addStatement("return executor.executeQuery(sql, this::mapRow, builder.parameters.toArray())")
                    .build();
            clientBuilder.addMethod(findManyFluent);

            // withIncludes method
            MethodSpec withIncludes = generateWithIncludes(entityModel, modelClass, idField);
            clientBuilder.addMethod(withIncludes);

            // findMany method with filters and includes
            MethodSpec findManyWithIncludes = MethodSpec.methodBuilder("findMany")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.bestGuess("WhereBuilder")), "whereConsumer")
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.bestGuess("IncludeBuilder")), "includeConsumer")
                    .returns(ParameterizedTypeName.get(ClassName.get(List.class), modelClass))
                    .addStatement("IncludeBuilder includeBuilder = new IncludeBuilder()")
                    .addStatement("includeConsumer.accept(includeBuilder)")
                    .addStatement("$T<$T> results = findMany(whereConsumer)", List.class, modelClass)
                    .beginControlFlow("if (!results.isEmpty() && !includeBuilder.includes.isEmpty())")
                    .addStatement("return results.stream().map(r -> withIncludes(r, includeBuilder)).collect($T.toList())", Collectors.class)
                    .endControlFlow()
                    .addStatement("return results")
                    .build();
            clientBuilder.addMethod(findManyWithIncludes);

            // create method (Fluent API)
            MethodSpec create = MethodSpec.methodBuilder("create")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.bestGuess("DataBuilder")), "consumer")
                    .returns(modelClass)
                    .addStatement("DataBuilder builder = new DataBuilder()")
                    .addStatement("consumer.accept(builder)")
                    .addStatement("String cols = String.join($S, builder.columns)", ", ")
                    .addStatement("String placeholders = builder.columns.stream().map(c -> $S).collect($T.joining($S))", "?", Collectors.class, ", ")
                    .addStatement("String sql = $S + cols + $S + placeholders + $S", "INSERT INTO " + entityModel.name() + " (", ") VALUES (", ")")
                    .addStatement("executor.executeUpdate(sql, builder.values.toArray())")
                    .addStatement("return null") 
                    .build();
            clientBuilder.addMethod(create);

            // Detect ID field type dynamically
            SchemaModel.FieldModel idField = entityModel.fields().stream()
                    .filter(f -> f.attributes().stream().anyMatch(a -> a.name().equals("id")))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No @id field found for model " + entityModel.name()));
            Class<?> idJavaType = getJavaType(idField.type());

            // update method (Fluent API)
            MethodSpec update = MethodSpec.methodBuilder("update")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(idJavaType, "id")
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.bestGuess("DataBuilder")), "consumer")
                    .returns(modelClass)
                    .addStatement("DataBuilder builder = new DataBuilder()")
                    .addStatement("consumer.accept(builder)")
                    .addStatement("String setClause = builder.columns.stream().map(c -> c + $S).collect($T.joining($S))", " = ?", Collectors.class, ", ")
                    .addStatement("String sql = $S + setClause + $S", "UPDATE " + entityModel.name() + " SET ", " WHERE " + idField.name() + " = ?")
                    .addStatement("$T<Object> params = new $T<>(builder.values)", List.class, ArrayList.class)
                    .addStatement("params.add(id)")
                    .addStatement("executor.executeUpdate(sql, params.toArray())")
                    .addStatement("return findById(id)")
                    .build();
            clientBuilder.addMethod(update);

            // findById method
            MethodSpec findById = MethodSpec.methodBuilder("findById")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(idJavaType, "id")
                    .returns(modelClass)
                    .addStatement("String sql = $S", "SELECT * FROM " + entityModel.name() + " WHERE " + idField.name() + " = ?")
                    .addStatement("$T<$T> results = executor.executeQuery(sql, this::mapRow, id)", List.class, modelClass)
                    .addStatement("return results.isEmpty() ? null : results.get(0)")
                    .build();
            clientBuilder.addMethod(findById);

            // findById with includes method
            MethodSpec findByIdWithIncludes = MethodSpec.methodBuilder("findById")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(idJavaType, "id")
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.bestGuess("IncludeBuilder")), "includeConsumer")
                    .returns(modelClass)
                    .addStatement("IncludeBuilder includeBuilder = new IncludeBuilder()")
                    .addStatement("includeConsumer.accept(includeBuilder)")
                    .addStatement("$T result = findById(id)", modelClass)
                    .beginControlFlow("if (result != null && !includeBuilder.includes.isEmpty())")
                    .addStatement("return withIncludes(result, includeBuilder)")
                    .endControlFlow()
                    .addStatement("return result")
                    .build();
            clientBuilder.addMethod(findByIdWithIncludes);

            // delete method
            MethodSpec delete = MethodSpec.methodBuilder("delete")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(idJavaType, "id")
                    .returns(void.class)
                    .addStatement("String sql = $S", "DELETE FROM " + entityModel.name() + " WHERE " + idField.name() + " = ?")
                    .addStatement("executor.executeUpdate(sql, id)")
                    .build();
            clientBuilder.addMethod(delete);

            // Generate main JormClient
            // The method generateMainClient is called outside the loop to generate the central Jorm class.

            JavaFile javaFile = JavaFile.builder(packageName, clientBuilder.build())
                    .indent("    ")
                    .build();

            javaFile.writeTo(outputDirectory);
        }
        
        generateMainClient();
    }
    
    private void generateMainClient() throws IOException {
        ClassName connectionManagerClass = ClassName.get("dev.jorm.db", "ConnectionManager");
        ClassName queryExecutorClass = ClassName.get("dev.jorm.db", "QueryExecutor");

        TypeSpec.Builder mainClientBuilder = TypeSpec.classBuilder("Jorm")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        boolean isSpring = false;
        if (schema.config() != null) {
            for (SchemaModel.ConfigEntry entry : schema.config()) {
                if ("framework".equals(entry.key()) && "spring".equals(entry.value())) {
                    isSpring = true;
                    break;
                }
            }
        }

        if (isSpring) {
            ClassName repositoryClass = ClassName.get("org.springframework.stereotype", "Repository");
            mainClientBuilder.addAnnotation(repositoryClass);
        }

        mainClientBuilder.addField(FieldSpec.builder(queryExecutorClass, "executor", Modifier.PRIVATE, Modifier.FINAL).build());

        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(connectionManagerClass, "connectionManager")
                .addStatement("this.executor = new $T(connectionManager)", queryExecutorClass)
                .build();
        mainClientBuilder.addMethod(constructor);

        for (SchemaModel.EntityModel entityModel : schema.models()) {
            String clientClassName = entityModel.name() + "Client";
            String methodName = entityModel.name().substring(0, 1).toLowerCase() + entityModel.name().substring(1);
            
            ClassName clientType = ClassName.get(packageName, clientClassName);
            
            MethodSpec accessor = MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(clientType)
                    .addStatement("return new $T(executor)", clientType)
                    .build();
                    
            mainClientBuilder.addMethod(accessor);
        }
        
        JavaFile javaFile = JavaFile.builder(packageName, mainClientBuilder.build())
                .indent("    ")
                .build();

        javaFile.writeTo(outputDirectory);
    }

    private MethodSpec generateMapper(SchemaModel.EntityModel entityModel, ClassName modelClass) {
        MethodSpec.Builder mapperBuilder = MethodSpec.methodBuilder("mapRow")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ResultSet.class, "rs")
                .returns(modelClass)
                .addException(SQLException.class);

        StringBuilder constructorArgs = new StringBuilder();

        for (int i = 0; i < entityModel.fields().size(); i++) {
            SchemaModel.FieldModel field = entityModel.fields().get(i);
            
            if (isNativeType(field.type())) {
                String resultSetMethod = getResultSetMethod(field.type());
                mapperBuilder.addStatement("$T _$L = rs.$L($S)", 
                        getJavaType(field.type()), field.name(), resultSetMethod, field.name());
            } else {
                // For relation fields, initialize with null or empty list
                if (field.isArray()) {
                    mapperBuilder.addStatement("$T<$T> _$L = new $T<>()", 
                            List.class, ClassName.bestGuess(field.type()), field.name(), ArrayList.class);
                } else {
                    mapperBuilder.addStatement("$T _$L = null", 
                            ClassName.bestGuess(field.type()), field.name());
                }
            }
            
            constructorArgs.append("_").append(field.name());
            if (i < entityModel.fields().size() - 1) {
                constructorArgs.append(", ");
            }
        }

        mapperBuilder.addStatement("return new $T($L)", modelClass, constructorArgs.toString());
        return mapperBuilder.build();
    }

    private MethodSpec generateWithIncludes(SchemaModel.EntityModel entityModel, ClassName modelClass, SchemaModel.FieldModel idField) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("withIncludes")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(modelClass, "result")
                .addParameter(ClassName.bestGuess("IncludeBuilder"), "includeBuilder")
                .returns(modelClass);

        // We will build new variables for each field, copying from result initially
        for (SchemaModel.FieldModel field : entityModel.fields()) {
            if (isNativeType(field.type())) {
                method.addStatement("$T _$L = result.$L()", getJavaType(field.type()), field.name(), field.name());
            } else {
                if (field.isArray()) {
                    method.addStatement("$T<$T> _$L = result.$L()", List.class, ClassName.bestGuess(field.type()), field.name(), field.name());
                } else {
                    method.addStatement("$T _$L = result.$L()", ClassName.bestGuess(field.type()), field.name(), field.name());
                }
            }
        }

        // Iterate relations and check if they are in includes
        for (SchemaModel.FieldModel field : entityModel.fields()) {
            if (!isNativeType(field.type())) {
                String fk = getForeignKeyName(field.type(), entityModel.name());
                if (fk == null) continue; // unable to resolve relation

                method.beginControlFlow("if (includeBuilder.includes.contains($S))", field.name());
                
                ClassName targetClientClass = ClassName.bestGuess(field.type() + "Client");
                
                if (field.isArray()) {
                    method.addStatement("_$L = new $T(executor).findMany(w -> w.$L().equals(result.$L()))", 
                            field.name(), targetClientClass, fk, idField.name());
                } else {
                    // For singular relation (e.g. Post -> User author)
                    // The FK is typically on this entity.
                    // Wait, if it's singular, the FK is on this entity pointing to the target entity's ID.
                    // Let's check if the FK exists on this entity.
                    boolean fkOnThisEntity = entityModel.fields().stream().anyMatch(f -> f.name().equals(fk));
                    if (fkOnThisEntity) {
                        method.addStatement("_$L = new $T(executor).findById(result.$L())", 
                                field.name(), targetClientClass, fk);
                    } else {
                        method.addStatement("_$L = new $T(executor).findMany(w -> w.$L().equals(result.$L())).stream().findFirst().orElse(null)", 
                                field.name(), targetClientClass, fk, idField.name());
                    }
                }
                
                method.endControlFlow();
            }
        }

        StringBuilder constructorArgs = new StringBuilder();
        for (int i = 0; i < entityModel.fields().size(); i++) {
            constructorArgs.append("_").append(entityModel.fields().get(i).name());
            if (i < entityModel.fields().size() - 1) {
                constructorArgs.append(", ");
            }
        }
        method.addStatement("return new $T($L)", modelClass, constructorArgs.toString());

        return method.build();
    }

    private Class<?> getJavaType(String schemaType) {
        return switch (schemaType) {
            case "String" -> String.class;
            case "Int" -> Integer.class;
            case "Float" -> Float.class;
            case "Boolean" -> Boolean.class;
            case "DateTime" -> java.time.LocalDateTime.class;
            default -> String.class; // Enums as String fallback
        };
    }

    private TypeSpec generateWhereBuilder(SchemaModel.EntityModel entityModel) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("WhereBuilder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        builder.addField(FieldSpec.builder(ParameterizedTypeName.get(List.class, String.class), "conditions")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .initializer("new $T<>()", ArrayList.class)
                .build());

        builder.addField(FieldSpec.builder(ParameterizedTypeName.get(List.class, Object.class), "parameters")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .initializer("new $T<>()", ArrayList.class)
                .build());

        for (SchemaModel.FieldModel field : entityModel.fields()) {
            ClassName filterClass = getFilterClass(field.type());
            
            builder.addMethod(MethodSpec.methodBuilder(field.name())
                    .addModifiers(Modifier.PUBLIC)
                    .returns(filterClass)
                    .addStatement("return new $T(this, $S)", filterClass, field.name())
                    .build());
        }

        return builder.build();
    }

    private ClassName getFilterClass(String schemaType) {
        return switch (schemaType) {
            case "String" -> ClassName.bestGuess("StringFilter");
            case "Int" -> ClassName.bestGuess("IntFilter");
            default -> ClassName.bestGuess("StringFilter"); // Fallback
        };
    }

    private TypeSpec generateStringFilter() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("StringFilter")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        builder.addField(FieldSpec.builder(ClassName.bestGuess("WhereBuilder"), "parent", Modifier.PRIVATE, Modifier.FINAL).build());
        builder.addField(FieldSpec.builder(String.class, "column", Modifier.PRIVATE, Modifier.FINAL).build());

        builder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.bestGuess("WhereBuilder"), "parent")
                .addParameter(String.class, "column")
                .addStatement("this.parent = parent")
                .addStatement("this.column = column")
                .build());

        builder.addMethod(MethodSpec.methodBuilder("equals")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "value")
                .returns(ClassName.bestGuess("WhereBuilder"))
                .addStatement("parent.conditions.add(column + $S)", " = ?")
                .addStatement("parent.parameters.add(value)")
                .addStatement("return parent")
                .build());

        builder.addMethod(MethodSpec.methodBuilder("contains")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "value")
                .returns(ClassName.bestGuess("WhereBuilder"))
                .addStatement("parent.conditions.add(column + $S)", " LIKE ?")
                .addStatement("parent.parameters.add($S + value + $S)", "%", "%")
                .addStatement("return parent")
                .build());

        builder.addMethod(MethodSpec.methodBuilder("startsWith")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "value")
                .returns(ClassName.bestGuess("WhereBuilder"))
                .addStatement("parent.conditions.add(column + $S)", " LIKE ?")
                .addStatement("parent.parameters.add(value + $S)", "%")
                .addStatement("return parent")
                .build());

        return builder.build();
    }

    private TypeSpec generateIntFilter() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("IntFilter")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        builder.addField(FieldSpec.builder(ClassName.bestGuess("WhereBuilder"), "parent", Modifier.PRIVATE, Modifier.FINAL).build());
        builder.addField(FieldSpec.builder(String.class, "column", Modifier.PRIVATE, Modifier.FINAL).build());

        builder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.bestGuess("WhereBuilder"), "parent")
                .addParameter(String.class, "column")
                .addStatement("this.parent = parent")
                .addStatement("this.column = column")
                .build());

        builder.addMethod(MethodSpec.methodBuilder("equals")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Integer.class, "value")
                .returns(ClassName.bestGuess("WhereBuilder"))
                .addStatement("parent.conditions.add(column + $S)", " = ?")
                .addStatement("parent.parameters.add(value)")
                .addStatement("return parent")
                .build());

        builder.addMethod(MethodSpec.methodBuilder("gt")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Integer.class, "value")
                .returns(ClassName.bestGuess("WhereBuilder"))
                .addStatement("parent.conditions.add(column + $S)", " > ?")
                .addStatement("parent.parameters.add(value)")
                .addStatement("return parent")
                .build());

        builder.addMethod(MethodSpec.methodBuilder("lt")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Integer.class, "value")
                .returns(ClassName.bestGuess("WhereBuilder"))
                .addStatement("parent.conditions.add(column + $S)", " < ?")
                .addStatement("parent.parameters.add(value)")
                .addStatement("return parent")
                .build());

        return builder.build();
    }

    private TypeSpec generateIncludeBuilder(SchemaModel.EntityModel entityModel) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("IncludeBuilder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        builder.addField(FieldSpec.builder(ParameterizedTypeName.get(List.class, String.class), "includes", Modifier.PUBLIC, Modifier.FINAL)
                .initializer("new $T<>()", ArrayList.class)
                .build());

        for (SchemaModel.FieldModel field : entityModel.fields()) {
            // Only generate include methods for relation types (not native primitives)
            if (!isNativeType(field.type())) {
                builder.addMethod(MethodSpec.methodBuilder(field.name())
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(boolean.class, "include")
                        .returns(ClassName.bestGuess("IncludeBuilder"))
                        .beginControlFlow("if (include)")
                        .addStatement("this.includes.add($S)", field.name())
                        .endControlFlow()
                        .addStatement("return this")
                        .build());
            }
        }

        return builder.build();
    }

    private String getForeignKeyName(String targetEntity, String sourceEntity) {
        // First, check if the relation attribute is on the source entity (e.g. Post including User)
        SchemaModel.EntityModel sourceModel = schema.models().stream()
                .filter(m -> m.name().equals(sourceEntity))
                .findFirst()
                .orElse(null);
                
        if (sourceModel != null) {
            for (SchemaModel.FieldModel field : sourceModel.fields()) {
                if (field.type().equals(targetEntity)) {
                    for (SchemaModel.AttributeModel attr : field.attributes()) {
                        if (attr.name().equals("relation") && attr.argument() != null) {
                            String arg = attr.argument();
                            int start = arg.indexOf("fields: [");
                            if (start != -1) {
                                start += "fields: [".length();
                                int end = arg.indexOf("]", start);
                                if (end != -1) {
                                    return arg.substring(start, end).trim();
                                }
                            }
                        }
                    }
                }
            }
        }

        // Second, check if the relation attribute is on the target entity (e.g. User including Post)
        SchemaModel.EntityModel targetModel = schema.models().stream()
                .filter(m -> m.name().equals(targetEntity))
                .findFirst()
                .orElse(null);
                
        if (targetModel != null) {
            for (SchemaModel.FieldModel field : targetModel.fields()) {
                if (field.type().equals(sourceEntity)) {
                    // Find relation attribute
                    for (SchemaModel.AttributeModel attr : field.attributes()) {
                        if (attr.name().equals("relation") && attr.argument() != null) {
                            // argument is like: fields: [authorId], references: [id]
                            String arg = attr.argument();
                            int start = arg.indexOf("fields: [");
                            if (start != -1) {
                                start += "fields: [".length();
                                int end = arg.indexOf("]", start);
                                if (end != -1) {
                                    return arg.substring(start, end).trim();
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Fallback guess: sourceEntity name + "Id" (e.g. userId)
        return sourceEntity.substring(0, 1).toLowerCase() + sourceEntity.substring(1) + "Id";
    }

    private TypeSpec generateDataBuilder(SchemaModel.EntityModel entityModel) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("DataBuilder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        builder.addField(FieldSpec.builder(ParameterizedTypeName.get(List.class, String.class), "columns")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .initializer("new $T<>()", ArrayList.class)
                .build());

        builder.addField(FieldSpec.builder(ParameterizedTypeName.get(List.class, Object.class), "values")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .initializer("new $T<>()", ArrayList.class)
                .build());

        for (SchemaModel.FieldModel field : entityModel.fields()) {
            boolean isAutoIncrement = field.attributes().stream().anyMatch(attr -> attr.name().equals("autoincrement"));
            if (isAutoIncrement) continue;

            builder.addMethod(MethodSpec.methodBuilder(field.name())
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(getJavaType(field.type()), "value")
                    .returns(ClassName.bestGuess("DataBuilder"))
                    .addStatement("this.columns.add($S)", field.name())
                    .addStatement("this.values.add(value)")
                    .addStatement("return this")
                    .build());
        }

        return builder.build();
    }

    private String getResultSetMethod(String schemaType) {
        return switch (schemaType) {
            case "String" -> "getString";
            case "Int" -> "getInt";
            case "Float" -> "getFloat";
            case "Boolean" -> "getBoolean";
            case "DateTime" -> "getTimestamp";
            default -> "getString"; // Enums as String fallback
        };
    }
}
