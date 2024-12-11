# Instructions
Note that SQLite needs to be installed for this example to work.
Included the lib and header file from version 3.47.2

## Example -> clsSQLiteManager
The example code wraps normal C functionality in a C++ class for using the SQLite database
- The prepare statements current only checks if statement is SELECT or not but can be enhanced as needed to protect
  against any SQL injection attacks (which is not the goal of the example)

In order to prepare SQLite the following needs to be done
1. Download the header file for version to be used (Taken from amalgamation download)
2. Download the Windows x64 version (Taken from Precompiled Binaries for Windows 64 bit)
3. Extract header file only and then extract dll and def file
4. Run Visual Studio Command prompt and navigate to dll and def file
5. Use the command 'LIB /DEF:sqlite3.def /MACHINE:x64' to generate lib file used for linking
6. When running the dll needs to be with the exe