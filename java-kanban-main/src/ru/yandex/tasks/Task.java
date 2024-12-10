package ru.yandex.tasks;

import ru.yandex.managers.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    public String name;
    public String description;
    public int id;

    public Status status;
    public Duration duration = Duration.ZERO;
    public LocalDateTime startTime = null;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = InMemoryTaskManager.setNumberOfId();
        status = Status.NEW;
    }

    public String toWriter() {
        return String.format("%s,TASK,%s,%s,%s,%s,%s\n", id, name, status, description, duration.toMinutes(), startTime);
    }
    public LocalDateTime getEndTime() {
        if (startTime == null && duration == null) return null;
        return startTime.plus(duration);
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
