package com.mine.app.controller;

import com.mine.app.controller.configuration.EmbeddedRedis;
import com.mine.app.model.api.TaskApi;
import com.mine.app.model.domain.TaskState;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EmbeddedRedis.class)
class TaskControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private static Jedis JEDIS_CLIENT;

    @BeforeAll
    public static void initBeforeAll() {
        JEDIS_CLIENT = new Jedis();
    }

    @BeforeEach
    public void init() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        JEDIS_CLIENT.flushDB();
    }

    @Test
    void testCreate() {
        final var toCreate = buildTask();
        final var result = createTask(toCreate);
        toCreate.setId(result.getId());
        assertEquals(toCreate, result);
    }

    @Test
    void testFindById() {
        final var created = createTask();
        final var retrieved = get(created.getUserId(), created.getId());
        assertEquals(created, retrieved);
    }

    @Test
    void testDelete() {
        final var created = createTask();
        RestAssured.given().delete("/api/v1/users/{userId}/tasks/{id}", created.getUserId(), created.getId()).then().statusCode(204);
        RestAssured.given().get("/api/v1/users/{userId}/tasks/{id}", created.getUserId(), created.getId())
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdateNotExistingEntity() {
        RestAssured.given()
                .body(buildTask())
                .header("Content-Type", "application/json")
                .put("/api/v1/users/{userId}/tasks/{id}", UUID.randomUUID(), UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    void testGetEmptyList() {
        final var result = RestAssured.given()
                .body(buildTask())
                .header("Content-Type", "application/json")
                .get("/api/v1/users/{userId}/tasks", UUID.randomUUID())
                .then()
                .statusCode(200)
                .extract()
                .as(TaskApi[].class);
        assertEquals(0, result.length);
    }

    @Test
    void testGetAllByUserId() {
        final var userId = "usr";
        final var otherUserId = "other-user";
        final var tasks = List.of(buildTask(userId), buildTask(userId), buildTask(otherUserId));
        tasks.forEach(this::createTask);
        final var result = RestAssured.given()
                .body(buildTask())
                .header("Content-Type", "application/json")
                .get("/api/v1/users/{userId}/tasks", userId)
                .then()
                .statusCode(200)
                .extract()
                .as(TaskApi[].class);
        assertEquals(2, result.length);
    }

    private TaskApi get(String userId, UUID id) {
        return RestAssured.given().get("/api/v1/users/{userId}/tasks/{id}", userId, id)
                .then()
                .statusCode(200)
                .extract()
                .as(TaskApi.class);
    }

    private TaskApi createTask(TaskApi task) {
        return RestAssured.given()
                .body(task)
                .header("Content-Type", "application/json")
                .post("/api/v1/users/{userId}/tasks", task.getUserId())
                .then()
                .statusCode(200)
                .extract()
                .as(TaskApi.class);
    }

    private TaskApi createTask() {
        final var toCreate = buildTask();
        return createTask(toCreate);
    }

    private TaskApi buildTask() {
        return buildTask(RandomStringUtils.randomNumeric(5));
    }

    private TaskApi buildTask(String userId) {
        return TaskApi.builder()
                .dueDate(LocalDate.now())
                .state(TaskState.TODO)
                .description("Task description " + RandomStringUtils.randomNumeric(1))
                .userId(userId)
                .build();
    }

}