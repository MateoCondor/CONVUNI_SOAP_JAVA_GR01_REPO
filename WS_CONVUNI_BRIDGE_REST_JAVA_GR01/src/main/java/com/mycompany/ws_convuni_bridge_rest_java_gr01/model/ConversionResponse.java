package com.mycompany.ws_convuni_bridge_rest_java_gr01.model;

public class ConversionResponse {

    private boolean success;
    private String message;
    private String category;
    private double inputValue;
    private String fromUnit;
    private String toUnit;
    private double convertedValue;

    public ConversionResponse() {
    }

    public ConversionResponse(boolean success, String message, String category,
            double inputValue, String fromUnit, String toUnit, double convertedValue) {
        this.success = success;
        this.message = message;
        this.category = category;
        this.inputValue = inputValue;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.convertedValue = convertedValue;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit;
    }

    public double getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(double convertedValue) {
        this.convertedValue = convertedValue;
    }
}
