# RequestTracker Connector

This connector will make it easier to use the RequestTracker REST 1.0 API

Target Mule version is v4

All responses are converted to POJOs

The operators are based on those given in the RT REST documentation found here: https://rt-wiki.bestpractical.com/wiki/REST

Currently, this allows you to:
 * Create, Get, Update and Search Users
 * Get, Search Queues
 * Create, Get, Update, Merge and Search Tickets
 * Poll for ticket updates (based on either Created, LastUpdate, Resolved or Started timestamps)
 * Get and Update Ticket Links
 * List and Get Ticket attachments
 * List, Get, Create and List Summary of Ticket History Entries (comments)

One operator, "Search queues", in this connector, was not found in the documentation, but was guessable based upon the other endpoints. There might be more undocumented endpoints. 

## Building and installing
To build the connector download the code and run "mvn install"

Because "mvn install" adds the package to your local maven repo, you can edit your Mule projects pom.xml file and add the dependency there:

    <dependency>
        <groupId>com.redpillinpro.requestTrackerMuleConnector</groupId>
        <artifactId>mule-requesttracker-connector</artifactId>
        <version>1.0.3-SNAPSHOT</version>
        <classifier>mule-plugin</classifier>
    </dependency>
