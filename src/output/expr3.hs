import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (1)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (3)) regB
 , Load (ImmValue (8)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (2)) regC
 , Compute GtE regB reg0 regD
 , Branch regD (Rel (3))
 , Load (ImmValue (-1)) regD
 , Compute Mul regB regD regB
 , Compute GtE regC reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regC regE regC
 , Compute Mul regD regE regD
 , Push regD
 , Load (ImmValue (-1)) regD
 , Compute Incr regD reg0 regD
 , Compute GtE regB regC regE
 , Compute Sub regB regC regB
 , Branch regE (Rel (-3))
 , Pop regE
 , Compute Mul regD regE regD
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Store regD (IndAddr regB)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]