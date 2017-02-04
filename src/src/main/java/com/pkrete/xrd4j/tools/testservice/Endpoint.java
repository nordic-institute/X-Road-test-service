package com.pkrete.xrd4j.tools.testservice;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.message.ErrorMessage;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.common.util.PropertiesUtil;
import com.pkrete.xrd4j.server.AbstractAdapterServlet;
import com.pkrete.xrd4j.server.deserializer.AbstractCustomRequestDeserializer;
import com.pkrete.xrd4j.server.deserializer.CustomRequestDeserializer;
import com.pkrete.xrd4j.server.serializer.AbstractServiceResponseSerializer;
import com.pkrete.xrd4j.server.serializer.ServiceResponseSerializer;
import com.pkrete.xrd4j.tools.testservice.model.TestServiceRequest;
import com.pkrete.xrd4j.tools.testservice.model.TestServiceResponse;
import com.pkrete.xrd4j.tools.testservice.util.ApplicationHelper;
import java.util.Properties;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements single X-Road v6 compatible service: "testService".
 * Service descriptions are defined in "test-servuce.wsdl" file that's located
 * in WEB-INF/classes folder. The name of the WSDL file and the namespace is
 * configured in WEB-INF/classes/test-service.properties file.
 *
 * @author Petteri Kivimäki
 */
public class Endpoint extends AbstractAdapterServlet {

    private Properties props;
    private static final Logger logger = LoggerFactory.getLogger(Endpoint.class);
    private String namespaceSerialize;
    private String namespaceDeserialize;
    private String prefix;

    @Override
    public void init() {
        super.init();
        logger.debug("Starting to initialize Enpoint.");
        this.props = PropertiesUtil.getInstance().load("/test-service.properties");
        this.namespaceSerialize = this.props.getProperty("namespace.serialize");
        this.namespaceDeserialize = this.props.getProperty("namespace.deserialize");
        this.prefix = this.props.getProperty("namespace.prefix.serialize");
        logger.debug("Namespace for incoming ServiceRequests : \"" + this.namespaceDeserialize + "\".");
        logger.debug("Namespace for outgoing ServiceResponses : \"" + this.namespaceSerialize + "\".");
        logger.debug("Namespace prefix for outgoing ServiceResponses : \"" + this.prefix + "\".");
        logger.debug("Endpoint initialized.");
    }

    /**
     * Must return the path of the WSDL file.
     *
     * @return absolute path of the WSDL file
     */
    @Override
    protected String getWSDLPath() {
        String path = this.props.getProperty("wsdl.path");
        logger.debug("WSDL path : \"" + path + "\".");
        return path;
    }

    @Override
    protected ServiceResponse handleRequest(ServiceRequest request) throws SOAPException, XRd4JException {
        long startTime = System.currentTimeMillis();
        ServiceResponseSerializer serializer;
        ServiceResponse<TestServiceRequest, TestServiceResponse> response;

        // Process services by service code
        if ("testService".equals(request.getProducer().getServiceCode())) {
            // Process "helloService" service
            logger.info("Process \"testService\" service.");
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
            logger.debug("Start message prosessing.");
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
                logger.warn("No \"name\" parameter found. Return a non-techinal error message.");
                ErrorMessage error = new ErrorMessage("422", "422 Unprocessable Entity. Missing \"name\" element.");
                response.setErrorMessage(error);
            }
            logger.debug("Message prosessing done!");
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
                AttachmentPart attachPart = response.getSoapMessage().createAttachmentPart("<attachment>" + serviceResponse.getResponseAttachment() + "</attachment>", "application/xml");
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
                logger.warn("\"requestNode\" is null. Null is returned.");
                return null;
            }
            // Create new TestServiceRequest object
            TestServiceRequest request = new TestServiceRequest();
            // Loop through the request
            for (int i = 0; i < requestNode.getChildNodes().getLength(); i++) {
                // Request data is inside of "name" element
                if (requestNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE
                        && "responseBodySize".equals(requestNode.getChildNodes().item(i).getLocalName())) {
                    logger.debug("Found \"responseBodySize\" element.");
                    // "responseBodySize" element was found - set value
                    request.setResponseBodySize(requestNode.getChildNodes().item(i).getTextContent());
                } else if (requestNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE
                        && "responseAttachmentSize".equals(requestNode.getChildNodes().item(i).getLocalName())) {
                    logger.debug("Found \"responseAttachmentSize\" element.");
                    // "responseAttachmentSize" element was found - set value
                    request.setResponseAttachmentSize(requestNode.getChildNodes().item(i).getTextContent());
                }
            }
            logger.warn("Return the TestServiceRequest object.");
            return request;
        }
    }
}
