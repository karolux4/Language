import Sprockell

prog :: [Instruction]
prog = [ 
   Compute Add 1 2 3
 , Load (DirAddr 3) 3
 , EndProg
 ]

main = run [prog]
