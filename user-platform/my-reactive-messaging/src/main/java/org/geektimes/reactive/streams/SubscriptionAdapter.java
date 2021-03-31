package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * SubscriptionAdapter
 *
 * @author qrXun on 2021/3/29
 */
public class SubscriptionAdapter implements Subscription {

    private final DecoratingSubscriber<?> decoratingSubscriber;

    public SubscriptionAdapter(Subscriber<?> subscriber) {
        this.decoratingSubscriber = new DecoratingSubscriber<>(subscriber);
    }

    @Override
    public void request(long n) {
        if (n < 1) {
            throw new IllegalArgumentException("The number of elements to requests must be more than zero!");
        }
        decoratingSubscriber.setMaxRequest(n);
    }

    @Override
    public void cancel() {
        decoratingSubscriber.cancel(true);
    }

    public DecoratingSubscriber<?> getDecoratingSubscriber() {
        return decoratingSubscriber;
    }
}
