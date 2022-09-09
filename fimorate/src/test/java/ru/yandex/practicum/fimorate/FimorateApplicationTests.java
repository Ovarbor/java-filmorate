package ru.yandex.practicum.fimorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.yandex.practicum.fimorate.controller.FilmController;
import ru.yandex.practicum.fimorate.controller.UserController;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FimorateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createUser() {
		UserController userController = new UserController();
		User user = new User("123@", "123", "123", LocalDate.now());
		userController.create(user);
		assertEquals(1, user.getId());
	}

//	@Test
//	void createTask() throws IOException, InterruptedException {
//		URI url = URI.create("http://localhost:8080/tasks/task/");
//		Task task = new Task("1", "1", Time_1, 5);
//		httpTaskServer.fileManager.createTask(task);
//		String json = gson.toJson(task);
//		final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
//		HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
//		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		assertEquals("POST", request.method(), "Неверный метод запроса.");
//		assertEquals(200, response.statusCode(), "Код обработки запроса неверен.");
//		assertEquals(1, task.getIdentifier(), "Идентификаторы не совпадают.");
//	}
}
