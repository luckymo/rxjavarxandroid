package test.com.rxjavarxandroid.api;

import android.content.Context;

import junit.framework.Assert;

import org.reactivestreams.Subscriber;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import test.com.rxjavarxandroid.entity.ApiResult;
import test.com.rxjavarxandroid.entity.ChannleEntity;
import test.com.rxjavarxandroid.exception.ApiException;
import test.com.rxjavarxandroid.http.HttpMethods;
import test.com.rxjavarxandroid.subscriber.SubscriberListener;
import test.com.rxjavarxandroid.subscriber.TransactionSubscriber;


/**
 * 网络请求计息类
 * Created by xiaomo on 2016/8/12.
 */
public class DomyShowService {

    /**
     * @param SubscriberListener
     * @param context
     */
    public void getChannleList(SubscriberListener SubscriberListener, Context context, String url,LinkedHashMap<String, String> map){
        if(map==null) {
         map=new LinkedHashMap<>();
        }
        Observable observable =    HttpMethods.getInstance().getApiService().getChannleList(url,map);
        toSubscribe(observable, new TransactionSubscriber<ApiResult<List<ChannleEntity>>>(SubscriberListener,context));
    }

    /**
     * @param SubscriberListener
     * @param context
     */
    public void post(SubscriberListener SubscriberListener, Context context, String url, HashMap<String, String> map){
        if(map==null) {
            map=new LinkedHashMap<>();
        }
        Observable observable =    HttpMethods.getInstance().getApiService().post(url,map);
        toSubscribe(observable, new TransactionSubscriber<ApiResult>(SubscriberListener,context));
    }

    private <T> void toSubscribe(Observable<T> o, Observer<T> s){
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //设置重连请求次数
                .doOnSubscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        atomicInteger.incrementAndGet();
                    }
                })
                .retry(2)
                .onErrorResumeNext(new HttpResponseFunc<T>())//网络&解析错误转换为友好提示
                .subscribe(s);
    }

    /**
     * 服务器返回的错误码(400，504等)转换为用户可读的文字
     * @param <T>
     */
    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {

        @Override
        public Observable<T> apply(Throwable throwable) {
            return Observable.error(ApiException.handleException(throwable));
        }
    }

}
