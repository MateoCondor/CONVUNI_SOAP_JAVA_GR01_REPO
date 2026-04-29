package ec.edu.monster.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "conversionResult")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConversionResultType")
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

    public static ConversionResponse unauthorized(String category) {
        return new ConversionResponse(false,
                "Token invalido o expirado. Debe autenticarse nuevamente.",
                category,
                0,
                "",
                "",
                0);
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
