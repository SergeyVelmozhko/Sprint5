import ru.yandex.managers.FileBackedTaskManager;
import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Path.of("java-kanban-main/src/ru/yandex/file/history.txt").toFile());

        //Созддание задачи для проверки

        Epic epic = new Epic("1","1");
        Task task = new Task("2", "2");
        Subtask subtask = new Subtask("3", "3", epic.id);
        task.startTime = LocalDateTime.of(2024, 9, 19, 18, 52);
        epic.startTime = LocalDateTime.of(2024, 9, 19, 18, 53);
        subtask.startTime = LocalDateTime.of(2024, 8, 19, 18, 52);

        //Проверка на добавление задачи в файл "history.txt"

        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addEpic(epic);
        fileBackedTaskManager.addSudtask(subtask);
        System.out.println(fileBackedTaskManager.getPrioritizedTasks());
    }
}
