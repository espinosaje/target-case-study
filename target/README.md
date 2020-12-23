================================================
=============== Introduction ===================
================================================

The application is build with Spring Boot, using REST controllers to expose RESTful web services.
There are 3 controllers, one for NAME, which fetches data from the Redsky API, another one for PRICE, which connects to a NoSQL DB, and the Main service, a data aggregator that builds the final response.

The main entry point is service AggregatedProductController. It calls PriceController to connect to the NoSQL database and get the Pricing information, it then calls RedSkyProductController to get the product information from the Redsky API to extract the name. If the call to the external IP doesn’t find any records, it will pick up the name from the Price call (NoSQL DB) as a fallback route, if both calls fail to pull any data, the main service will return an empty response.
The 2 auxiliary controllers, Price and RedSkyProduct, are exposed as Rest services, thinking of scalability in case we need to modularize the system into microservices. This is also useful for individual testing using Postman.
Note: For simplicity, the main controller is directly calling their methods instead of hitting their endpoints.

The database is managed in MongoDB Cloud (Atlas), using the free tier of the cloud service; there is no need to install anything locally
 
It uses Github for code versioning, accessed through the Eclipse plugin. Jenkins is setup for CI/CD, building the code when the Git repository is updated, it also generates a Docker and uploads it to Docker Hub. The final image is uploaded to Amazon ECR and deployed to ECS.

Error Handling is only implemented in the main controller since it is the only one that is supposed to be exposed to consuming applications.

========================================================
====================== Links ===========================
========================================================

MongoDB - used princesita for login:
https://cloud.mongodb.com/v2/5fd4153d84833d6eab964146#clusters

Jira:
https://espinosachavez.atlassian.net/secure/RapidBoard.jspa?rapidView=6

AWS:
Used princesita account

========================================================
=============== Building the project ===================
========================================================

The following tools are needed to build, execute and test the project.
- Java 8 SDK (later versions might work too)
- Maven
- Docker
- AWS CLI

Running the application
- Checkout the code as a Maven project (it was developed using Eclipse)
- Run the "Maven Install" command
- Run Image/create container with the command "docker run -p 8090:8080 target-case-study:target-image" (or run the image from Docker Desktop)

Testing

The generated image is deployed to Amazon Web Services's ECR and a task is running on AWS's ECS. The below Postman collection has groups of requests to test on Localhost, Local container (started on port 8090) and AWS.
https://www.getpostman.com/collections/865e9d44182b6d0370c0
