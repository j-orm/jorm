package dev.jorm.parser;

import java.util.ArrayList;
import dev.jorm.core.model.SchemaModel;
import java.util.List;

public class JormVisitorImpl extends JormBaseVisitor<Object> {

    @Override
    public SchemaModel visitSchema(JormParser.SchemaContext ctx) {
        List<SchemaModel.ConfigEntry> configs = new ArrayList<>();
        List<SchemaModel.EntityModel> models = new ArrayList<>();
        List<SchemaModel.EnumModel> enums = new ArrayList<>();

        for (JormParser.BlockContext blockCtx : ctx.block()) {
            if (blockCtx.configBlock() != null) {
                configs.addAll((List<SchemaModel.ConfigEntry>) visitConfigBlock(blockCtx.configBlock()));
            } else if (blockCtx.modelBlock() != null) {
                models.add((SchemaModel.EntityModel) visitModelBlock(blockCtx.modelBlock()));
            } else if (blockCtx.enumBlock() != null) {
                enums.add((SchemaModel.EnumModel) visitEnumBlock(blockCtx.enumBlock()));
            }
        }

        return new SchemaModel(configs, models, enums);
    }

    @Override
    public List<SchemaModel.ConfigEntry> visitConfigBlock(JormParser.ConfigBlockContext ctx) {
        List<SchemaModel.ConfigEntry> entries = new ArrayList<>();
        for (JormParser.ConfigEntryContext entryCtx : ctx.configEntry()) {
            entries.add((SchemaModel.ConfigEntry) visitConfigEntry(entryCtx));
        }
        return entries;
    }

    @Override
    public SchemaModel.ConfigEntry visitConfigEntry(JormParser.ConfigEntryContext ctx) {
        String key = ctx.configKey().getText();
        String value = ctx.STRING_LIT().getText();
        // Remove quotes
        value = value.substring(1, value.length() - 1);
        return new SchemaModel.ConfigEntry(key, value);
    }

    @Override
    public SchemaModel.EntityModel visitModelBlock(JormParser.ModelBlockContext ctx) {
        String name = ctx.ID().getText();
        List<SchemaModel.FieldModel> fields = new ArrayList<>();
        for (JormParser.FieldContext fieldCtx : ctx.field()) {
            fields.add((SchemaModel.FieldModel) visitField(fieldCtx));
        }
        return new SchemaModel.EntityModel(name, fields);
    }

    @Override
    public SchemaModel.FieldModel visitField(JormParser.FieldContext ctx) {
        String name = ctx.ID().getText();
        String type = ctx.fieldType().ID().getText();
        boolean isArray = ctx.fieldType().isArray != null;
        boolean isOptional = ctx.fieldType().isOptional != null;

        List<SchemaModel.AttributeModel> attributes = new ArrayList<>();
        for (JormParser.AttributeContext attrCtx : ctx.attribute()) {
            attributes.add((SchemaModel.AttributeModel) visitAttribute(attrCtx));
        }

        return new SchemaModel.FieldModel(name, type, isArray, isOptional, attributes);
    }

    @Override
    public SchemaModel.AttributeModel visitAttribute(JormParser.AttributeContext ctx) {
        String name = ctx.ID().getText();
        String argument = null;
        if (ctx.attributeArg() != null) {
            argument = ctx.attributeArg().getText();
            if (ctx.attributeArg().STRING_LIT() != null) {
                // Remove quotes
                argument = argument.substring(1, argument.length() - 1);
            }
        }
        return new SchemaModel.AttributeModel(name, argument);
    }

    @Override
    public SchemaModel.EnumModel visitEnumBlock(JormParser.EnumBlockContext ctx) {
        String name = ctx.ID().getText();
        List<String> values = new ArrayList<>();
        for (JormParser.EnumValueContext valCtx : ctx.enumValue()) {
            values.add(valCtx.ID().getText());
        }
        return new SchemaModel.EnumModel(name, values);
    }
}
