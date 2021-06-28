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
 , Load (ImmValue (20000)) regB
 , WriteInstr regB (DirAddr (4))
 , Load (ImmValue (18)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (33))
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
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
 , Load (ImmValue (36)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (51))
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
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
 , Load (ImmValue (54)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (69))
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
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
 , Receive regB
 , ReadInstr (DirAddr (2))
 , Receive regC
 , Compute Or regB regC regB
 , ReadInstr (DirAddr (3))
 , Receive regC
 , Compute Or regB regC regB
 , Branch regB (Rel (-8))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog,prog,prog,prog]