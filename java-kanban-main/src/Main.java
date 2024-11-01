import ru.yandex.managers.InMemoryTaskManager;
import ru.yandex.managers.Managers;
import ru.yandex.tasks.Epic;
import ru.yandex.tasks.Subtask;
import ru.yandex.tasks.Task;

public class Main {

    public static void main(String[] args) {


        Managers managers = new Managers();
        managers.getDefault();
        InMemoryTaskManager inMemoryTaskManager = managers.getDefault();
        Epic epic = new Epic("1","1");
        Task task = new Task("2", "2");
        Subtask subtask = new Subtask("3", "3", epic);
        Epic epic2 = new Epic("4","4");
        Task task2 = new Task("5", "5");
        Subtask subtask2 = new Subtask("6", "6", epic);


       //Создание
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSudtask(subtask, epic);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.addSudtask(subtask2, epic2);
        inMemoryTaskManager.addSudtask(subtask, epic);
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task2);

        inMemoryTaskManager.getHistory();

//        // Получение списка задач
//        taskManager.getAllTask();
//
//        //Получение по идентификатору
//        taskManager.getObjectById(1);
//
//        //Удаление всех задач
//        taskManager.removeTask(); // Скрыто для проверки обновления
//
//        //Обновление
//        ru.yandex.Tasks.Task task1 = new ru.yandex.Tasks.Task("Task1", "descTask1");
//        ru.yandex.Tasks.Epic epic1 = new ru.yandex.Tasks.Epic("Epic1", "descEpic1");
//        ru.yandex.Tasks.Subtask subtask1 = new ru.yandex.Tasks.Subtask("subtask1", "descSubtask1", epic);
//
//        taskManager.updateTask(task1, ru.yandex.Tasks.Status.IN_PROGRESS);
//        taskManager.updateEpic(epic1);
//        taskManager.updateSubtask(subtask1, ru.yandex.Tasks.Status.DONE, epic);
//        taskManager.getAllTask(); // проверка на обновление
//
//        //Удаление по идентификатору
//        taskManager.removeById(1);
//        taskManager.getAllTask(); // Проверка на удаление
//
//        //Получение списка всех подзадач определённого эпика
//        taskManager.getSudtaskByEpic(epic);







    }
}
