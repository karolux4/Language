import Sprockell

prog :: [Instruction]
prog = [ 
   Load (ImmValue 0) regB
 , Load (ImmValue 0) regB
 , Load (ImmValue 0) regB
 , Store reg0 (DirAddr 0)
 , Store reg0 (DirAddr 1)
 , Load (ImmValue 2) regB
 , Load (ImmValue 3) regB
 , Load (ImmValue 4) regB
 , Load (ImmValue 9) regB
 , EndProg
 ]

main = run [prog]
