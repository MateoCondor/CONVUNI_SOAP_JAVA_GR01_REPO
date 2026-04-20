package ec.espe.monster.views;

import java.awt.event.ActionListener;
import java.util.List;

import ec.espe.monster.enums.UnitConversionType;

public interface IUnitConversionView {

    UnitConversionType getUnitConversionTypeSelected();

    Enum<?> getConversionUnitSource();

    Enum<?> getConversionUnitDestination();

    String getConversionUnitValue();

    void setLogoutListener(ActionListener listener);

    void setUnitConversionTypeChangedListener(ActionListener listener);

    void setSubmitListener(ActionListener listener);

    void loadUnitConversionTypes(List<UnitConversionType> options);

    <T extends Enum<T>> void loadConversionUnits(List<T> options);

    // Métodos de respuesta y control
    void showErrorMessage(String message);

    void showResult(double result);

    void resetForm();

    void showView();

    void hideView();
}