package org.geektimes.reactive.streams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;
import java.util.List;

/**
 * SimplePublisher
 *
 * @author qrXun on 2021/3/29
 */
public class SimplePublisher<T> implements Publisher<T> {

    private List<Subscriber> subscriberList = new LinkedList<>();

    @Override
    public void subscribe(Subscriber<? super T> s) {
        SubscriptionAdapter subscription = new SubscriptionAdapter(s);
        s.onSubscribe(subscription);
        subscriberList.add(subscription.getDecoratingSubscriber());
    }

    public void publish(T data) {
        subscriberList.forEach(subscriber -> subscriber.onNext(data));
    }

    public static void main(String[] args) {
        SimplePublisher publisher = new SimplePublisher();
        publisher.subscribe(new BusinessSubscriber(5));
        for (int i = 0; i < 5; i++) {
            publisher.publish(i);
        }
    }
}