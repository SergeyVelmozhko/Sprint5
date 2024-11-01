package ru.yandex.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.tasks.*;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    static Task task = new Task("Первая задача", "Первое описание");
    static Epic epic = new Epic("Первый эпик", "Первое описание эпика");
    static Subtask subtask = new Subtask("Первая подзадача", "Первое описание подзадачи", epic);

    @BeforeEach
     void add() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSudtask(subtask, epic);
    }

    @Test
    void addsAndRemovesTasks() {
        assertEquals(1, inMemoryTaskManager.tasks.size());
        assertEquals(1, inMemoryTaskManager.epics.size());
        assertEquals(1, inMemoryTaskManager.subtasks.size());
        inMemoryTaskManager.removeTask();
        assertEquals(0, inMemoryTaskManager.tasks.size());
        assertEquals(0, inMemoryTaskManager.epics.size());
        assertEquals(0, inMemoryTaskManager.subtasks.size());
    }

    @Test
    void showsByID() {
        assertEquals(inMemoryTaskManager.tasks.toString(), inMemoryTaskManager.getObjectById(1));
        assertEquals(inMemoryTaskManager.epics.toString(), inMemoryTaskManager.getObjectById(2));
        assertEquals(inMemoryTaskManager.subtasks.toString(), inMemoryTaskManager.getObjectById(3));

    }

    @Test
    void moveHistory() {
        assertEquals(3, inMemoryTaskManager.inMemoryHistoryManager.history.size());
    }

    @Test
    void removeById() {
        inMemoryTaskManager.removeById(1);
        assertEquals(0, inMemoryTaskManager.tasks.size());

    }

    @Test
    void updatesTask(){
        Task secondTask = new Task("Вторая задача", "Описание второй задачи");
        secondTask.id = 1;
        inMemoryTaskManager.updateTask(secondTask, Status.DONE);
        assertEquals(secondTask, inMemoryTaskManager.tasks.get(1));
    }
    @Test
    void notOpportunitiesAddEpicToEpic() {
        Epic secondEpic = new Epic("Второй эпик", "Описание второго эпика");
        inMemoryTaskManager.addEpic(epic);
        assertFalse(inMemoryTaskManager.subtasks.containsValue(secondEpic));
    }
}