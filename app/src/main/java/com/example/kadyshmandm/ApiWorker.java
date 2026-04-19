package com.example.kadyshmandm;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiWorker extends Worker {

    private static final String TAG = "ApiWorker";
    private static final String API_URL = "https://dog.ceo/api/breeds/image/random";

    public ApiWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.v(TAG, "Загрузка из API началась");

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Android-App");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Ошибка ответа: " + responseCode);
                return Result.failure();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            String jsonResponse = response.toString().trim();
            Log.v(TAG, "Ответ API: " + jsonResponse);

            String imageUrl = parseImageUrl(jsonResponse);
            if (imageUrl == null || imageUrl.isEmpty()) {
                Log.e(TAG, "Не удалось извлечь URL");
                return Result.failure();
            }

            Log.v(TAG, "URL картинки: " + imageUrl);

            Data outputData = new Data.Builder()
                    .putString("image_url", imageUrl)
                    .build();

            return Result.success(outputData);

        } catch (Exception e) {
            Log.e(TAG, "Ошибка: " + e.getMessage(), e);
            e.printStackTrace();
            return Result.failure();
        }
    }

    private String parseImageUrl(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("message");
        } catch (Exception e) {
            Log.e(TAG, "Ошибка парсинга: " + e.getMessage());
            return null;
        }
    }
}