package org.mule.extension.requesttracker.api.models.response;

public class AttachmentHeaders {
    private String ContentTransferEncoding; //: base64
    private String XMailer; //: MIME-tools 5.509 (Entity 5.509)
    private String Subject; //: MulePromote.png
    private String ContentDisposition; //: inline; filename="MulePromote.png"
    private String ContentType; //: image/png; name="MulePromote.png"
    private String MIMEVersion; //: 1.0
    private String ContentLength; //: 89299
    private String XRTInterface;
    private String RTMessageID;

    public String getContentTransferEncoding() {
        return ContentTransferEncoding;
    }

    public void setContentTransferEncoding(String contentTransferEncoding) {
        ContentTransferEncoding = contentTransferEncoding;
    }

    public String getXMailer() {
        return XMailer;
    }

    public void setXMailer(String XMailer) {
        this.XMailer = XMailer;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getContentDisposition() {
        return ContentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        ContentDisposition = contentDisposition;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getMIMEVersion() {
        return MIMEVersion;
    }

    public void setMIMEVersion(String MIMEVersion) {
        this.MIMEVersion = MIMEVersion;
    }

    public String getContentLength() {
        return ContentLength;
    }

    public void setContentLength(String contentLength) {
        ContentLength = contentLength;
    }

    public String getXRTInterface() {
        return XRTInterface;
    }

    public void setXRTInterface(String XRTInterface) {
        this.XRTInterface = XRTInterface;
    }

    public String getRTMessageID() {
        return RTMessageID;
    }

    public void setRTMessageID(String RTMessageID) {
        this.RTMessageID = RTMessageID;
    }
}
