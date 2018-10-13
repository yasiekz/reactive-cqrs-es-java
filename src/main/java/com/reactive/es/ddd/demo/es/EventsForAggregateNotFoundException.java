package com.reactive.es.ddd.demo.es;

public class EventsForAggregateNotFoundException extends RuntimeException {

    public EventsForAggregateNotFoundException(final String message) {
        super(message);
    }
}
