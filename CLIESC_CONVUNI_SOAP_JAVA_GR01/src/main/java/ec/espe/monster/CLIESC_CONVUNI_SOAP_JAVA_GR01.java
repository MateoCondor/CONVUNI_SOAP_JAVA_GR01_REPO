/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.espe.monster;

import ec.espe.monster.clients.UnitConversionService;
import ec.espe.monster.clients.UnitConversionSoapWebService;
import ec.espe.monster.controllers.AuthController;
import ec.espe.monster.services.AuthService;
import ec.espe.monster.views.LoginView;

/**
 *
 * @author alexa
 */
public class CLIESC_CONVUNI_SOAP_JAVA_GR01 {

    public static void main(String[] args) {

        try {

            UnitConversionService locator = new UnitConversionService();
            UnitConversionSoapWebService client = locator.getUnitConversionPort();
            AuthService authService = new AuthService(client);

            java.awt.EventQueue.invokeLater(() -> {
                try {
                    LoginView loginView = new LoginView();
                    new AuthController(loginView, authService, client);
                    loginView.setVisible(true);
                    loginView.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception ex) {
            System.err.println("Error al conectar con el servicio SOAP: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
