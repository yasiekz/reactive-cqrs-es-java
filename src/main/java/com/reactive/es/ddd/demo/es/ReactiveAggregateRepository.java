package com.reactive.es.ddd.demo.es;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import reactor.core.publisher.Mono;

public final class ReactiveAggregateRepository<T extends AggregateRoot<R>, R extends Event> implements
    ReactiveAggregateSaver<T>, ReactiveAggregateLoader<T> {

    private final ReactiveEventStore<R> eventStore;

    public ReactiveAggregateRepository(final ReactiveEventStore<R> eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public Mono<T> load(final UUID aggregateId, Class<T> clazz) {
        T instance = createInstance(clazz);
        return eventStore.load(aggregateId).reduce(instance, applyPastEvent());
    }

    @Override
    public Mono<Void> save(T aggregateRoot) {
        return aggregateRoot.getRecordedEvents()
            .map(eventStore::saveEvent)
            .then(Mono.just(aggregateRoot))
            .map(markAsSaved())
            .then();
    }


    private T createInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException
            e) {
            throw new RuntimeException("Can not create instance of class " + clazz.getName(), e);
        }
    }

    private BiFunction<T, R, T> applyPastEvent() {
        return (instance, event) -> {
            instance.applyPastEvent(event);
            return instance;
        };
    }

    private Function<T, T> markAsSaved() {
        return (aggregate) -> {
            aggregate.markEventsAsSaved();
            return aggregate;
        };
    }
}
