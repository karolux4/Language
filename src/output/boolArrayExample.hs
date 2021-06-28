import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (51))
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regSP
 , Load (ImmValue (0)) regB
 , Load (ImmValue (5)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (1)) regB
 , Load (ImmValue (6)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (5)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (4)) regC
 , Compute LtE regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (39))
 , Load (ImmValue (0)) regB
 , Load (ImmValue (0)) regC
 , Compute Add regC regB regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Compute And regC regB regC
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Store regC (IndAddr regB)
 , Load (ImmValue (5)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Add regB regC regB
 , Load (ImmValue (5)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (11))
 , Nop
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
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
 , Load (ImmValue (5)) regB
 , Compute Sub regSP regB regSP
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Load (ImmValue (4)) regB
 , Compute Sub regC regB regC
 , Compute Lt regB reg0 regD
 , Branch regD (Rel (5))
 , Store reg0 (IndAddr regC)
 , Compute Incr regC reg0 regC
 , Compute Decr regB reg0 regB
 , Jump (Rel (-5))
 , Load (ImmValue (82)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1)) regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Push regC
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (99)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (0)) regC
 , Load (ImmValue (5)) regD
 , Compute GtE regC regD regD
 , Branch regD (Rel (8))
 , Load (ImmValue (0)) regD
 , Compute Add regD regC regD
 , Compute Sub regA regD regD
 , Load (IndAddr regD) regD
 , Push regD
 , Compute Incr regC reg0 regC
 , Jump (Rel (-9))
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , Load (ImmValue (119)) regB
 , Push regB
 , Push regA
 , Compute Decr regSP reg0 regB
 , Load (ImmValue (1)) regC
 , Compute NEq regC reg0 regC
 , Push regC
 , Compute Equal reg0 reg0 reg0
 , Push reg0
 , Load (ImmValue (1)) regC
 , Compute Or regC reg0 regC
 , Push regC
 , Load (ImmValue (1)) regC
 , Load (ImmValue (1)) regD
 , Compute And regC regD regC
 , Push regC
 , Compute Equal reg0 reg0 reg0
 , Push reg0
 , Compute Add regB reg0 regA
 , Jump (Abs (1))
 , EndProg
 ]

main = run [prog]