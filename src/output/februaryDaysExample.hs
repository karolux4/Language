import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (129))
 , Load (ImmValue (3)) regB
 , Compute Sub regA regB regSP
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (4)) regC
 , Compute GtE regB reg0 regD
 , Branch regD (Rel (3))
 , Load (ImmValue (-1)) regD
 , Compute Mul regB regD regB
 , Compute GtE regC reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regC regE regC
 , Compute Mul regD regE regD
 , Push regD
 , Load (ImmValue (-1)) regD
 , Compute Incr regD reg0 regD
 , Compute GtE regB regC regE
 , Compute Sub regB regC regB
 , Branch regE (Rel (-3))
 , Pop regE
 , Compute Mul regD regE regD
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Store regD (IndAddr regB)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (100)) regC
 , Compute GtE regB reg0 regD
 , Branch regD (Rel (3))
 , Load (ImmValue (-1)) regD
 , Compute Mul regB regD regB
 , Compute GtE regC reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regC regE regC
 , Compute Mul regD regE regD
 , Push regD
 , Load (ImmValue (-1)) regD
 , Compute Incr regD reg0 regD
 , Compute GtE regB regC regE
 , Compute Sub regB regC regB
 , Branch regE (Rel (-3))
 , Pop regE
 , Compute Mul regD regE regD
 , Load (ImmValue (2)) regB
 , Compute Sub regA regB regB
 , Store regD (IndAddr regB)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (400)) regC
 , Compute GtE regB reg0 regD
 , Branch regD (Rel (3))
 , Load (ImmValue (-1)) regD
 , Compute Mul regB regD regB
 , Compute GtE regC reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regC regE regC
 , Compute Mul regD regE regD
 , Push regD
 , Load (ImmValue (-1)) regD
 , Compute Incr regD reg0 regD
 , Compute GtE regB regC regE
 , Compute Sub regB regC regB
 , Branch regE (Rel (-3))
 , Pop regE
 , Compute Mul regD regE regD
 , Load (ImmValue (3)) regB
 , Compute Sub regA regB regB
 , Store regD (IndAddr regB)
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (4)) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Equal regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (119))
 , Load (ImmValue (2)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (100)) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Equal regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (115))
 , Load (ImmValue (3)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (400)) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Equal regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (111))
 , Load (ImmValue (29)) regB
 , WriteInstr regB numberIO
 , Jump (Abs (113))
 , Load (ImmValue (28)) regB
 , WriteInstr regB numberIO
 , Nop
 , Jump (Abs (117))
 , Load (ImmValue (29)) regB
 , WriteInstr regB numberIO
 , Nop
 , Jump (Abs (121))
 , Load (ImmValue (28)) regB
 , WriteInstr regB numberIO
 , Nop
 , Compute Incr regA reg0 regB
 , Compute Incr regB reg0 regC
 , Compute Add regC reg0 regSP
 , Compute Incr regSP reg0 regSP
 , Load (IndAddr regB) regA
 , Load (IndAddr regC) regC
 , Jump (Ind regC)
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (142)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (2012)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (150)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1055)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (158)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (2000)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (166)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1700)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (174)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1040)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , EndProg
 ]

main = run [prog]