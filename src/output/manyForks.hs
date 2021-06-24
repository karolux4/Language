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
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , Load (ImmValue (1)) regB
 , WriteInstr regB (DirAddr (4))
 , Load (ImmValue (16)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (55))
 , Load (ImmValue (19)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (28))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , Load (ImmValue (31)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (40))
 , Load (ImmValue (4)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , ReadInstr (DirAddr (2))
 , Receive regA
 , ReadInstr (DirAddr (3))
 , Receive regB
 , Compute Or regA regB regA
 , Branch regA (Rel (-5))
 , Load (ImmValue (2)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , ReadInstr (DirAddr (1))
 , Receive regA
 , Branch regA (Rel (-2))
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (79))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (60))
 , Nop
 , Load (ImmValue (1)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (86)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (125))
 , Load (ImmValue (89)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (98))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , Load (ImmValue (101)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (110))
 , Load (ImmValue (4)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , ReadInstr (DirAddr (2))
 , Receive regA
 , ReadInstr (DirAddr (3))
 , Receive regB
 , Compute Or regA regB regA
 , Branch regA (Rel (-5))
 , Load (ImmValue (2)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , Load (ImmValue (5)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (148))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (129))
 , Nop
 , ReadInstr (DirAddr (1))
 , Receive regA
 , Branch regA (Rel (-2))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (158)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (179))
 , Load (ImmValue (161)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (166))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (169)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (174))
 , Load (ImmValue (4)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (2)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (5)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (202))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (183))
 , Nop
 , EndProg
 ]

main = run [prog,prog,prog,prog]