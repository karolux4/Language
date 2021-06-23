import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Load (ImmValue (10)) regB
 , Push regB
 , Load (ImmValue (9)) regB
 , Push regB
 , Load (ImmValue (8)) regB
 , Push regB
 , Load (ImmValue (7)) regB
 , Push regB
 , Load (ImmValue (6)) regB
 , Push regB
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (4)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Pop regB
 , Store regB (DirAddr (0))
 , Pop regB
 , Store regB (DirAddr (1))
 , Pop regB
 , Store regB (DirAddr (2))
 , Pop regB
 , Store regB (DirAddr (3))
 , Pop regB
 , Store regB (DirAddr (4))
 , Pop regB
 , Store regB (DirAddr (5))
 , Pop regB
 , Store regB (DirAddr (6))
 , Pop regB
 , Store regB (DirAddr (7))
 , Pop regB
 , Store regB (DirAddr (8))
 , Pop regB
 , Store regB (DirAddr (9))
 , WriteInstr reg0 (DirAddr (1))
 , WriteInstr reg0 (DirAddr (2))
 , WriteInstr reg0 (DirAddr (3))
 , WriteInstr reg0 (DirAddr (4))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (1)) regC
 , Load (ImmValue (0)) regD
 , Compute Add regD regC regD
 , Store regB (IndAddr regD)
 , Load (ImmValue (1)) regB
 , Load (ImmValue (2)) regC
 , Load (ImmValue (1)) regD
 , Compute Add regD regC regD
 , WriteInstr regB (IndAddr regD)
 , Load (ImmValue (2)) regB
 , Load (ImmValue (1)) regC
 , Compute Add regC regB regC
 , ReadInstr (IndAddr regC)
 , Receive regC
 , Load (ImmValue (1)) regB
 , Load (ImmValue (1)) regD
 , Compute Add regD regB regD
 , WriteInstr regC (IndAddr regD)
 , ReadInstr (DirAddr (4))
 , Receive regB
 , Push regB
 , ReadInstr (DirAddr (3))
 , Receive regB
 , Push regB
 , ReadInstr (DirAddr (2))
 , Receive regB
 , Push regB
 , ReadInstr (DirAddr (1))
 , Receive regB
 , Push regB
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Load (DirAddr (9)) regB
 , Push regB
 , Load (DirAddr (8)) regB
 , Push regB
 , Load (DirAddr (7)) regB
 , Push regB
 , Load (DirAddr (6)) regB
 , Push regB
 , Load (DirAddr (5)) regB
 , Push regB
 , Load (DirAddr (4)) regB
 , Push regB
 , Load (DirAddr (3)) regB
 , Push regB
 , Load (DirAddr (2)) regB
 , Push regB
 , Load (DirAddr (1)) regB
 , Push regB
 , Load (DirAddr (0)) regB
 , Push regB
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Load (ImmValue (12)) regB
 , Push regB
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (7)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (9)) regB
 , Push regB
 , Load (ImmValue (8)) regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Push regB
 , Load (ImmValue (3)) regB
 , Push regB
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (0)) regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Pop regC
 , Store regC (IndAddr regB)
 , Compute Incr regB reg0 regB
 , Load (DirAddr (9)) regB
 , Push regB
 , Load (DirAddr (8)) regB
 , Push regB
 , Load (DirAddr (7)) regB
 , Push regB
 , Load (DirAddr (6)) regB
 , Push regB
 , Load (DirAddr (5)) regB
 , Push regB
 , Load (DirAddr (4)) regB
 , Push regB
 , Load (DirAddr (3)) regB
 , Push regB
 , Load (DirAddr (2)) regB
 , Push regB
 , Load (DirAddr (1)) regB
 , Push regB
 , Load (DirAddr (0)) regB
 , Push regB
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , Pop regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]