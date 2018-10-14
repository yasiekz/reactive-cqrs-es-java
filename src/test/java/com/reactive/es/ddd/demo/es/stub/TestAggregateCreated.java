package com.reactive.es.ddd.demo.es.stub;

import java.util.Objects;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestAggregateCreated)) {
            return false;
        }
        final TestAggregateCreated that = (TestAggregateCreated) o;
        return Objects.equals(title, that.title) && Objects.equals(aggregateId, that.aggregateId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(aggregateId, title);
    }
}
