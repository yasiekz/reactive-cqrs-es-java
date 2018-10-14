package com.reactive.es.ddd.demo.infrastructure.mongo;

import com.reactive.es.ddd.demo.es.Event;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventMongoRepository<T extends Event> extends ReactiveCrudRepository<EventWrapper, ObjectId> {

    Flux<EventWrapper<T>> findByAggregateId(UUID aggregateId);
}
