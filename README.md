## Prerequisites

1. JDK 17 installed
2. Maven installed
3. (Optional) Docker installed to bootstrap redis service, alternatively custom redis instance host and port can be
   configured through application.yml

## Build and run

1. Navigate to the root of the project task-app
2. Execute command "mvn clean install test package". Note: integration tests are using embedded redis, so test might
   fail if default redis port is occupied.
    1. If you have docker installed, you may start dockerized redis by running "docker-compose up -d"
3. Navigate to target folder
4. Execute "java -jar task-app-1.0-SNAPSHOT.jar"

## Commands

After running application locally you should be able to perform the following commands. Note: service is equipped with
basic validation, so failing to provide description, status or invalid date in the past will result in 400 error. For
running on local machine host: localhost, port: 8080

1. curl -X POST http://{host}:{port}/api/v1/users/{userId}/tasks -d '{"description":"Task description 1","state":"TODO"
   ,"dueDate":"2022-12-20"}' -H "Content-Type: application/json"
2. curl http://{host}:{port}/api/v1/users/{userId}/tasks
3. curl http://{host}:{port}/api/v1/users/{userId}/tasks/{id}
4. curl -X DELETE http://{host}:{port}/api/v1/users/{userId}/tasks/{id}
5. curl -X PUT http://{host}:{port}/api/v1/users/{userId}/tasks/{id} -d '{"description":"Task description 1","state":"
   IN_PROGRESS","dueDate":"2022-12-20"}' -H "Content-Type: application/json"