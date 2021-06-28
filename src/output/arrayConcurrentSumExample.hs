import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
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
 , Load (ImmValue (10)) regB
 , Push regB
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (9)) regB
 , Push regB
 , Load (ImmValue (19)) regB
 , Load (ImmValue (-1)) regC
 , Compute Mul regC regB regC
 , Push regC
 , Load (ImmValue (3)) regD
 , Load (ImmValue (3)) regB
 , Compute Add regD regB regD
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Pop regC
 , WriteInstr regC (IndAddr regD)
 , Compute Decr regD reg0 regD
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (0)) regB
 , WriteInstr regB (DirAddr (7))
 , Load (ImmValue (38)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (69))
 , Load (ImmValue (1)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (0)) regB
 , Load (ImmValue (3)) regC
 , Compute Add regC regB regC
 , ReadInstr (IndAddr regC)
 , Receive regC
 , Load (ImmValue (1)) regB
 , Load (ImmValue (3)) regD
 , Compute Add regD regB regD
 , ReadInstr (IndAddr regD)
 , Receive regD
 , Compute Add regC regD regC
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Store regC (IndAddr regB)
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (7))
 , Receive regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Add regB regC regB
 , Load (ImmValue (7)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (72)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (103))
 , Load (ImmValue (1)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (2)) regB
 , Load (ImmValue (3)) regC
 , Compute Add regC regB regC
 , ReadInstr (IndAddr regC)
 , Receive regC
 , Load (ImmValue (3)) regB
 , Load (ImmValue (3)) regD
 , Compute Add regD regB regD
 , ReadInstr (IndAddr regD)
 , Receive regD
 , Compute Add regC regD regC
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Store regC (IndAddr regB)
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (7))
 , Receive regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Add regB regC regB
 , Load (ImmValue (7)) regC
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
 , ReadInstr (DirAddr (7))
 , Receive regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog,prog,prog]