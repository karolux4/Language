import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (3)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (10)) regB
 , Push regB
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (100)) regB
 , Push regB
 , Load (ImmValue (0)) regD
 , Compute Sub regA regD regD
 , Load (ImmValue (2)) regB
 , Compute Sub regD regB regD
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Pop regC
 , Store regC (IndAddr regD)
 , Compute Incr regD reg0 regD
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (3)) regC
 , Compute GtE regB regC regC
 , Branch regC (Rel (8))
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Push regC
 , Compute Incr regB reg0 regB
 , Jump (Rel (-9))
 , Load (ImmValue (123)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (10)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (2)) regB
 , Compute Lt regB reg0 regC
 , Branch regC (Rel (6))
 , Compute Add regSP regB regC
 , Load (IndAddr regC) regC
 , WriteInstr regC numberIO
 , Compute Decr regB reg0 regB
 , Jump (Rel (-6))
 , Load (ImmValue (125)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (10)) regC
 , WriteInstr regC charIO
 , Load (ImmValue (3)) regB
 , Compute Add regSP regB regSP
 , EndProg
 ]

main = run [prog]