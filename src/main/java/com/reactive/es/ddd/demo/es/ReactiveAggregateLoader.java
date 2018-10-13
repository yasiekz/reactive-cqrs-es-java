package com.reactive.es.ddd.demo.es;

import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ReactiveAggregateLoader<T extends AggregateRoot> {

    Mono<T> load(UUID aggregateId, Class<T> clazz);
}
