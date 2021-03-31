package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * BusinessSubscriber
 *
 * @author qrXun on 2021/3/29
 */
public class BusinessSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;

    private final long maxRequest;

    private int count = 0;

    public BusinessSubscriber(long maxRequest) {
        this.maxRequest = maxRequest;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        this.subscription.request(maxRequest);
    }

    @Override
    public void onNext(T t) {
        System.out.println("收到数据" + t);
        if (count++ > 2){
            subscription.cancel();
            return;
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("遇到异常" + t);
    }

    @Override
    public void onComplete() {
        System.out.println("收到数据完成");

    }
}
