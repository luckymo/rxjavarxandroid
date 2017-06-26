package test.com.rxjavarxandroid.subscriber;

import android.content.Context;
import android.util.Log;


import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;
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
    public void onComplete() {
        super.onComplete();
        if (mSubscriberListener != null) {
            mSubscriberListener.onComplete();
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

    @Override
    public void onSubscribe(Disposable s) {
    super.onSubscribe(s);
        if (mSubscriberListener != null) {
            mSubscriberListener.onSubscribe(s);
        }
    }
}
