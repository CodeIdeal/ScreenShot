package cabana.tk.screenshot;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.OutputStream;

import cabana.tk.screenshot.view.FloatView;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final String BUTTON_STATE_CAPTURE = "button_state_capture";
    private static final String BUTTON_STATE_ENABLE = "button_state_enable";

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 110;
    private static final int REQUEST_CODE_WRITE_SETTINGS = 111;
    private FloatView mFloatView;
    private Button bt;
    private MainActivity mAct = this;

    private int mScreeniIndex;
    private String mButtonState = BUTTON_STATE_ENABLE;

//    private Thread thread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            while (true) {
//                SystemClock.sleep(100);
//                savecreen("Work/" + mScreeniIndex++ + ".png");
//            }
//        }
//    }, "task");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //当系统版本大于等于6.0时，悬浮权限要特殊获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                requestAlertWindowPermission();
            } else {
                ShowFloatButton();
            }
        } else {
            ShowFloatButton();
        }
    }


    /**
     * 调用系统原生可执行截屏文件screencap进行截屏，该文件处于/system/bin目录下
     *
     * @param name
     */
    public void savecreen(String name) {
        Log.d(TAG, "savecreen: 截屏开始");
        String cmd = "screencap -p /sdcard/" + name;
        try {
            // 权限设置
            Process p = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new
                    DataOutputStream(outputStream);
            // 将命令写入
            dataOutputStream.writeBytes(cmd);
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            outputStream.close();
            Log.d(TAG, "savecreen: 截屏成功");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //发送请求开启悬浮窗
    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    //接受请求结果，并执行请求成功后大逻辑
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Log.i(TAG, "onActivityResult granted");
                    ShowFloatButton();
                }
            }
        }
    }

    //显示悬浮按钮
    private void ShowFloatButton() {
        mFloatView = FloatView.getInstance(Utils.getContext());
        mFloatView.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 点击按钮");
//                if (mButtonState == mAct.BUTTON_STATE_ENABLE) {
//                    thread.start();
//                    mButtonState = mAct.BUTTON_STATE_CAPTURE;
//                    Log.d(TAG, "onClick: 开始任务");
//                } else if (mButtonState == mAct.BUTTON_STATE_CAPTURE) {
//                    thread.interrupt();
//                    mButtonState = mAct.BUTTON_STATE_ENABLE;
//                    mScreeniIndex = 0;
//                    Log.d(TAG, "onClick: 结束任务");
//                }
                Utils.createPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenShot");
                savecreen("ScreenShot/"+System.currentTimeMillis() + ".png");
            }
        });
        mFloatView.show(null);
    }
}
