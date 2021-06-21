import Sprockell

prog :: [Instruction]
prog = [ 
   Load (ImmValue 3) regB
 , Store regB (DirAddr 0)
 , Load (ImmValue 2) regB
 , Store regB (DirAddr 1)
 , Load (DirAddr 1) regB
 , Load (DirAddr 0) regC
 , Compute Mul regB regC regB
 , Store regB (DirAddr 2)
 , Load (ImmValue 1) regB
 , Compute Equal regB reg0 regB
 , Store regB (DirAddr 3)
 , Load (ImmValue 1) regB
 , Compute And regB reg0 regB
 , Store regB (DirAddr 4)
 , Load (ImmValue 1) regB
 , Compute Or regB reg0 regB
 , Store regB (DirAddr 5)
 , Store reg0 (DirAddr 6)
 , Load (DirAddr 2) regB
 , Load (DirAddr 2) regC
 , Compute Mul regB regC regB
 , Load (DirAddr 2) regC
 , Compute Mul regB regC regB
 , Load (ImmValue 6) regC
 , Store regB (IndAddr regC)
 , Load (DirAddr 0) regB
 , Load (ImmValue 0) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs 51)
 , Load (ImmValue 3) regB
 , Store regB (DirAddr 7)
 , Load (DirAddr 0) regB
 , Load (ImmValue 1) regC
 , Compute Sub regB regC regB
 , Load (ImmValue 0) regC
 , Store regB (IndAddr regC)
 , Load (DirAddr 7) regB
 , WriteInstr regB numberIO
 , Load (DirAddr 0) regB
 , Load (ImmValue 2) regC
 , Compute Equal regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs 47)
 , Load (DirAddr 2) regB
 , WriteInstr regB numberIO
 , Jump (Abs 49)
 , Load (DirAddr 3) regB
 , WriteInstr regB numberIO
 , Nop
 , Jump (Abs 25)
 , Nop
 , EndProg
 ]

main = run [prog]
