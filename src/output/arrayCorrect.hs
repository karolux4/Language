import Sprockell

prog :: [Instruction]
prog = [ 
   Load (ImmValue 3) regB
 , Load (ImmValue 0) regB
 , Load (ImmValue 0) regB
 , Load (ImmValue 5) regB
 , Load (ImmValue 1) regB
 , Load (ImmValue 1) regB
 , Load (ImmValue 0) regB
 , Load (ImmValue 1) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 0) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 0) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 1) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 2) regB
 , Load (ImmValue 1) regB
 , Load (ImmValue 0) regB
 , Load (ImmValue 1) regB
 , Load (ImmValue 1) regB
 , Load (ImmValue 1) regB
 , EndProg
 ]

main = run [prog]
