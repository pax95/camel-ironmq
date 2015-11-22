# camel-ironmq


Ironmq component for [Apaceh Camel](http://camel.apache.org) to support integration with [IronMQ](http://www.iron.io/products/mq) an elastic and durable hosted message queue service.

The master branch targets IronMq v3 while v2 is supported using the [v2 branch](https://github.com/pax95/camel-ironmq/tree/v2).

To run it requires a IronMQ account with projectId and token.

See examples below or the [unit tests](https://github.com/pax95/camel-ironmq/tree/master/src/test/java/org/apache/camel/component/ironmq) for how to use Camel and IronMQ together.

## Status

[![Build Status](https://travis-ci.org/pax95/camel-ironmq.svg?branch=master)](https://travis-ci.org/pax95/camel-ironmq)
[![Coverage Status](https://coveralls.io/repos/pax95/camel-ironmq/badge.svg?branch=master&service=github)](https://coveralls.io/github/pax95/camel-ironmq?branch=master)

## Uri format:

  ironmq://queue-name[?options]

## URI options:


Name        | Default value | Context   | Description
------          | ------------- | -------   | -----------
client          | null          | Shared    | Reference to a io.iron.ironmq.Client in the Registry.
projectId       | null          | Shared    | IronMQ projectid
token           | null          | Shared    | IronMQ token
ironMQCloud     | https://mq-aws-us-east-1.iron.io | Shared  | IronMq Cloud url. See [IronIo documents](http://dev.iron.io/mq/reference/clouds/) for valid options
preserveHeaders    | false      | Shared | Should Camel headers be preserved. This will add the Camel headers to the Iron MQ message body as a json payload with a header list, and a message body. This is useful when Camel is both consumer and producer.
concurrentConsumers | 1             | Consumer  | Number of concurrent consumers.
timeout          | 60      | Consumer  | After timeout (in seconds), item will be placed back onto the queue
maxMessagesPerPoll  | 1        | Consumer | How many messages pr. poll.
batchDelete | false | Consumer | Should messages be deleted in one batch. This will limit the number of api requests since messages are deleted in one request, instead of one pr. exchange. If enabled care should be taken that the consumer is idempotent when processing exchanges.
wait | 0 | Consumer | Time in seconds to wait for a message to become available. This enables long polling. Default is 0 (does not wait), maximum is 30.  
visibilityDelay    | 0        | Producer  | The item will not be available on the queue until this many seconds have passed. Default is 0 seconds.

## The camel message body

Should be either a String or a array of Strings. In the latter case the batch of strings will be send to IronMq as one request, creating  one message pr. element in the array on IronMq.

## Message headers set by the IronMQ producer

Header                  |Type  | Description
------------------------|------|--------------
CamelIronMQOperation    |String|If value set to 'CamelIronMQClearQueue' the queue is cleared of unconsumed  messages.
CamelIronMQMessageId    |String or io.iron.ironmq.Ids|The id of the IronMQ message as a String when sending a single message, or a Ids object when sending a single batch list of string messages.

## Message headers set by the IronMQ consumer


Header                  |Type  | Description
------------------------|------|--------------
CamelIronMQMessageId    |String|The id of the IronMQ message consumed.
CamelIronMQReservationId|String|The reservation of the IronMQ message.
CamelIronMQReservedCount|String|The number of times this message has been reserved.


## Consumer example

* Consume 50 messages pr. poll from the queue 'testqueue', and save the messages to disk.

        from("ironmq:testqueue?projectId=myIronMQProjectid&token=myIronMQToken&maxMessagesPerPoll=50").to("file:somefolder)


## Producer example

* Take the direct input and enqueue it on 'testqueue'.

        from("direct:start").to("ironmq:testqueue?projectId=myIronMQProjectid&token=myIronMQToken").

* Dequeue from [activemq/jms](http://camel.apache.org/jms.html) and enqueue the message on Ironmq.

        from("activemq:foo).to("ironmq:testqueue?projectId=myIronMQProjectid&token=myIronMQToken").

There is a jms example here https://github.com/pax95/ironmq-jms-example

## Building from source



  $ git clone git://github.com/pax95/camel-ironmq.git
  $ cd camel-ironmq
  $ mvn clean install
