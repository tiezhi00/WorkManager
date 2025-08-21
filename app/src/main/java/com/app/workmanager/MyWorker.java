package com.app.workmanager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker{
    private static final String TAG = "MyWorker";

    //构造方法
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    //doWork方法
    @NonNull
    @Override
    public Result doWork() {
        //获取输入数据
        String inputData = getInputData().getString(MainActivity.INPUT_DATA_KEY);
        //完成后台任务
        try {
            Log.d(TAG, "doWork: $inputData 后台任务开始执行...");
            Thread.sleep(3000);
            Log.d(TAG, "doWork:$inputData 后台任务执行完成...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success(new Data(
                new Data.Builder()
                        .putString(MainActivity.OUTPUT_DATA_KEY, inputData + " 任务完成")
                        .build()
        ));
    }
}
