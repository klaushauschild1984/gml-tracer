grammar GML;

LINE_COMMENT: '%' ~[\n]* '\n' -> skip;

WITHESPACE : [ \r\n\t]+ -> skip;

fragment LETTER: [a-zA-Z];

fragment DIGIT: [0-9];

TRUE: 'true';
FALSE: 'false';

// control operators
IF:    'if';
APPLY: 'apply';

// number operators
ADDI:   'addi';
ADDF:   'addf';
ACOS:   'acos';
ASIN:   'asin';
CLAMPF: 'clampf';
COS:    'cos';
DIVI:   'divi';
DIVF:   'divf';
EQI:    'eqi';
EQF:    'eqf';
FLOOR:  'floor';
FRAC:   'frac';
LESSI:  'lessi';
LESSF:  'lessf';
MODI:   'modi';
MULI:   'muli';
MULF:   'mulf';
NEGI:   'negi';
NEGF:   'negf';
REAL:   'real';
SIN:    'sin';
SQRT:   'sqrt';
SUBI:   'subi';
SUBF:   'subf';

// point operators
GETX:  'getx';
GETY:  'gety';
GETZ:  'getZ';
POINT: 'point';

// array operators
GET:    'get';
LENGTH: 'length';

// geometric primitive operators
SHPERE:   'sphere';
CUBE:     'cube';
CYLINDER: 'cylinder';
CONE:     'cone';
PLANE:    'plane';

// transformation operators
TRANSLATE: 'translate';
SCALE:     'scale';
USCALE:    'uscale';
ROTATEX:   'rotatex';
ROTATEY:   'rotatey';
ROTATEZ:   'rotatez';

// light operators
LIGHT:      'light';
POINTLIGHT: 'pointlight';
SPOTLIGHT:  'spotlight';

// constructive solid geometry operators
UNION:      'union';
INTERSECT:  'intersect';
DIFFERENCE: 'difference';

// rendering operator
RENDER: 'render';

IDENTIFIER: LETTER (LETTER | DIGIT | '-' | '_')*;

BINDER: '/' IDENTIFIER;

STRING : '"' ~('\r' | '\n' | '"')* '"';

NUMBER: '-'? DIGIT+ ('.' DIGIT+)?;

operator: IF
        | APPLY
        | ADDI
        | ADDF
        | ACOS
        | ASIN
        | CLAMPF
        | COS
        | DIVI
        | DIVF
        | EQI
        | EQF
        | FLOOR
        | FRAC
        | LESSI
        | LESSF
        | MODI
        | MULI
        | MULF
        | NEGI
        | NEGF
        | REAL
        | SIN
        | SQRT
        | SUBI
        | SUBF
        | GETX
        | GETY
        | GETZ
        | POINT
        | GET
        | LENGTH
        | SHPERE
        | CUBE
        | CYLINDER
        | CONE
        | PLANE
        | TRANSLATE
        | SCALE
        | USCALE
        | ROTATEX
        | ROTATEY
        | ROTATEZ
        | LIGHT
        | POINTLIGHT
        | SPOTLIGHT
        | UNION
        | INTERSECT
        | DIFFERENCE
        | RENDER
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
