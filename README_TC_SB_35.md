
# **SISTEMA DE GESTIÓN DE EMERGENCIAS VETERINARIAS - MVP**
**Producto mínimo viable**

**Desarrollado por:** Santiago Balcero.  
**Fecha de desarrollo:** noviembre - diciembre, 2022.  

## **DESCRIPCIÓN**

Sistema para la gestión de emergencias veterinarias. Permite a una empresa que ofrece servicios de asistencia de medicina veterinaria gestionar el registro, actualización, desactivación, activación y borrado de clínicas veterinarias aliadas y sus veterinarios.  
Para que los clientes de esta empresa puedan encontrar la clínica veterinaria más cercana a su ubicación, el sistema cuenta con un mapa marcado con la ubicación de cada clínica aliada. Los clientes con suscripción activa pueden registrar sus emergencias y solicitar atención en alguna de las clínicas aliadas. La cobertura máxima para cada cliente es de 2 emergencias atendidas durante el periodo de su suscripción.  
El sistema permite a un administrador de la empresa que ofrece la cobertura la carga masiva de clientes a través de archivos CSV. También le permite la visualización y descarga de reportes con la totalización de las emergencias cubiertas para cada año, mes, especie atendida y síntoma reportado, durante los últimos 5 años.

## **STACK TECNOLÓGICO**  
Stack basado en los requerimientos del ejercicio.


### **BASE DE DATOS**

- Motor de base de datos: Oracle 21 XE
- Gestor de base de datos: DBeaver versión 22.2.1

### **BACKEND**

- Java - JDK versión 11.0.16
- Spring Boot - versión 2.7.6
- Gradle - versión 7.5.1
- Editor de código: Visual Studio Code versión 1.74.2

#### **DEPENDENCIAS BACKEND**

Todas las dependencias pueden ser encontradas en [Maven Repository](https://mvnrepository.com/).  
Dependencias en el archivo gradle.build del proyecto Spring Boot.

    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-validation', version: '2.7.5'
    implementation group: 'net.sf.supercsv', name: 'super-csv', version: '2.4.0'
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-batch', version: '2.7.0'
    implementation 'commons-codec:commons-codec:1.15'
    implementation 'org.modelmapper:modelmapper:3.1.0'
    implementation group: 'com.google.maps', name: 'google-maps-services', version: '2.1.2'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.oracle.database.jdbc:ojdbc8'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

#### **COMUNICACIÓN CON APIS EXTERNAS EN BACKEND**

- **Google:** para obtener las coordenadas de las clínicas veterinarias y poder mostrar su ubicación en el mapa, el sistema se comunica con la [API de Geocoding de Google](https://developers.google.com/maps/documentation/geocoding).

### **FRONTEND**

- TypeScript - versión 4.7.2
- Angular - versión 14.2.7
- NodeJs - versión 16.17.0
- Editor de código: Visual Studio Code versión 1.74.2

#### **DEPENDENCIAS BACKEND**

Todas las dependencias pueden instalarse utilizando el siguiente comando en alguna consola abierta en la carpeta raíz del proyecto.  

    npm install nombre_de_dependencia

Dependencias en el archivo package.json en el proyecto Angular.  

    "@angular/animations": "^14.2.0",
    "@angular/common": "^14.2.0",
    "@angular/compiler": "^14.2.0",
    "@angular/core": "^14.2.0",
    "@angular/forms": "^14.2.0",
    "@angular/platform-browser": "^14.2.0",
    "@angular/platform-browser-dynamic": "^14.2.0",
    "@angular/router": "^14.2.0",
    "@types/googlemaps": "^3.43.3",
    "file-saver": "^2.0.5",
    "primeicons": "^6.0.1",
    "primeng": "14.2",
    "rxjs": "~7.5.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.11.4"

#### **COMUNICACIÓN CON APIS EXTERNAS EN FRONTEND**

- **Google:** para acceder a una instancia de mapa de Google el sistema se comunica con la [API de Maps Javascript de Google](https://developers.google.com/maps/documentation/javascript).
- **Gobierno de Colombia:** para obtener la lista de todos los municipios de cada departamento el sistema se comunica con la [API de datos del Gobierno de Colombia](https://www.datos.gov.co/).

## **INSTALACIÓN**

Para ejecutar el proyecto en diferentes entornos se deben seguir los siguientes pasos:

- Descargar la carpeta TC_SB_35 y ubicarla en el directorio C:\

#### **INSTALACIÓN BASE DE DATOS**

- Verificar que el nuevo entorno tenga instalada una instancia de base de datos de Oracle 21 XE
- Descomprimir el archivo que se encuentra en TC_SB_35/DB
- Entrar a la carpeta descomprimida
- Utilizar SQL Plus o SQL Developer para ejecutar el script TC_SB_35_SYS_SCRIPT.sql desde el usuario System en la base de datos Oracle
- Utilizar SQL PLus o SQL Developer para ejecutar el script TC_SB_35_SYS_SCHEMA_SCRIPT.sql desde el usuario creado en el script anterior
- Verificar que los objetos hayan sido creados correctamente
- En caso de utilizar DBeaver como gestor, se puede cargar cada script de las diferentes carpetas en TC_SB_35/DB siguiendo el orden que especifica el archivo TC_SB_35_SYS_SCRIPT.sql y posteriormente el orden que espeficica el archivo TC_SB_35_SYS_SCHEMA_SCRIPT.sql

#### **INSTALACIÓN BACKEND**

- Verificar que el nuevo entorno tenga instalados los elementos que componen el [stack tecnológico del backend](#backend)
- Descomprimir el archivo que se encuentra en TC_SB_35/API
- Abrir la carpeta descomprimida en Visual Studio Code

#### **INSTALACIÓN FRONTEND**

- Verificar que el nuevo entorno tenga instalados los elementos que componen el [stack tecnológico del frontend](#frontend)
- Descomprimir el archivo que se encuentra en TC_SB_35/Front
- Abrir la carpeta descomprimida en Visual Studio Code
- Debido a que la carpeta fue comprimida sin la subcarpeta node_modules, abrir una nueva terminal desde la carpeta descomprimida y ejecutar el comando: npm install