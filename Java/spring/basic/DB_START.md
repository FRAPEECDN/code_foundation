# Setup of HyperSQL

1. Download latest HyperSQL and extract to working directory
2. use command 'java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:springdemo --dbname.0 springdemo'
    a. make sure to run in under data folder
    b. this will create the runtime database
    c. check under ../data to make sure the files for the database was created
3. use command 'java -cp ../lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing'
    a. make sure to run under bin folder
    b. This will start the database Manager
    c. use 'jdbc:hsqldb:hsql://localhost/springdemo' as connection url
4. HyperSQL is setup as in memory when using unit and integrated tests