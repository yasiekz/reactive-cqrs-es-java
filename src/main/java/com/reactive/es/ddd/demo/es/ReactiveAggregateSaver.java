package com.reactive.es.ddd.demo.es;

import reactor.core.publisher.Mono;

public interface ReactiveAggregateSaver<T extends AggregateRoot> {

    Mono<Void> save(T aggregateRoot);
}
