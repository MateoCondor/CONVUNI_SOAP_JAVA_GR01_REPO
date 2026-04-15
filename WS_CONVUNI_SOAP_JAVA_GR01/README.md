# WS_CONVUNI_SOAP_JAVA_GR01

Servicio web de conversion de unidades con Jakarta EE, Maven y Java 17.

Incluye:
- API SOAP para autenticacion y conversion de unidades.

## Tecnologias

- Java 17
- Maven (proyecto `war`)
- Jakarta EE 10
- JAX-WS (SOAP)
- JAX-RS (REST)

## Estructura Principal

- `src/main/java/com/mycompany/ws_convuni_soap_java_gr01/webservice/UnitConversionSoapWebService.java`
- `src/main/java/com/mycompany/ws_convuni_soap_java_gr01/service/UnitConversionService.java`
- `src/main/java/com/mycompany/ws_convuni_soap_java_gr01/service/AuthenticationService.java`

## Operaciones SOAP Disponibles

Servicio: `UnitConversionService`

- `login(username, password)`
- `convertLength(value, fromUnit, toUnit)`
- `convertMass(value, fromUnit, toUnit)`
- `convertTemperature(value, fromUnit, toUnit)`
- `healthCheck()`

### Credenciales de prueba

- Usuario: `MONSTER`
- Clave: `MONSTER9`

## Unidades soportadas

- Longitud: `mm`, `cm`, `m`, `km`, `in`, `ft`, `yd`, `mi`
- Masa: `mg`, `g`, `kg`, `lb`, `oz`, `t`
- Temperatura: `c`, `f`, `k`


## Notas

- Las credenciales estan hardcodeadas en `AuthenticationService`; para produccion deberian moverse a una configuracion segura.
- El directorio `target/` no debe subirse al repositorio (esta excluido en `.gitignore`).