package org.mule.extension.requesttracker.integration;

import org.junit.Test;
import org.mule.extension.requesttracker.api.models.response.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.junit.Assert.assertNotNull;

public class RequestTrackerTicketOperationsTest extends RequestTrackerOperationsBaseTest {


    /**
     * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
     */
    @Override
    protected String getConfigFile() {
        return "test-ticket-config.xml";
    }

    @Test
    public void executeGetTicketOperation() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/show"))
                .willReturn(aResponse()
                        .withBodyFile("ticket1")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        Ticket ticket = (Ticket) flowRunner("getTicketFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/show"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals("General", ticket.getQueue());
        assertEquals("1", ticket.getId());
    }

    @Test
    public void executeSearchTicketsOperation() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/search/ticket"))
                .willReturn(aResponse()
                        .withBodyFile("ticketSearch")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        List<Ticket> tickets = (List<Ticket>) flowRunner("searchTicketsFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/search/ticket"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password"))
                .withQueryParam("fields", equalTo("id,Subject"))
                .withQueryParam("query", equalTo("id > 0"))
        );

        assertEquals(26, tickets.size());
        assertEquals("1", tickets.get(0).getId());
        assertEquals("Test", tickets.get(0).getSubject());
    }

    @Test
    public void executeGetTicketLinksOperation() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/links/show"))
                .willReturn(aResponse()
                        .withBodyFile("ticketLinks")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        TicketLinks ticketLinks = (TicketLinks) flowRunner("getTicketLinksFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/links/show"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals("fsck.com-rt://your.server.com/ticket/1", ticketLinks.getDependedOnBy());

    }

    @Test
    public void executeGetTicketAttachments() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/attachments"))
                .willReturn(aResponse()
                        .withBodyFile("ticketAttachments")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        Attachments attachments = (Attachments) flowRunner("getTicketAttachmentsFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/attachments"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals(15, attachments.getAttachments().size());
        assertEquals("text/plain", attachments.getAttachments().get(0).getContentType());
        assertEquals("61", attachments.getAttachments().get(0).getId());
        assertEquals("(Unnamed)", attachments.getAttachments().get(0).getName());
        assertEquals("50b", attachments.getAttachments().get(0).getSize());
    }

    @Test
    public void executeGetTicketAttachment() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/attachments/1"))
                .willReturn(aResponse()
                        .withBodyFile("ticketAttachment")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        Attachment attachment = (Attachment) flowRunner("getTicketAttachmentFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/attachments/1"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals("1", attachment.getId());
        assertEquals("image/png", attachment.getContentType());
    }

    @Test
    public void executeGetTicketAttachmentContent() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/attachments/1/content"))
                .willReturn(aResponse().withBody("ABCD")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        AttachmentContent content = (AttachmentContent) flowRunner("getTicketAttachmentContentFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/attachments/1/content"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals("ABCD", content.getContent());
    }

    @Test
    public void executeGetTicketHistorySummary() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/history"))
                .willReturn(aResponse()
                        .withBodyFile("ticketHistorySummary")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        List<HistorySummary> summaryList = (List<HistorySummary>) flowRunner("getTicketHistorySummaryFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/history"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals(9, summaryList.size());
        assertEquals("187", summaryList.get(0).getId());
        assertEquals("Ticket created by root", summaryList.get(0).getName());
    }

    @Test
    public void executeGetTicketHistory() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/history"))
                .withQueryParam("format", equalTo("l"))
                .willReturn(aResponse()
                        .withBodyFile("ticketHistory")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        List<HistoryEntry> historyEntries = (List<HistoryEntry>) flowRunner("getTicketHistoryFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/history"))
                .withQueryParam("format", equalTo("l"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals(9, historyEntries.size());
        assertHistoryEntry(historyEntries.get(0));

    }

    @Test
    public void executeGetTicketHistoryEntry() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/ticket/1/history/id/187"))
                .willReturn(aResponse()
                        .withBodyFile("ticketHistoryEntry")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        HistoryEntry historyEntry = (HistoryEntry) flowRunner("getTicketHistoryEntryFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/history/id/187"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertHistoryEntry(historyEntry);
    }

    private void assertHistoryEntry(HistoryEntry historyEntry) {
        assertEquals("187", historyEntry.getId());
        assertEquals("23", historyEntry.getTicket());
        assertEquals("0", historyEntry.getTimeTaken());
        assertEquals("Create", historyEntry.getType());
        assertEquals("Ticket created by root", historyEntry.getDescription());
        assertEquals("A longer description\n\nWIth more lines than ever!", historyEntry.getContent());
        assertEquals(LocalDateTime.of(2020, 10, 22, 8, 2, 20), historyEntry.getCreated());
        assertNotNull(historyEntry.getAttachments());
        assertEquals(1, historyEntry.getAttachments().size());
        assertEquals("61", historyEntry.getAttachments().get(0).getId());
        assertEquals("untitled", historyEntry.getAttachments().get(0).getName());
        assertEquals("50b", historyEntry.getAttachments().get(0).getSize());
    }


    @Test
    public void executeCreateHistoryEntry() throws Exception {
        instanceRule.stubFor(post(urlPathEqualTo("/REST/1.0/ticket/1/comment"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain; charset=utf-8")
                        .withBody("RT/5.0.0 200 Ok\n\n" +
                                "# Comments added\n\n")));

        UpdateResponse updateResponse = (UpdateResponse) flowRunner("createTicketHistoryEntryFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(postRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/comment"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password"))
                .withRequestBodyPart(aMultipart()
                        .withName("content")
                        .withBody(containing("id: 1\n" +
                                "Action: comment\n" +
                                "Text: test\n" +
                                "Attachment: ")).build())
                .withRequestBodyPart(aMultipart()
                        .withName("attachment_1")
                        .withBody(equalTo("test")).build())
        );

        assertEquals(200, updateResponse.getStatus());
    }

    @Test
    public void executeCreateTicket() throws Exception {
        instanceRule.stubFor(post(urlPathEqualTo("/REST/1.0/ticket/new"))
                .willReturn(aResponse().withBody("RT/5.0.0 200 Ok\n\n" +
                        "# Ticket 1 created.\n\n").withHeader("Content-Type", "text/plain; charset=utf-8")));

        UpdateItemResponse updateItemResponse = (UpdateItemResponse) flowRunner("createTicketFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(postRequestedFor(urlPathEqualTo("/REST/1.0/ticket/new"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password"))
                .withRequestBodyPart(aMultipart().withName("content").withBody(containing(
                        "id: ticket/new\n" +
                                "Queue: 1\n" +
                                "Subject: subject\n" +
                                "Priority: 1")).build()));

        assertEquals(200, updateItemResponse.getStatus());
        assertEquals("1", updateItemResponse.getItemId());
    }

    @Test
    public void executeUpdateTicket() throws Exception {
        instanceRule.stubFor(post(urlPathEqualTo("/REST/1.0/ticket/1/edit"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain; charset=utf-8")
                        .withBody("RT/5.0.0 200 Ok\n\n" +
                                "# Ticket 1 updated.\n\n")));

        UpdateItemResponse updateItemResponse = (UpdateItemResponse) flowRunner("updateTicketFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(postRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/edit"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password"))
                .withRequestBodyPart(aMultipart().withName("content").withBody(equalTo("Queue: 2")).build()));

        assertEquals(200, updateItemResponse.getStatus());
        assertEquals("1", updateItemResponse.getItemId());
    }

    @Test
    public void executeUpdateTicketLinks() throws Exception {
        instanceRule.stubFor(post(urlPathEqualTo("/REST/1.0/ticket/1/links"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain; charset=utf-8")
                        .withBody("RT/5.0.0 200 Ok\n\n" +
                                "# Links for ticket 1 updated.\n\n")));

        UpdateItemResponse updateItemResponse = (UpdateItemResponse) flowRunner("updateTicketLinksFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(postRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/links"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password"))
                .withRequestBodyPart(aMultipart().withName("content").withBody(equalTo("HasMember: test")).build()));

        assertEquals(200, updateItemResponse.getStatus());
        assertEquals("1", updateItemResponse.getItemId());
    }

    @Test
    public void executeMergeTickets() throws Exception {
        instanceRule.stubFor(post(urlPathEqualTo("/REST/1.0/ticket/1/merge/2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain; charset=utf-8")
                        .withBody("RT/5.0.0 200 Ok\n\n" +
                                "# Merge completed.\n\n\n")));

        UpdateResponse updateResponse = (UpdateResponse) flowRunner("mergeTicketsFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(postRequestedFor(urlPathEqualTo("/REST/1.0/ticket/1/merge/2"))
            .withQueryParam("user", equalTo("root"))
            .withQueryParam("pass", equalTo("password")));

        assertEquals(200, updateResponse.getStatus());

    }
}
