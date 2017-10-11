# simplerec

This is a command-line example of Mahout's "user-based recommender in 5 minutes" (http://mahout.apache.org/users/recommender/userbased-5-minutes.html), using Spring Boot as the wrapper.

## Data

Generate a CSV file of three columns: user ID, title instance ID, strength of interaction. The strength metric I used was based on data I could get without much effort from our Postgres database:
* A download actually happened = 4
* A download was requested, but the file was never taken = 3
* The title was added to a reading list = 2
* [Hoped for data:] The title detail page was visited = 1

## Running
* Create the CSV, and add the path to the file to src/main/resources/application.properties, as the value for user.interactions.data
* mvn clean install
* java -jar target/rec-0.0.1-SNAPSHOT.jar <user ID> <number of recommendations>
* The list of recommended title instance IDs will be listed in the console