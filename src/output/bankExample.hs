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
 , Load (ImmValue (20000)) regB
 , WriteInstr regB (DirAddr (4))
 , Load (ImmValue (13)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (26))
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , Load (ImmValue (2000)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (29)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (42))
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , Load (ImmValue (3000)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (45)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (58))
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , Load (ImmValue (10000)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , ReadInstr (DirAddr (1))
 , Receive regA
 , ReadInstr (DirAddr (2))
 , Receive regB
 , Compute Or regA regB regA
 , ReadInstr (DirAddr (3))
 , Receive regB
 , Compute Or regA regB regA
 , Branch regA (Rel (-8))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog,prog,prog,prog]