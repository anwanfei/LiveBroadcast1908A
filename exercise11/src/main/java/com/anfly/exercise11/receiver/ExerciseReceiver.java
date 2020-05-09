package com.anfly.exercise11.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.anfly.exercise11.activity.VpActivity;
import com.anfly.exercise11.bean.FoodBean;

public class ExerciseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FoodBean data = (FoodBean) intent.getSerializableExtra("data");
        Intent intent2 = new Intent(context, VpActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra("data", data);
        context.startActivity(intent2);
    }
}
