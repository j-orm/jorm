package dev.jorm.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testBasicSchemaParsing() {
        String schema = """
                config {
                    database = "postgresql"
                }

                model User {
                    id Int @id @autoincrement
                    name String
                    email String @unique
                    role Role
                }

                enum Role {
                    USER
                    ADMIN
                }
                """;

        JormLexer lexer = new JormLexer(CharStreams.fromString(schema));
        JormParser parser = new JormParser(new CommonTokenStream(lexer));
        
        JormParser.SchemaContext tree = parser.schema();
        
        JormVisitorImpl visitor = new JormVisitorImpl();
        SchemaModel model = visitor.visitSchema(tree);

        // Assert Config
        assertEquals(1, model.config().size());
        assertEquals("database", model.config().get(0).key());
        assertEquals("postgresql", model.config().get(0).value());

        // Assert Enum
        assertEquals(1, model.enums().size());
        assertEquals("Role", model.enums().get(0).name());
        assertEquals(2, model.enums().get(0).values().size());

        // Assert Model
        assertEquals(1, model.models().size());
        assertEquals("User", model.models().get(0).name());
        assertEquals(4, model.models().get(0).fields().size());
        
        SchemaModel.FieldModel idField = model.models().get(0).fields().get(0);
        assertEquals("id", idField.name());
        assertEquals("Int", idField.type());
        assertEquals(2, idField.attributes().size());
        assertEquals("id", idField.attributes().get(0).name());
        assertEquals("autoincrement", idField.attributes().get(1).name());
    }
}
