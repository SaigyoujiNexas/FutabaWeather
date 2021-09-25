package com.saigyouji.futabaweather.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool
{
    public static final ExecutorService ThreadPool = Executors.newCachedThreadPool();

    public static void addTask(Runnable task)
    {
        ThreadPool.execute(task);
    }
}
