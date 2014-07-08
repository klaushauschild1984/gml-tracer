grammar GML;

LINE_COMMENT: '%' ~[\n]* '\n' -> skip;

WITHESPACE : [ \r\n\t]+ -> skip;

fragment LETTER: [a-zA-Z];

fragment DIGIT: [0-9];

TRUE: 'true';
FALSE: 'false';

ADDI:   'addi';
SUBI:   'subi';
MULI:   'muli';
DIVI:   'divi';
LESSI:  'lessi';
IF:     'if';
APPLY:  'apply';

IDENTIFIER: LETTER (LETTER | DIGIT | '-' | '_')*;

BINDER: '/' IDENTIFIER;

STRING: '"' [a-zA-Z0-9 .]+ '"';

NUMBER: '-'? DIGIT+ ('.' DIGIT+)?;

operator: ADDI
        | SUBI
        | MULI
        | DIVI
        | LESSI
        | IF
        | APPLY
        ;

bool: TRUE | FALSE;

identifier: IDENTIFIER;

binder: BINDER;

number: NUMBER;

string: STRING;

function: '{' tokenList '}';

array: '[' tokenList ']';

token: operator
     | binder
     | bool
     | identifier
     | number
     | string
     ;

tokenGroup: token
          | function
          | array
          ;

tokenList: tokenGroup*;
