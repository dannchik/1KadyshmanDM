package com.example.kadyshmandm;

import static com.example.kadyshmandm.R.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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
                    .setTitle("⚠️ Подтверждение")
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
                        textDateTime.setText("📅 Дата: " + date);
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
                        textDateTime.setText("⏰ Время: " + time);
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
            textThreadStatus.setText("Статус: ⚠️ Блокировка UI...");
            Toast.makeText(this, "⚠️ Не нажимай дольше 5 секунд!", Toast.LENGTH_LONG).show();

            try {
                Thread.sleep(20000);
                textThreadStatus.setText("Статус: ✅ Завершено");
            } catch (InterruptedException e) {
                e.printStackTrace();
                textThreadStatus.setText("Статус: ❌ Прервано");
            }
        });

        findViewById(R.id.btnThreadAsync).setOnClickListener(v -> {
            textThreadStatus.setText("Статус: 🧵 Поток запущен...");

            new Thread(() -> {
                try {
                    for (int i = 1; i <= 20; i++) {
                        Thread.sleep(1000);
                        int finalI = i;
                        runOnUiThread(() -> {
                            textThreadStatus.setText("Статус: 🧵 Секунда " + finalI + "/20");
                        });
                    }

                    runOnUiThread(() -> {
                        textThreadStatus.setText("Статус: ✅ Завершено");
                        Toast.makeText(this, "Поток завершён!", Toast.LENGTH_SHORT).show();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        textThreadStatus.setText("Статус: ❌ Прервано");
                    });
                }
            }).start();
        });

        textHandlerStatus = findViewById(R.id.textHandlerStatus);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                int counter = msg.getData().getInt("counter");
                textHandlerStatus.setText("📨 Handler: тик " + counter + "/50");

                if (counter == 50) {
                    Toast.makeText(MainActivity.this, "Handler завершён!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        findViewById(R.id.btnHandler).setOnClickListener(v -> {
            textHandlerStatus.setText("📨 Handler: запущен...");

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
            textWorkStatus.setText("⚙️ Запуск 3 задач последовательно...");

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
            textWorkStatus.setText("⚙️ Запуск 2 задач параллельно...");

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
            textWorkStatus.setText("🌐 Загрузка из API...");
            imageApi.setImageResource(android.R.drawable.ic_menu_gallery);

            OneTimeWorkRequest apiWork = new OneTimeWorkRequest.Builder(ApiWorker.class).build();

            WorkManager.getInstance(this)
                    .getWorkInfoByIdLiveData(apiWork.getId())
                    .observe(this, workInfo -> {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                String imageUrl = workInfo.getOutputData().getString("image_url");

                                if (imageUrl.endsWith(".mp4") || imageUrl.endsWith(".webm")) {
                                    textWorkStatus.setText("❌ API вернул видео, а не картинку");
                                    Toast.makeText(this, "Попробуй ещё раз — пришло видео", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                textWorkStatus.setText("✅ API: загрузка завершена");

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

        // 🔹 SharedPreferences
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

        // 🔹 База данных (SQLite) - Практика 5, Задание 2
        dbHelper = new DatabaseHelper(this);
        editItemTitle = findViewById(R.id.editProductName);
        editItemDescription = findViewById(R.id.editProductPrice);
        editItemValue = findViewById(R.id.editProductQty);
        editItemCategory = findViewById(R.id.editProductCategory);
        editDeleteId = findViewById(R.id.editDeleteId);
        textDbResult = findViewById(R.id.textDbResult);
        textDbList = findViewById(R.id.textDbList);

        // ➕ Добавить запись
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

        // 🔍 Найти запись
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

        // ✏️ Изменить запись
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

        // 🗑️ Удалить запись по ID
        findViewById(R.id.btnDbDelete).setOnClickListener(v -> {
            String idStr = editDeleteId.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Введите ID для удаления!", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("🗑️ Подтверждение")
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

        // Загрузить список при старте
        refreshItemList();
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

    // 🔹 Вспомогательные методы для БД
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
}