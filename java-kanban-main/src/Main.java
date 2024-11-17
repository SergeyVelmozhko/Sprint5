import ru.yandex.managers.FileBackedTaskManager;
import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Path.of("java-kanban-main/src/ru/yandex/file/history.txt").toFile());

        //Созддание задачи для проверки
//        Managers managers = new Managers();
        Epic epic = new Epic("1","1");
        Task task = new Task("2", "2");
        Subtask subtask = new Subtask("3", "3", 5);

        //Проверка на добавление задачи в файл "history.txt"
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addEpic(epic);
        fileBackedTaskManager.addSudtask(subtask);
    }
}
