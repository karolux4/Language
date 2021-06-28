import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (6)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (10)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (9)) regB
 , Push regB
 , Load (ImmValue (0)) regD
 , Compute Sub regA regD regD
 , Load (ImmValue (2)) regB
 , Compute Sub regD regB regD
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Pop regC
 , Store regC (IndAddr regD)
 , Compute Incr regD reg0 regD
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (10)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (9)) regB
 , Push regB
 , Load (ImmValue (3)) regD
 , Compute Sub regA regD regD
 , Load (ImmValue (2)) regB
 , Compute Sub regD regB regD
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Pop regC
 , Store regC (IndAddr regD)
 , Compute Incr regD reg0 regD
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (3)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (3)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (3)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (3)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (2)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute Equal regD regE regD
 , Compute And regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (3)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]