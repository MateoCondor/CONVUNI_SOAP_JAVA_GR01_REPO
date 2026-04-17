package com.mycompany.ws_convuni_soap_java_gr01.resources;

import com.mycompany.ws_convuni_soap_java_gr01.controller.AuthenticationController;
import com.mycompany.ws_convuni_soap_java_gr01.controller.ConversionController;
import com.mycompany.ws_convuni_soap_java_gr01.model.ConversionResponse;
import com.mycompany.ws_convuni_soap_java_gr01.model.LoginResponse;
import com.mycompany.ws_convuni_soap_java_gr01.model.request.ConversionRequest;
import com.mycompany.ws_convuni_soap_java_gr01.model.request.LoginRequest;
import com.mycompany.ws_convuni_soap_java_gr01.service.AuthenticationService;
import com.mycompany.ws_convuni_soap_java_gr01.service.UnitConversionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConversionApiResource {

    private static final AuthenticationController AUTH_CONTROLLER =
            new AuthenticationController(new AuthenticationService());
    private static final ConversionController CONVERSION_CONTROLLER =
            new ConversionController(new UnitConversionService());

    @POST
    @Path("login")
    public Response login(LoginRequest request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new LoginResponse(false, "Request body is required."))
                    .build();
        }

        LoginResponse response = AUTH_CONTROLLER.login(request.getUsername(), request.getPassword());
        return Response.ok(response).build();
    }

    @POST
    @Path("convert/{category}")
    public Response convert(@PathParam("category") String category, ConversionRequest request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ConversionResponse(false,
                            "Request body is required.",
                            category,
                            0,
                            "",
                            "",
                            0))
                    .build();
        }

        ConversionResponse response = switch (normalize(category)) {
            case "length" -> CONVERSION_CONTROLLER.convertLength(
                    request.getValue(), request.getFromUnit(), request.getToUnit());
            case "mass" -> CONVERSION_CONTROLLER.convertMass(
                    request.getValue(), request.getFromUnit(), request.getToUnit());
            case "temperature" -> CONVERSION_CONTROLLER.convertTemperature(
                    request.getValue(), request.getFromUnit(), request.getToUnit());
            default -> new ConversionResponse(false,
                    "Invalid category. Use length, mass or temperature.",
                    category,
                    request.getValue(),
                    request.getFromUnit(),
                    request.getToUnit(),
                    0);
        };

        if (!"length".equals(normalize(category))
                && !"mass".equals(normalize(category))
                && !"temperature".equals(normalize(category))) {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        return Response.ok(response).build();
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
