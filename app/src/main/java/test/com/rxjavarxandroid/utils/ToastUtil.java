package test.com.rxjavarxandroid.utils;

import android.content.Context;
import android.widget.Toast;


import test.com.rxjavarxandroid.comman.RxjavaRetrofitApplication;

/**
 * @ClassName: ToastUtil
 * @Description: Toast 工具类，Toast弹窗单例化，避免多次按键造成的Toast反复触发
 */
public class ToastUtil {

    public static ToastUtil toastUtil;
    public Context context;
    private Toast mToast;
    public ToastUtil(Context context){
        this.context = context;
    }
    public static synchronized ToastUtil getInstance(){
        if(null==toastUtil){
            toastUtil = new ToastUtil(RxjavaRetrofitApplication.getInstance().getApplicationContext());
        }
        return toastUtil;
    }


    /**
     * 自定义时间
     * @param text
     * @param duration
     */
    public void showToast(String text,int duration) {
        if(mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);  
        }
       mToast.show();
    }  
    public void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    /**
     * android 原生toast提示
     * @param content
     */
    public static void show(String content){
       Toast.makeText(RxjavaRetrofitApplication.getInstance(),content,Toast.LENGTH_SHORT).show();
    }

}
