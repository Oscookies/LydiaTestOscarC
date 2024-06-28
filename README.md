# Lydia App
## For Android Dev Role


### Summary:

The app fetches users from the https://randomuser.me/api/1.3/ endpoint and displays it in the app with pagination.
The app handles a click on a user to see more details about the user.


### Requirements

● Compiling project ✓
● Kotlin ✓
● Clear code ✓ (I hope)
● Offline mode / Connectivity issue handling
    - Done adding a Room DB to store the users
    - Error propagation from network layer to view layer in order to know if there is an error (in this case, Internet Connection Error)


### External libraries used

Retrofit
- Network requests

Coil
- Image loading

Sandwich (https://github.com/skydoves/sandwich)
- Makes getting responses and handling errors from the API easier, like iOS enum Result<Success, Failure>




