package test.com.rxjavarxandroid.http;


import io.reactivex.ObservableOnSubscribe;

public abstract class MyOnSubscribe<C> implements ObservableOnSubscribe {
    private C c;

    public MyOnSubscribe(C c) {
        setT(c);
    }

    public C getT() {
        return c;
    }

    public void setT(C c) {
        this.c = c;
    }


}