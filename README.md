# Coronicle Backend

Coronicle is an app which allows users to first locally track their geo position. Once they are infected they can upload 
their traces to this backend. This allows all users to query with their own traces if they have been in close
contact to someone infected.

It is a Standard Spring Boot Application using MySql database.

Frontend: [found here](https://github.com/Coronicle/coronicle-be) - react native app

## Endpoints

`POST /infectedtrace` :
Used to save infected user's location history
 
   Request Body: 
   ```
      [{"lat":37.33165776,"lon":-122.03069996,"time":"2020-03-21T19:55:28.179Z"}]
   ```


`POST /checktracee` :
Used to check if given location history has the possibility to be infected by comparing to saved location history from      infected users.
 
   Request Body: 
   ```
      [{"lat":37.33165776,"lon":-122.03069996,"time":"2020-03-21T19:55:28.179Z"},    {"lat":37.331656,"lon":-122.03069996,"time":"2020-03-21T19:55:28.179Z"}]
   ``` 


## Building the application

You need **Java 13** to run and build this application.

Execute `./mvnw clean install` to build the project.
