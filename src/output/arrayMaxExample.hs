import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (68))
 , Load (ImmValue (11)) regB
 , Compute Sub regA regB regSP
 , Load (ImmValue (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (10)) regB
 , Compute Sub regA regB regB
 , Store regC (IndAddr regB)
 , Load (ImmValue (1)) regB
 , Load (ImmValue (11)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (11)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (10)) regC
 , Compute Lt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (56))
 , Load (ImmValue (11)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (10)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Compute Gt regC regB regC
 , Compute Equal regC reg0 regB
 , Branch regB (Abs (46))
 , Load (ImmValue (11)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (10)) regB
 , Compute Sub regA regB regB
 , Store regC (IndAddr regB)
 , Jump (Abs (46))
 , Nop
 , Load (ImmValue (11)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (11)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (15))
 , Nop
 , Load (ImmValue (10)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
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
 , Load (ImmValue (105)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1)) regC
 , Push regC
 , Load (ImmValue (3)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (10)) regC
 , Push regC
 , Load (ImmValue (9)) regC
 , Push regC
 , Load (ImmValue (4)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (19)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (100)) regC
 , Push regC
 , Load (ImmValue (45)) regC
 , Push regC
 , Load (ImmValue (99)) regC
 , Push regC
 , Load (ImmValue (10)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (171)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (6711)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (3)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (90)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (13)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (4)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (19)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (11)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (12)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Push regD
 , Load (ImmValue (100)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Load (ImmValue (2)) regC
 , Compute GtE regD reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regD regE regD
 , Compute GtE regC reg0 regF
 , Branch regF (Rel (3))
 , Load (ImmValue (-1)) regF
 , Compute Mul regC regF regC
 , Compute Mul regE regF regE
 , Push regE
 , Load (ImmValue (-1)) regE
 , Compute Incr regE reg0 regE
 , Compute GtE regD regC regF
 , Compute Sub regD regC regD
 , Branch regF (Rel (-3))
 , Pop regF
 , Compute Mul regE regF regE
 , Push regE
 , Load (ImmValue (8)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Load (ImmValue (5)) regC
 , Compute Mul regD regC regD
 , Push regD
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (223)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1)) regC
 , Load (ImmValue (9)) regD
 , Compute Mul regC regD regC
 , Load (ImmValue (4)) regD
 , Compute Sub regC regD regC
 , Push regC
 , Load (ImmValue (8)) regC
 , Load (ImmValue (2)) regD
 , Compute Mul regC regD regC
 , Push regC
 , Load (ImmValue (10)) regC
 , Load (ImmValue (0)) regD
 , Compute Mul regC regD regC
 , Push regC
 , Load (ImmValue (2)) regC
 , Load (ImmValue (124)) regD
 , Compute Mul regC regD regC
 , Push regC
 , Load (ImmValue (10000)) regC
 , Load (ImmValue (10000)) regD
 , Compute Sub regC regD regC
 , Push regC
 , Load (ImmValue (89)) regC
 , Load (ImmValue (2)) regD
 , Compute Mul regC regD regC
 , Push regC
 , Load (ImmValue (10)) regC
 , Load (ImmValue (10)) regD
 , Compute Mul regC regD regC
 , Load (ImmValue (10)) regD
 , Compute Mul regC regD regC
 , Push regC
 , Load (ImmValue (53)) regC
 , Push regC
 , Load (ImmValue (99)) regC
 , Load (ImmValue (9)) regD
 , Load (ImmValue (-1)) regE
 , Compute Mul regE regD regE
 , Compute Sub regC regE regC
 , Push regC
 , Load (ImmValue (1000000)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Load (ImmValue (-1)) regC
 , Compute Mul regC regD regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , EndProg
 ]

main = run [prog]