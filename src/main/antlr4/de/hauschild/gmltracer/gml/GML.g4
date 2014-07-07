grammar GML;

NEWLINE: '\r'? '\n' -> channel(HIDDEN);

WITHESPACE : [ \r\t\n]+ -> channel(HIDDEN);

LINE_COMMENT: '%' ~[\n]* '\n' -> skip;

LETTER: [a-zA-Z];

DIGIT: [0-9];

NUMBER_CONTENT: '-'? [1-9] DIGIT* ('.' DIGIT+)?;

fragment STRING_CONTENT: [a-zA-Z0-9 .]+;

tokenList: tokenGroup*;

tokenGroup: token
          | function
          | array
          ;

function: '{' tokenList '}';

array: '[' tokenList ']';

operator: 'addi'
        | 'subi'
        | 'muli'
        | 'divi'
        | 'lessi'
        | 'if'
        | 'apply'
        ;

binder: '/' reference=identifier;

bool: 'true' | 'false';

token: operator
     | binder
     | bool
     | identifier
     | number
     | string
     ;

number: value=NUMBER_CONTENT;

identifier: LETTER (LETTER | DIGIT | '-' | '_')*;

string: '"' value=STRING_CONTENT '"';

