import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
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
 , WriteInstr reg0 (DirAddr (1))
 , WriteInstr reg0 (DirAddr (2))
 , WriteInstr reg0 (DirAddr (3))
 , WriteInstr reg0 (DirAddr (4))
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (7)) regB
 , Push regB
 , Load (ImmValue (8)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (6)) regB
 , Push regB
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
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (10)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
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
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (5)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
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
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
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
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (15)) regB
 , Compute Sub regA regB regB
 , Load (ImmValue (4)) regC
 , Compute Sub regB regC regB
 , Compute Lt regC reg0 regD
 , Branch regD (Rel (6))
 , Pop regD
 , Store regD (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-6))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
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
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (15)) regB
 , Compute Sub regA regB regB
 , Load (ImmValue (4)) regC
 , Compute Sub regB regC regB
 , Compute Lt regC reg0 regD
 , Branch regD (Rel (6))
 , Pop regD
 , Store regD (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-6))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
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
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (15)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
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
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
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
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (5)) regC
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
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (10)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
 , Load (ImmValue (123)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (10)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Compute Add regSP regB regC
 , Load (IndAddr regC) regC
 , WriteInstr regC numberIO
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (125)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (10)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (5)) regB
 , Compute Add regSP regB regSP
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
 , Load (ImmValue (123)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (10)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (4)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Compute Add regSP regB regC
 , Load (IndAddr regC) regC
 , WriteInstr regC numberIO
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (125)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (10)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (5)) regB
 , Compute Add regSP regB regSP
 , EndProg
 ]

main = run [prog]