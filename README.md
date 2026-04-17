# CONVUNI SOAP JAVA GR01

Repositorio con 3 módulos:

- `WS_CONVUNI_SOAP_JAVA_GR01`: backend Java (Jakarta EE) con servicios SOAP y endpoints REST.
- `CLIMOV_CONVUNI_SOAP_JAVA_GR01`: cliente móvil (Expo/React Native).
- `CLIWEB_CONVUNI_SOAP_JAVA_GR01`: cliente web (Expo Web).

## 1) Requisitos previos

Instala estas herramientas antes de iniciar:

- Git
- Node.js 20 o superior
- npm 10 o superior
- Java JDK 17
- Maven 3.9 o superior
- Servidor Jakarta EE 10 para desplegar el `.war` (recomendado: Payara 6 o GlassFish 7)

Opcional para pruebas móviles:

- Expo Go (Android/iOS)
- Android Studio (si usarás emulador)

## 2) Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd CONVUNI_SOAP_JAVA_GR01_REPO
```

Si ya lo tienes clonado, solo entra a la carpeta raíz del proyecto.

## 3) Instalar dependencias

Desde la raíz del repositorio, ejecuta:

```bash
cd CLIMOV_CONVUNI_SOAP_JAVA_GR01
npm install

cd ..\CLIWEB_CONVUNI_SOAP_JAVA_GR01
npm install

cd ..\WS_CONVUNI_SOAP_JAVA_GR01
mvn clean package
```

Esto deja instaladas las dependencias de los clientes y genera el artefacto WAR del backend.

## 4) Levantar el backend (SOAP + REST)

1. Genera el WAR (si no lo hiciste ya):

```bash
cd WS_CONVUNI_SOAP_JAVA_GR01
mvn clean package
```

2. Despliega `target/WS_CONVUNI_SOAP_JAVA_GR01-1.0-SNAPSHOT.war` en tu servidor Jakarta EE (Payara/GlassFish).

3. Verifica que el backend quede publicado en `http://localhost:8080/WS_CONVUNI_SOAP_JAVA_GR01`.

La API REST queda bajo:

`http://localhost:8080/WS_CONVUNI_SOAP_JAVA_GR01/resources`

Credenciales de prueba del login:

- Usuario: `MONSTER`
- Clave: `MONSTER9`

## 5) Configurar URL del API en los clientes

### 5.1 Para Móvil

```
Usamos: http://TU_IP_LOCAL:8080/WS_CONVUNI_SOAP_JAVA_GR01/resources
```

Ejemplo: `http://192.168.1.10:8080/WS_CONVUNI_SOAP_JAVA_GR01/resources`

Usa la IP de tu computadora cuando pruebes desde celular o emulador Android.


Si abres la web desde otra máquina, usa la IP de la máquina que ejecuta el backend.

## 6) Ejecutar clientes

### 6.1 Cliente móvil

```bash
cd CLIMOV_CONVUNI_SOAP_JAVA_GR01
npx expo start --clear
```

Luego elige en consola:

- `a` para Android
- `w` para web
- o escanea el QR con Expo Go

### 6.2 Cliente web

```bash
cd CLIWEB_CONVUNI_SOAP_JAVA_GR01
npx expo start --web
```

## 7) Comprobación rápida

1. Backend desplegado y accesible en puerto 8080.
2. Clientes con configurados a la URL correcta.
3. Login con `MONSTER / MONSTER9`.
4. Conversión de unidades funcionando en las categorías length, mass y temperature.
