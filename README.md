# jksExportKey
Java tool which makes easier exporting keys from JKS (Java Key Store)

# Building requirements
You need maven as it is a maven project

# Building
> mvn package

# Usage
The binary is standard jar file. It will be created inside target folder.

> java -jar jksExportKey-x.y.jar <keystore> <alias> <password>

> java -jar <jar.file> <jks.file> <alias.in.jks> <jks.password> > <private-key.pkcs8.key>

# More information
[https://dev.miteff.com/](https://dev.miteff.com/)
