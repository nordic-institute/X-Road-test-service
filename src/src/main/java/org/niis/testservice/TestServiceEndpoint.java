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
package org.niis.testservice;

import org.niis.testservice.model.TestServiceRequest;
import org.niis.testservice.model.TestServiceResponse;
import org.niis.testservice.util.ApplicationHelper;
import org.niis.xrd4j.common.exception.XRd4JException;
import org.niis.xrd4j.common.message.ErrorMessage;
import org.niis.xrd4j.common.message.ServiceRequest;
import org.niis.xrd4j.common.message.ServiceResponse;
import org.niis.xrd4j.common.util.PropertiesUtil;
import org.niis.xrd4j.server.AbstractAdapterServlet;
import org.niis.xrd4j.server.deserializer.AbstractCustomRequestDeserializer;
import org.niis.xrd4j.server.deserializer.CustomRequestDeserializer;
import org.niis.xrd4j.server.serializer.AbstractServiceResponseSerializer;
import org.niis.xrd4j.server.serializer.ServiceResponseSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import java.util.Properties;

/**
 * This class implements single X-Road v6 compatible service: "testService".
 * Service descriptions are defined in "test-servuce.wsdl" file that's located
 * in WEB-INF/classes folder. The name of the WSDL file and the namespace is
 * configured in WEB-INF/classes/test-service.properties file.
 *
 * @author Petteri Kivimäki
 */
public class TestServiceEndpoint extends AbstractAdapterServlet {

    private Properties props;
    private static final Logger LOG = LoggerFactory.getLogger(TestServiceEndpoint.class);
    private String namespaceSerialize;
    private String namespaceDeserialize;
    private String prefix;

    @Override
    public void init() throws ServletException {
        super.init();
        LOG.debug("Starting to initialize Endpoint.");
        this.props = PropertiesUtil.getInstance().load("/test-service.properties");
        this.namespaceSerialize = this.props.getProperty("namespace.serialize");
        this.namespaceDeserialize = this.props.getProperty("namespace.deserialize");
        this.prefix = this.props.getProperty("namespace.prefix.serialize");
        LOG.debug("Namespace for incoming ServiceRequests : \"" + this.namespaceDeserialize + "\".");
        LOG.debug("Namespace for outgoing ServiceResponses : \"" + this.namespaceSerialize + "\".");
        LOG.debug("Namespace prefix for outgoing ServiceResponses : \"" + this.prefix + "\".");
        LOG.debug("Endpoint initialized.");
    }

    /**
     * Must return the path of the WSDL file.
     *
     * @return absolute path of the WSDL file
     */
    protected String getWSDLPath() {
        String path = this.props.getProperty("wsdl.path");
        LOG.debug("WSDL path : \"" + path + "\".");
        return path;
    }

    protected ServiceResponse handleRequest(ServiceRequest request) throws SOAPException, XRd4JException {
        long startTime = System.currentTimeMillis();
        ServiceResponseSerializer serializer;
        ServiceResponse<TestServiceRequest, TestServiceResponse> response;

        // Process services by service code
        if ("testService".equals(request.getProducer().getServiceCode())) {
            // Process "helloService" service
            LOG.info("Process \"testService\" service.");
            // Create a new response serializer that serializes the response
            // to SOAP
            serializer = new TestServiceResponseSerializer();
            // Create a custom request deserializer that parses the request
            // data from the SOAP request
            CustomRequestDeserializer customDeserializer = new TestServiceDeserializer();
            // Parse the request data from the request
            customDeserializer.deserialize(request, this.namespaceDeserialize);
            // Create a new ServiceResponse object
            response = new ServiceResponse<>(request.getConsumer(), request.getProducer(), request.getId());
            // Set namespace of the SOAP response
            response.getProducer().setNamespaceUrl(this.namespaceSerialize);
            response.getProducer().setNamespacePrefix(this.prefix);
            LOG.debug("Start message prosessing.");
            if (request.getRequestData() != null) {
                // Get the request object
                TestServiceRequest serviceRequest = (TestServiceRequest) request.getRequestData();
                // Create response object
                TestServiceResponse serviceResponse = new TestServiceResponse();
                // Generate response data according to the request parameters
                serviceResponse.setResponseBody(ApplicationHelper.getRandomString(serviceRequest.getResponseBodySize()));
                serviceResponse.setResponseAttachment(ApplicationHelper.getRandomString(serviceRequest.getResponseAttachmentSize()));
                // Set response value
                response.setResponseData(serviceResponse);
            } else {
                // No request data is found - an error message is returned
                LOG.warn("No \"name\" parameter found. Return a non-techinal error message.");
                ErrorMessage error = new ErrorMessage("422", "422 Unprocessable Entity. Missing \"name\" element.");
                response.setErrorMessage(error);
            }
            LOG.debug("Message prosessing done!");
            // Get processing time
            long processingTime = System.currentTimeMillis() - startTime;
            // Set processing time
            ((TestServiceResponse) response.getResponseData()).setProcessingTime(Long.toString(processingTime));
            // Serialize the response to SOAP
            serializer.serialize(response, request);
            // Return the response - AbstractAdapterServlet takes care of
            // the rest
            return response;
        }
        // No service matching the service code in the request was found -
        // and error is returned
        serializer = new TestServiceResponseSerializer();
        response = new ServiceResponse();
        ErrorMessage error = new ErrorMessage("SOAP-ENV:Client", "Unknown service code.", null, null);
        response.setErrorMessage(error);
        serializer.serialize(response, request);
        return response;
    }

    /**
     * This class is responsible for serializing response data of testService
     * service responses.
     */
    private class TestServiceResponseSerializer extends AbstractServiceResponseSerializer {

        @Override
        /**
         * Serializes the response data.
         *
         * @param response ServiceResponse holding the application specific
         * response object
         * @param soapResponse SOAPMessage's response object where the response
         * element is added
         * @param envelope SOAPMessage's SOAPEnvelope object
         */
        public void serializeResponse(ServiceResponse response, SOAPElement soapResponse, SOAPEnvelope envelope) throws SOAPException {
            TestServiceResponse serviceResponse = (TestServiceResponse) response.getResponseData();
            // Add "data" element
            SOAPElement data = soapResponse.addChildElement(envelope.createName("data"));
            // Add response body to "data" element
            data.addTextNode(serviceResponse.getResponseBody());
            // Add "processingTime" element
            SOAPElement processingTime = soapResponse.addChildElement(envelope.createName("processingTime"));
            // Add processing time
            processingTime.addTextNode(serviceResponse.getProcessingTime());

            // Add attachment
            if (serviceResponse.getResponseAttachment() != null && !serviceResponse.getResponseAttachment().isEmpty()) {
                AttachmentPart attachPart = response.getSoapMessage()
                        .createAttachmentPart("<attachment>" + serviceResponse.getResponseAttachment() + "</attachment>", "application/xml");
                attachPart.setContentId("attachment_id");
                response.getSoapMessage().addAttachmentPart(attachPart);
            }
        }
    }

    /**
     * This class is responsible for deserializing request data of testService
     * service requests. The type declaration "<TestServiceRequest>" defines the
     * type of the request data, which in this case is TestServiceRequest.
     */
    private class TestServiceDeserializer extends AbstractCustomRequestDeserializer<TestServiceRequest> {

        /**
         * Deserializes the "request" element.
         *
         * @param requestNode request element
         * @return content of the request element
         */
        @Override
        protected TestServiceRequest deserializeRequest(Node requestNode, SOAPMessage message) throws SOAPException {
            if (requestNode == null) {
                LOG.warn("\"requestNode\" is null. Null is returned.");
                return null;
            }
            // Create new TestServiceRequest object
            TestServiceRequest request = new TestServiceRequest();
            // Loop through the request
            for (int i = 0; i < requestNode.getChildNodes().getLength(); i++) {
                // Request data is inside of "responseBodySize" element
                if (requestNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE
                        && "responseBodySize".equals(requestNode.getChildNodes().item(i).getLocalName())) {
                    LOG.debug("Found \"responseBodySize\" element.");
                    // "responseBodySize" element was found - set value
                    request.setResponseBodySize(requestNode.getChildNodes().item(i).getTextContent());
                } else if (requestNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE
                        && "responseAttachmentSize".equals(requestNode.getChildNodes().item(i).getLocalName())) {
                    LOG.debug("Found \"responseAttachmentSize\" element.");
                    // "responseAttachmentSize" element was found - set value
                    request.setResponseAttachmentSize(requestNode.getChildNodes().item(i).getTextContent());
                }
            }
            LOG.debug("Return the TestServiceRequest object.");
            return request;
        }
    }
}
