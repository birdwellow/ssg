# Maven test project for combining Angular 2 frontend with Spring Boot backend

## Set Up

### Prerequisites
* Maven 3
* NodeJS, NPM
* Angular CLI

### Steps

The following steps use the description in https://github.com/prashantpro/ng-jee as a basis.

1. Create project directory in YOUR-PROJECT-ROOT directory:
```sh
cd [YOUR-PROJECT-ROOT]
mkdir test-mvn-ng2-springboot
cd test-mvn-ng2-springboot
```

1. Create the parent `[YOUR-PROJECT-ROOT]/pom.xml` similar to:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>net.fvogel</groupId>
    <artifactId>test-mvn-ng2-springboot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.2.RELEASE</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <modules>
        <module>frontend</module>
        <module>backend</module>
    </modules>
</project>
```

1. Create the directory for sub-module *backend* and inside its `[YOUR-PROJECT-ROOT]/backend/pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>net.fvogel</groupId>
    <artifactId>backend</artifactId>
    <packaging>jar</packaging>

    <name>Web App Backend</name>

    <parent>
        <groupId>net.fvogel</groupId>
        <artifactId>test-mvn-ng2-springboot</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>..</relativePath>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <start-class>net.fvogel.App</start-class>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
            <resource>
                <directory>../frontend/dist</directory>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

1. Create the sub-module *frontend*:
```sh
ng new frontend --style=sass
```
Afterwards, remove generated Git artefacts:
```sh
rm -rf .git
rm .gitignore
```

1. Include the to frontend project dir as sub-module by adding the `[YOUR-PROJECT-ROOT]/frontend/pom.xml` similar to:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>net.fvogel</groupId>
    <artifactId>frontend</artifactId>
    <packaging>jar</packaging>

    <name>Web App Frontend</name>

    <parent>
        <groupId>net.fvogel</groupId>
        <artifactId>test-mvn-ng2-springboot</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>..</relativePath>
    </parent>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-clean-plugin</artifactId>
                <version>3.0.0</version>
                <configuration>
                    <failOnError>false</failOnError>
                    <filesets>
                        <fileset>
                            <directory>dist</directory>
                        </fileset>
                    </filesets>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>1.5.0</version>
                    <executions>
                        <execution>
                            <id>angular-cli build</id>
                            <configuration>
                                <workingDirectory>.</workingDirectory>
                                <executable>ng</executable>
                                <arguments>
                                    <argument>build</argument>
                                    <argument>--prod</argument>
                                    <argument>--base-href</argument>
                                    <argument>"/frontend/"</argument>
                                </arguments>
                            </configuration>
                            <phase>generate-resources</phase>
                            <goals>
                                <goal>exec</goal>
                            </goals>
                        </execution>
                    </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

1. In `[YOUR-PROJECT-ROOT]/frontend/.angular-cli.json`, set the build output directory to `dist/static`. This way it will be available as static resources directory:
```json
...
  "outDir": "dist/static",
...
```
