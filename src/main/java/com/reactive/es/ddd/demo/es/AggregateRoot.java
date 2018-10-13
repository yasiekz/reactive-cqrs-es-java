package com.reactive.es.ddd.demo.es;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;

public abstract class AggregateRoot<T extends Event> {

    protected UUID id;
    private List<T> events = new ArrayList<>();

    public void markEventsAsSaved() {
        clearRecordedEvents();
    }

    public Flux<T> getRecordedEvents() {
        return Flux.fromIterable(events);
    }

    public final void applyPastEvent(T event) {
        doApplyPastEvent(event);
    }

    public UUID getId() {
        return id;
    }

    protected abstract void doApplyPastEvent(T event);

    protected void recordEvent(T event) {
        events.add(event);
    }

    private void clearRecordedEvents() {
        events.clear();
    }
}
