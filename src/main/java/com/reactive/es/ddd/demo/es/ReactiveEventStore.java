package com.reactive.es.ddd.demo.es;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveEventStore<T extends Event> {

    Mono<Void> saveEvent(T event);

    Flux<T> load(UUID aggregateId);
}
