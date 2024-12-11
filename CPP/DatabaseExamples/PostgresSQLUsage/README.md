# Base
The PostgreSQL version makes use of a specific cpp libary for Postgres which runs on top of the normal c libary published by Postgres
In order to completely use the code the following needs to be done

Navigate to https://github.com/jtv/libpqxx for libpqxx.
Linux and Mac can follow instructions as provided (if trying that)
When using MSVC (Visual Studio) some extra work needs to be done to use it as was done header

# Instructions

1. Download or clone the libpqxx from github
2. Download and install CMake
3. Configure CMake to show up in Windows path (part of Window settings)
4. Run command prompt as Administrator
5. Make sure CMake show version or help to test it
6. Navigate to downloaded position in Command prompt
7. Run 'CMake ./' to check if everything is ok
  a. Most likely will run into Postgres not found issue, to fix it you will need to add to the file CMakeCache.txt
    - PostgreSQL_INCLUDE_DIR:PATH=
    - PostgreSQL_LIBRARY_RELEASE:FILEPATH= 

    to match where Postgres Client has been installed for example version 17 would be C:\Program Files\PostgreSQL\17
    For the include folder there is the need for 3 files
      - libpq-fe.h
      - pg_config_ext.h
      - postgres_ext.h
    it can be found with Postgres source

    Note: see folder setup under project for the files
8. Run CMake --build ./ command 
9. You will now find a MSVS solution file that can be used for future builds
10. Adjust Project properties to match required C++ version, for me that was C++ 20.
11. Build both debug and release version

##
For the actual example project, the linker needs to be configured so Debug uses Debug libpqxx and Release uses Release libpqxx
In addition make sure to link in the following lib files as well
1. libpq.lib -> C Library that comes with Postgres
2. Ws2_32.lib -> Socket libray that is used

You can after setup try and play with examples 

## Database
Used Docket version for Postgres setup to run, hence needing only the client portion of Postgres being installed