package com.app.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class MainActivity extends AppCompatActivity {
    private WorkManager workManager = WorkManager.getInstance(this);
    private Button btn_enqueue;
    private static final String TAG = "MainActivity";
    public static final String INPUT_DATA_KEY = "input_data_key";
    public static final String WORK_A_NAME = "Work A";
    public static final String WORK_B_NAME = "Work B";
    public static final String OUTPUT_DATA_KEY = "output_data_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_enqueue = findViewById(R.id.btn_enqueue);

        btn_enqueue.setOnClickListener(v -> {
            //加入工作请求
            OneTimeWorkRequest workRequestA = createWorkRequest(WORK_A_NAME);
            OneTimeWorkRequest workRequestB = createWorkRequest(WORK_B_NAME);
            workManager.beginWith(workRequestA) // 先执行工作A
                    .then(workRequestB) // 然后执行工作B
                    .enqueue(); // 入队



            // 监听任务状态
//            workManager.getWorkInfoByIdLiveData(workRequestA.getId()).observe(this, workInfo -> {
//                Log.d(TAG, "onCreate: " + workInfo);
//                if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//                    // 任务完成
//                    String outputData = workInfo.getOutputData().getString(OUTPUT_DATA_KEY);
//                    Log.d(TAG, "任务完成，输出数据: " + outputData);
//                }
//            });
        });


    }

    @NonNull
    private static OneTimeWorkRequest createWorkRequest(String name) {
        Constraints constraints = new Constraints.Builder()
//                .setRequiresCharging(true) // 需要充电
//                .setRequiresBatteryNotLow(true) // 电量不低
                .setRequiredNetworkType(NetworkType.CONNECTED) // 需要联网
                .build();
        // 创建一个一次性工作请求
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints) // 设置约束条件
                .setInputData(new androidx.work.Data.Builder()
                        .putString(INPUT_DATA_KEY, name) // 传递数据
                        .build())
                .build();
        return workRequest;
    }
}