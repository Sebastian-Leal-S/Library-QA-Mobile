# Library-QA-Mobile

- [Library-QA-Mobile](#library-qa-mobile)
  - [Introducción](#introducción)
  - [Instalación](#instalación)
    - [Requisitos](#requisitos)
    - [Uso en proyectos maven](#uso-en-proyectos-maven)
  - [Ejemplo test](#ejemplo-test)
  - [Métodos](#métodos)
  - [Contribuciones](#contribuciones)

## Introducción

Libray-QA-Mobile se crea con la idea de mejorar dia a dia, basado en el código y necesidades que satisfacía las Shared Libraries se diseño eh implemento el proyecto de Library-QA-Mobile haciendo uso de tecnologías como Java, Selenium, Appium, TestNG, log4j entre otras. Cuenta con métodos similares a los existentes de Shared Libraries provenientes de Selenium como click, sendKeys o clear pero aumentando sus capacidades como tiempos dinámicos de espera, fácil manejo de la generación de evidencias, mientras su complejidad disminuía favoreciendo su uso dentro de la fabrica QA - Squad Automatización.

## Instalación

> La version actual: **0.1**

### Requisitos

- Java JDK versión 17 o superior
  - Establece la variable de entorno `JAVA_HOME` en la ubicación del ejecutable de Java (el JDK, no el JRE).
  - Para probar esto, intenta ejecutar el comando ```javac```. Este comando no existirá si solo tienes instalado el JRE. Si te encuentras con una lista de opciones de línea de comandos, estás referenciando correctamente al JDK.

### Uso en proyectos maven

- Maven versión 3.9.x o suoerior
	- Establece la variable de entorno `M2_HOME` en la ubicacion donde se encuentre la carpeta bin de maven
	- Para probar esto, intenta ejecutar el comando ```mvn -v```. Este comando debería mostrar la versión de Maven
	  instalada junto con la versión de Java que está utilizando.

Comando de instalación desde CMD

``` CMD
mvn install:install-file -Dfile=< ruta-jar >\Library-QA-Mobile.jar -DgroupId=com.periferiaitgroup -DartifactId=Library-QA -Dversion=< Version-Library-QA > -Dpackaging=jar
```

Llamado dependencia en pom.xml

``` MAVEN
<dependencies>
 <dependency>
  <groupId>com.periferiaitgroup</groupId>
     <artifactId>Library-QA-Mobile</artifactId>
     <version>${Library-QA.version}</version>
 </dependency>
</dependencies>
```

## Ejemplo test

TODO: Documentar caso completo con DataProvider, métodos Before y After.

## Métodos

TODO: Documentar métodos generales y todos los métodos existentes

## Contribuciones

TODO: Directrices para contribuir al proyecto, incluyendo información sobre cómo reportar problemas y enviar pull requests.
