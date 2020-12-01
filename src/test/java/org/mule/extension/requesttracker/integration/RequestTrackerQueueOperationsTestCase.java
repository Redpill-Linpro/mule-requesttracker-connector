package org.mule.extension.requesttracker.integration;

import org.junit.Test;
import org.mule.extension.requesttracker.api.models.response.Queue;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

public class RequestTrackerQueueOperationsTestCase extends RequestTrackerOperationsBaseTest {

    /**
     * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
     */
    @Override
    protected String getConfigFile() {
        return "test-queue-config.xml";
    }

    @Test
    public void executeGetQueueOperation() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/queue/1/show"))
                .willReturn(aResponse()
                        .withBodyFile("queue1")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        Queue queue = (Queue) flowRunner("getQueueFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/queue/1/show"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals("General", queue.getName());
    }

    @Test
    public void executeSearchQueueOperation() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/search/queue"))
                .willReturn(aResponse().withBodyFile("queueSearch").withHeader("Content-Type", "text/plain; charset=utf-8")));

        List<Queue> queues = (List<Queue>) flowRunner("searchQueuesFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/search/queue"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password"))
                .withQueryParam("query", equalTo("id > 0")));

        assertEquals(1, queues.size());
        assertEquals("General", queues.get(0).getName());
    }
}
