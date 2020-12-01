package org.mule.extension.requesttracker.internal;

import org.mule.extension.requesttracker.api.exceptions.RequestTrackerConnectionException;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerResponseCheckException;
import org.mule.extension.requesttracker.api.models.request.*;
import org.mule.extension.requesttracker.api.models.response.*;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerError;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerErrorTypeProvider;
import org.mule.extension.requesttracker.internal.utils.FieldUtils;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerRequestConverter;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerResponseConverter;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RequestTrackerTicketOperations extends RequestTrackerBaseOperations {


    @Summary("Get ticket by ticket ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public Ticket getTicket(@Connection RequestTrackerConnection connection, String ticketId) throws RequestTrackerConnectionException {
        try {
            List<Ticket> tickets = get(connection, "ticket/" + ticketId + "/show", Ticket.class);
            return tickets.isEmpty() ? null : tickets.get(0);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Search for Tickets")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public List<Ticket> searchTickets(@Connection RequestTrackerConnection connection, @ParameterGroup(name = "Search") SearchRequest searchRequest, @ParameterGroup(name = "Fields") TicketFields fields) throws RequestTrackerConnectionException {
        try {
            Map<String, String> params = searchRequest.asParams();
            params.put("fields", FieldUtils.asParam(fields, TicketFields.class));
            return get(connection, "search/ticket", params, Ticket.class);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting Ticket Search: " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting Ticket Search", e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get Links for Ticket ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public TicketLinks getTicketLinks(@Connection RequestTrackerConnection connection, String ticketId) throws RequestTrackerConnectionException {
        try {
            List<TicketLinks> ticketLinks = get(connection, "ticket/" + ticketId + "/links/show", TicketLinks.class);
            return ticketLinks.isEmpty() ? null : ticketLinks.get(0);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting Links for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting Links for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get list of attachments for Ticket ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public Attachments getTicketAttachments(@Connection RequestTrackerConnection connection, String ticketId) throws RequestTrackerConnectionException {
        try {
            List<Attachments> attachments = get(connection, "ticket/" + ticketId + "/attachments", Attachments.class);
            return attachments.isEmpty() ? null : attachments.get(0);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting Attachments for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting Attachments for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get Attachment by Ticket ID and Attachment ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public Attachment getTicketAttachment(@Connection RequestTrackerConnection connection, String ticketId, String attachmentId) throws RequestTrackerConnectionException {
        try {
            List<Attachment> attachments = get(connection, "ticket/" + ticketId + "/attachments/" + attachmentId, Attachment.class);
            return attachments.isEmpty() ? null : attachments.get(0);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting Attachment " + attachmentId + " for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting Attachment " + attachmentId + " for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get Content of Attachment for Ticket ID and Attachment ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public AttachmentContent getTicketAttachmentContent(@Connection RequestTrackerConnection connection, String ticketId, String attachmentId) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertAttachmentContentResponse(connection.get("ticket/" + ticketId + "/attachments/" + attachmentId + "/content").getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting Content for attachment " + attachmentId + " for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting Content for attachment " + attachmentId + " for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get list of ticket history entry summaries for Ticket ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public List<HistorySummary> getTicketHistorySummary(@Connection RequestTrackerConnection connection, String ticketId) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertHistorySummaryResponse(connection.get("ticket/" + ticketId + "/history").getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting history summary for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting history summary for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get list of ticket history entry for Ticket ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public List<HistoryEntry> getTicketHistory(@Connection RequestTrackerConnection connection, String ticketId) throws RequestTrackerConnectionException {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("format", "l");
            return get(connection, "ticket/" + ticketId + "/history", params, HistoryEntry.class);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting History for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting History for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get history entry for Ticket ID and history entry ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public HistoryEntry getTicketHistoryEntry(@Connection RequestTrackerConnection connection, String ticketId, String historyId) throws RequestTrackerConnectionException {
        try {
            List<HistoryEntry> historyEntries = get(connection, "ticket/" + ticketId + "/history/id/" + historyId, HistoryEntry.class);
            return historyEntries.isEmpty() ? null : historyEntries.get(0);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting History Entry " + historyId + " for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting History Entry " + historyId + " for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Create new history entry for Ticket ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public UpdateResponse createHistoryEntry(@Connection RequestTrackerConnection connection,
                                             String ticketId,
                                             @ParameterGroup(name = "Comment") TicketHistoryComment newComment,
                                             @ParameterGroup(name = "Attachments") AttachmentsGroup attachments) throws RequestTrackerConnectionException {
        if (attachments.getAttachments() != null) {
            newComment.setFileNames(attachments.getAttachments().keySet());
        }
        try {
            return RequestTrackerResponseConverter.convertSimpleResponseCodeResponse(connection.post("ticket/" + ticketId + "/comment", RequestTrackerRequestConverter.convertToRequestTrackerBody(newComment, TicketHistoryComment.class), transformAttachments(attachments)).getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Creating History Entry for Ticket " + ticketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while Creating History Entry for Ticket " + ticketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Create new Ticket")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public UpdateItemResponse createTicket(@Connection RequestTrackerConnection connection, CreateTicket newTicket) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertCreateTicketResponse(connection.post("ticket/new", RequestTrackerRequestConverter.convertToRequestTrackerBody(newTicket, CreateTicket.class, true)).getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Creating Ticket: " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while Creating Ticket", e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Update existing Ticket")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public UpdateItemResponse updateTicket(@Connection RequestTrackerConnection connection, String ticketId, UpdateTicket ticketUpdates) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertEditTicketResponse(connection.post("ticket/" + ticketId + "/edit", RequestTrackerRequestConverter.convertToRequestTrackerBody(ticketUpdates, UpdateTicket.class, true)).getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Updating Ticket: " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while Updating Ticket", e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Update Links for Ticket ID")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public UpdateItemResponse updateTicketLinks(@Connection RequestTrackerConnection connection, String ticketId, UpdateTicketLinks ticketLinksUpdates) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertEditTicketLinksResponse(connection.post("ticket/" + ticketId + "/links", RequestTrackerRequestConverter.convertToRequestTrackerBody(ticketLinksUpdates, UpdateTicketLinks.class, true)).getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Updating Ticket Links: " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while Updating Ticket Links", e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Merge tickets")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public UpdateResponse mergeTickets(@Connection RequestTrackerConnection connection, String originTicketId, String intoTicketId) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertSimpleResponseCodeResponse(connection.post("ticket/" + originTicketId + "/merge/" + intoTicketId, null).getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Merging Ticket" + originTicketId + " into Ticket " + intoTicketId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while  Merging Ticket" + originTicketId + " into Ticket " + intoTicketId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

}
