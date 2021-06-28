import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (1)) regB
 , Compute Equal regB reg0 regB
 , Compute Equal regB reg0 regB
 , Load (ImmValue (3)) regC
 , Push regC
 , Load (ImmValue (2)) regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Push regC
 , Load (ImmValue (3)) regC
 , Push regC
 , Load (ImmValue (2)) regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Load (ImmValue (3)) regD
 , Compute LtE regD reg0 regE
 , Branch regE (Rel (9))
 , Pop regE
 , Load (ImmValue (2)) regF
 , Compute Add regSP regF regF
 , Load (IndAddr regF) regF
 , Compute Equal regE regF regE
 , Compute And regC regE regC
 , Compute Decr regD reg0 regD
 , Jump (Rel (-9))
 , Load (ImmValue (3)) regD
 , Compute Add regSP regD regSP
 , Compute And regB regC regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]