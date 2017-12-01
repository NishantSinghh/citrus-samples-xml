Citrus samples ![Logo][1]
==============

Incident Manager sample
---------

The IncidentManager sample application handles incoming incidents via SOAP WebService interface. The Incident Manager application offers both JMS and Http message transport
binding for opening incident orders:

* SOAP Http endpoint binding (http://localhost:18001/incident/IncidentManager/v1)
** SOAP action: /IncidentManager/openIncident
* SOAP Jms endpoint binding ()
** SOAP action: /IncidentManager/openIncident

Once the incident is opened a network service backend is consulted with analyse request. The backend service uses a Http/XML interface. The network Http server is simulated with Citrus.
According to the analyse outcome the field force service team receives a order request to get on site problem analysis. The field force service is also simulated by Citrus and is an asynchronous JMS/XML service.
Last not least the Incident Manager application sends SMS messages via a SmsGateway application to the customer. The SMS gateway application is a SOAP/Http interface which is also simulated with Citrus.

Server
---------

Got to the war folder and start the IncidentManager WebService application in a Web Container. Easiest
way for you to do this is to execute

     mvn jetty:run

here!

An embedded Jetty Web Server Container is started with the IncidentManager application deployed. You can
alsp call "mvn package" and deploy the resulting war archive to a separate Web container of your choice.

Citrus test
---------

Once the sample application is deployed and running you can execute the Citrus test cases in citrus-test folder.
Open a separate command line terminal and navigate to the citrus-test folder.

Execute all Citrus tests by calling

     mvn verify

You can also pick a single test by calling

     mvn verify -Dit.test=TestName

You should see Citrus performing several tests with lots of debugging output in both terminals (sample application server
and Citrus test client). And of course green tests at the very end of the build.

Information
---------

For more information on Citrus see [www.citrusframework.org][2], including
a complete [reference manual][3].

 [1]: https://www.citrusframework.org/img/brand-logo.png "Citrus"
 [2]: https://www.citrusframework.org
 [3]: https://www.citrusframework.org/reference/html/
