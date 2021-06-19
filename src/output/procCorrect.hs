import Sprockell

prog :: [Instruction]
prog = [ 
   Load (ImmValue 3) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 3) regB
 , Load (ImmValue 5) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 3) regB
 , Load (ImmValue 2) regB
 , EndProg
 ]

main = run [prog]
