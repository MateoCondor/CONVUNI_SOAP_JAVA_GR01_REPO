package ec.edu.monster.services;

import java.util.concurrent.CompletableFuture;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import ec.edu.monster.dto.UnitConversionRequest;
import ec.edu.monster.models.LengthConversion;
import ec.edu.monster.models.MassConversion;
import ec.edu.monster.models.TemperatureConversion;
import ec.edu.monster.models.UnitConversionResult;
import ec.espe.monster.clients.ConversionResultType;
import ec.espe.monster.clients.UnitConversionSoapWebService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnitConversionService {
    private final UnitConversionSoapWebService client;

    public CompletableFuture<UnitConversionResult> convertMass(MassConversion conversion) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UnitConversionRequest dto = new UnitConversionRequest(conversion.getFrom().value,
                        conversion.getTo().value, conversion.getValue());

                ConversionResultType response = client.convertMass(dto.value(), dto.fromUnit(), dto.toUnit());

                return new UnitConversionResult(response.getConvertedValue(), response.getMessage());
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

    public CompletableFuture<UnitConversionResult> convertLength(LengthConversion conversion) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UnitConversionRequest dto = new UnitConversionRequest(conversion.getFrom().value,
                        conversion.getTo().value, conversion.getValue());

                ConversionResultType response = client.convertLength(dto.value(), dto.fromUnit(), dto.toUnit());

                return new UnitConversionResult(response.getConvertedValue(), response.getMessage());
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

    public CompletableFuture<UnitConversionResult> convertTemperature(TemperatureConversion conversion) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UnitConversionRequest dto = new UnitConversionRequest(conversion.getFrom().value,
                        conversion.getTo().value, conversion.getValue());

                ConversionResultType response = client.convertTemperature(dto.value(), dto.fromUnit(), dto.toUnit());

                return new UnitConversionResult(response.getConvertedValue(), response.getMessage());
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
