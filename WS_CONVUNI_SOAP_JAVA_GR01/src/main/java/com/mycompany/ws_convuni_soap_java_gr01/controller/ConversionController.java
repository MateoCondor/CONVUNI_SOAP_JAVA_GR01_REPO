package com.mycompany.ws_convuni_soap_java_gr01.controller;

import com.mycompany.ws_convuni_soap_java_gr01.model.ConversionResponse;
import com.mycompany.ws_convuni_soap_java_gr01.service.UnitConversionService;

public class ConversionController {

    private final UnitConversionService unitConversionService;

    public ConversionController(UnitConversionService unitConversionService) {
        this.unitConversionService = unitConversionService;
    }

    public ConversionResponse convertLength(double value, String fromUnit, String toUnit) {
        return unitConversionService.convertLength(value, fromUnit, toUnit);
    }

    public ConversionResponse convertMass(double value, String fromUnit, String toUnit) {
        return unitConversionService.convertMass(value, fromUnit, toUnit);
    }

    public ConversionResponse convertTemperature(double value, String fromUnit, String toUnit) {
        return unitConversionService.convertTemperature(value, fromUnit, toUnit);
    }
}
