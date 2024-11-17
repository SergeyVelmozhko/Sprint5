package ru.yandex.managers;

import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Status;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;

import java.io.*;
import java.nio.file.Files;


public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadFromFile(file);
    }

    public void save() {

        StringBuilder sb = new StringBuilder();
        sb.append("id,type,name,status,description,epic\n");

        for (Task task : tasks.values()) {
            sb.append(task.toWriter());
        }

        for (Epic epic : epics.values()) {
            sb.append(epic.toWriter());
        }

        for (Subtask subtask : subtasks.values()) {
            sb.append(subtask.toWriter());
        }
        try {
            Files.writeString(file.toPath(), sb);
        } catch (IOException e) {
            throw new ManagersSaveException("Ошибка при сохранении");
        }
    }

    public void loadFromFile(File file) {
        try {
            if (file.exists() && Files.size(file.toPath()) > 0) {

                FileReader reader = new FileReader("java-kanban-main/src/ru/yandex/file/history.txt");
                BufferedReader br = new BufferedReader(reader);
                while (br.ready()) {
                    String line = br.readLine();
                    String[] array = line.split(",");
                    switch (array[1]) {
                        case "TASK" -> {
                            Task task = new Task(array[2], array[4]);
                            task.status = Status.valueOf(array[3]);
                            task.id = Integer.parseInt(array[0]);
                            tasks.put(task.id, task);
                        }
                        case "EPIC" -> {
                            Epic epic = new Epic(array[2], array[4]);
                            epic.status = Status.valueOf(array[3]);
                            epic.id = Integer.parseInt(array[0]);
                            epics.put(epic.id, epic);
                        }
                        case "SUBTASK" -> {
                            Subtask subtask = new Subtask(array[2], array[4], Integer.parseInt(array[5]));
                            subtask.status = Status.valueOf(array[3]);
                            subtask.id = Integer.parseInt(array[0]);
                            subtasks.put(subtask.id, subtask);
                        }
                    }
                }
                br.close();
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