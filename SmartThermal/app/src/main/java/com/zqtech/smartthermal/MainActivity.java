package com.zqtech.smartthermal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zqtech.smartthermal.model.CameraDevice;
import com.zqtech.smartthermal.model.CameraManager;

public class MainActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private TextView textView;
    private SurfaceView surfaceView;
    private View layout;
    private EditText ip;
    private EditText port;
    private EditText userName;
    private EditText password;
    private Button start, set, stop;
    private boolean flag;

    private final StartRenderingReceiver receiver = new StartRenderingReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        CameraManager.getInstance().setContext(getApplicationContext());
        initView();
    }

    protected void startPlay(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(CameraManager.ACTION_START_RENDERING);
        filter.addAction(CameraManager.ACTION_DVR_OUTLINE);
        registerReceiver(receiver, filter);
        textView.setVisibility(View.VISIBLE);
        textView.setText(getString(R.string.tv_connect_cam));
        if(flag){
            flag = false;
            new Thread(){
                @Override
                public void run() {
                    CameraManager.getInstance().setSurfaceHolder(surfaceView.getHolder());
                    CameraManager.getInstance().realPlay();
                }
            }.start();
        }else {
            new Thread(){
                @Override
                public void run() {
                    CameraManager.getInstance().setSurfaceHolder(surfaceView.getHolder());
                    CameraManager.getInstance().initSDK();
                    CameraManager.getInstance().loginDevice();
                    CameraManager.getInstance().realPlay();
                }
            }.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(){
            @Override
            public void run() {
                CameraManager.getInstance().logoutDevice();
                CameraManager.getInstance().freeSDK();
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new Thread(){
                @Override
                public void run() {
                    CameraManager.getInstance().stopPlay();
                }
            }.start();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                startPlay();
                break;
            case R.id.stop:
                CameraManager.getInstance().stopPlay();
                break;
            case R.id.set:
                setPlayer();
                break;
        }
    }

    private CameraDevice getCameraDevice(){
        SharedPreferences sharedPreferences = getSharedPreferences("dbinfo", 0);
        String ip = sharedPreferences.getString("ip", "");
        String port = sharedPreferences.getString("port", "");
        String userName = sharedPreferences.getString("userName", "");
        String password = sharedPreferences.getString("password", "");
        CameraDevice cameraDevice = new CameraDevice(ip, port, userName, password);
        return cameraDevice;
    }

    protected void setDBData(String ip, String port, String userName, String password){
        SharedPreferences sharedPreferences = getSharedPreferences("dbinfo", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ip", ip);
        editor.putString("port", port);
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.commit();
    }

    public void setPlayer(){
        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.alert,(ViewGroup) findViewById(R.id.alert));
        ip = layout.findViewById(R.id.ip);
        port = layout.findViewById(R.id.port);
        userName = layout.findViewById(R.id.userName);
        password = layout.findViewById(R.id.passWord);
        CameraDevice db = getCameraDevice();
        ip.setText(db.getIp());
        port.setText(db.getPort());
        userName.setText(db.getUserName());
        password.setText(db.getPassword());

        new AlertDialog.Builder(this).setTitle("设置").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setDBData(ip.getText().toString(), port.getText()
                                        .toString(), userName.getText().toString(),
                                password.getText().toString());
                    }
                }).setNegativeButton("取消", null).show();
    }

    /**
     * 初始化
     */
    private void initView() {
        // 获取手机分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        textView = findViewById(R.id.tv_Loading);
        surfaceView = findViewById(R.id.sf_VideoMonitor);
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
        stop = findViewById(R.id.stop);
        stop.setOnClickListener(this);
        set = findViewById(R.id.set);
        set.setOnClickListener(this);

        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = dm.widthPixels - 30;
        lp.height = lp.width / 16 * 9;
        surfaceView.setLayoutParams(lp);
        textView.setLayoutParams(lp);
        Log.d(TAG, "视频窗口尺寸：" + lp.width + "x" + lp.height);

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, getLocalClassName() + " surfaceDestroyed");
                surfaceView.destroyDrawingCache();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, getLocalClassName() + " surfaceCreated");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                Log.d(TAG, getLocalClassName() + " surfaceChanged");
            }
        });

    }

    // 广播接收器
    private class StartRenderingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (CameraManager.ACTION_START_RENDERING.equals(intent.getAction())) {
                textView.setVisibility(View.GONE);
            }
            if (CameraManager.ACTION_DVR_OUTLINE.equals(intent.getAction())) {
                textView.setText(getString(R.string.tv_connect_cam_error));
            }
        }
    }
}