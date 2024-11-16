package ru.yandex.tasks;

public class Subtask extends Task {
    public int idEpic;
    public Subtask(String name, String description, int idEpic) {
        super(name, description);
        this.idEpic = idEpic;
    }

    @Override
    public String toWriter() {
        return String.format("%d,SUBTASK,%s,%s,%s,%d\n", id, name, status, description, idEpic);
    }

    @Override
    public String toString() {
        return "ru.yandex.Tasks.Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
