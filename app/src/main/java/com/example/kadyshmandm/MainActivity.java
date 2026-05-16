package com.example.kadyshmandm;

import static com.example.kadyshmandm.R.*;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MyDialogFragment.CustomDialogListener {

    private static final String TAG = "MainActivity_Lifecycle";

    public static final String EXTRA_NAME = "com.example.kadyshmandm.NAME";
    public static final String EXTRA_SURNAME = "com.example.kadyshmandm.SURNAME";
    public static final String EXTRA_GROUP = "com.example.kadyshmandm.GROUP";
    public static final String EXTRA_AGE = "com.example.kadyshmandm.AGE";
    public static final String EXTRA_GRADE = "com.example.kadyshmandm.GRADE";

    private TextView textDateTime;
    private TextView textThreadStatus;
    private TextView textHandlerStatus;
    private TextView textWorkStatus;
    private ImageView imageApi;
    private Calendar calendar;
    private Handler handler;
    private EditText editUsername;
    private TextView textPrefsResult;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private DatabaseHelper dbHelper;
    private EditText editItemTitle, editItemDescription, editItemValue, editItemCategory, editDeleteId;
    private TextView textDbResult, textDbList;
    private WebView webView;
    private TextView textWebStatus;
    private EditText editWebUrl;
    private MediaPlayer mediaPlayer;
    private TextView textMediaStatus, textMediaTime;
    private SeekBar seekBarMedia;
    private Handler mediaHandler;
    private Runnable mediaRunnable;
    private static final String AUDIO_URL = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
    private ImageView imageAnim;
    private static final String CHANNEL_ID_NOW = "notify_now_channel";
    private static final String CHANNEL_ID_DELAYED = "notify_delayed_channel";
    private static final int NOTIFICATION_ID_NOW = 1;
    private static final int NOTIFICATION_ID_DELAYED = 2;
    private static final int NOTIFICATION_PERMISSION_CODE = 100;
    private static final int ALARM_PERMISSION_CODE = 101;
    private TextView textNotifyStatus;
    private EditText editJsonName, editJsonAge, editJsonRating, editJsonEmail, editJsonCity;
    private TextView textJsonResult;
    private static final String JSON_FILENAME = "user_data.json";
    // Content Provider
    private EditText editBookTitle, editBookAuthor, editBookYear, editBookPrice;
    private TextView textProviderResult;
    private static final Uri BOOKS_URI = Uri.parse("content://com.example.kadyshmandm.provider/books");
    private static final String BOOKS_JSON_FILE = "books_data.json";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Активность создана");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnScrollView).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ScrollViewActivity.class));
        });

        findViewById(R.id.btnCategoryList).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CategoryActivity.class));
        });

        findViewById(R.id.btnRecyclerView).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
        });

        findViewById(R.id.btnSpinner).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SpinnerActivity.class));
        });

        findViewById(R.id.btnNavigationDrawer).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NavigationDrawerActivity.class));
        });

        findViewById(R.id.btnBottomBar).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BottomBarActivity.class));
        });

        findViewById(R.id.btnSendDeclarative).setOnClickListener(v -> sendMessageDeclarative(v));

        findViewById(R.id.btnToLinear).setOnClickListener(v -> {
            Log.d(TAG, "onClick: Переход к LinearActivity");
            startActivity(new Intent(MainActivity.this, LinearActivity.class));
        });

        findViewById(R.id.btnStartChain).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Activity1NameSurname.class));
        });

        findViewById(R.id.btnStaticFragment).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StaticFragmentActivity.class));
        });

        findViewById(R.id.btnDynamicFragment).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DynamicFragmentActivity.class));
        });

        findViewById(R.id.btnContainerViewFragment).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ContainerViewFragmentActivity.class));
        });

        findViewById(R.id.btnStartService).setOnClickListener(v -> {
            startService(new Intent(MainActivity.this, CounterService.class));
            Toast.makeText(this, "Сервис запущен", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnStopService).setOnClickListener(v -> {
            stopService(new Intent(MainActivity.this, CounterService.class));
            Toast.makeText(this, "Сервис остановлен", Toast.LENGTH_SHORT).show();
        });

        textDateTime = findViewById(R.id.textDateTime);
        calendar = Calendar.getInstance();

        findViewById(R.id.btnAlertDialog).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Подтверждение")
                    .setMessage("Вы уверены, что хотите продолжить?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Да", (dialog, which) ->
                            Toast.makeText(this, "Подтверждено!", Toast.LENGTH_SHORT).show())
                    .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        findViewById(R.id.btnDatePicker).setOnClickListener(v -> {
            new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        String date = dayOfMonth + "." + (month + 1) + "." + year;
                        textDateTime.setText("Дата: " + date);
                        Toast.makeText(this, "Выбрана дата: " + date, Toast.LENGTH_SHORT).show();
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        findViewById(R.id.btnTimePicker).setOnClickListener(v -> {
            new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        textDateTime.setText("Время: " + time);
                        Toast.makeText(this, "Выбрано время: " + time, Toast.LENGTH_SHORT).show();
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            ).show();
        });

        findViewById(R.id.btnCustomDialog).setOnClickListener(v -> {
            MyDialogFragment dialog = new MyDialogFragment();
            dialog.show(getSupportFragmentManager(), "CustomDialog");
        });

        textThreadStatus = findViewById(R.id.textThreadStatus);

        findViewById(R.id.btnThreadBlock).setOnClickListener(v -> {
            textThreadStatus.setText("Статус: Блокировка UI...");
            Toast.makeText(this, "Не нажимай дольше 5 секунд!", Toast.LENGTH_LONG).show();

            try {
                Thread.sleep(20000);
                textThreadStatus.setText("Статус: Завершено");
            } catch (InterruptedException e) {
                e.printStackTrace();
                textThreadStatus.setText("Статус: Прервано");
            }
        });

        findViewById(R.id.btnThreadAsync).setOnClickListener(v -> {
            textThreadStatus.setText("Статус: Поток запущен...");

            new Thread(() -> {
                try {
                    for (int i = 1; i <= 20; i++) {
                        Thread.sleep(1000);
                        int finalI = i;
                        runOnUiThread(() -> {
                            textThreadStatus.setText("Статус: Секунда " + finalI + "/20");
                        });
                    }

                    runOnUiThread(() -> {
                        textThreadStatus.setText("Статус: Завершено");
                        Toast.makeText(this, "Поток завершён!", Toast.LENGTH_SHORT).show();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        textThreadStatus.setText("Статус: Прервано");
                    });
                }
            }).start();
        });

        textHandlerStatus = findViewById(R.id.textHandlerStatus);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                int counter = msg.getData().getInt("counter");
                textHandlerStatus.setText("Handler: тик " + counter + "/50");

                if (counter == 50) {
                    Toast.makeText(MainActivity.this, "Handler завершён!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        findViewById(R.id.btnHandler).setOnClickListener(v -> {
            textHandlerStatus.setText("Handler: запущен...");

            new Thread(() -> {
                for (int i = 1; i <= 50; i++) {
                    try {
                        Thread.sleep(500);

                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("counter", i);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });

        textWorkStatus = findViewById(R.id.textWorkStatus);
        imageApi = findViewById(R.id.imageApi);

        findViewById(R.id.btnWorkSequential).setOnClickListener(v -> {
            textWorkStatus.setText("Запуск 3 задач последовательно...");

            OneTimeWorkRequest work1 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task_name", "Task 1").build())
                    .build();

            OneTimeWorkRequest work2 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task_name", "Task 2").build())
                    .build();

            OneTimeWorkRequest work3 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task_name", "Task 3").build())
                    .build();

            WorkManager.getInstance(this)
                    .beginWith(work1)
                    .then(work2)
                    .then(work3)
                    .enqueue();

            Toast.makeText(this, "3 задачи запущены последовательно", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnWorkParallel).setOnClickListener(v -> {
            textWorkStatus.setText("Запуск 2 задач параллельно...");

            OneTimeWorkRequest work1 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task_name", "Parallel 1").build())
                    .build();

            OneTimeWorkRequest work2 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task_name", "Parallel 2").build())
                    .build();

            List<OneTimeWorkRequest> works = new ArrayList<>();
            works.add(work1);
            works.add(work2);

            WorkManager.getInstance(this).enqueue(works);

            Toast.makeText(this, "2 задачи запущены параллельно", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnWorkApi).setOnClickListener(v -> {
            textWorkStatus.setText("Загрузка из API...");
            imageApi.setImageResource(android.R.drawable.ic_menu_gallery);

            OneTimeWorkRequest apiWork = new OneTimeWorkRequest.Builder(ApiWorker.class).build();

            WorkManager.getInstance(this)
                    .getWorkInfoByIdLiveData(apiWork.getId())
                    .observe(this, workInfo -> {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                String imageUrl = workInfo.getOutputData().getString("image_url");

                                if (imageUrl.endsWith(".mp4") || imageUrl.endsWith(".webm")) {
                                    textWorkStatus.setText("API вернул видео, а не картинку");
                                    Toast.makeText(this, "Попробуй ещё раз — пришло видео", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                textWorkStatus.setText("API: загрузка завершена");

                                new Thread(() -> {
                                    try {
                                        HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                                        connection.connect();
                                        InputStream stream = connection.getInputStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(stream);

                                        runOnUiThread(() -> {
                                            imageApi.setImageBitmap(bitmap);
                                            Toast.makeText(this, "Картинка загружена!", Toast.LENGTH_SHORT).show();
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        runOnUiThread(() -> {
                                            Toast.makeText(this, "Ошибка загрузки картинки", Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                }).start();
                            } else {
                                textWorkStatus.setText("API: ошибка");
                            }
                        }
                    });

            WorkManager.getInstance(this).enqueue(apiWork);
        });

        editUsername = findViewById(R.id.editUsername);
        textPrefsResult = findViewById(R.id.textPrefsResult);

        findViewById(R.id.btnSavePrefs).setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            if (username.isEmpty()) {
                Toast.makeText(this, "Введите имя!", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_USERNAME, username);
            editor.apply();
            textPrefsResult.setText("Сохранено: " + username);
            Toast.makeText(this, "Имя сохранено!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnLoadPrefs).setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String username = prefs.getString(KEY_USERNAME, "Не найдено");
            textPrefsResult.setText("Загружено: " + username);
            if (!"Не найдено".equals(username)) {
                editUsername.setText(username);
            }
            Toast.makeText(this, "Имя загружено!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnUpdatePrefs).setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String oldUsername = prefs.getString(KEY_USERNAME, null);
            if (oldUsername == null) {
                Toast.makeText(this, "Сначала сохраните имя!", Toast.LENGTH_SHORT).show();
                return;
            }
            String newUsername = editUsername.getText().toString().trim();
            if (newUsername.isEmpty()) {
                Toast.makeText(this, "Введите новое имя!", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_USERNAME, newUsername);
            editor.apply();
            textPrefsResult.setText("Изменено: " + oldUsername + " → " + newUsername);
            Toast.makeText(this, "Имя изменено!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnDeletePrefs).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Подтверждение")
                    .setMessage("Удалить сохранённое имя пользователя?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove(KEY_USERNAME);
                        editor.apply();
                        editUsername.setText("");
                        textPrefsResult.setText("Удалено");
                        Toast.makeText(this, "Имя удалено!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        dbHelper = new DatabaseHelper(this);
        editItemTitle = findViewById(R.id.editProductName);
        editItemDescription = findViewById(R.id.editProductPrice);
        editItemValue = findViewById(R.id.editProductQty);
        editItemCategory = findViewById(R.id.editProductCategory);
        editDeleteId = findViewById(R.id.editDeleteId);
        textDbResult = findViewById(R.id.textDbResult);
        textDbList = findViewById(R.id.textDbList);

        findViewById(R.id.btnDbAdd).setOnClickListener(v -> {
            String title = editItemTitle.getText().toString().trim();
            String desc = editItemDescription.getText().toString().trim();
            String valueStr = editItemValue.getText().toString().trim();
            String category = editItemCategory.getText().toString().trim();

            if (title.isEmpty() || valueStr.isEmpty()) {
                Toast.makeText(this, "Заполните название и значение!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                Item item = new Item(title, desc, Double.parseDouble(valueStr), category);
                if (dbHelper.addItem(item)) {
                    textDbResult.setText("Добавлено: " + title);
                    refreshItemList();
                    clearItemFields();
                    Toast.makeText(this, "Запись добавлена!", Toast.LENGTH_SHORT).show();
                } else {
                    textDbResult.setText("Ошибка добавления");
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Неверный формат значения!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnDbFind).setOnClickListener(v -> {
            String title = editItemTitle.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(this, "Введите название для поиска!", Toast.LENGTH_SHORT).show();
                return;
            }

            Item item = dbHelper.findItem(title);
            if (item != null) {
                editItemDescription.setText(item.getDescription());
                editItemValue.setText(String.valueOf(item.getValue()));
                editItemCategory.setText(item.getCategory());
                textDbResult.setText("Найдено: " + item.toString());
            } else {
                textDbResult.setText("Не найдено: " + title);
            }
        });

        findViewById(R.id.btnDbUpdate).setOnClickListener(v -> {
            String title = editItemTitle.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(this, "Введите название!", Toast.LENGTH_SHORT).show();
                return;
            }

            Item existing = dbHelper.findItem(title);
            if (existing == null) {
                Toast.makeText(this, "Запись не найдена!", Toast.LENGTH_SHORT).show();
                return;
            }

            String newDesc = editItemDescription.getText().toString().trim();
            String newValueStr = editItemValue.getText().toString().trim();
            String newCategory = editItemCategory.getText().toString().trim();

            try {
                if (dbHelper.updateItem(existing.getId(), title, newDesc,
                        Double.parseDouble(newValueStr), newCategory)) {
                    textDbResult.setText("Обновлено: " + title);
                    refreshItemList();
                    Toast.makeText(this, "Запись обновлена!", Toast.LENGTH_SHORT).show();
                } else {
                    textDbResult.setText("Ошибка обновления");
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Неверный формат значения!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnDbDelete).setOnClickListener(v -> {
            String idStr = editDeleteId.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Введите ID для удаления!", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Подтверждение")
                    .setMessage("Удалить запись с ID=" + idStr + "?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        try {
                            if (dbHelper.deleteItem(Integer.parseInt(idStr))) {
                                textDbResult.setText("Удалено ID=" + idStr);
                                refreshItemList();
                                editDeleteId.setText("");
                                Toast.makeText(this, "Запись удалена!", Toast.LENGTH_SHORT).show();
                            } else {
                                textDbResult.setText("Не найдено ID=" + idStr);
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(this, "Неверный формат ID!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        refreshItemList();

        webView = findViewById(R.id.webView);
        textWebStatus = findViewById(R.id.textWebStatus);
        editWebUrl = findViewById(R.id.editWebUrl);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String defaultUrl = "https://online-edu.mirea.ru";
        editWebUrl.setText(defaultUrl);
        webView.loadUrl(defaultUrl);
        textWebStatus.setText("Загружено: " + defaultUrl);

        findViewById(R.id.btnLoadWeb).setOnClickListener(v -> {
            String url = editWebUrl.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(this, "Введите URL!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            textWebStatus.setText("Загрузка: " + url);
            webView.loadUrl(url);
            Toast.makeText(this, "Загружаю: " + url, Toast.LENGTH_SHORT).show();
        });

        textMediaStatus = findViewById(R.id.textMediaStatus);
        textMediaTime = findViewById(R.id.textMediaTime);
        seekBarMedia = findViewById(R.id.seekBarMedia);
        mediaHandler = new Handler(Looper.getMainLooper());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(AUDIO_URL);
            mediaPlayer.setOnPreparedListener(mp -> {
                textMediaStatus.setText("Готов к воспроизведению");
                seekBarMedia.setMax(mediaPlayer.getDuration());
                textMediaTime.setText(formatTime(0) + " / " + formatTime(mediaPlayer.getDuration()));
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                textMediaStatus.setText("Трек завершён");
                seekBarMedia.setProgress(0);
                textMediaTime.setText(formatTime(0) + " / " + formatTime(mediaPlayer.getDuration()));
            });
            mediaPlayer.prepareAsync();
            textMediaStatus.setText("Загрузка аудио...");
        } catch (IOException e) {
            textMediaStatus.setText("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        findViewById(R.id.btnPlay).setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                textMediaStatus.setText("Воспроизведение...");
                startProgressUpdater();
            }
        });

        findViewById(R.id.btnPause).setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                textMediaStatus.setText("Пауза");
                stopProgressUpdater();
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(v -> {
            if (mediaPlayer.isPlaying() || mediaPlayer.getCurrentPosition() > 0) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepareAsync();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                textMediaStatus.setText("Остановлено");
                seekBarMedia.setProgress(0);
                textMediaTime.setText(formatTime(0) + " / " + formatTime(mediaPlayer.getDuration()));
                stopProgressUpdater();
            }
        });

        seekBarMedia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    textMediaTime.setText(formatTime(progress) + " / " + formatTime(mediaPlayer.getDuration()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopProgressUpdater();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer.isPlaying()) {
                    startProgressUpdater();
                }
            }
        });

        imageAnim = findViewById(R.id.imageAnim);

        findViewById(R.id.btnAnimRotate).setOnClickListener(v -> {
            ObjectAnimator rotate = ObjectAnimator.ofFloat(imageAnim, "rotation", 0f, 360f);
            rotate.setDuration(1000);
            rotate.start();
        });

        findViewById(R.id.btnAnimMove).setOnClickListener(v -> {
            ObjectAnimator move = ObjectAnimator.ofFloat(imageAnim, "translationX", 0f, 200f);
            move.setDuration(1000);
            move.start();
        });

        findViewById(R.id.btnAnimScale).setOnClickListener(v -> {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageAnim, "scaleX", 1f, 2f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageAnim, "scaleY", 1f, 2f);
            scaleX.setDuration(1000);
            scaleY.setDuration(1000);
            scaleX.start();
            scaleY.start();
        });

        findViewById(R.id.btnAnimReset).setOnClickListener(v -> {
            imageAnim.setRotation(0f);
            imageAnim.setTranslationX(0f);
            imageAnim.setScaleX(1f);
            imageAnim.setScaleY(1f);
        });

        textNotifyStatus = findViewById(R.id.textNotifyStatus);

        createNotificationChannel(CHANNEL_ID_NOW, "Срочные уведомления", "Уведомления, которые показываются сразу");
        createNotificationChannel(CHANNEL_ID_DELAYED, "Отложенные уведомления", "Уведомления по расписанию");

        findViewById(R.id.btnNotifyNow).setOnClickListener(v -> {
            if (checkNotificationPermission()) {
                sendImmediateNotification();
                textNotifyStatus.setText("Уведомление отправлено");
                Toast.makeText(this, "Уведомление отправлено!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnNotifyDelayed).setOnClickListener(v -> {
            if (checkNotificationPermission() && checkAlarmPermission()) {
                scheduleDelayedNotification(10000); // 10 секунд
                textNotifyStatus.setText("Уведомление запланировано через 10 сек");
                Toast.makeText(this, "Уведомление запланировано!", Toast.LENGTH_SHORT).show();
            }
        });

        editJsonName = findViewById(R.id.editJsonName);
        editJsonAge = findViewById(R.id.editJsonAge);
        editJsonRating = findViewById(R.id.editJsonRating);
        editJsonEmail = findViewById(R.id.editJsonEmail);
        editJsonCity = findViewById(R.id.editJsonCity);
        textJsonResult = findViewById(R.id.textJsonResult);

        Gson gson = new Gson();

        findViewById(R.id.btnJsonSerialize).setOnClickListener(v -> {
            try {
                String name = editJsonName.getText().toString().trim();
                int age = Integer.parseInt(editJsonAge.getText().toString().trim());
                double rating = Double.parseDouble(editJsonRating.getText().toString().trim());
                String email = editJsonEmail.getText().toString().trim();
                String city = editJsonCity.getText().toString().trim();

                User user = new User(name, age, email, city, rating);
                String jsonString = gson.toJson(user);

                textJsonResult.setText("JSON:\n" + jsonString);
                Toast.makeText(this, "Сериализация успешна!", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Проверьте формат возраста и рейтинга!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnJsonDeserialize).setOnClickListener(v -> {
            try {
                String jsonString = textJsonResult.getText().toString();
                if (jsonString.contains("JSON:")) {
                    jsonString = jsonString.replace("JSON:\n", "").trim();
                }

                User user = gson.fromJson(jsonString, User.class);
                if (user != null) {
                    editJsonName.setText(user.getName());
                    editJsonAge.setText(String.valueOf(user.getAge()));
                    editJsonRating.setText(String.valueOf(user.getRating()));
                    editJsonEmail.setText(user.getEmail());
                    editJsonCity.setText(user.getCity());
                    textJsonResult.setText("Объект восстановлен:\n" + user.toString());
                    Toast.makeText(this, "Десериализация успешна!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Ошибка десериализации: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btnJsonSaveFile).setOnClickListener(v -> {
            try {
                String name = editJsonName.getText().toString().trim();
                int age = Integer.parseInt(editJsonAge.getText().toString().trim());
                double rating = Double.parseDouble(editJsonRating.getText().toString().trim());
                String email = editJsonEmail.getText().toString().trim();
                String city = editJsonCity.getText().toString().trim();

                User user = new User(name, age, email, city, rating);
                String jsonString = gson.toJson(user);

                FileOutputStream fos = openFileOutput(JSON_FILENAME, MODE_PRIVATE);
                OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                writer.write(jsonString);
                writer.close();
                fos.close();

                Toast.makeText(this, "Файл сохранён: " + JSON_FILENAME, Toast.LENGTH_SHORT).show();
                textJsonResult.setText("Сохранено в файл:\n" + jsonString);

            } catch (Exception e) {
                Toast.makeText(this, "Ошибка сохранения: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btnJsonLoadFile).setOnClickListener(v -> {
            try {
                FileInputStream fis = openFileInput(JSON_FILENAME);
                InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                int ch;
                while ((ch = reader.read()) != -1) {
                    sb.append((char) ch);
                }
                reader.close();
                fis.close();

                String jsonString = sb.toString();
                User user = gson.fromJson(jsonString, User.class);

                if (user != null) {
                    editJsonName.setText(user.getName());
                    editJsonAge.setText(String.valueOf(user.getAge()));
                    editJsonRating.setText(String.valueOf(user.getRating()));
                    editJsonEmail.setText(user.getEmail());
                    editJsonCity.setText(user.getCity());
                    textJsonResult.setText("Загружено из файла:\n" + user.toString());
                    Toast.makeText(this, "Файл загружен!", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(this, "Файл не найден или ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        editBookTitle = findViewById(R.id.editBookTitle);
        editBookAuthor = findViewById(R.id.editBookAuthor);
        editBookYear = findViewById(R.id.editBookYear);
        editBookPrice = findViewById(R.id.editBookPrice);
        textProviderResult = findViewById(R.id.textProviderResult);
        ContentResolver resolver = getContentResolver();
        Gson gsonCP = new Gson();

        findViewById(R.id.btnProviderInsert).setOnClickListener(v -> {
            try {
                String title = editBookTitle.getText().toString().trim();
                String author = editBookAuthor.getText().toString().trim();
                int year = Integer.parseInt(editBookYear.getText().toString().trim());
                double price = Double.parseDouble(editBookPrice.getText().toString().trim());

                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("author", author);
                values.put("year", year);
                values.put("price", price);

                Uri newUri = resolver.insert(BOOKS_URI, values);
                if (newUri != null) {
                    textProviderResult.setText("Добавлено: " + title);
                    Toast.makeText(this, "Книга добавлена!", Toast.LENGTH_SHORT).show();
                    clearBookFields();
                } else {
                    textProviderResult.setText("Ошибка добавления");
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Проверьте формат года и цены!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnProviderQuery).setOnClickListener(v -> {
            try {
                Cursor cursor = resolver.query(BOOKS_URI, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    StringBuilder sb = new StringBuilder("Книги:\n");
                    do {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
                        int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
                        double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                        sb.append("[").append(id).append("] ").append(title)
                                .append(" — ").append(author).append(" (").append(year).append("), ")
                                .append(price).append("₽\n");
                    } while (cursor.moveToNext());
                    cursor.close();
                    textProviderResult.setText(sb.toString());
                    Toast.makeText(this, "Загружено книг: " + (sb.toString().split("\n").length - 1), Toast.LENGTH_SHORT).show();
                } else {
                    textProviderResult.setText("Список пуст");
                    if (cursor != null) cursor.close();
                }
            } catch (Exception e) {
                textProviderResult.setText("Ошибка: " + e.getMessage());
            }
        });

        findViewById(R.id.btnProviderExportJson).setOnClickListener(v -> {
            try {
                Cursor cursor = resolver.query(BOOKS_URI, null, null, null, null);
                List<Book> books = new ArrayList<>();

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Book book = new Book(
                                cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
                                cursor.getString(cursor.getColumnIndexOrThrow("title")),
                                cursor.getString(cursor.getColumnIndexOrThrow("author")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("year")),
                                cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                        );
                        books.add(book);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

                String jsonString = gsonCP.toJson(books);

                FileOutputStream fos = openFileOutput(BOOKS_JSON_FILE, MODE_PRIVATE);
                fos.write(jsonString.getBytes());
                fos.close();

                textProviderResult.setText("Экспортировано " + books.size() + " книг в JSON");
                Toast.makeText(this, "Файл сохранён: " + BOOKS_JSON_FILE, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Ошибка экспорта: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btnProviderImportJson).setOnClickListener(v -> {
            try {
                FileInputStream fis = openFileInput(BOOKS_JSON_FILE);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();

                String jsonString = new String(buffer);
                Type bookListType = new TypeToken<List<Book>>(){}.getType();
                List<Book> books = gsonCP.fromJson(jsonString, bookListType);

                int imported = 0;
                for (Book book : books) {
                    ContentValues values = new ContentValues();
                    values.put("title", book.getTitle());
                    values.put("author", book.getAuthor());
                    values.put("year", book.getYear());
                    values.put("price", book.getPrice());
                    if (resolver.insert(BOOKS_URI, values) != null) {
                        imported++;
                    }
                }

                textProviderResult.setText("Импортировано " + imported + " книг из JSON");
                Toast.makeText(this, "Импортировано " + imported + " книг!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Файл не найден или ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Активность перезапускается после остановки");
    }

    public void sendMessageProgrammatic(View view) {
        Log.d(TAG, "sendMessageProgrammatic: Запуск перехода с объектом Student");

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editName = findViewById(R.id.editName);
        EditText editSurname = findViewById(R.id.editSurname);
        EditText editGroup = findViewById(R.id.editGroup);
        EditText editAge = findViewById(R.id.editAge);
        EditText editGrade = findViewById(R.id.editGrade);

        Student student = new Student(
                editName.getText().toString().trim(),
                editSurname.getText().toString().trim(),
                editGroup.getText().toString().trim(),
                editAge.getText().toString().trim(),
                editGrade.getText().toString().trim()
        );

        intent.putExtra("STUDENT_OBJECT", student);
        startActivity(intent);
    }

    public void sendMessageDeclarative(View view) {
        Log.d(TAG, "sendMessageDeclarative: Запуск перехода с объектом Student");

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editName = findViewById(R.id.editName);
        EditText editSurname = findViewById(R.id.editSurname);
        EditText editGroup = findViewById(R.id.editGroup);
        EditText editAge = findViewById(R.id.editAge);
        EditText editGrade = findViewById(R.id.editGrade);

        Student student = new Student(
                editName.getText().toString().trim(),
                editSurname.getText().toString().trim(),
                editGroup.getText().toString().trim(),
                editAge.getText().toString().trim(),
                editGrade.getText().toString().trim()
        );
        intent.putExtra("STUDENT_OBJECT", student);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String text) {
        Toast.makeText(this, "Введено: " + text, Toast.LENGTH_SHORT).show();
        if (textDateTime != null) {
            textDateTime.setText("Custom Dialog: " + text);
        }
    }
    private void refreshItemList() {
        List<Item> items = dbHelper.getAllItems();
        if (items.isEmpty()) {
            textDbList.setText("Список пуст");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Item i : items) {
                sb.append("[").append(i.getId()).append("] ").append(i.toString()).append("\n");
            }
            textDbList.setText(sb.toString());
        }
    }

    private void clearItemFields() {
        editItemTitle.setText("");
        editItemDescription.setText("");
        editItemValue.setText("");
        editItemCategory.setText("");
    }
    private String formatTime(int milliseconds) {
        int seconds = milliseconds / 1000;
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", mins, secs);
    }
    private void startProgressUpdater() {
        mediaRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBarMedia.setProgress(currentPosition);
                    textMediaTime.setText(formatTime(currentPosition) + " / " + formatTime(mediaPlayer.getDuration()));
                    mediaHandler.postDelayed(this, 1000);
                }
            }
        };
        mediaHandler.post(mediaRunnable);
    }

    private void stopProgressUpdater() {
        if (mediaRunnable != null) {
            mediaHandler.removeCallbacks(mediaRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopProgressUpdater();
    }
    private void createNotificationChannel(String channelId, String channelName, String channelDesc) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDesc);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
    private boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                new AlertDialog.Builder(this)
                        .setTitle("Требуется разрешение")
                        .setMessage("Приложению нужно разрешение для отправки уведомлений")
                        .setPositiveButton("OK", (dialog, which) -> {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                                    NOTIFICATION_PERMISSION_CODE);
                        })
                        .setNegativeButton("Отмена", (dialog, which) -> {
                            dialog.dismiss();
                            textNotifyStatus.setText("Разрешение отклонено");
                        })
                        .show();
                return false;
            }
        }
        return true;
    }
    private boolean checkAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                new AlertDialog.Builder(this)
                        .setTitle("Требуется разрешение")
                        .setMessage("Приложению нужно разрешение на точные будильники для отложенных уведомлений")
                        .setPositiveButton("Открыть настройки", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                            startActivity(intent);
                        })
                        .setNegativeButton("Отмена", (dialog, which) -> {
                            dialog.dismiss();
                            textNotifyStatus.setText("Разрешение отклонено");
                        })
                        .show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение получено!", Toast.LENGTH_SHORT).show();
                sendImmediateNotification();
            } else {
                Toast.makeText(this, "Разрешение отклонено", Toast.LENGTH_SHORT).show();
                textNotifyStatus.setText("Разрешение отклонено");
            }
        }
    }
    private void sendImmediateNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_NOW)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Практика 6")
                .setContentText("Это обычное уведомление из вашего приложения!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID_NOW, builder.build());
        }
    }
    private void scheduleDelayedNotification(long delayMillis) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("channel_id", CHANNEL_ID_DELAYED);
        intent.putExtra("notification_id", NOTIFICATION_ID_DELAYED);
        intent.putExtra("title", "Отложенное уведомление");
        intent.putExtra("text", "Прошло 10 секунд!");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerTime = System.currentTimeMillis() + delayMillis;

        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }

    private void clearBookFields() {
        editBookTitle.setText("");
        editBookAuthor.setText("");
        editBookYear.setText("");
        editBookPrice.setText("");
    }
}