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

RequestTrackerMuleConnector-1.0.0-mule-plugin.jar can then be found in the "target" directory

Within Anypoint Studio you can load a local .jar file. 
 1. Either click on the small cog in your Mule Palette og right click your Mule Configuration file -> Manage Dependencies -> Manage Modules. 
 2. Click the green + (Plus symbol)
 3. Click "from Maven"
 4. Click on the Install button to Install a local dependency
 5. Click "Browse..." next til the "File" input field
 6. Navigate to the "target" directory and open the file RequestTrackerMuleConnector-1.0.0-mule-plugin.jar
 7. Click the "Install" button
 8. Click "OK"
 9. Click "Apply and Close"
 10. In your Mule Palette you can now go to Add Modules and add RequestTracker from the list
 
If you modify the code and rebuild the .jar file, you have to remove it from Studio and then add it again. Modules can be removed in step 2, where currently installed modules can be selected and removed. 

