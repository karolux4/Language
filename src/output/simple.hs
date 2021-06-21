import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Load (ImmValue (3)) regB
 , WriteInstr regB (DirAddr (1))
 , ReadInstr (DirAddr (1))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (2)) regB
 , Store regB (DirAddr (0))
 , Load (DirAddr (0)) regB
 , ReadInstr (DirAddr (1))
 , Receive regC
 , Compute Mul regB regC regB
 , Store regB (DirAddr (1))
 , Load (ImmValue (1)) regB
 , Compute Equal regB reg0 regB
 , Store regB (DirAddr (2))
 , Load (ImmValue (1)) regB
 , Compute And regB reg0 regB
 , Store regB (DirAddr (3))
 , Load (ImmValue (1)) regB
 , Compute Or regB reg0 regB
 , Store regB (DirAddr (4))
 , Store reg0 (DirAddr (5))
 , Load (DirAddr (1)) regB
 , Load (DirAddr (1)) regC
 , Compute Mul regB regC regB
 , Load (DirAddr (1)) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (5)) regC
 , Store regB (IndAddr regC)
 , ReadInstr (DirAddr (1))
 , Receive regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (67))
 , Load (ImmValue (3)) regB
 , Store regB (DirAddr (6))
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (1))
 , Receive regC
 , Load (ImmValue (1)) regD
 , Compute Sub regC regD regC
 , Load (ImmValue (1)) regD
 , WriteInstr regC (IndAddr regD)
 , WriteInstr reg0 (DirAddr (0))
 , Load (DirAddr (6)) regC
 , WriteInstr regC numberIO
 , ReadInstr (DirAddr (1))
 , Receive regC
 , Load (ImmValue (2)) regD
 , Compute Equal regC regD regC
 , Compute Equal regC reg0 regD
 , Branch regD (Abs (62))
 , Load (DirAddr (1)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , WriteInstr regD numberIO
 , Jump (Abs (65))
 , Load (DirAddr (2)) regC
 , Compute Equal regC reg0 regC
 , WriteInstr regC numberIO
 , Nop
 , Jump (Abs (30))
 , Nop
 , EndProg
 ]

main = run [prog]