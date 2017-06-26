package test.com.rxjavarxandroid.dao;


import test.com.rxjavarxandroid.comman.RxjavaRetrofitApplication;

/**
 * Created by xiaomo on 2016/8/17.
 * 将已创建的多表Manager对象封装在一个类
 */
public class DaoUtils {

    private static  ChannleManager mChannleManager;
    /**
     * 单列模式获取HirstoryManager对象
     * @return
     */
    public static ChannleManager getChannleInstance(){
        if (mChannleManager == null) {
            mChannleManager = new ChannleManager(RxjavaRetrofitApplication.getInstance());
        }
        return mChannleManager;
    }

}
