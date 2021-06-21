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
 , Jump (Abs (29))
 , Load (ImmValue (4)) regC
 , Store regC (DirAddr (0))
 , TestAndSet (DirAddr (0))
 , Receive regC
 , Branch regC (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (2))
 , Receive regD
 , Load (DirAddr (0)) regE
 , Compute Sub regD regE regD
 , Load (ImmValue (2)) regE
 , WriteInstr regD (IndAddr regE)
 , WriteInstr reg0 (DirAddr (0))
 , EndProg
 , TestAndSet (DirAddr (0))
 , Receive regD
 , Branch regD (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (2))
 , Receive regE
 , Load (DirAddr (0)) regF
 , Compute Sub regE regF regE
 , Load (ImmValue (2)) regF
 , WriteInstr regE (IndAddr regF)
 , WriteInstr reg0 (DirAddr (0))
 , EndProg
 ]

main = run [prog,prog]