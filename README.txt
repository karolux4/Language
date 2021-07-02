These are the instructions to set up the Pickle Cannon language compiler:

Firstly, there were 2 modifications to Sprockell memory regions.
Local memory was expanded from 32 integers to 64 integers and shared memory from 8 integers to 16 integers.
This was done because Pickle Cannon allows the use of arrays and procedures that can become quite large on the memory.
Shared memory was expanded because 1 memory unit is always taken up by global lock, and then each newly spawned thread takes up another unit.
So if 3 threads were spawned already - 4 shared memory integers are reserved.
The only modified Sprockell file was BasicFunctions.hs which can be found in src/output/Sprockell folder. If you want to apply these changes
to your installed Haskell package, copy this file into your Sprockell repository copy and run the 'cabal install' command. However, all
Sprockell source files are copied into src/output folder so that you can run Sprockell programs from that folder even without Haskell package installation.

The project was written in Eclipse IDE using purely Java. To use compiler follow these steps:
1. Import the project into your IDE as Eclipse project (project already comes with ANTLR and JUnit4 .jar files located in lib folder)
2. Open the compiler class located in src/compiler/Compiler.java file.
3. Compiler class has a main method that can be used in two ways:
	3.1. If main method is ran without supllying an argument, it will compile the file main.pickle located in src/sample folder.
	3.2. If main method is ran with the argument, it will run the file which was supplied as an argument.
	     So if the argument was 'src/sample/main.pickle' it would compile the same main.pickle file.
4. Write your program either in src/sample/main.pickle file, or in another file (recommended to store it in src/sample directory and add .pickle extension)
5. Run the main method of the Compiler class
6. Generated Sprockell program file can be found in src/output directory with the same name only with .hs extension (in case of main - main.hs)
7. Open the GHCi in the output folder and load the generated file.
8. Run generated file's main function

About testing:
Testing is done automatically and all tests can be ran from src/tests/combined/TestSuite.java file when running this file as a JUnit test.
However, keep in mind that the semantic test has two tests for infinite time execution, these two tests spawn two ghc processes that are not killed
after the test has ended. To kill them open the Task Manager on Windows or terminal on Linux and kill these processes. Moreover, if for some strange
reason the automatic testing does not work (should not happen) you can generate test source files using the compiler class method and then run them on GHCi manually.
Last note about testing, make sure that if you are working on Linux that your path to the project does not contain spaces in directory names
as then the terminal does manage to run ghc command.

That is all regarding basic instructions. More about syntax and language can be found in the report .pdf file.
Have fun! (there are already some precompiled programs in src/output folder)