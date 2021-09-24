# Operación Fuego de Quasar

_Han Solo ha sido recientemente nombrado General de l a Alianza
Rebelde y busca dar un gran golpe contra el Imperio Galáctico para
reavivar la llama de la resistencia.
El servicio de i nteligencia rebelde ha detectado un l lamado de auxilio de
una nave portacarga i mperial a l a deriva en un campo de asteroides. El
manifiesto de l a nave es ultra clasificado, pero se rumorea que
transporta raciones y armamento para una legión entera._


### Pre-requisitos 📋


```
Java - JDK 16.0.2
```

### Instalación 🔧

_Una vez descargados el proyecto del repositorio se debe proceder a compilar, en este caso se ejemplifica mediante cmd de windows_

_Ejecutar en la "ruta fisica del proyecto" la siguiente instruccion:_

```
mvnw.cmd clean install
```

_Ya compilado el proyecto se procede a ejecutar el jar_

_Ejecutar en la "ruta fisica del proyecto"/target la siguiente instruccion:_

```
java -jar operacion-fuego-quasar-1.0.0-SNAPSHOT.jar
```

_Con esto prodremos ver como el aplicativo levanta_

## Pruebas ⚙️

_Para realizar las pruebas del aplicativo deberan ser usando los siguiente endpoints:_

------------


> http://127.0.0.1:8001/topsecret

_Al ser un endpoint POST se requiere enviar el siguiente json en el cuerpo de la peticion:_

```
{
  "satellites": [
    {
      "name": "kenobi",
      "distance": 100,
      "message": [ "este", "", "", "mensaje", "" ]
    },
    {
      "name": "skywalker",
      "distance": 115.5,
      "message": [ "", "es", "", "", "secreto"
      ]
    },
    {
      "name": "sato",
      "distance": 142.7,
      "message": [  "este", "", "un", "", "" ]
    }
  ]
}
```
_El endpoint devolvera un mensaje similar al siguiente:_
```
{
    "position": {
        "x": -58.315252587138595,
        "y": -69.55141837312165
    },
    "message": "este es un mensaje secreto"
}
```
------------


> http://127.0.0.1:8001/topsecret_split/kenobi

_Al ser un endpoint POST se requiere enviar en siguiente json en el cuerpo de la peticion:_

```
{
  "distance": 100,
  "message": [ "este", "", "", "mensaje", "" ]
}
```

------------


> http://127.0.0.1:8001/topsecret_split/skywalker

_Al ser un endpoint POST se requiere enviar en siguiente json en el cuerpo de la peticion:_

```
{
  "distance": 115.5,
  "message": [ "", "es", "", "", "secreto" ]
}
```

------------


> http://127.0.0.1:8001/topsecret_split/sato

_Al ser un endpoint POST se requiere enviar en siguiente json en el cuerpo de la peticion:_

```
{
  "distance": 142.7,
  "message": [  "este", "", "un", "", "" ]
}
```

------------


> http://127.0.0.1:8001/topsecret_split

_Al ser un endpoint GET se espera que regrese un objeto json similar al siguiente:_

```
{
    "position": {
        "x": -58.315252587138595,
        "y": -69.55141837312165
    },
    "message": "este es un mensaje secreto"
}
```

## Construido con 🛠️


* [Spring boot](https://spring.io/)
* [Maven](https://maven.apache.org/)
* [Java - JDK 16.0.2](https://jdk.java.net/16/)


## Autores ✒️


* **Juan Paulino Olalde Granados** - *Desarrollador*

