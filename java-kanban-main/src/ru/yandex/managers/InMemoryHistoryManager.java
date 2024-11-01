package ru.yandex.managers;

import ru.yandex.tasks.Task;

import java.util.HashMap;
import java.util.LinkedHashSet;


public class InMemoryHistoryManager<T extends Task> implements HistoryManager {
    LinkedHashSet<T> history = new LinkedHashSet<>();


    @Override
    public void addHistory(Task task) {
        history.remove(task);
        if (history.size() > 10) {
            history.removeFirst();
        }
        history.addLast((T) task);
    }


    @Override
    public void getHistory() {
        System.out.println(history);
    }
}

