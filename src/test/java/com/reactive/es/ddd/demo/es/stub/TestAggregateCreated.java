package com.reactive.es.ddd.demo.es.stub;

import java.util.UUID;

public class TestAggregateCreated extends TestEvent {

    private String title;

    public TestAggregateCreated(final UUID aggregateId, String title) {
        super(aggregateId);
        this.title = title;
    }

    @Override
    public TestAggregate apply(final TestAggregate aggregate) {
        aggregate.applyEvent(this);
        return aggregate;
    }

    public String getTitle() {
        return title;
    }
}
