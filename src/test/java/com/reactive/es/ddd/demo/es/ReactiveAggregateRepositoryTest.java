package com.reactive.es.ddd.demo.es;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.reactive.es.ddd.demo.es.stub.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ReactiveAggregateRepositoryTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String TITLE = "abc";

    @Mock
    private ReactiveEventStore<TestEvent> eventStore;

    private ReactiveAggregateRepository<TestAggregate, TestEvent> repository;

    @BeforeEach
    void setUp() {
        repository = new ReactiveAggregateRepository<>(eventStore);
    }

    @Test
    @DisplayName("Should load aggregate from given events")
    void testLoad() {
        // given
        Flux<TestEvent> events = Flux.just(new TestAggregateCreated(ID, TITLE),
            new TestAggregateMoneyDeposit(ID, BigDecimal.valueOf(3)), new TestAggregateMoneyDeposit(ID, BigDecimal.TEN),
            new TestAggregateMoneyWithdrawn(ID, BigDecimal.valueOf(5)));
        when(eventStore.load(ID)).thenReturn(events);

        // when
        Mono<TestAggregate> mono = repository.load(ID, TestAggregate.class);

        // then
        StepVerifier.create(mono)
            .expectNextMatches(
                returned -> returned.getId().equals(ID) && returned.getTitle().equals(TITLE) && returned.getMoney()
                    .equals(BigDecimal.valueOf(8)))
            .verifyComplete();

    }

    @Test
    @DisplayName("Should save events from given aggregate")
    void testSave() {
        // given
        TestAggregate aggregate = TestAggregate.create(ID, TITLE);
        aggregate.deposit(BigDecimal.valueOf(5));
        aggregate.deposit(BigDecimal.valueOf(2));
        aggregate.withdraw(BigDecimal.valueOf(3));
        when(eventStore.saveEvent(any())).thenReturn(Mono.empty());
        List<TestEvent> events = aggregate.getRecordedEvents().collectList().block();

        // when
        Mono<Void> mono = repository.save(aggregate);

        // then
        StepVerifier.create(mono).verifyComplete();
        events.forEach(event -> verify(eventStore).saveEvent(event));
        assertEquals(0, aggregate.getRecordedEvents().collectList().block().size());
    }
}