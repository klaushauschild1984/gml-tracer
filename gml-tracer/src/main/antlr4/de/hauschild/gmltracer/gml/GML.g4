grammar GML;

tokenList: (tokenGroup | Whitespace)*;

tokenGroup: token
          | function
          | array
          ;

function: Whitespace* '{' Whitespace* tokenList Whitespace* '}' Whitespace*;

array: Whitespace* '[' Whitespace* tokenList Whitespace* ']' Whitespace*;

token: Operator
     | Binder
     | Bool
     | Identifier
     | Number
     | String
     ;

Operator: 'addi'
        | 'subi'
        | 'muli'
        | 'divi'
        | 'lessi'
        | 'if'
        | 'apply'
        ;

Identifier: Letter (Letter | Digit | '-' | '_')*;

Binder: '/' Identifier;

Bool: 'true'
    | 'false'
    ;

Number: '-'? ('1'..'9') Digit* ('.' Digit+)?;

String: '"' (Letter | Digit)+ '"';

Letter: ('a'..'z' | 'A'..'Z');

Digit: ('0'..'9');

Whitespace : (' ' | '\t' | '\r'? '\n');
