camel-ironmq
============

Ironmq component for Camel supports integration with [IronMQ](http://www.iron.io/products/mq) an elastic and durable hosted message queue service.

To run it requires a IronMQ acount with projectId and token.

Uri format:
===========
	ironmq://queue-name[?options]

URI options:
============
<table>
  <tr>
    <th>Name</th>
    <th>Default value</th>
    <th>Context</th>
    <th>Description</th>
  </tr>
  <tr>
    <th>client</th>
    <td>null</td>
    <td>Shared</td>
    <td>Reference to a io.iron.ironmq.Client in the Registry.</td>
  </tr>
  <tr>
    <th>projectId</th>
    <td>null</td>
    <td>Shared</td>
    <td>IronMQ projectid</td>
  </tr>
  <tr>
    <th>token</th>
    <td>null</td>
    <td>Shared</td>
    <td>IronMQ token</td>
  </tr>
  <tr>
    <th>ironMQCloud</th>
    <td>ironAWSUSEast</td>
    <td>Shared</td>
    <td>Reference to a io.iron.ironmq.Cloud in the registry. See [IronIo documents](http://dev.iron.io/mq/reference/clouds/) for valid options</td>
  </tr>
  <tr>
    <th>timeout</th>
    <td>60</td>
    <td>Shared</td>
    <td>After timeout (in seconds), item will be placed back onto the queue</td>
  </tr>
  <tr>
    <th>maxMessagesPerPoll</th>
    <td>1</td>
    <td>Consumer</td>
    <td>How many messages pr. poll</td>
  </tr>
  <tr>
    <th>expiresIn</th>
    <td>604800</td>
    <td>Producer</td>
    <td> How long in seconds to keep the item on the queue before it is deleted. Default is 604,800 seconds (7 days). Maximum is 2,592,000 seconds (30 days).</td>
  </tr>
    <tr>
    <th>visibilityDelay</th>
    <td>0</td>
    <td>Producer</td>
    <td>The item will not be available on the queue until this many seconds have passed. Default is 0 seconds.</td>
  </tr>

</table>

Consumer example
========

	from("ironmq:testqueue?projectId=myIronMQProjectid&token=myIronMQToken&maxMessagesPerPoll=50").to(""mock:result"")


Producer example
========

	from("direct:start").to("ironmq:testqueue?projectId=myIronMQProjectid&token=myIronMQToken").

Building from source
====================

	
	$ git clone git://github.com/pax95/camel-ironmq.git
	$ cd camel-ironmq
	$ mvn clean package

	
  

