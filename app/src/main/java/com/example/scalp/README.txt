Christian Martell and Carlos Rivera
May 10, 2019
https://github.com/chrmartell/Scalp

Major Features/Screens:
1. HomeActivity - holds a navigation drawer and fragment container for fragment such as below:
2. TicketsFragment - displays a custom list view with array adapter, shows all available tickets
3. PostaTicketFragment - allows user to enter data and post the ticket, also able to press able to
press a button to bring up the camera to take pictures of tickets
4. ProfileFragment - profile page for log in user
5. TicketInfoAct - when clicking a ticket in the listview go to an activity with full details and purchase option

Optional Features:
1. Camera (8pts) - when submitting ticket information a button is available to take a picture
2. Data storage using SQLite (10pts) - all of our user/ticket data and some functions are run using SQLite
3. Data storage using file read/write or data serialization (8pts) - in order to have an listview with multiple columns
you must create your own layout and use array lists/adapter. Because tickets are an object and not a primitive data
type we must instantiate an object of ticket when reading in from database and then fill out our list view
4. Data storage using key/value pair storage (6pts) - using sharedpreferences to create a user session as well as some features
such as a: remember me checkbox at login screen that works on sign out and app opening
5. Notification (6pts) - when a ticket is sold/purchased or picture is taken a notification is sent to device

Testing Methodologies:
Just creating users and tickets and a lot of trial error testing our code manually.
Not test cases or JUnit, just going in manually and trying to use the app as intended.

Usage:
No specific usage. Register should work completely fine so you can sign up and get in.

Lessons Learned:
So much. We didn't use any existing APIs. Everything we did was content we learned from class or watching YouTube videos.
However there is no video or class exercise that can teach us everything, so we take those simple lessons and apply them
to our much more complex app. By the end, we got really good at SQL statements as well the nuanaces of fragment vs activity,
and custom list view adapater.