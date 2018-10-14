package com.reactive.es.ddd.demo.infrastructure.mongo;

import com.reactive.es.ddd.demo.es.Event;
import java.time.LocalDateTime;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class EventWrapper<T extends Event> {

    @Indexed(background = true)
    private final UUID aggregateId;
    private final LocalDateTime createdAt;
    private final T event;
    @Id
    private ObjectId id;

    public EventWrapper(final UUID aggregateId, final LocalDateTime createdAt, final T event) {
        this.aggregateId = aggregateId;
        this.createdAt = createdAt;
        this.event = event;
    }

    public ObjectId getId() {
        return id;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public T getEvent() {
        return event;
    }
}
