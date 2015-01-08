WHOIS Core
==========

Configure
---------
The first thing to do is to review __ALL the configuration files__ in folder:

    whois-default/src/main/resources

Test
-----
Run the tests via the following command from the __project root__ folder:

    mvn test -DfailIfNoTests=false -Dtest=AllTestSuite

Package
-------
Package the application via the following command from the __project root__ folder:

    mvn clean install

The __executable jar__ file produced is:

    whois-default/target/whois-default-<version>.jar

Run
---
You may run the executable jar from the __whois-default module root__ via the following command:

    java -jar target/whois-default-<version>.jar

Or run the application's main class via the following command from the __whois-default module root__ folder:

    mvn exec:java -Dexec.mainClass=whois.core.socket.SocketServer

Terminate
---------
Press __Ctrl-C__ to terminate.