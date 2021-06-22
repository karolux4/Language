import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Load (ImmValue (10)) regB
 , Load (ImmValue (3)) regC
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
 , Store regD (DirAddr (0))
 , Load (ImmValue (16)) regB
 , Load (ImmValue (-1)) regC
 , Compute Mul regC regB regC
 , Load (ImmValue (2)) regB
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regB regD
 , Compute GtE regC reg0 regB
 , Branch regB (Rel (3))
 , Load (ImmValue (-1)) regB
 , Compute Mul regC regB regC
 , Compute GtE regD reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regD regE regD
 , Compute Mul regB regE regB
 , Push regB
 , Load (ImmValue (-1)) regB
 , Compute Incr regB reg0 regB
 , Compute GtE regC regD regE
 , Compute Sub regC regD regC
 , Branch regE (Rel (-3))
 , Pop regE
 , Compute Mul regB regE regB
 , Store regB (DirAddr (1))
 , Load (ImmValue (200)) regB
 , Load (ImmValue (-1)) regC
 , Compute Mul regC regB regC
 , Load (ImmValue (3)) regB
 , Compute GtE regC reg0 regD
 , Branch regD (Rel (3))
 , Load (ImmValue (-1)) regD
 , Compute Mul regC regD regC
 , Compute GtE regB reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regB regE regB
 , Compute Mul regD regE regD
 , Push regD
 , Load (ImmValue (-1)) regD
 , Compute Incr regD reg0 regD
 , Compute GtE regC regB regE
 , Compute Sub regC regB regC
 , Branch regE (Rel (-3))
 , Pop regE
 , Compute Mul regD regE regD
 , Store regD (DirAddr (2))
 , Load (ImmValue (500)) regB
 , Load (ImmValue (3)) regC
 , Load (ImmValue (-1)) regD
 , Compute Mul regD regC regD
 , Compute GtE regB reg0 regC
 , Branch regC (Rel (3))
 , Load (ImmValue (-1)) regC
 , Compute Mul regB regC regB
 , Compute GtE regD reg0 regE
 , Branch regE (Rel (3))
 , Load (ImmValue (-1)) regE
 , Compute Mul regD regE regD
 , Compute Mul regC regE regC
 , Push regC
 , Load (ImmValue (-1)) regC
 , Compute Incr regC reg0 regC
 , Compute GtE regB regD regE
 , Compute Sub regB regD regB
 , Branch regE (Rel (-3))
 , Pop regE
 , Compute Mul regC regE regC
 , Store regC (DirAddr (3))
 , Load (DirAddr (0)) regB
 , WriteInstr regB numberIO
 , Load (DirAddr (1)) regB
 , WriteInstr regB numberIO
 , Load (DirAddr (2)) regB
 , WriteInstr regB numberIO
 , Load (DirAddr (3)) regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]