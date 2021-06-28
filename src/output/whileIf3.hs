import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
 , Compute Equal reg0 reg0 regB
 , Branch regB (Abs (11))
 , Load (ImmValue (2)) regB
 , WriteInstr regB numberIO
 , Jump (Abs (6))
 , Nop
 , Load (ImmValue (1)) regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]