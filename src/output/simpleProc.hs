import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (62))
 , Load (ImmValue (100)) regB
 , Push regB
 , Load (ImmValue (0)) regB
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
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , Load (ImmValue (24)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute GtE regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (54))
 , Load (ImmValue (4)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , WriteInstr regC numberIO
 , Load (ImmValue (4)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (4)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (26))
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
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , WriteInstr regB (DirAddr (1))
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (0)) regB
 , Load (ImmValue (2)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (1)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Compute Lt regC reg0 regD
 , Branch regD (Rel (6))
 , Pop regD
 , Store regD (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Compute Decr regC reg0 regC
 , Jump (Rel (-6))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (1)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , WriteInstr regC numberIO
 , Load (ImmValue (0)) regB
 , Load (ImmValue (2)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (1)) regC
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
 , Load (ImmValue (1)) regB
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
 , Load (ImmValue (2)) regB
 , Compute Add regSP regB regSP
 , Load (ImmValue (0)) regB
 , Load (ImmValue (2)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (3)) regC
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
 , Load (ImmValue (1)) regB
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
 , Load (ImmValue (2)) regB
 , Compute Add regSP regB regSP
 , Load (ImmValue (169)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (185)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Load (ImmValue (10)) regC
 , Push regC
 , Load (ImmValue (4)) regC
 , Push regC
 , Load (ImmValue (3)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (14))
 , EndProg
 ]

main = run [prog]