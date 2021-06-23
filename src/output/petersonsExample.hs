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
 , WriteInstr reg0 (DirAddr (3))
 , WriteInstr reg0 (DirAddr (4))
 , WriteInstr regB (DirAddr (5))
 , Load (ImmValue (0)) regC
 , WriteInstr regC (DirAddr (6))
 , Load (ImmValue (16)) regC
 , WriteInstr regC (DirAddr (1))
 , Jump (Abs (59))
 , Load (ImmValue (10)) regB
 , Store regB (DirAddr (0))
 , Load (DirAddr (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (56))
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
 , Branch regC (Abs (41))
 , Jump (Abs (29))
 , Nop
 , ReadInstr (DirAddr (6))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (6)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (3)) regB
 , WriteInstr reg0 (IndAddr regB)
 , Load (DirAddr (0)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Jump (Abs (18))
 , Nop
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (62)) regC
 , WriteInstr regC (DirAddr (2))
 , Jump (Abs (105))
 , Load (ImmValue (10)) regB
 , Store regB (DirAddr (0))
 , Load (DirAddr (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (102))
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
 , Branch regC (Abs (87))
 , Jump (Abs (75))
 , Nop
 , ReadInstr (DirAddr (6))
 , Receive regB
 , Load (ImmValue (2)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (6)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (4)) regB
 , WriteInstr reg0 (IndAddr regB)
 , Load (DirAddr (0)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Jump (Abs (64))
 , Nop
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , ReadInstr (DirAddr (1))
 , Receive regA
 , ReadInstr (DirAddr (2))
 , Receive regB
 , Compute Or regA regB regA
 , Branch regA (Rel (-5))
 , ReadInstr (DirAddr (6))
 , Receive regC
 , WriteInstr regC numberIO
 , EndProg
 ]

main = run [prog,prog,prog]