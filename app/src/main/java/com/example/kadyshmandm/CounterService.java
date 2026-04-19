package com.example.kadyshmandm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service {

    private static final String TAG = "CounterService";
    private Thread counterThread;
    private volatile boolean isRunning = false;
    private int counter = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Сервис создан");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Сервис запущен");

        if (!isRunning) {
            isRunning = true;
            counter = 0;

            counterThread = new Thread(() -> {
                while (isRunning) {
                    try {
                        Thread.sleep(1000);
                        counter++;
                        Log.d(TAG, "Секунд прошло: " + counter);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            counterThread.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        if (counterThread != null) {
            counterThread.interrupt();
        }
        Log.d(TAG, "Сервис остановлен. Итого секунд: " + counter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}