import Sprockell

prog :: [Instruction]
prog = [ 
   Compute Add regSprID regA regB
 , Load (DirAddr 3) regB
 , EndProg
 ]

main = run [prog]
