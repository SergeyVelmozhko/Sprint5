package ru.yandex.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeTaskManagerTest {
    static File file;
    static FileBackedTaskManager fileBackedTaskManager;

    @BeforeAll
    public static void begin() throws IOException {
        file = File.createTempFile("Для проверки", ".txt");
        fileBackedTaskManager = new FileBackedTaskManager(file);
    }
    @BeforeEach
    public void delete() throws IOException {
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void saveAndPrioritizedTasksTest() throws IOException {
        Epic epic = new Epic("Задача 1", "1");
        Task task = new Task("Задача 2", "2");
        Subtask subtask = new Subtask("Задача 3", "3", epic.id);
        task.startTime = LocalDateTime.of(2024, 9, 19, 18, 52);
        epic.startTime = LocalDateTime.of(2024, 9, 19, 18, 53);
        subtask.startTime = LocalDateTime.of(2024, 9, 19, 18, 51);
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addTask(epic);
        fileBackedTaskManager.addTask(subtask);
        Assertions.assertTrue(Files.size(file.toPath()) > 0);
        fileBackedTaskManager.getPrioritizedTasks().stream().forEach(i -> System.out.println(i.name + ":" + i.startTime));
    }

    @Test
    public void isIntersectionTest() {
        Epic epic = new Epic("1", "1");
        Task task = new Task("2", "2");
        task.startTime = LocalDateTime.of(2024, 9, 19, 18, 52);
        task.duration = Duration.ofMinutes(90);
        epic.startTime = LocalDateTime.of(2024, 9, 19, 18, 53);

        Assertions.assertFalse(fileBackedTaskManager.isIntersection(epic));
        Assertions.assertTrue(fileBackedTaskManager.isIntersection(task));
    }
}

