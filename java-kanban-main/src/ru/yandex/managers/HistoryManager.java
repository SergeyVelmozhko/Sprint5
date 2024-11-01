package ru.yandex.managers;

import ru.yandex.tasks.Task;

public interface HistoryManager<T extends Task> {


    void addHistory(T task);



    public void getHistory();
}
