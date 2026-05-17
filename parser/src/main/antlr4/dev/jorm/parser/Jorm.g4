grammar Jorm;

// Lexer Rules
WHITESPACE: [ \t\r\n]+ -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;

CONFIG: 'config';
MODEL: 'model';
ENUM: 'enum';

ID: [a-zA-Z_][a-zA-Z0-9_]*;
STRING_LIT: '"' (~["])* '"';

// Parser Rules
schema: block* EOF;

block: configBlock | modelBlock | enumBlock;

configBlock: CONFIG '{' configEntry* '}';
configEntry: ID '=' STRING_LIT;

modelBlock: MODEL ID '{' field* '}';
field: ID fieldType attribute*;

fieldType: ID isArray='[]'? isOptional='?'?;

attribute: '@' ID ('(' attributeArg ')')?;
attributeArg: ID | STRING_LIT;

enumBlock: ENUM ID '{' enumValue* '}';
enumValue: ID;