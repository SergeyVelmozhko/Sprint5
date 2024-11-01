package ru.yandex.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class InMemoryHistoryManagerTest<T extends Task> {
    static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    static Task task = new Task("Первая задача", "Первое описание");
    static Epic epic = new Epic("Первый эпик", "Первое описание эпика");
    static Subtask subtask = new Subtask("Первая подзадача", "Первое описание подзадачи", epic);
    static Task task2 = new Task("Вторая задача", "Второе описание");
    static Epic epic2 = new Epic("Второй эпик", "Второе описание эпика");
    static Subtask subtask2 = new Subtask("Вторая подзадача", "Второе описание подзадачи", epic);

    @Test
    void addHistory() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSudtask(subtask, epic);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.addSudtask(subtask2, epic2);
        inMemoryTaskManager.addTask(task);

        LinkedHashSet<T> actual = new LinkedHashSet<>();
        LinkedHashSet<T> expected = new LinkedHashSet<>(inMemoryTaskManager.inMemoryHistoryManager.history);

        actual.add((T) task);
        actual.add((T) epic);
        actual.add((T) subtask);
        actual.add((T) task2);
        actual.add((T) epic2);
        actual.add((T) subtask2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void sizeHistory() {
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

        LinkedHashSet<T> actual = new LinkedHashSet<>(inMemoryTaskManager.inMemoryHistoryManager.history);
        Assertions.assertEquals(6, actual.size());
    }
}
