package com.reactive.es.ddd.demo.es.stub;

import java.math.BigDecimal;
import java.util.UUID;

public class TestAggregateMoneyWithdrawn extends TestEvent {

    private BigDecimal amount;

    public TestAggregateMoneyWithdrawn(final UUID aggregateId, BigDecimal amount) {
        super(aggregateId);
        this.amount = amount;
    }

    @Override
    public TestAggregate apply(final TestAggregate aggregate) {
        aggregate.applyEvent(this);
        return aggregate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
