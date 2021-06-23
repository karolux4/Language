import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Pop regB
 , Store regB (DirAddr (0))
 , Pop regB
 , Store regB (DirAddr (1))
 , Pop regB
 , Store regB (DirAddr (2))
 , Pop regB
 , Store regB (DirAddr (3))
 , Pop regB
 , Store regB (DirAddr (4))
 , WriteInstr reg0 (DirAddr (1))
 , WriteInstr reg0 (DirAddr (2))
 , WriteInstr reg0 (DirAddr (3))
 , WriteInstr reg0 (DirAddr (4))
 , Load (ImmValue (8)) regB
 , Push regB
 , Load (ImmValue (7)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Pop regB
 , Store regB (DirAddr (5))
 , Pop regB
 , Store regB (DirAddr (6))
 , Pop regB
 , Store regB (DirAddr (7))
 , Pop regB
 , Store regB (DirAddr (8))
 , Pop regB
 , Store regB (DirAddr (9))
 , Load (ImmValue (6)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Pop regB
 , Store regB (DirAddr (10))
 , Pop regB
 , Store regB (DirAddr (11))
 , Pop regB
 , Store regB (DirAddr (12))
 , Pop regB
 , Store regB (DirAddr (13))
 , Pop regB
 , Store regB (DirAddr (14))
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Pop regB
 , Store regB (DirAddr (15))
 , Pop regB
 , Store regB (DirAddr (16))
 , Pop regB
 , Store regB (DirAddr (17))
 , Pop regB
 , Store regB (DirAddr (18))
 , Pop regB
 , Store regB (DirAddr (19))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (10)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute Equal regD regE regD
 , Compute And regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (5)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute Equal regD regE regD
 , Compute And regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute Equal regD regE regD
 , Compute And regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (15)) regB
 , Load (ImmValue (4)) regC
 , Compute Lt regC reg0 regD
 , Branch regD (Rel (6))
 , Pop regD
 , Store regD (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-6))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute Equal regD regE regD
 , Compute And regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (15)) regB
 , Load (ImmValue (4)) regC
 , Compute Lt regC reg0 regD
 , Branch regD (Rel (6))
 , Pop regD
 , Store regD (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-6))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute Equal regD regE regD
 , Compute And regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute NEq regD regE regD
 , Compute Or regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute Equal regD regE regD
 , Compute And regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regC reg0 regD
 , Branch regD (Rel (9))
 , Pop regD
 , Load (ImmValue (4)) regE
 , Compute Add regSP regE regE
 , Load (IndAddr regE) regE
 , Compute NEq regD regE regD
 , Compute Or regB regD regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-9))
 , Load (ImmValue (5)) regC
 , Compute Add regSP regC regSP
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (7))
 , Load (ImmValue (10)) regC
 , Compute Add regC regB regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-7))
 , Load (ImmValue (5)) regB
 , Compute LtE regB reg0 regC
 , Branch regC (Rel (5))
 , Pop regC
 , WriteInstr regC numberIO
 , Compute Decr regB reg0 regB
 , Jump (Rel (-5))
 , EndProg
 ]

main = run [prog]