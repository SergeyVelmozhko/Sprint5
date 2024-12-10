package ru.yandex.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class FileBackedTaskManagerTest {
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
    public void saveTest() throws IOException {
        Epic epic = new Epic("1", "1");
        Task task = new Task("2", "2");
        Subtask subtask = new Subtask("3", "3", epic.id);
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addTask(epic);
        fileBackedTaskManager.addTask(subtask);
        Assertions.assertTrue(Files.size(file.toPath()) > 0);
    }

    @Test
    public void loadFromFileTest() {
        Epic epic = new Epic("1", "1");
        Task task = new Task("2", "2");
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addEpic(epic);
        FileBackedTaskManager fbtm = new FileBackedTaskManager(file);


        Assertions.assertEquals(epic.toWriter(), fbtm.epics.get(4).toWriter());
        Assertions.assertEquals(task.toWriter(), fbtm.tasks.get(5).toWriter());
        }
    }
