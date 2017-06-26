package test.com.rxjavarxandroid.comman;

import android.app.Application;


import com.facebook.stetho.Stetho;


import test.com.rxjavarxandroid.api.DomyShowService;

public class RxjavaRetrofitApplication extends Application {

    private static RxjavaRetrofitApplication instance;
    private DomyShowService mDomyShowService;


    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = this;
        }
        Stetho.initializeWithDefaults(this);
    }


    /**
     * 请求服务类

     */
    public DomyShowService getDomyShowService(){
        if(mDomyShowService==null) {
            mDomyShowService = new DomyShowService();
        }
        return mDomyShowService;
    }

    public static  RxjavaRetrofitApplication getInstance() {
        return instance;
    }

}

