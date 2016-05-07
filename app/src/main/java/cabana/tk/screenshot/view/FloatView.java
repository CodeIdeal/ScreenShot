package cabana.tk.screenshot.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import cabana.tk.screenshot.R;

/**
 * Created by k on 2015/12/8.
 */
public class FloatView implements View.OnTouchListener, View.OnClickListener {
    private static final String TAG = "FloatView";
    private Context context;
    private static FloatView mFloatView;
    private WindowManager mWM;
    private View mView;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

    private boolean isshow;

    float down_X;
    float down_Y;
    float up_X;
    float up_Y;
    private View.OnClickListener listener;
    private float move_x;
    private float move_y;

    private FloatView(Context context) {
        this.context = context;
        mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
    }

    public static FloatView getInstance(Context context) {
        if (mFloatView == null) {
            synchronized (FloatView.class) {
                if (mFloatView == null) {
                    return new FloatView(context);
                }
            }
        }
        return mFloatView;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void show(String address) {
        if (!isshow) {
            if (mView == null) {
                mView = View.inflate(context, R.layout.layout_floatview, null);
            }
            TextView textView = (TextView) mView.findViewById(R.id.flaotview_text);
            if (address != null)
                textView.setText(address);
            mView.setOnTouchListener(this);
            mView.setOnClickListener(this);
            mWM.addView(mView, mParams);
        }
        isshow = true;
    }

    public void hide() {
        if(isshow){
            if (mView != null) {
                if (mView.getParent() != null) {
                    mWM.removeView(mView);
                }
                mView = null;
            }
        }

        isshow = false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float start_X = 0;
        float start_Y = 0;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_X = motionEvent.getRawX();
                down_Y = motionEvent.getRawY();

                start_X = down_X;
                start_Y = down_Y;
                break;
            case MotionEvent.ACTION_UP:
//                up_X = motionEvent.getRawX();
//                up_Y = motionEvent.getRawY();
//                float diffx = up_X - start_X;
//                float diffy = up_Y - start_Y;
//                if (Math.abs(diffx) < 10 && Math.abs(diffy) < 10) {
//                    Log.d(TAG, "onTouch: onclick");
//                    listener.onClick(view);
//                }
                break;
            case MotionEvent.ACTION_MOVE:
                move_x = motionEvent.getRawX();
                move_y = motionEvent.getRawY();
                float diffX = move_x - down_X;
                float diffY = move_y - down_Y;
                mParams.x += diffX;
                mParams.y += diffY;
                mWM.updateViewLayout(mView, mParams);
                down_X = move_x;
                down_Y = move_y;
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
