TestNG sample ![Logo][1]
==============

This sample demonstrates the TestNG support in Citrus. We write some TestNG Citrus test cases that test the REST API of the todo sample application. The TestNG support is
also described in detail in [reference guide][4]

Objectives
---------

The [todo-list](../todo-app/README.md) sample application provides a REST API for managing todo entries.
Citrus is able to call the API methods as a client in order to validate the Http response messages.

We need a Http client component in the configuration:

```xml
    <citrus-http:client id="todoClient"
                        request-url="http://localhost:8080"/>
```
    
In test cases we can reference this client component in order to send REST calls to the server. Citrus is able to integrate with TestNG as test execution framework. You can use
the `AbstractTestNGCitrusTest` implementation as base for your test.
    
```java
public class TodoListIT extends AbstractTestNGCitrusTest {

    @Test
    @CitrusXmlTest(name = "TodoList_Get_IT")
    public void testGet() {}

    @Test
    @CitrusXmlTest(name = "TodoList_Post_IT")
    public void testPost() {}

}      
```
        
The `AbstractTestNGCitrusTest` makes sure that Citrus framework is loaded at startup and all configuration is done properly. Also we need to set the annotation `@CitrusXmlTest` on our test methods in
addition to the normal TestNG `@Test` annotation. This way we can use the XML test definition in order to send and receive messages using that client component. 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<spring:beans xmlns="http://www.citrusframework.org/schema/testcase"
              xmlns:spring="http://www.springframework.org/schema/beans"
              xmlns:http="http://www.citrusframework.org/schema/http/testcase"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                  http://www.citrusframework.org/schema/testcase http://www.citrusframework.org/schema/testcase/citrus-testcase.xsd
                                  http://www.citrusframework.org/schema/http/testcase http://www.citrusframework.org/schema/http/testcase/citrus-http-testcase.xsd">

  <testcase name="TodoList_Post_IT">
    <meta-info>
      <author>Citrus</author>
      <creationdate>2017-12-04</creationdate>
      <status>FINAL</status>
      <last-updated-by>Citrus</last-updated-by>
      <last-updated-on>2017-12-04T00:00:00</last-updated-on>
    </meta-info>

    <variables>
      <variable name="todoName" value="citrus:concat('todo_', citrus:randomNumber(4))"/>
      <variable name="todoDescription" value="Description: ${todoName}"/>
    </variables>

    <actions>
      <http:send-request client="todoClient">
        <http:POST path="/todolist">
          <http:headers content-type="application/x-www-form-urlencoded"/>
          <http:body>
            <http:data>title=${todoName}&amp;description=${todoDescription}</http:data>
          </http:body>
        </http:POST>
      </http:send-request>

      <http:receive-response client="todoClient">
        <http:headers status="302" reason-phrase="FOUND"/>
      </http:receive-response>

      <http:send-request client="todoClient">
        <http:GET path="/todolist">
          <http:headers accept="text/html"/>
        </http:GET>
      </http:send-request>

      <http:receive-response client="todoClient">
        <http:headers status="200" reason-phrase="OK"/>
        <http:body type="xhtml">
          <http:validate>
            <http:xpath expression="//xh:h1" value="TODO list"/>
            <http:xpath expression="(//xh:li[@class='list-group-item']/xh:span)[last()]" value="${todoName}"/>
          </http:validate>
        </http:body>
      </http:receive-response>
    </actions>
  </testcase>
</spring:beans>
```

In order to setup Maven for TestNG we need to add the dependency to the project POM file.

```xml
<dependency>
  <groupId>testng</groupId>
  <artifactId>testng</artifactId>
  <version>${testng.version}</version>
  <scope>test</scope>
</dependency>    
```
       
That completes the project setup. We are now ready to execute the tests.
       
Run
---------

**NOTE:** This test depends on the [todo-app](../todo-app/) WAR which must have been installed into your local maven repository using `mvn clean install` beforehand.

The sample application uses Maven as build tool. So you can compile, package and test the
sample with Maven.
 
     mvn clean verify -Dembedded
    
This executes the complete Maven build lifecycle. The embedded option automatically starts a Jetty web
container before the integration test phase. The todo-list system under test is automatically deployed in this phase.
After that the Citrus test cases are able to interact with the todo-list application in the integration test phase.

During the build you will see Citrus performing some integration tests.
After the tests are finished the embedded Jetty web container and the todo-list application are automatically stopped.

System under test
---------

The sample uses a small todo list application as system under test. The application is a web application
that you can deploy on any web container. You can find the todo-list sources [here](../todo-app). Up to now we have started an 
embedded Jetty web container with automatic deployments during the Maven build lifecycle. This approach is fantastic 
when running automated tests in a continuous build.
  
Unfortunately the Jetty server and the sample application automatically get stopped when the Maven build is finished. 
There may be times we want to test against a standalone todo-list application.  

You can start the sample todo list application in Jetty with this command.

     mvn jetty:run

This starts the Jetty web container and automatically deploys the todo list app. Point your browser to
 
    http://localhost:8080/todolist/

You will see the web UI of the todo list and add some new todo entries.

Now we are ready to execute some Citrus tests in a separate JVM.

Citrus test
---------

Once the sample application is deployed and running you can execute the Citrus test cases.
Open a separate command line terminal and navigate to the sample folder.

Execute all Citrus tests by calling

     mvn verify

You can also pick a single test by calling

     mvn verify -Dit.test=<testname>

You should see Citrus performing several tests with lots of debugging output in both terminals (sample application server
and Citrus test client). And of course green tests at the very end of the build.

Of course you can also start the Citrus tests from your favorite IDE.
Just start the Citrus test using the TestNG IDE integration in IntelliJ, Eclipse or Netbeans.

Further information
---------

For more information on Citrus see [www.citrusframework.org][2], including
a complete [reference manual][3].

 [1]: https://www.citrusframework.org/img/brand-logo.png "Citrus"
 [2]: https://www.citrusframework.org
 [3]: https://www.citrusframework.org/reference/html/
 [4]: https://www.citrusframework.org/reference/html#run-with-testng