package com.reactive.es.ddd.demo.infrastructure.mongo;

import com.reactive.es.ddd.demo.MongoFromDockerInitializer;
import com.reactive.es.ddd.demo.es.EventsForAggregateNotFoundException;
import com.reactive.es.ddd.demo.es.ReactiveEventStore;
import com.reactive.es.ddd.demo.es.stub.TestAggregateCreated;
import com.reactive.es.ddd.demo.es.stub.TestAggregateMoneyDeposit;
import com.reactive.es.ddd.demo.es.stub.TestAggregateMoneyWithdrawn;
import com.reactive.es.ddd.demo.es.stub.TestEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@ContextConfiguration(initializers = MongoFromDockerInitializer.class)
@ExtendWith(SpringExtension.class)
class MongoReactiveEventStoreTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String TITLE = "abc";

    @Autowired
    ReactiveEventStore<TestEvent> eventStore;

    @Autowired
    EventMongoRepository<TestEvent> eventRepository;

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should save and load group of events correctly")
    void testSaveAndLoadEvents() {
        // given
        TestEvent event1 = new TestAggregateCreated(ID, TITLE);
        TestEvent event2 = new TestAggregateMoneyDeposit(ID, BigDecimal.TEN);
        TestEvent event3 = new TestAggregateMoneyWithdrawn(ID, BigDecimal.valueOf(3));
        TestEvent otherAggregateEvent = new TestAggregateCreated(UUID.randomUUID(), TITLE);

        List<TestEvent> testEvents = new ArrayList<>();
        testEvents.add(event1);
        testEvents.add(event2);
        testEvents.add(event3);
        testEvents.add(otherAggregateEvent);

        // when
        testEvents.forEach(event -> eventStore.saveEvent(event).block());
        Flux<TestEvent> returned = eventStore.load(ID);

        // then
        StepVerifier.create(returned)
            .expectNextMatches(r -> r instanceof TestAggregateCreated && r.getAggregateId().equals(ID))
            .expectNextMatches(r -> r instanceof TestAggregateMoneyDeposit && r.getAggregateId().equals(ID))
            .expectNextMatches(r -> r instanceof TestAggregateMoneyWithdrawn && r.getAggregateId().equals(ID))
            .verifyComplete();
    }

    @Test
    @DisplayName("Should throw exception when no events found")
    void testExceptionThrowing() {
        // given - there are no events in db
        // when
        Flux<TestEvent> returned = eventStore.load(ID);

        // then
        StepVerifier.create(returned).verifyError(EventsForAggregateNotFoundException.class);
    }
}