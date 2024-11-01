package ru.yandex.managers;

import ru.yandex.tasks.*;

import java.util.HashMap;

public class InMemoryTaskManager<T extends Task> implements TaskManager {
    private static int numberOfId = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    // Получение списка задач
    @Override
    public void getAllTask() {
        if (!tasks.isEmpty())
            System.out.println(tasks);
        if (!epics.isEmpty())
            System.out.println(epics);
        if (!subtasks.isEmpty())
            System.out.println(subtasks);
    }

    //Удаление всех задач
    @Override
    public void removeTask() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    //Получение по идентификатору
    @Override
    public String getObjectById(int id) {
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
            return tasks.toString();
        } else if (epics.containsKey(id)) {
            System.out.println(epics.get(id));
            return epics.toString();
        } else if (subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
            return subtasks.toString();
        } else
            return "Нет задачи с таким номером";
    }

    //Создание
    @Override
    public void addTask(Task task) {
        if (tasks.containsKey(task.id))
            System.out.println("Такая задача уже есть");
        else
            tasks.put(task.id, task);

        inMemoryHistoryManager.addHistory(task);

    }

    @Override
    public void addEpic(Epic epic) {
        if (epics.containsKey(epic.id))
            System.out.println("Такой эпик уже есть");
        else
            epics.put(epic.id, epic);

        inMemoryHistoryManager.addHistory(epic);

    }

    @Override
    public void addSudtask(Subtask subtask, Epic epic) {
        if (subtasks.containsKey(subtask.id))
            System.out.println("Такая подзадача уже есть");
        else {
            subtasks.put(subtask.id, subtask);
            epic.idSubtasks.add(subtask.id);
        }
        inMemoryHistoryManager.addHistory(subtask);
    }

    //Обновление
    @Override
    public void updateTask(Task secondTask, Status status) {
        if (tasks.containsKey(secondTask.id)) {
            tasks.put(secondTask.id, secondTask);
            secondTask.status = status;
        } else {
            System.out.println("Не найдена задача");
        }
    }

    @Override
    public void updateEpic(Epic epic1) {
        if (epics.containsKey(epic1.id)) {
            epics.put(epic1.id, epic1);
            Subtask subtask = subtasks.get(epic1.id);
            if (!subtasks.containsKey(epic1.id) || subtask.status == Status.NEW) {
                epic1.status = Status.NEW;
            } else if (subtasks.containsKey(epic1.id) && subtask.status == Status.DONE) {
                epic1.status = Status.DONE;
            } else
                epic1.status = Status.IN_PROGRESS;
        } else {
            System.out.println("Не найдена задача");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask1, Status status, Epic epic) {
        if (subtasks.containsKey(epic.id)) {
            subtasks.put(epic.id, subtask1);
            subtask1.status = status;
        } else {
            System.out.println("Не найдена задача");
        }
    }

    //Удаление по идентификатору
    @Override
    public void removeById(int id) {
        if (tasks.containsKey(id))
            tasks.remove(id);
        else if (epics.containsKey(id))
            epics.remove(id);
        else if (subtasks.containsKey(id))
            subtasks.remove(id);
        else
            System.out.println("Нет задачи с данным ID: " + id);
    }

    //Получение списка всех подзадач определённого эпика
    @Override
    public void getSudtaskByEpic(Epic epic) {
        for (Subtask el : subtasks.values()) {
            for (int i : epic.idSubtasks) {
                if (subtasks.containsKey(i)) {
                    el = subtasks.get(i);
                    System.out.println(el);
                }
            }
        }
    }

    @Override
    public void getHistory() {
        inMemoryHistoryManager.getHistory();
    }

    public static int setNumberOfId() {
        return ++numberOfId;
    }
}






