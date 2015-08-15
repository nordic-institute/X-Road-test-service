package com.pkrete.xrd4j.tools.test_service.model;

/**
 * This class represents a test service request.
 *
 * @author Petteri Kivimäki
 */
public class TestServiceRequest {

    private String responseBodySize;
    private String responseAttachmentSize;

    /**
     * Returns the response body size.
     *
     * @return number of characters in the response body
     */
    public String getResponseBodySize() {
        return responseBodySize;
    }

    /**
     * Sets the number of characters in the response body.
     *
     * @param responseBodySize new value
     */
    public void setResponseBodySize(String responseBodySize) {
        this.responseBodySize = responseBodySize;
    }

    /**
     * Returns the response attachment size
     *
     * @return number of characters in the response attachment part
     */
    public String getResponseAttachmentSize() {
        return responseAttachmentSize;
    }

    /**
     * Sets the number of characters in the response attachment part.
     *
     * @param responseAttachmentSize new value
     */
    public void setResponseAttachmentSize(String responseAttachmentSize) {
        this.responseAttachmentSize = responseAttachmentSize;
    }
}
