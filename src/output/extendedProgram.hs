import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (52))
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regSP
 , Load (ImmValue (0)) regB
 , Load (ImmValue (6)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (5)) regC
 , Compute LtE regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (44))
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (0)) regB
 , Compute Gt regC regB regC
 , Compute Equal regC reg0 regB
 , Branch regB (Abs (34))
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , WriteInstr regC numberIO
 , Jump (Abs (34))
 , Nop
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (6)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (7))
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
 , Branch regSprID (Rel (2))
 , Jump (Rel (6))
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (0)) regB
 , WriteInstr regB (DirAddr (3))
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Load (ImmValue (-1)) regC
 , Compute Mul regC regB regC
 , Push regC
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Load (ImmValue (-1)) regC
 , Compute Mul regC regB regC
 , Push regC
 , Load (ImmValue (10)) regB
 , Push regB
 , Load (ImmValue (9)) regB
 , Push regB
 , Load (ImmValue (4)) regD
 , Load (ImmValue (5)) regB
 , Compute Add regD regB regD
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Pop regC
 , WriteInstr regC (IndAddr regD)
 , Compute Decr regD reg0 regD
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (95)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (164))
 , Load (ImmValue (2)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (3)) regC
 , Compute Lt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (148))
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (4)) regC
 , Compute Add regC regB regC
 , ReadInstr (IndAddr regC)
 , Receive regC
 , Load (ImmValue (0)) regB
 , Compute Gt regC regB regC
 , Compute Equal regC reg0 regB
 , Branch regB (Abs (138))
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (4)) regD
 , Compute Add regD regC regD
 , ReadInstr (IndAddr regD)
 , Receive regD
 , Compute Add regB regD regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (138))
 , Nop
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (105))
 , Nop
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (3))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Add regB regC regB
 , Load (ImmValue (3)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (167)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (236))
 , Load (ImmValue (2)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (3)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (6)) regC
 , Compute Lt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (220))
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (4)) regC
 , Compute Add regC regB regC
 , ReadInstr (IndAddr regC)
 , Receive regC
 , Load (ImmValue (0)) regB
 , Compute Gt regC regB regC
 , Compute Equal regC reg0 regB
 , Branch regB (Abs (210))
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (4)) regD
 , Compute Add regD regC regD
 , ReadInstr (IndAddr regD)
 , Receive regD
 , Compute Add regB regD regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (210))
 , Nop
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (177))
 , Nop
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (3))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Add regB regC regB
 , Load (ImmValue (3)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , ReadInstr (DirAddr (1))
 , Receive regB
 , ReadInstr (DirAddr (2))
 , Receive regC
 , Compute Or regB regC regB
 , Branch regB (Rel (-5))
 , Load (ImmValue (259)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (0)) regC
 , Load (ImmValue (6)) regD
 , Compute GtE regC regD regD
 , Branch regD (Rel (8))
 , Load (ImmValue (4)) regD
 , Compute Add regD regC regD
 , ReadInstr (IndAddr regD)
 , Receive regD
 , Push regD
 , Compute Incr regC reg0 regC
 , Jump (Rel (-9))
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , ReadInstr (DirAddr (3))
 , Receive regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog,prog,prog]