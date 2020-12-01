package org.mule.extension.requesttracker.unit;

import org.junit.Test;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerResponseCheckException;
import org.mule.extension.requesttracker.api.models.response.AttachmentHeaders;
import org.mule.extension.requesttracker.api.models.response.AttachmentSummary;
import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerResponseConverter;

import java.util.List;

import static org.junit.Assert.*;

public class RequestTrackerResponseConverterTest {
    private final String value = "test";

    @Test
    public void testConvertResponseSimple() throws Exception {
        SimpleClass result = RequestTrackerResponseConverter.convertResponse(getSimpleResponse(value), SimpleClass.class).get(0);
        assertEquals(value, result.getString());
    }

    @Test
    public void testConvertResponseMultiline() throws Exception {
        SimpleClass result = RequestTrackerResponseConverter.convertResponse(getSimpleResponse("test\n test2"), SimpleClass.class).get(0);
        assertEquals("test" + System.lineSeparator() + "test2", result.getString());
    }

    @Test
    public void testConvertResponseId() throws Exception {
        SimpleClass result = RequestTrackerResponseConverter.convertResponse(getSimpleResponse(value), SimpleClass.class).get(0);
        assertEquals("1", result.getId());
    }

    @Test
    public void testConvertResponseAttachments() throws Exception {
        SimpleClass result = RequestTrackerResponseConverter.convertResponse(
                constructSuccessfulResponse("Attachments: \n" +
                "             13: untitled (9b)"), SimpleClass.class).get(0);
        assertNotNull(result.getAttachments());
        assertEquals(1, result.getAttachments().size());
        assertEquals("13", result.getAttachments().get(0).getId());
        assertEquals("untitled", result.getAttachments().get(0).getName());
        assertEquals("9b", result.getAttachments().get(0).getSize());
    }

    @Test
    public void testConvertResponseHeaders() throws Exception {
        SimpleClass result = RequestTrackerResponseConverter.convertResponse(
                constructSuccessfulResponse("Headers: Content-Disposition: form-data;\n" +
                        "          filename=\"MulePromote.png\";\n" +
                        "          name=\"attachment_1\"\n" +
                        "         Content-Transfer-Encoding: base64\n" +
                        "         Content-Type: image/png; name=\"MulePromote.png\"\n" +
                        "         Content-Length: 89299"), SimpleClass.class).get(0);
        assertEquals("form-data;" + System.lineSeparator() + "filename=\"MulePromote.png\";" + System.lineSeparator() + "name=\"attachment_1\"", result.getHeaders().getContentDisposition());
        assertEquals("base64", result.getHeaders().getContentTransferEncoding());
        assertEquals("image/png; name=\"MulePromote.png\"", result.getHeaders().getContentType());
        assertEquals("89299", result.getHeaders().getContentLength());
    }

    @Test
    public void testConvertResponseFail() throws Exception {
        try {
            RequestTrackerResponseConverter.convertResponse(constructFailedResponse("message"), SimpleClass.class);
            fail("Exception was not thrown for failed response");
        } catch (RequestTrackerResponseCheckException e) {
            assertEquals(409, e.getStatusCode());
            assertTrue(e.getMessage().contains("message"));
        }
    }

    private String getSimpleResponse(String value) {
        return constructSuccessfulResponse("string: " + value);
    }

    private String constructSuccessfulResponse(String body) {
        return "RT/5.0.0 200 Ok" + System.lineSeparator() + System.lineSeparator() + "id: test/1\n"+body;
    }

    private String constructFailedResponse(String body) {
        return "RT/5.0.0 409 Syntax Error" + System.lineSeparator() + System.lineSeparator() + body;
    }

    public static class SimpleClass extends RequestTrackerObject {
        private String string;
        private List<AttachmentSummary> Attachments;
        private AttachmentHeaders Headers;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public List<AttachmentSummary> getAttachments() {
            return Attachments;
        }

        public void setAttachments(List<AttachmentSummary> attachments) {
            Attachments = attachments;
        }

        public AttachmentHeaders getHeaders() {
            return Headers;
        }

        public void setHeaders(AttachmentHeaders headers) {
            Headers = headers;
        }
    }
}
