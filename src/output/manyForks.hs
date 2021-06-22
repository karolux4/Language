import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Branch regSprID (Rel (2))
 , Jump (Rel (6))
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , Load (ImmValue (1)) regB
 , WriteInstr regB (DirAddr (4))
 , Load (ImmValue (13)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (52))
 , Load (ImmValue (16)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (25))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , Load (ImmValue (28)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (37))
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
 , Store regB (DirAddr (0))
 , Load (DirAddr (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (71))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (DirAddr (0)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Jump (Abs (57))
 , Nop
 , Load (ImmValue (1)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (78)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (117))
 , Load (ImmValue (81)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (90))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , Load (ImmValue (93)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (102))
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
 , Store regB (IndAddr regC)
 , Load (DirAddr (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (134))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (DirAddr (0)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Jump (Abs (120))
 , Nop
 , ReadInstr (DirAddr (1))
 , Receive regA
 , Branch regA (Rel (-2))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (144)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (165))
 , Load (ImmValue (147)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (152))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (155)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (160))
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
 , Store regB (IndAddr regC)
 , Load (DirAddr (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (182))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (DirAddr (0)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Jump (Abs (168))
 , Nop
 , EndProg
 ]

main = run [prog,prog,prog,prog]