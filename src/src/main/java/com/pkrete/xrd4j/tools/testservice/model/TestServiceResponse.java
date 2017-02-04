package com.pkrete.xrd4j.tools.testservice.model;

/**
 * This class represents a test service response.
 *
 * @author Petteri Kivimäki
 */
public class TestServiceResponse {

    private String responseBody;
    private String responseAttachment;
    private String processingTime;

    /**
     * Returns the response body.
     *
     * @return the response body
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Sets the value of the response body.
     *
     * @param responseBody new value
     */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    /**
     * Returns the response attachment content.
     *
     * @return the value of the response attachment part
     */
    public String getResponseAttachment() {
        return responseAttachment;
    }

    /**
     * Sets the value of the response attachment part.
     *
     * @param responseAttachment new value
     */
    public void setResponseAttachment(String responseAttachment) {
        this.responseAttachment = responseAttachment;
    }

    /**
     * Returns the processing time of the request in milliseconds.
     *
     * @return processing time of the request in milliseconds
     */
    public String getProcessingTime() {
        return processingTime;
    }

    /**
     * Sets the processing time of the request in milliseconds.
     *
     * @param processingTime new value
     */
    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }
}
