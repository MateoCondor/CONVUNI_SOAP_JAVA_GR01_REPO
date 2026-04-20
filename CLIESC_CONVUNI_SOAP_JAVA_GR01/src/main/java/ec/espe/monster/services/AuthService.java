package ec.espe.monster.services;

import java.util.concurrent.CompletableFuture;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import ec.espe.monster.clients.LoginResultType;
import ec.espe.monster.clients.UnitConversionSoapWebService;
import ec.espe.monster.dto.LoginRequest;
import ec.espe.monster.models.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthService {
    private final UnitConversionSoapWebService client;

    public CompletableFuture<User> Login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                LoginRequest dto = new LoginRequest(username, password);

                LoginResultType response = client.login(dto.username(), dto.password());

                return new User(dto.username(), response.isSuccess());
            } catch (SOAPFaultException ex) {
                throw new RuntimeException("Error en el servidor de conversión: " + ex.getFault().getFaultString());

            } catch (WebServiceException ex) {
                throw new RuntimeException(
                        "No se pudo establecer comunicación con el servicio SOAP. Verifique su conexión.");

            } catch (Exception ex) {
                throw new RuntimeException("Error inesperado: " + ex.getMessage());
            }
        });
    }
}
