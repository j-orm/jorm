package dev.jorm.core.model;

import java.util.List;

public record SchemaModel(
        List<ConfigEntry> config,
        List<EntityModel> models,
        List<EnumModel> enums
) {
    public record ConfigEntry(String key, String value) {}

    public record EntityModel(String name, List<FieldModel> fields) {}

    public record EnumModel(String name, List<String> values) {}

    public record FieldModel(
            String name,
            String type,
            boolean isArray,
            boolean isOptional,
            List<AttributeModel> attributes
    ) {}

    public record AttributeModel(String name, String argument) {}
}
