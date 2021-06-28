import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (82))
 , Load (ImmValue (3)) regB
 , Compute Sub regA regB regSP
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Equal regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (1)) regD
 , Compute Equal regC regD regC
 , Compute Or regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (19))
 , Load (ImmValue (1)) regB
 , WriteInstr regB numberIO
 , Jump (Abs (74))
 , Load (ImmValue (2)) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (1)) regB
 , Load (ImmValue (2)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (1)) regB
 , Load (ImmValue (3)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (2)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (69))
 , Load (ImmValue (3)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (2)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (3)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (3)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (2)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Add regB regC regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (31))
 , Nop
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , Nop
 , Compute Incr regA reg0 regB
 , Compute Incr regB reg0 regC
 , Compute Add regC reg0 regSP
 , Compute Incr regSP reg0 regSP
 , Load (IndAddr regB) regA
 , Load (IndAddr regC) regC
 , Jump (Ind regC)
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (0)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (95)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (103)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (2)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (111)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (3)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (119)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (4)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (127)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (40)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , EndProg
 ]

main = run [prog]