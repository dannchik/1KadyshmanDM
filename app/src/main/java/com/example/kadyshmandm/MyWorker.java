package com.example.kadyshmandm;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    private static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String taskName = getInputData().getString("task_name");
        if (taskName == null) {
            taskName = "Task";
        }

        Log.v(TAG, taskName + " - началась");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        Log.v(TAG, taskName + " - завершена");
        return Result.success();
    }
}