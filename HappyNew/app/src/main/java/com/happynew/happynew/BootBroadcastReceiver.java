package com.happynew.happynew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by MaiBenBen on 2017/1/30.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    //开机广播
    static final String action_boot = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        //启动 ClockService.class 活动
        Intent clockIntent=new Intent(context,ClockService.class);
        context.startService(clockIntent);
    }
}




