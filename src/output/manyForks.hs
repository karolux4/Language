import Sprockell

prog :: [Instruction]
prog = [ 
   Jump (Abs (1))
 , Push regSP
 , Pop regA
 , Compute Decr regA reg0 regA
 , Branch regSprID (Rel (2))
 , Jump (Rel (6))
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , Load (ImmValue (1)) regB
 , WriteInstr regB (DirAddr (4))
 , Load (ImmValue (16)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (64))
 , Load (ImmValue (19)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (31))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Push regA
 , Pop regSP
 , Compute Incr regSP reg0 regSP
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , Load (ImmValue (34)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (46))
 , Load (ImmValue (4)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Push regA
 , Pop regSP
 , Compute Incr regSP reg0 regSP
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , ReadInstr (DirAddr (2))
 , Receive regB
 , ReadInstr (DirAddr (3))
 , Receive regC
 , Compute Or regB regC regB
 , Branch regB (Rel (-5))
 , Load (ImmValue (2)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Push regA
 , Pop regSP
 , Compute Incr regSP reg0 regSP
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , ReadInstr (DirAddr (1))
 , Receive regB
 , Branch regB (Rel (-2))
 , Load (ImmValue (5)) regB
 , Push regB
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (88))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (69))
 , Nop
 , Load (ImmValue (1)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (95)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (143))
 , Load (ImmValue (98)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (110))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Push regA
 , Pop regSP
 , Compute Incr regSP reg0 regSP
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , Load (ImmValue (113)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (125))
 , Load (ImmValue (4)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Push regA
 , Pop regSP
 , Compute Incr regSP reg0 regSP
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , ReadInstr (DirAddr (2))
 , Receive regB
 , ReadInstr (DirAddr (3))
 , Receive regC
 , Compute Or regB regC regB
 , Branch regB (Rel (-5))
 , Load (ImmValue (2)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Push regA
 , Pop regSP
 , Compute Incr regSP reg0 regSP
 , WriteInstr reg0 (IndAddr regSprID)
 , ReadInstr (IndAddr regSprID)
 , Receive regB
 , Compute Equal regB reg0 regC
 , Branch regC (Rel (-3))
 , Jump (Ind regB)
 , Load (ImmValue (5)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (166))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (147))
 , Nop
 , ReadInstr (DirAddr (1))
 , Receive regB
 , Branch regB (Rel (-2))
 , Load (ImmValue (1)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , Load (ImmValue (176)) regB
 , WriteInstr regB (DirAddr (1))
 , Jump (Abs (197))
 , Load (ImmValue (179)) regB
 , WriteInstr regB (DirAddr (2))
 , Jump (Abs (184))
 , Load (ImmValue (3)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (187)) regB
 , WriteInstr regB (DirAddr (3))
 , Jump (Abs (192))
 , Load (ImmValue (4)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (2)) regB
 , Load (ImmValue (4)) regC
 , WriteInstr regB (IndAddr regC)
 , WriteInstr reg0 (IndAddr regSprID)
 , EndProg
 , Load (ImmValue (5)) regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (0)) regC
 , Compute Gt regB regC regB
 , Compute Equal regB reg0 regC
 , Branch regC (Abs (220))
 , ReadInstr (DirAddr (4))
 , Receive regB
 , WriteInstr regB numberIO
 , Load (ImmValue (0)) regB
 , Compute Sub regA regB regB
 , Load (IndAddr regB) regB
 , Load (ImmValue (1)) regC
 , Compute Sub regB regC regB
 , Load (ImmValue (0)) regC
 , Compute Sub regA regC regC
 , Store regB (IndAddr regC)
 , Jump (Abs (201))
 , Nop
 , EndProg
 ]

main = run [prog,prog,prog,prog]