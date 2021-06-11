lexer grammar Vocab;


// Keywords
BOOL: 	 B O O L;
INT: 	 I N T;
ELSE:    E L S E ;
FALSE:   F A L S E ;
FORK:    F O R K;
IF:      I F ;
JOIN:    J O I N;
PRINT:   P R I N T;
PICKLE:  P I C K L E;
CANNON:  C A N N O N;
SYNC:    S Y N C;
TRUE:    T R U E ;
WHILE:   W H I L E ;



// Operators
AND:    '&&';
OR:     '||';
ASSIGN: '=';
COMMA:  ',';
EQ:     '==';
GE:     '>=';
GT:     '>';
LE:     '<=';
LBRACE: '{';
LPAR:   '(';
LSQ:    '[';
LT:     '<';
MINUS:  '-';
NE:     '!=';
NOT:    '!';
PLUS:   '+';
RBRACE: '}';
RPAR:   ')';
RSQ:    ']';
SEMI:   ';';
SLASH:  '/';
STAR:   '*';

// Content-bearing token types
ID: LETTER (LETTER | DIGIT)*;
NUM: DIGIT (DIGIT)*;

fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];

// ignore whitespace
WS : [ \t\n\r] -> skip;

fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment J: [jJ];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment Q: [qQ];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment V: [vV];
fragment W: [wW];
fragment X: [xX];
fragment Y: [yY];
fragment Z: [zZ];