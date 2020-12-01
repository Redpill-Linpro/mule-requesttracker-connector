package org.mule.extension.requesttracker.integration;

import org.junit.Test;
import org.mule.extension.requesttracker.api.models.response.Ticket;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.util.Reference;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.api.processor.Processor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.junit.Assert.*;
import static org.mule.tck.probe.PollingProber.check;

public class RequestTrackerTicketListenerTest extends RequestTrackerOperationsBaseTest {


    private static final int PROBER_TIMEOUT = 10000;
    private static final int PROBER_DELAY = 1500;

    private static List<Message> RECEIVED_MESSAGES;


    public final static class AddMessageToList implements Processor {

        @Override
        public CoreEvent process(CoreEvent event) throws MuleException {
            RECEIVED_MESSAGES.add(event.getMessage());
            return event;
        }
    }

    @Override
    protected void doSetUpBeforeMuleContextCreation() throws Exception {
        super.doSetUpBeforeMuleContextCreation();

        RECEIVED_MESSAGES = new CopyOnWriteArrayList<>();
    }

    @Override
    protected void doTearDown() throws Exception {
        RECEIVED_MESSAGES = null;
    }

    @Override
    protected String getConfigFile() {
        return "test-ticket-listener-config.xml";
    }

    @Test
    public void checkForNewTicket() throws Exception {

        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/search/ticket"))
                .willReturn(aResponse()
                        .withBodyFile("ticketSearch")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        startFlow("onNewTicket");

        Ticket ticket = new Ticket();
        ticket.setId("1");
        assertPoll(ticket, "1");

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/search/ticket"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        stopFlow("onNewTicket");

    }

    private void assertPoll(Ticket ticket, String expectedId) {
        Message message = expect(ticket);
        Ticket payload = (Ticket) message.getPayload().getValue();
        assertEquals(expectedId, payload.getId());
    }

    private Message expect(Ticket ticket) {
        Reference<Message> messageHolder = new Reference<>();
        check(PROBER_TIMEOUT, PROBER_DELAY, () -> {
            getPicked(ticket).ifPresent(messageHolder::set);
            return messageHolder.get() != null;
        });

        return messageHolder.get();
    }

    private Optional<Message> getPicked(Ticket ticket) {
        return RECEIVED_MESSAGES.stream()
                .filter(message -> {
                    String id = (String) message.getAttributes().getValue();
                    return ticket.getId().equals(id);
                })
                .findFirst();
    }

    private void startFlow(String flowName) throws Exception {
        ((Startable) getFlowConstruct(flowName)).start();
    }

    private void stopFlow(String flowName) throws Exception {
        ((Stoppable) getFlowConstruct(flowName)).stop();
    }
}
