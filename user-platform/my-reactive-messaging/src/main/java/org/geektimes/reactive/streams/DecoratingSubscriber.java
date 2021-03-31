package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

/**
 * DecoratingSubscriber
 *
 * @author qrXun on 2021/3/29
 */
public class DecoratingSubscriber<T> implements Subscriber<T> {

    private final Subscriber<T> source;

    private final Logger logger;

    private long maxRequest = -1;

    private boolean canceled = false;

    private boolean completed = false;

    private long requestCount = 0;

    public DecoratingSubscriber(Subscriber<T> source) {
        this.source = source;
        this.logger = Logger.getLogger(source.getClass().getName());
    }

    @Override
    public void onSubscribe(Subscription s) {
        source.onSubscribe(s);
    }

    @Override
    public void onNext(T t) {
        assertRequest();

        if (completed) {
            logger.severe("The data subscription was completed, This method should not be invoked again!");
            return;
        }

        if (canceled) {
            logger.warning(String.format("The Subscriber has canceled the data subscription," +
                    " current data[%s] will be ignored!", t));
            return;
        }

        if (requestCount == maxRequest && maxRequest < Long.MAX_VALUE) {
            onComplete();
            logger.warning(String.format("The number of requests is up to the max threshold[%d]," +
                    " the data subscription is completed!", maxRequest));
            return;
        }

        next(t);

    }

    private void next(T t) {
        try {
            source.onNext(t);
        } catch (Throwable e){
            onError(e);
        } finally {
            requestCount++;
        }

    }

    private void assertRequest() {
        if (maxRequest < 1) {
            throw new IllegalStateException("the number of request must be initialized before " +
                    "Subscriber#onNext(Object) method, please set the positive number to " +
                    "Subscription#request(int) method on Publisher#subscribe(Subscriber) phase.");
        }


    }

    @Override
    public void onError(Throwable t) {
        source.onError(t);
    }

    @Override
    public void onComplete() {
        source.onComplete();
        this.completed = true;
    }

    public void cancel(boolean canceled) {
        this.canceled = canceled;
    }

    public void setMaxRequest(long maxRequest) {
        this.maxRequest = maxRequest;
    }
}
