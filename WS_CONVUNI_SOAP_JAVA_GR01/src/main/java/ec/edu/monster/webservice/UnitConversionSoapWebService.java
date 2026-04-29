package ec.edu.monster.webservice;

import ec.edu.monster.controller.AuthenticationController;
import ec.edu.monster.controller.ConversionController;
import ec.edu.monster.model.ConversionResponse;
import ec.edu.monster.model.LoginResponse;
import ec.edu.monster.service.AuthenticationService;
import ec.edu.monster.service.UnitConversionService;
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
