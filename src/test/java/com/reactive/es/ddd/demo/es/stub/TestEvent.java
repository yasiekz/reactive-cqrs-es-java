package com.reactive.es.ddd.demo.es.stub;

import com.reactive.es.ddd.demo.es.Event;
import com.reactive.es.ddd.demo.es.stub.TestAggregate;
import java.util.UUID;

public abstract class TestEvent extends Event {

    public TestEvent(final UUID aggregateId) {
        super(aggregateId);
    }

    public abstract TestAggregate apply(TestAggregate aggregate);
}
