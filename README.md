### Before running *mvn clean install* please update mysql root password in file [core.properties](https://git.crossover.com/Y1K7W1b/y1k7w1b/edit/master/core/src/main/resources/core.properties)


### Assumptions
* There is no tickets quantity limit in any route.
* User is not able to create extra accounts.
* When running *mvn install* the accounts get cleared because of integration tests.


### Admin user credentials
Email: **admin@admin.com**

Password: **secret**

### Setup
1 - Update mysql root password in file [core.properties](https://git.crossover.com/Y1K7W1b/y1k7w1b/edit/master/core/src/main/resources/core.properties)

2 - Execute *mvn clean install* in the parent folder.

3 - Open [http://localhost:8080] (http://localhost:8080) in your browser.
