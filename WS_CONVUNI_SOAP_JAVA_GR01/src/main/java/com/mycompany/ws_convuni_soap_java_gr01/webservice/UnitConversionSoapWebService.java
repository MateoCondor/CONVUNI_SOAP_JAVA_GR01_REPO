package com.mycompany.ws_convuni_soap_java_gr01.webservice;

import com.mycompany.ws_convuni_soap_java_gr01.controller.AuthenticationController;
import com.mycompany.ws_convuni_soap_java_gr01.controller.ConversionController;
import com.mycompany.ws_convuni_soap_java_gr01.model.ConversionResponse;
import com.mycompany.ws_convuni_soap_java_gr01.model.LoginResponse;
import com.mycompany.ws_convuni_soap_java_gr01.service.AuthenticationService;
import com.mycompany.ws_convuni_soap_java_gr01.service.UnitConversionService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(
        serviceName = "UnitConversionService",
        portName = "UnitConversionPort",
        targetNamespace = "http://webservice.ws_convuni_soap_java_gr01.mycompany.com/"
)
public class UnitConversionSoapWebService {

    private static final AuthenticationService AUTHENTICATION_SERVICE = new AuthenticationService();
    private static final AuthenticationController AUTH_CONTROLLER =
            new AuthenticationController(AUTHENTICATION_SERVICE);
    private static final ConversionController CONVERSION_CONTROLLER =
        new ConversionController(new UnitConversionService());

    @WebMethod(operationName = "login")
    public LoginResponse login(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) {
        return AUTH_CONTROLLER.login(username, password);
    }

    @WebMethod(operationName = "convertLength")
    public ConversionResponse convertLength(
            @WebParam(name = "value") double value,
            @WebParam(name = "fromUnit") String fromUnit,
            @WebParam(name = "toUnit") String toUnit) {
        return CONVERSION_CONTROLLER.convertLength(value, fromUnit, toUnit);
    }

    @WebMethod(operationName = "convertMass")
    public ConversionResponse convertMass(
            @WebParam(name = "value") double value,
            @WebParam(name = "fromUnit") String fromUnit,
            @WebParam(name = "toUnit") String toUnit) {
        return CONVERSION_CONTROLLER.convertMass(value, fromUnit, toUnit);
    }

    @WebMethod(operationName = "convertTemperature")
    public ConversionResponse convertTemperature(
            @WebParam(name = "value") double value,
            @WebParam(name = "fromUnit") String fromUnit,
            @WebParam(name = "toUnit") String toUnit) {
        return CONVERSION_CONTROLLER.convertTemperature(value, fromUnit, toUnit);
    }

    @WebMethod(operationName = "healthCheck")
    public String healthCheck() {
        return "SOAP conversion service is running.";
    }
}
