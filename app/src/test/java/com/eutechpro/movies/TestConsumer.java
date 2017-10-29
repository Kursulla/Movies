package com.eutechpro.movies;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;

/**
 * Since {@link io.reactivex.Observable#subscribe(Consumer, Consumer)} expects {@link Consumer}, I find it strange to use there {@link TestObserver}.
 * <br/>
 * So I made this small wrapper around {@link TestObserver}
 */
@SuppressWarnings("unchecked")
public class TestConsumer<T> extends TestObserver<T> {
    public T getContent() {
        return (T) getEvents().get(0).get(0);
    }
}
