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
 , WriteInstr reg0 (DirAddr (3))
 , WriteInstr reg0 (DirAddr (4))
 , WriteInstr reg0 (DirAddr (5))
 , Load (ImmValue (0)) regB
 , WriteInstr regB (DirAddr (6))
 , Load (ImmValue (21)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (73))
 , Load (ImmValue (1)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (10)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (70))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (3)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (1)) regB
 , Load (ImmValue (5)) regC
 , WriteInstr regB (IndAddr regC)
 , ReadInstr (DirAddr (4))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Equal regB regC regB
 , ReadInstr (DirAddr (5))
 , Receive regC
 , Load (ImmValue (1)) regD
 , Compute Equal regC regD regC
 , Compute And regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (52))
 , Jump (Abs (40))
 , Nop
 , ReadInstr (DirAddr (6))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (6)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (3)) regB
 , WriteInstr reg0 (IndAddr regB)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (27))
 , Nop
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (76)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (128))
 , Load (ImmValue (1)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (10)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (125))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
 , WriteInstr regB (IndAddr regC)
 , ReadInstr (DirAddr (3))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Equal regB regC regB
 , ReadInstr (DirAddr (5))
 , Receive regC
 , Load (ImmValue (0)) regD
 , Compute Equal regC regD regC
 , Compute And regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (107))
 , Jump (Abs (95))
 , Nop
 , ReadInstr (DirAddr (6))
 , Receive regB
 , Load (ImmValue (2)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (6)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (4)) regB
 , WriteInstr reg0 (IndAddr regB)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (82))
 , Nop
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , ReadInstr (DirAddr (1))
 , Receive regB
 , ReadInstr (DirAddr (2))
 , Receive regC
 , Compute Or regB regC regB
 , Branch regB (Rel (-5))
 , ReadInstr (DirAddr (6))
 , Receive regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog,prog,prog]