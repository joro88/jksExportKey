# jksExportKey
Java tool which makes easier exporting keys from JKS (Java Key Store). It could be executed on Linux and on Windows / DOS.

# Usage
Binary is standard jar file. It will be created inside target folder.

> java -jar jksExportKey-x.y.jar <keystore> <alias> <password>

> java -jar <jar.file> <jks.file> <alias.in.jks> <jks.password> > <private-key.pkcs8.key>

# Building
You need apache maven as it is a maven project

Building:
> mvn package

# More information
[https://dev.miteff.com/](https://dev.miteff.com/)
