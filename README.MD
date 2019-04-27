Rullion Screening Task
----------------------

The solution was implemented using Java 12 project and Maven dependency management.
The src/main/java folder contains the source code
The src/test/java folder contains the tests, including the acceptance criteria test (with the same tests as appear in the exercise).

---

Command line support:
Using the command line you may run dynamic tests on the code
You may run the command line using maven as following (required Maven + JDK12):

    set JAVA_HOME=C:\Program Files\Java\jdk-12
    mvn install
    mvn exec:java -Dexec.mainClass=com.rullion.Main

Run example:

    Add a comma separated list of tasks:
    a,b,c,d
    Add a list of comma separated list of dependencies using > character (e.g. a>b,b>c):
    a>b,c>d
    Result: b,a,d,c
         
---

Enjoy!

Ofir Germansky.