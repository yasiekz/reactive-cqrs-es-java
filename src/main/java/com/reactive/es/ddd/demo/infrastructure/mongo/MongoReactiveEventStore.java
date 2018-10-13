package com.reactive.es.ddd.demo.infrastructure.mongo;

import com.reactive.es.ddd.demo.es.Event;
import com.reactive.es.ddd.demo.es.EventsForAggregateNotFoundException;
import com.reactive.es.ddd.demo.es.ReactiveEventStore;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public final class MongoReactiveEventStore<T extends Event> implements ReactiveEventStore<T> {

    private final EventMongoRepository<T> repository;

    @Autowired
    public MongoReactiveEventStore(final EventMongoRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Void> saveEvent(final T event) {
        EventWrapper<T> eventWrapper = new EventWrapper<>(event.getAggregateId(), LocalDateTime.now(), event);
        return repository.save(eventWrapper).then();
    }

    @Override
    public Flux<T> load(final UUID aggregateId) {
        return repository.findByAggregateId(aggregateId)
            .switchIfEmpty(Flux.error(
                new EventsForAggregateNotFoundException("There are no events for aggregate: " + aggregateId)))
            .map(EventWrapper::getEvent);
    }
}
