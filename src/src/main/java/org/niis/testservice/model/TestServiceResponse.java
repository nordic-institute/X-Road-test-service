/*
 * The MIT License
 * Copyright © 2018 Nordic Institute for Interoperability Solutions (NIIS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.niis.testservice.model;

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
