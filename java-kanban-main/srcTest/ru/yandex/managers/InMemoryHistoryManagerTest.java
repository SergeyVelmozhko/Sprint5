package ru.yandex.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.tasks.*;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class InMemoryHistoryManagerTest {
    static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    static Task task;
    static Epic epic;
    static Subtask subtask;
    static Task task2;
    static Epic epic2;
    static Subtask subtask2;

    public static void init(String nameTask, String descriptionTask,
                            String nameEpic, String descriptionEpic,
                            String nameSubtask, String descriptionSubtask,
                            String nameTask2, String descriptionTask2,
                            String nameEpic2, String descriptionEpic2,
                            String nameSubtask2, String descriptionSubtask2) {
        task = new Task(nameTask, descriptionTask);
        epic = new Epic(nameEpic, descriptionEpic);
        subtask = new Subtask(nameSubtask, descriptionSubtask, epic);
        task2 = new Task(nameTask2, descriptionTask2);
        epic2 = new Epic(nameEpic2, descriptionEpic2);
        subtask2 = new Subtask(nameSubtask2, descriptionSubtask2, epic);
    }

    @BeforeAll
    public static void parametersTask() {
        init("Первая задача", "Первое описание",
                "Первый эпик", "Первое описание эпика",
                "Первая подзадача", "Первое описание подзадачи",
                "Вторая задача", "Второе описание",
                "Второй эпик", "Второе описание эпика",
                "Вторая подзадача", "Второе описание подзадачи");
    }

    @Test
    void addHistory() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSudtask(subtask, epic);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.addSudtask(subtask2, epic2);
        inMemoryTaskManager.addTask(task);

        LinkedHashSet<Task> actual = new LinkedHashSet<>();
        LinkedHashSet<Task> expected = new LinkedHashSet<>(inMemoryTaskManager.inMemoryHistoryManager.history);

        actual.add(task);
        actual.add(epic);
        actual.add(subtask);
        actual.add(task2);
        actual.add(epic2);
        actual.add(subtask2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void sizeHistory() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSudtask(subtask, epic);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.addSudtask(subtask2, epic2);
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSudtask(subtask, epic);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.addSudtask(subtask2, epic2);
        inMemoryTaskManager.addTask(task);

        LinkedHashSet<Task> actual = new LinkedHashSet<>(inMemoryTaskManager.inMemoryHistoryManager.history);
        Assertions.assertEquals(6, actual.size());
    }
}
