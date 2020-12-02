package org.mule.extension.requesttracker.internal;

import org.mule.extension.requesttracker.api.models.request.TicketFields;
import org.mule.extension.requesttracker.api.models.response.Ticket;
import org.mule.extension.requesttracker.api.enums.TargetField;
import org.mule.extension.requesttracker.internal.utils.FieldUtils;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerResponseConverter;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.*;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.PollContext;
import org.mule.runtime.extension.api.runtime.source.PollingSource;
import org.mule.runtime.extension.api.runtime.source.SourceCallbackContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Alias("updatedTicketListener")
@DisplayName("On Ticket Updated")
@Summary("Triggers when a new Ticket is updated")
public class RequestTrackerTicketListener extends PollingSource<Ticket, String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestTrackerTicketListener.class);

    @Connection
    private ConnectionProvider<RequestTrackerConnection> connectionProvider;

    @Parameter
    @Summary("Field to check for updates on")
    private TargetField updateField;

    @Optional
    @Parameter
    @Summary("Will never poll for items older than this time")
    private LocalDateTime initialFromTime;

    @Parameter
    @ParameterGroup(name = "Fields", showInDsl=true)
    private TicketFields fields;

    private RequestTrackerConnection conn;

    @Override
    protected void doStart() throws MuleException {
        if (initialFromTime == null) {
            initialFromTime = LocalDateTime.now();
        }
        conn = connectionProvider.connect();
    }

    @Override
    protected void doStop() {
        if (conn != null) {
            connectionProvider.disconnect(conn);
        }
    }

    @Override
    public void poll(PollContext<Ticket, String> pollContext) {
        try {
            if (pollContext.isSourceStopping() && conn.isConnected()) {
                return;
            }
        } catch (Exception e) {
            LOGGER.error("Unable to check for connection", e);
            return;
        }
        Map<String, String> params = new HashMap<>();
        LocalDateTime fromTime = pollContext.getWatermark().isPresent() ? (LocalDateTime) pollContext.getWatermark().get() : initialFromTime;
        params.put("query", updateField.getFieldName() + " > '" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(fromTime) + "'");
        params.put("fields", FieldUtils.asParam(fields, TicketFields.class));
        try {
            List<Ticket> tickets = RequestTrackerResponseConverter.convertResponse(conn.get("search/ticket", params).getBody(), Ticket.class);

            for (Ticket ticket : tickets) {
                pollContext.accept(item -> {
                    Result<Ticket, String> result = Result.<Ticket, String>builder().output(ticket).attributes(ticket.getId()).build();
                    item.setResult(result);
                    switch (updateField) {
                        case CREATED:
                            item.setWatermark(ticket.getCreated() == null ? LocalDateTime.now() : ticket.getCreated());
                            break;
                        case UPDATED:
                            item.setWatermark(ticket.getLastUpdated() == null ? LocalDateTime.now() : ticket.getLastUpdated());
                            break;
                        case RESOLVED:
                            item.setWatermark(ticket.getResolved() == null ? LocalDateTime.now() : ticket.getResolved());
                            break;
                        case STARTED:
                            item.setWatermark(ticket.getStarted() == null ? LocalDateTime.now() : ticket.getStarted());
                            break;
                    }
                    item.setId(ticket.getId());
                });
            }
        } catch (TimeoutException | IOException e) {
            LOGGER.error("Unable to complete polling for new Tickets", e);
        }
    }

    @Override
    public void onRejectedItem(Result<Ticket, String> result, SourceCallbackContext sourceCallbackContext) {
        // This method is used to release resources, but we have non of that
    }
}
