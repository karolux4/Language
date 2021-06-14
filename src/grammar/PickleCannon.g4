grammar PickleCannon;

import Vocab;

/** Program (functions are declared before main program) */
program: proc* CANNON block EOF
	   ;

/** Procedure declaration */
proc: PICKLE ID LPAR (pars (COMMA pars)* )? RPAR block
	;

/** Procedure parameters */
pars : type ID			#varArg
     | type ID LSQ RSQ	#arrayArg
     ;

/** Block */
block: LBRACE stat* RBRACE
     ; 

/** Statements */
stat: type ID (ASSIGN expr)? SEMI				#simpleVarStat
	| type ID LSQ expr RSQ (ASSIGN expr)? SEMI	#arrayVarStat
	| target ASSIGN expr SEMI					#assignStat
	| IF LPAR expr RPAR block (ELSE block)? 	#ifStat 
	| WHILE LPAR expr RPAR block			    #whileStat 
	| FORK block								#forkStat
	| JOIN SEMI									#joinStat
	| SYNC block								#syncStat
	| block										#blockStat
	| PRINT LPAR expr RPAR SEMI					#printStat
	| ID args SEMI								#callStat
	;
	
/** Target of an assignment */
target: ID				 #idTarget
	  | ID LSQ expr RSQ  #arrayTarget;

 /** Arguments of a call. */   
args: LPAR (expr (COMMA expr)*)? RPAR
    ;

/** Expression */
expr: prfOp expr					#prfExpr
    | expr multOp expr 				#multExpr
    | expr plusOp expr 				#plusExpr
    | expr compOp expr				#compExpr
    | expr boolOp expr 				#boolExpr
    | LPAR expr RPAR				#parExpr
    | ID							#idExpr
    | NUM							#numExpr
    | TRUE							#trueExpr
    | FALSE							#falseExpr
    | ID LSQ expr RSQ  				#indexExpr
    | LSQ (expr (COMMA expr)*)? RSQ #arrayExpr
    ;

/** Prefix operator */
prfOp: MINUS | NOT;

/** Multiplicative operator */
multOp: STAR | SLASH;

/** Additive operator */
plusOp: PLUS | MINUS;

/** Boolean operator */
boolOp: AND | OR;

/** Comparison operator */
compOp: LE | LT | GE | GT | EQ | NE;

/** Data type */
type: INT #intType
    | BOOL #boolType;