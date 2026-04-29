/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.monster;

import ec.edu.monster.controllers.AuthController;
import ec.edu.monster.controllers.UnitConversionController;
import ec.edu.monster.models.User;
import ec.edu.monster.services.AuthService;
import ec.edu.monster.services.UnitConversionService;
import ec.edu.monster.views.LoginView;
import ec.edu.monster.views.UnitConversionView;
import ec.espe.monster.clients.UnitConversionSoapWebService;

/**
 *
 * @author alex
 */
public class CLICON_CONVUNI_SOAP_JAVA_GR01 {

    public static void main(String[] args) {
        ec.espe.monster.clients.UnitConversionService locator = new ec.espe.monster.clients.UnitConversionService();

        UnitConversionSoapWebService soapClient = locator.getUnitConversionPort();

        AuthService authService = new AuthService(soapClient);
        LoginView loginView = new LoginView();

        AuthController authController = new AuthController(authService, loginView);

        User user = authController.runLogin();

        if (user.isAuth()) {
            UnitConversionService unitConversionService = new UnitConversionService(soapClient);
            UnitConversionView unitConversionView = new UnitConversionView();

            UnitConversionController unitConversionController = new UnitConversionController(
                    unitConversionService,
                    unitConversionView);

            unitConversionController.run();
        }
    }
}
