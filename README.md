# Getting Started

# E-INVOICING SYSTEM

## steps to run this application

you just have to run the application and access [app link](http://localhost:8080) running on port 8080
the initial data is uploaded when you run the app

database schema file is attached in this project to the root folder as `init.sql`

##Credential

Up on running an admin account is created with the following credentials `username: 'admin'` and `password: 'password'`
this provides an access and refresh token to be used throughout the runtime of the application where authentication is necessary
* access token lasts for 1 day while refreshToken lasts for 7 days.


##notification service and testing

notifications handling by rabbitmq is implemented and working as it should so are the unit and integration tests
