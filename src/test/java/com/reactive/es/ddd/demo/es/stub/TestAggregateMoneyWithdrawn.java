package com.reactive.es.ddd.demo.es.stub;

import java.math.BigDecimal;
import java.util.Objects;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TestAggregateMoneyWithdrawn that = (TestAggregateMoneyWithdrawn) o;
        return Objects.equals(amount, that.amount) && Objects.equals(aggregateId, that.aggregateId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(aggregateId, amount);
    }
}
