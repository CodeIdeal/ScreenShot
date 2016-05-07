package cabana.tk.screenshot;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by k on 2016-5-2.
 */
public class ShotApplication extends Application{

    private static Context mContext;
    private static SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mSharedPreferences = getSharedPreferences("Main",BIND_AUTO_CREATE);
    }

    public static Context getContext(){
        return mContext;
    }

    public static SharedPreferences getSP(){
        return mSharedPreferences;
    }
}
