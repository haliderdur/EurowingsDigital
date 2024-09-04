# Eurowings Digital Project 
```
Author: Halid Erdur
```

This project is a both UI and API hybrid QA automation framework built using Cucumber BDD (Behavior-Driven Development)
with Selenium and REST-Assured library with Java programming language.
It provides a structured approach for automating UI and API tests, ensuring efficient test case management and easier
collaboration between QA engineers, other team members and stakeholders.
<br />
Maven as the build automation tool for managing dependencies and executing tests. JUnit for assertion and validation of
test results.
<br />
There are two main steps for this framework. First one is business layer that involves all teams members including
non-technical members. In this part I write my test scenarios under resources folder with Gherkin syntax in plain
English. Second part is implementation layer which is technical part includes creating project structure and writing the
source code.

## Steps to Create Project

### 1. Created a maven project called

> EurowingsDigital

### 2. Once maven project was created, IDE generated the project with `pom.xml` file

> 1. Properties added in `pom.xml` file by IDE

```xml

<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

> 2. Add dependencies from maven repository into the `pom.xml` file as below;

```xml

<dependencies>

    <!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.10.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-java -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.2.3</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-junit</artifactId>
        <version>7.2.3</version>
        <scope>test</scope>
    </dependency>

    <!--If you want to get rid of SLF4J Failed to load message from the console -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.32</version>
    </dependency>

    <dependency>
        <groupId>me.jvt.cucumber</groupId>
        <artifactId>reporting-plugin</artifactId>
        <version>7.2.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.github.javafaker/javafaker -->
    <dependency>
        <groupId>com.github.javafaker</groupId>
        <artifactId>javafaker</artifactId>
        <version>1.0.2</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/io.rest-assured/rest-assured -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.3.0</version>
        <scope>test</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.4</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
        <scope>provided</scope>
    </dependency>

</dependencies>

 ```

> 3. Add Build and Report Plugins

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
            <configuration>
                <parallel>methods</parallel>
                <!--<useUnlimitedThreads>true</useUnlimitedThreads>-->
                <threadCount>4</threadCount>
                <testFailureIgnore>true</testFailureIgnore>
                <includes>
                    <include>**/CukesRunner*.java</include>
                </includes>
            </configuration>
        </plugin>
        <plugin>
            <groupId>net.masterthought</groupId>
            <artifactId>maven-cucumber-reporting</artifactId>
            <version>2.8.0</version>
            <executions>
                <execution>
                    <id>execution</id>
                    <phase>test</phase>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                    <configuration>
                        <projectName>EurowingsDigital</projectName>
                        <outputDirectory>${project.build.directory}/cucumber-report-html</outputDirectory>
                        <cucumberOutput>${project.build.directory}/cucumber.json</cucumberOutput>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>

```

### 3. Created a package called `api.zippopotam` under `src/test/java`

> > under `api.zippopotam` package, created `apiCalls`, `pojos` and `stepDefinitions` packages
> 1. `apiCalls` package contains `ZippoApi` java class where API requests are made
> 2. `stepDefinitions` package contains `ZippoApiSteps` java class is where feature file scenarios and steps are
     implemented
> 3. `pojos` package contains `ZippoPojo` java class where API responses are converted to a Java Objects for
     serialization and deserialization

### 4. Created a package called `ui.eurowings` under `src/test/java`

> > under `ui.eurowings` package, created `pages`, `stepDefinitions` and `utilities` packages
> 1. `pages` package contains page java classes where we store web app respective page elements with using @FindBy
     selenium annotations.
> * `BasePage` abstract class is super class of other page classes.
>
> `Page Object Model` :  
> Created BasePage java abstract class and add a constructor with
> `PageFactory.initElements()` method to extend this class from other pages classes.
> Achieved reusability in this way.
>
>```java
> public abstract class BasePage {
>    public BasePage() {
>        PageFactory.initElements(Driver.getDriver(), this);
>    }
>}
>```

> 2. `stepDef` package contains following;
> * `Hooks` java class where @Before and @After annotations are in use for specific actions. `Hooks` class for
    determining common steps like `Driver.getDriver().manage().window().maximize();` and `Driver.closeDriver();` for all
    scenarios by using `@Before` and `@After` annotations which come from cucumber.
> * `UI_StepDefinitions` class is where feature file scenarios and steps are implemented

> 3. `utilities` package contains `BrowserUtils` java class where global methods are implemented to be used and reused
     in step definition classes.
>* `Driver` class provides WebDriver in a Singleton design pattern where QAs can run tests with using different type of
   browsers or headless browsers.
>* `ConfigurationReader` java class to read data from `configuration.properties` file where we can store environmental
   variables.
>* `configuration.properties` file to store some credentials in order to avoid hard coding if we need and determine
   which type of browser we use and add following information:
>```properties
>browser=chrome
>baseURL= https://www.example.com/
>username=
>password=
>apiKey=
>```

### 5. Create a package called `runners` under `src/test/java`

> 1. under `runners` package, created `CukesRunner` and `FailedTestRunner` java classes to run tests with
     CucumberOptions
> 2. Add below information above CukesRunner class name
>
>```java
>
> @RunWith(Cucumber.class)
> @CucumberOptions(
>        plugin = {
>               "pretty",
>               "html:target/cucumber-report.html",
>               "rerun:target/rerun.txt",
>               "me.jvt.cucumber.report.PrettyReports:target/cucumber",
>               "json:target/cucumber.json"
>        },
>         features = "src/test/resources/features",
>         glue = {"ui.eurowings.stepDefinitions", "api.zippopotam.stepDefinitions"},
>        dryRun = false,
>        publish = true,
>        tags = ""
> )
> 
> 
> public class CukesRunner {
> }
>``` 
> * Tags should be same as in .feature files; @UI, @API, @TEST, TC01, TC02, API01, UI02 so on...
>
> 3. Add below information above FailedTestRunner class name
>
>```java
> @RunWith(Cucumber.class)
> @CucumberOptions(
>   glue = {"ui/eurowings/stepDefinitions", "api/zippopotam/stepDefinitions"},
>   features = "@target/rerun.txt"
> )
> 
> public class FailedTestRunner {
> 
> }
>```

### 6. Create `resources directory` under project level

> 1. Create a file with `.feature` extension to define scenarios and steps. This just a regular file where we use
     Gherkin syntax.
>
> 2. In feature file follow cucumber BDD (Gherkin) approach

### 7. Reporting

> This project structure releases 3 different report. We specify under the `plugin` section in `CukesRunner`
> class `Cucumber Options` annotation. All report releases under `target` directory after running project.
>
>* `"html:target/cucumber-reports.html"` is basic cucumber report for sharing via email in Jenkins.
>* `"me.jvt.cucumber.report.PrettyReports:target/cucumber"` is extended cucumber report to show all steps of scenario in
   detail.
>* `"json:target/cucumber.json"` is basic json file to import test result in Jira after execution of test cases.
>

#### 8. Chrome Driver issue

>
>After 116 version of Chrome, WebDriverManager doesn't support this one. In order to handle this problem, download newest `chromedriver.exe` and put it in the project directory. Make sure your chromeDriver is compatible with your chrome version.
