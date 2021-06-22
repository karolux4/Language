import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Load (ImmValue (3)) regB
 , Store regB (DirAddr (0))
 , Load (DirAddr (0)) regB
 , WriteInstr regB numberIO
 , Load (ImmValue (2)) regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Load (DirAddr (0)) regB
 , WriteInstr regB numberIO
 , Load (ImmValue (4)) regB
 , Store regB (DirAddr (1))
 , Load (DirAddr (1)) regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Load (DirAddr (0)) regB
 , WriteInstr regB numberIO
 , Load (ImmValue (2)) regB
 , Load (DirAddr (0)) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Load (DirAddr (0)) regB
 , WriteInstr regB numberIO
 , Load (ImmValue (2)) regB
 , Load (ImmValue (6)) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (3)) regC
 , Load (ImmValue (9)) regD
 , Compute Mul regC regD regC
 , Load (ImmValue (2)) regD
 , Compute Mul regC regD regC
 , Compute Add regB regC regB
 , Load (ImmValue (2)) regC
 , Load (ImmValue (3)) regD
 , Compute Sub regC regD regC
 , Load (ImmValue (2)) regD
 , Compute Mul regC regD regC
 , Compute Sub regB regC regB
 , Load (ImmValue (3)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Compute Sub regB regD regB
 , Load (ImmValue (0)) regC
 , Store regB (IndAddr regC)
 , Load (DirAddr (0)) regB
 , WriteInstr regB numberIO
 , Store reg0 (DirAddr (2))
 , Load (DirAddr (2)) regB
 , WriteInstr regB numberIO
 , Load (DirAddr (0)) regB
 , Load (ImmValue (3)) regC
 , Compute Gt regB regC regB
 , Load (ImmValue (2)) regC
 , Store regB (IndAddr regC)
 , Load (DirAddr (2)) regB
 , WriteInstr regB numberIO
 , Load (ImmValue (1)) regB
 , Compute And regB reg0 regB
 , Compute Or reg0 regB reg0
 , Load (ImmValue (2)) regB
 , Store reg0 (IndAddr regB)
 , Load (DirAddr (2)) regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]