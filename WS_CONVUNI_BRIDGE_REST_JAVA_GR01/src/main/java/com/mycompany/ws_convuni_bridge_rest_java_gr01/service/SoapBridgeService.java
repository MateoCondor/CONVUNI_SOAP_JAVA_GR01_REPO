package com.mycompany.ws_convuni_bridge_rest_java_gr01.service;

import com.mycompany.ws_convuni_bridge_rest_java_gr01.model.ConversionResponse;
import com.mycompany.ws_convuni_bridge_rest_java_gr01.model.LoginResponse;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class SoapBridgeService {

    private static final String SOAP_NAMESPACE = "http://webservice.ws_convuni_soap_java_gr01.mycompany.com/";
    private static final String SOAP_ENDPOINT_PROPERTY = "convuni.soap.endpoint";
    private static final String SOAP_ENDPOINT_ENV = "CONVUNI_SOAP_ENDPOINT";
    private static final String DEFAULT_SOAP_ENDPOINT =
            "http://localhost:8080/WS_CONVUNI_SOAP_JAVA_GR01/UnitConversionService";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public LoginResponse login(String username, String password) {
        String body = "<web:login>"
                + "<username>" + escapeXml(username) + "</username>"
                + "<password>" + escapeXml(password) + "</password>"
                + "</web:login>";

        try {
            Document document = invokeSoap("login", body);
            String fault = readTag(document, "faultstring");
            if (!fault.isEmpty()) {
                return new LoginResponse(false, fault);
            }

            boolean success = Boolean.parseBoolean(readTag(document, "success"));
            String message = readTag(document, "message");
            return new LoginResponse(success, message);
        } catch (Exception ex) {
            return new LoginResponse(false, "Failed to consume SOAP login service: " + ex.getMessage());
        }
    }

    public ConversionResponse convert(String category, double value, String fromUnit, String toUnit) {
        String operation = resolveOperation(category);
        if (operation.isEmpty()) {
            return new ConversionResponse(false,
                    "Invalid category. Use length, mass or temperature.",
                    category,
                    value,
                    fromUnit,
                    toUnit,
                    0);
        }

        String body = "<web:" + operation + ">"
                + "<value>" + value + "</value>"
                + "<fromUnit>" + escapeXml(fromUnit) + "</fromUnit>"
                + "<toUnit>" + escapeXml(toUnit) + "</toUnit>"
                + "</web:" + operation + ">";

        try {
            Document document = invokeSoap(operation, body);
            String fault = readTag(document, "faultstring");
            if (!fault.isEmpty()) {
                return new ConversionResponse(false,
                        fault,
                        category,
                        value,
                        fromUnit,
                        toUnit,
                        0);
            }

            boolean success = Boolean.parseBoolean(readTag(document, "success"));
            String message = readTag(document, "message");
            String responseCategory = readTag(document, "category");
            double inputValue = readDoubleTag(document, "inputValue", value);
            String responseFromUnit = readTag(document, "fromUnit");
            String responseToUnit = readTag(document, "toUnit");
            double convertedValue = readDoubleTag(document, "convertedValue", 0);

            return new ConversionResponse(
                    success,
                    message,
                    responseCategory.isEmpty() ? category : responseCategory,
                    inputValue,
                    responseFromUnit.isEmpty() ? fromUnit : responseFromUnit,
                    responseToUnit.isEmpty() ? toUnit : responseToUnit,
                    convertedValue);
        } catch (Exception ex) {
            return new ConversionResponse(false,
                    "Failed to consume SOAP conversion service: " + ex.getMessage(),
                    category,
                    value,
                    fromUnit,
                    toUnit,
                    0);
        }
    }

    private Document invokeSoap(String operation, String operationBody) throws Exception {
        String endpoint = resolveSoapEndpoint();
        String envelope = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "xmlns:web=\"" + SOAP_NAMESPACE + "\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + operationBody
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";

        HttpRequest request = HttpRequest.newBuilder(URI.create(endpoint))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "\"" + SOAP_NAMESPACE + operation + "\"")
                .POST(HttpRequest.BodyPublishers.ofString(envelope, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() >= 500) {
            throw new IllegalStateException("SOAP server error (" + response.statusCode() + ").");
        }

        if (response.statusCode() >= 400) {
            throw new IllegalStateException("SOAP request failed with status " + response.statusCode() + ".");
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new InputSource(new StringReader(response.body())));
    }

    private String resolveSoapEndpoint() {
        String fromSystemProperty = System.getProperty(SOAP_ENDPOINT_PROPERTY);
        if (fromSystemProperty != null && !fromSystemProperty.trim().isEmpty()) {
            return fromSystemProperty.trim();
        }

        String fromEnvironment = System.getenv(SOAP_ENDPOINT_ENV);
        if (fromEnvironment != null && !fromEnvironment.trim().isEmpty()) {
            return fromEnvironment.trim();
        }

        return DEFAULT_SOAP_ENDPOINT;
    }

    private String resolveOperation(String category) {
        if ("length".equals(category)) {
            return "convertLength";
        }
        if ("mass".equals(category)) {
            return "convertMass";
        }
        if ("temperature".equals(category)) {
            return "convertTemperature";
        }
        return "";
    }

    private String readTag(Document document, String localName) {
        NodeList namespacedNodes = document.getElementsByTagNameNS("*", localName);
        if (namespacedNodes.getLength() > 0) {
            return namespacedNodes.item(0).getTextContent().trim();
        }

        NodeList plainNodes = document.getElementsByTagName(localName);
        if (plainNodes.getLength() > 0) {
            return plainNodes.item(0).getTextContent().trim();
        }

        return "";
    }

    private double readDoubleTag(Document document, String localName, double defaultValue) {
        String rawValue = readTag(document, localName);
        if (rawValue.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(rawValue);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private String escapeXml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
