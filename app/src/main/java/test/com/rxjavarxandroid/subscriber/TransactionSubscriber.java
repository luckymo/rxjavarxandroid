package test.com.rxjavarxandroid.subscriber;

import android.content.Context;
import android.util.Log;


import rx.Subscriber;
import test.com.rxjavarxandroid.exception.ApiException;

/**
 * 数据返回处理基类
 * Created by xiaomo on 2016/8/9.
 */
public class TransactionSubscriber<T> extends  BaseSubscriber<T> {

    private SubscriberListener mSubscriberListener;
    private Context context;

    public TransactionSubscriber(SubscriberListener mSubscriberListener,  Context context) {
        this.mSubscriberListener = mSubscriberListener;
        this.context = context;
    }

    /**
     * 事件未发出前调用，可以做一些准备工作
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 相当于onclick事件
     * @param t
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberListener != null) {
            mSubscriberListener.onNext(t);
        }
    }

    /**
     * 事件队列完结
     */
    @Override
    public void onCompleted() {
        super.onCompleted();
        if (mSubscriberListener != null) {
            mSubscriberListener.onCompleted();
        }
    }

    /**
     * 事件队列异常
     * @param e
     */
    @Override
    public void onError(ApiException e) {
        Log.v("onError",e.toString());
        if (mSubscriberListener != null) {
            mSubscriberListener.onError(e);
        }
    }

}
