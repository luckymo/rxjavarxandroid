package test.com.rxjavarxandroid.http;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Rxjava封装工具类
 */
public class RxjavaUtil {

    /**
     * 在ui线程中工作
     *
     * @param uiTask
     */
    public static <T> void doInUIThread(UITask<T> uiTask) {
        doInUIThreadDelay(uiTask, 0, TimeUnit.MILLISECONDS);
    }


    /**
     * 延时在主线程中执行任务
     *
     * @param uiTask
     * @param time
     * @param timeUnit
     * @param <T>
     */
    public static <T> void doInUIThreadDelay(UITask<T> uiTask, long time, TimeUnit timeUnit) {
        Observable.just(uiTask)
                .delay(time, timeUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UITask<T>>() {
                    @Override
                    public void accept(UITask<T> uitask) {
                        uitask.doInUIThread();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }


    /**
     * 在IO线程中执行任务
     *
     * @param <T>
     */
    public static <T> void doInIOThread(IOTask<T> ioTask) {
        doInIOThreadDelay(ioTask, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * 延时在IO线程中执行任务
     *
     * @param <T>
     */
    public static <T> void doInIOThreadDelay(IOTask<T> ioTask, long time, TimeUnit timeUnit) {
        Observable.just(ioTask)
                .delay(time, timeUnit)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<IOTask<T>>() {
                    @Override
                    public void accept(IOTask<T> ioTask) {
                        ioTask.doInIOThread();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }


    /**
     * 执行Rx通用任务 (IO线程中执行耗时操作 执行完成调用UI线程中的方法)
     *
     * @param t
     * @param <T>
     */
    public static <T> void executeRxTask(CommonRxTask<T> t) {
        executeRxTaskDelay(t, 0, TimeUnit.MILLISECONDS);
    }


    /**
     * 延时执行Rx通用任务 (IO线程中执行耗时操作 执行完成调用UI线程中的方法)
     *
     * @param t
     * @param <T>
     */
    public static <T> void executeRxTaskDelay(CommonRxTask<T> t, long time, TimeUnit timeUnit) {
        MyOnSubscribe<CommonRxTask<T>> onsubscribe = new MyOnSubscribe<CommonRxTask<T>>(t) {

            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                getT().doInIOThread();
                e.onNext(getT());
                e.onComplete();
            }
        };
        Observable.create(onsubscribe)
                .delay(time, timeUnit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommonRxTask<T>>() {
                    @Override
                    public void accept(CommonRxTask<T> t) {
                        t.doInUIThread();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }



}