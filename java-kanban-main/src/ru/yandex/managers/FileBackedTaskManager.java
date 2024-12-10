package ru.yandex.managers;

import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Status;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;

import java.io.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;


public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadFromFile(file);
    }

    public void save() {

        StringBuilder stringForSave = new StringBuilder();
        stringForSave.append("id,type,name,status,description,duration,startTime,epic\n");

        for (Task task : tasks.values()) {
            stringForSave.append(task.toWriter());
        }

        for (Epic epic : epics.values()) {
            stringForSave.append(epic.toWriter());
        }

        for (Subtask subtask : subtasks.values()) {
            stringForSave.append(subtask.toWriter());
        }
        try {
            Files.writeString(file.toPath(), stringForSave);
        } catch (IOException e) {
            throw new ManagersSaveException("Ошибка при сохранении");
        }
    }

    public void loadFromFile(File file) {
        try {
            if (file.exists() && Files.size(file.toPath()) > 0) {

                FileReader reader = new FileReader("java-kanban-main/src/ru/yandex/file/history.txt");
                BufferedReader readingData = new BufferedReader(reader);
                while (readingData.ready()) {
                    String line = readingData.readLine();
                    String[] array = line.split(",");
                    switch (array[1]) {
                        case "TASK" -> {
                            Task task = new Task(array[2], array[4]);
                            task.status = Status.valueOf(array[3]);
                            task.id = Integer.parseInt(array[0]);
                            task.duration = Duration.ofMinutes(Long.parseLong(array[5]));
                            if (array[6].equals("null")) {
                                task.startTime = null;
                            } else {
                                task.startTime = LocalDateTime.parse(array[6]);
                            }
                            tasks.put(task.id, task);
                        }
                        case "EPIC" -> {
                            Epic epic = new Epic(array[2], array[4]);
                            epic.status = Status.valueOf(array[3]);
                            epic.id = Integer.parseInt(array[0]);
                            epic.duration = Duration.ofMinutes(Long.parseLong(array[5]));
                            if (array[6].equals("null")) {
                                epic.startTime = null;
                            } else {
                                epic.startTime = LocalDateTime.parse(array[6]);
                            }
                            epics.put(epic.id, epic);
                        }
                        case "SUBTASK" -> {
                            Subtask subtask = new Subtask(array[2], array[4], Integer.parseInt(array[7]));
                            subtask.status = Status.valueOf(array[3]);
                            subtask.id = Integer.parseInt(array[0]);
                            subtask.duration = Duration.ofMinutes(Long.parseLong(array[5]));
                            if (array[6].equals("null")) {
                                subtask.startTime = null;
                            } else {
                                subtask.startTime = LocalDateTime.parse(array[6]);
                            }
                            subtasks.put(subtask.id, subtask);
                        }
                    }
                }
                readingData.close();
            }
        } catch (IOException e) {
            throw new ManagersSaveException("Ошибка записи");
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSudtask(Subtask subtask) {
        super.addSudtask(subtask);
        save();
    }
}