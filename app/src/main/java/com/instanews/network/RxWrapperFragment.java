package com.instanews.network;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mohamed Elgendy on 22/5/2017.
 */

public class RxWrapperFragment extends Fragment{

    /**
     * Represents a generic action that needs to be executed by another party.
     *
     * @param <T> Type of the return that this action results in
     */
    public interface Action<T> {

        T run() throws Throwable;

    }

    /**
     * Represents a callback to return a generic result.
     *
     * @param <Y> Type of the generic result returned by callback
     */
    public interface Callback<Y> {

        void onSuccess(Y result);

        void onError(Throwable error);

        void onFinish();

    }

    private List<Subscription> subscriptionList;

    public RxWrapperFragment() {
        subscriptionList = new ArrayList<>();
    }

    /**
     * Performs the passed {@code Action} asynchronously and notified the passed {@code Callback} with
     * the result.
     *
     * @param action   Action to be performed.
     * @param callback Callback after Action competes of fails to be performed due to an exception.
     * @param <T>      Type of the return of the action.
     */
    protected <T> void performActionAsync(final Action<T> action, final Callback<T> callback) {
        final Subscription subscription = Single.create(new Single.OnSubscribe<T>() {
            @Override
            public void call(SingleSubscriber<? super T> singleSubscriber) {
                try {
                    singleSubscriber.onSuccess(action.run());
                } catch (Throwable t) {
                    singleSubscriber.onError(t);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<T>() {
                    @Override
                    public void onSuccess(T value) {
                        callback.onSuccess(value);
                        callback.onFinish();
                    }

                    @Override
                    public void onError(Throwable error) {

                        callback.onError(error);
                        callback.onFinish();

                    }
                });
        subscriptionList.add(subscription);
    }

    protected void clearSubscriptions() {
        for (Subscription subscription : subscriptionList) {
            subscription.unsubscribe();
        }
    }
}
