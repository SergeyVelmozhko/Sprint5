package ru.yandex.managers;

import ru.yandex.tasks.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    private static int numberOfId = 0;
    public HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();


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
            System.out.println("Задача: " + task.name + "уже заведена");
        else {
            tasks.put(task.id, task);
            isIntersection(task);
        }


        inMemoryHistoryManager.addHistory(task);

    }

    @Override
    public void addEpic(Epic epic) {
        if (epics.containsKey(epic.id))
            System.out.println("Эпик: " + epic.name + "уже заведен");
        else {
            epics.put(epic.id, epic);
            isIntersection(epic);
        }
        inMemoryHistoryManager.addHistory(epic);

    }

    @Override
    public void addSudtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.id))
            System.out.println("Подзадача: " + subtask.name + "уже заведена");
        else {
            subtasks.put(subtask.id, subtask);
            epics.get(subtask.idEpic).idSubtasks.add(subtask.id);
            isIntersection(subtask);

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

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        TreeSet<Task> listTasks = new TreeSet<>(Comparator.comparing(o -> o.startTime));
        listTasks.addAll(tasks.values());
        listTasks.addAll(epics.values());
        listTasks.addAll(subtasks.values());
        return listTasks;
    }

    public Boolean isIntersection(Task newTask) {
        LocalDateTime newTaskStart = newTask.startTime;
        LocalDateTime newTaskEnd = newTask.getEndTime();

        for (Task task : tasks.values()) {
            if (newTask.startTime == null || newTask.getEndTime() == null) return false;
            if (newTaskStart.isBefore(task.getEndTime()) && task.startTime.isBefore(newTaskEnd)) {
                return true;
            }
        }

        for (Subtask subtask : subtasks.values()) {
            if (newTask.startTime == null || newTask.getEndTime() == null) return false;
            if (newTaskStart.isBefore(subtask.getEndTime()) && subtask.startTime.isBefore(newTaskEnd)) {
                return true;
            }
        }
        return false;
    }

    public static int setNumberOfId() {
        return ++numberOfId;
    }
}






