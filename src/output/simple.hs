import Sprockell

prog :: [Instruction]
prog = [ 
   Load (ImmValue 3) regB
 , WriteInstr regB (DirAddr 0)
 , ReadInstr (DirAddr 0)
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue 2) regB
 , Store regB (DirAddr 0)
 , Load (DirAddr 0) regB
 , ReadInstr (DirAddr 0)
 , Receive regC
 , Compute Mul regB regC regB
 , Store regB (DirAddr 1)
 , Load (ImmValue 1) regB
 , Compute Equal regB reg0 regB
 , Store regB (DirAddr 2)
 , Load (ImmValue 1) regB
 , Compute And regB reg0 regB
 , Store regB (DirAddr 3)
 , Load (ImmValue 1) regB
 , Compute Or regB reg0 regB
 , Store regB (DirAddr 4)
 , Store reg0 (DirAddr 5)
 , Load (DirAddr 1) regB
 , Load (DirAddr 1) regC
 , Compute Mul regB regC regB
 , Load (DirAddr 1) regC
 , Compute Mul regB regC regB
 , Load (ImmValue 5) regC
 , Store regB (IndAddr regC)
 , ReadInstr (DirAddr 0)
 , Receive regB
 , Load (ImmValue 0) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs 58)
 , Load (ImmValue 3) regB
 , Store regB (DirAddr 6)
 , ReadInstr (DirAddr 0)
 , Receive regB
 , Load (ImmValue 1) regC
 , Compute Sub regB regC regB
 , Load (ImmValue 0) regC
 , WriteInstr regB (IndAddr regC)
 , Load (DirAddr 6) regB
 , WriteInstr regB numberIO
 , ReadInstr (DirAddr 0)
 , Receive regB
 , Load (ImmValue 2) regC
 , Compute Equal regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs 54)
 , Load (DirAddr 1) regB
 , WriteInstr regB numberIO
 , Jump (Abs 56)
 , Load (DirAddr 2) regB
 , WriteInstr regB numberIO
 , Nop
 , Jump (Abs 29)
 , Nop
 , EndProg
 ]

main = run [prog]
