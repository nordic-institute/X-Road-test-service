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
