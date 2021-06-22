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
 , Load (ImmValue (2)) regB
 , WriteInstr regB (DirAddr (2))
 , Load (ImmValue (3)) regB
 , Store regB (DirAddr (0))
 , Load (ImmValue (15)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (32))
 , Load (ImmValue (4)) regB
 , Store regB (DirAddr (0))
 , ReadInstr (DirAddr (2))
 , Receive regB
 , Load (DirAddr (0)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (2)) regC
 , WriteInstr regB (IndAddr regC)
 , ReadInstr (DirAddr (2))
 , Receive regB
 , WriteInstr regB numberIO
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regA
 , Compute Equal regA reg0 regB
 , Branch regB (Rel (-3))
 , Jump (Ind regA)
 , ReadInstr (DirAddr (1))
 , Receive regA
 , Branch regA (Rel (-2))
 , Load (ImmValue (38)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (54))
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (2))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (2)) regC
 , WriteInstr regB (IndAddr regC)
 , ReadInstr (DirAddr (2))
 , Receive regB
 , WriteInstr regB numberIO
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (3)) regB
 , Store regB (DirAddr (1))
 , Load (ImmValue (5)) regB
 , Store regB (DirAddr (2))
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (2))
 , Receive regB
 , Load (DirAddr (0)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (2)) regC
 , WriteInstr regB (IndAddr regC)
 , ReadInstr (DirAddr (2))
 , Receive regB
 , WriteInstr regB numberIO
 , WriteInstr reg0 (DirAddr (0))
 , EndProg
 ]

main = run [prog,prog]