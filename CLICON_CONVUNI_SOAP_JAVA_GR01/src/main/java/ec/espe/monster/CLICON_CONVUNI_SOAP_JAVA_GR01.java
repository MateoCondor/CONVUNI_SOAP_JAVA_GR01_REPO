/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.espe.monster;

import ec.espe.monster.clients.UnitConversionSoapWebService;
import ec.espe.monster.controllers.AuthController;
import ec.espe.monster.controllers.UnitConversionController;
import ec.espe.monster.models.User;
import ec.espe.monster.services.AuthService;
import ec.espe.monster.services.UnitConversionService;
import ec.espe.monster.views.LoginView;
import ec.espe.monster.views.UnitConversionView;

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
