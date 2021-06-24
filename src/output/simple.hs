import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Load (ImmValue (3)) regB
 , WriteInstr regB (DirAddr (1))
 , ReadInstr (DirAddr (1))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (2)) regB
 , Push regB
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , ReadInstr (DirAddr (1))
 , Receive regC
 , Compute Mul regB regC regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Compute Equal regB reg0 regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Compute And regB reg0 regB
 , Push regB
 , Load (ImmValue (1)) regB
 , Compute Or regB reg0 regB
 , Push regB
 , Push reg0
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (1)) regC
 , Compute Sub regA regC regC
 , Load (IndAddr regC) regC
 , Compute Mul regB regC regB
 , Load (ImmValue (5)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (5)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , ReadInstr (DirAddr (1))
 , Receive regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (89))
 , Load (ImmValue (3)) regB
 , Push regB
 , TestAndSet (DirAddr (0))
 , Receive regB
 , Branch regB (Rel (2))
 , Jump (Rel (-3))
 , ReadInstr (DirAddr (1))
 , Receive regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (1)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (DirAddr (0))
 , Load (ImmValue (6)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , ReadInstr (DirAddr (1))
 , Receive regB
 , Load (ImmValue (2)) regC
 , Compute Equal regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (82))
 , Load (ImmValue (1)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (-1)) regC
 , Compute Mul regC regB regC
 , WriteInstr regC numberIO
 , Jump (Abs (87))
 , Load (ImmValue (2)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Compute Equal regB reg0 regB
 , WriteInstr regB numberIO
 , Nop
 , Jump (Abs (46))
 , Nop
 , Load (ImmValue (5)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , WriteInstr regB numberIO
 , EndProg
 ]

main = run [prog]