import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
 , Compute NEq reg0 reg0 reg0
 , Compute Equal reg0 reg0 regB
 , Branch regB (Abs (12))
 , Load (ImmValue (10)) regB
 , WriteInstr regB numberIO
 , Jump (Abs (14))
 , Load (ImmValue (100)) regB
 , WriteInstr regB numberIO
 , Nop
 , EndProg
 ]

main = run [prog]