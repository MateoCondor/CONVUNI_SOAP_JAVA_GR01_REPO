package com.mycompany.ws_convuni_bridge_rest_java_gr01.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.mycompany.ws_convuni_bridge_rest_java_gr01.model.ConversionResponse;
import com.mycompany.ws_convuni_bridge_rest_java_gr01.model.LoginResponse;
import com.mycompany.ws_convuni_bridge_rest_java_gr01.model.request.ConversionRequest;
import com.mycompany.ws_convuni_bridge_rest_java_gr01.model.request.LoginRequest;
import com.mycompany.ws_convuni_bridge_rest_java_gr01.service.SoapBridgeService;
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
public class ConversionApiBridgeResource {

    private static final SoapBridgeService SOAP_BRIDGE_SERVICE = new SoapBridgeService();

    @POST
    @Path("login")
    @Operation(operationId = "loginBridge")
    public Response login(LoginRequest request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new LoginResponse(false, "Request body is required."))
                    .build();
        }

        LoginResponse response = SOAP_BRIDGE_SERVICE.login(request.getUsername(), request.getPassword());
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

        String normalizedCategory = normalize(category);
        if (!isSupportedCategory(normalizedCategory)) {
            ConversionResponse invalidCategoryResponse = new ConversionResponse(false,
                    "Invalid category. Use length, mass or temperature.",
                    category,
                    request.getValue(),
                    request.getFromUnit(),
                    request.getToUnit(),
                    0);
            return Response.status(Response.Status.BAD_REQUEST).entity(invalidCategoryResponse).build();
        }

        ConversionResponse response = SOAP_BRIDGE_SERVICE.convert(
                normalizedCategory,
                request.getValue(),
                request.getFromUnit(),
                request.getToUnit());

        return Response.ok(response).build();
    }

    private boolean isSupportedCategory(String category) {
        return "length".equals(category)
                || "mass".equals(category)
                || "temperature".equals(category);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
