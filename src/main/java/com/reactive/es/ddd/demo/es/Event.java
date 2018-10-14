package com.reactive.es.ddd.demo.es;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {

    protected UUID aggregateId;
    private LocalDateTime createdAt;

    public Event(final UUID aggregateId) {
        this.aggregateId = aggregateId;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
