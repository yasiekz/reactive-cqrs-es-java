package com.reactive.es.ddd.demo.es.stub;

import com.reactive.es.ddd.demo.es.AggregateRoot;
import java.math.BigDecimal;
import java.util.UUID;

public class TestAggregate extends AggregateRoot<TestEvent> {

    private String title;
    private BigDecimal money;

    public static TestAggregate create(UUID id, String title) {
        TestAggregate instance = new TestAggregate();
        TestAggregateCreated event = new TestAggregateCreated(id, title);

        instance.recordEvent(event);
        instance.applyEvent(event);

        return instance;
    }

    public void deposit(BigDecimal amount) {
        TestAggregateMoneyDeposit event = new TestAggregateMoneyDeposit(id, amount);
        recordEvent(event);
        applyEvent(event);
    }

    public void withdraw(BigDecimal amount) {
        TestAggregateMoneyWithdrawn event = new TestAggregateMoneyWithdrawn(id, amount);
        recordEvent(event);
        applyEvent(event);
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getMoney() {
        return money;
    }

    @Override
    protected void doApplyPastEvent(final TestEvent event) {
        event.apply(this);
    }

    void applyEvent(TestAggregateCreated event) {
        id = event.getAggregateId();
        title = event.getTitle();
        money = BigDecimal.ZERO;
    }

    void applyEvent(TestAggregateMoneyDeposit event) {
        id = event.getAggregateId();
        money = money.add(event.getAmount());
    }

    void applyEvent(TestAggregateMoneyWithdrawn event) {
        id = event.getAggregateId();
        money = money.subtract(event.getAmount());
    }
}
