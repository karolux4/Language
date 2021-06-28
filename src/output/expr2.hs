import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (1)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (7)) regB
 , Load (ImmValue (8)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (2)) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (9)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (3)) regC
 , Load (ImmValue (2)) regD
 , Compute Mul regC regD regC
 , Compute Add regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]