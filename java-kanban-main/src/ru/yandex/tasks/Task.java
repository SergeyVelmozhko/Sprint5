package ru.yandex.tasks;

import ru.yandex.managers.InMemoryTaskManager;

public class Task {
    public String name;
    public String description;
    public int id;

    public Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = InMemoryTaskManager.setNumberOfId();
        status = Status.NEW;
    }

    public String toWriter() {
        return String.format("%s,TASK,%s,%s,%s\n", id, name, status, description);
    }

    @Override
    public String toString() {
        return "ru.yandex.Tasks.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
