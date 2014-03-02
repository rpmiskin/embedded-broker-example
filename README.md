Embedded Broker Example
=======================

### Overview
Small example showing how to use the [embedded broker](https://github.com/rpmiskin/embedded-broker) in a Camel Spring
project.  See this [properties file](https://github.com/rpmiskin/embedded-broker-example/blob/master/src/main/resources/META-INF/spring/internal-broker.properties)
for configuration options.

Also shows the effect of a slow consumer on a route triggered by a timer. The code can be modified to show the difference
between using the direct end point and an ActiveMQ queue endpoint.

