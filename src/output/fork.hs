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
 , Load (ImmValue (0)) regB
 , WriteInstr regB (DirAddr (3))
 , Load (ImmValue (0)) regB
 , WriteInstr regB (DirAddr (4))
 , Load (ImmValue (0)) regB
 , WriteInstr regB (DirAddr (5))
 , Push reg0
 , Push reg0
 , Load (ImmValue (22)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (27))
 , Load (ImmValue (2)) regB
 , Load (ImmValue (3)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (30)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (40))
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (4)) regB
 , Load (ImmValue (5)) regC
 , WriteInstr regB (IndAddr regC)
 , ReadInstr (DirAddr (1))
 , Receive regA
 , ReadInstr (DirAddr (2))
 , Receive regB
 , Compute Or regA regB regA
 , Branch regA (Rel (-5))
 , ReadInstr (DirAddr (3))
 , Receive regB
 , ReadInstr (DirAddr (4))
 , Receive regC
 , Compute Add regB regC regB
 , ReadInstr (DirAddr (5))
 , Receive regC
 , Compute Add regB regC regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (9)) regC
 , Compute Equal regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (70))
 , Load (ImmValue (2)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , Jump (Abs (71))
 , WriteInstr reg0 numberIO
 , Nop
 , EndProg
 ]

main = run [prog,prog,prog]