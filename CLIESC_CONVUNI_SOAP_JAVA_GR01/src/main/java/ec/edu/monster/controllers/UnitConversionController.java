package ec.edu.monster.controllers;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import javax.swing.SwingUtilities;

import ec.edu.monster.enums.LengthUnit;
import ec.edu.monster.enums.MassUnit;
import ec.edu.monster.enums.TemperatureUnit;
import ec.edu.monster.enums.UnitConversionType;
import ec.edu.monster.models.LengthConversion;
import ec.edu.monster.models.MassConversion;
import ec.edu.monster.models.TemperatureConversion;
import ec.edu.monster.models.UnitConversionResult;
import ec.edu.monster.services.UnitConversionService;
import ec.edu.monster.views.IUnitConversionView;

public class UnitConversionController {
    private final IUnitConversionView view;
    private final UnitConversionService service;

    public UnitConversionController(IUnitConversionView view, UnitConversionService service) {
        this.view = view;
        this.service = service;

        this.view.setUnitConversionTypeChangedListener(e -> loadConversionUnits());
        this.view.setSubmitListener(e -> convert());

        initView();
    }

    private void initView() {

        view.loadUnitConversionTypes(Arrays.asList(UnitConversionType.values()));

        loadConversionUnits();
    }

    private void loadConversionUnits() {
        UnitConversionType selected = view.getUnitConversionTypeSelected();
        if (selected == null)
            return;

        switch (selected) {
            case Length -> view.loadConversionUnits(Arrays.asList(LengthUnit.values()));
            case Mass -> view.loadConversionUnits(Arrays.asList(MassUnit.values()));
            case Temperature -> view.loadConversionUnits(Arrays.asList(TemperatureUnit.values()));
        }
    }

    private void convert() {
        try {

            double value;
            try {
                value = Double.parseDouble(view.getConversionUnitValue());
            } catch (NumberFormatException e) {
                view.showErrorMessage("El valor de conversión tiene que ser un número válido.");
                return;
            }

            UnitConversionType selectedType = view.getUnitConversionTypeSelected();
            CompletableFuture<UnitConversionResult> futureResponse;

            view.resetForm();

            switch (selectedType) {
                case Mass -> {
                    MassConversion dto = new MassConversion();
                    dto.setFrom((MassUnit) view.getConversionUnitSource());
                    dto.setTo((MassUnit) view.getConversionUnitDestination());
                    dto.setValue(value);
                    futureResponse = service.convertMass(dto);
                }
                case Length -> {
                    LengthConversion dto = new LengthConversion();
                    dto.setFrom((LengthUnit) view.getConversionUnitSource());
                    dto.setTo((LengthUnit) view.getConversionUnitDestination());
                    dto.setValue(value);
                    futureResponse = service.convertLength(dto);
                }
                case Temperature -> {
                    TemperatureConversion dto = new TemperatureConversion();
                    dto.setFrom((TemperatureUnit) view.getConversionUnitSource());
                    dto.setTo((TemperatureUnit) view.getConversionUnitDestination());
                    dto.setValue(value);
                    futureResponse = service.convertTemperature(dto);
                }
                default -> throw new IllegalArgumentException("Tipo de conversión no válida");
            }

            futureResponse.thenAccept(response -> {
                SwingUtilities.invokeLater(() -> view.showResult(response.value()));
            }).exceptionally(ex -> {
                SwingUtilities.invokeLater(() -> {
                    String msg = (ex.getCause() != null) ? ex.getCause().getMessage() : ex.getMessage();
                    view.showErrorMessage(msg);
                });
                return null;
            });

        } catch (Exception ex) {
            view.showErrorMessage(ex.getMessage());
        }
    }
}