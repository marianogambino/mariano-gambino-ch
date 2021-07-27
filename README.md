# Wenance Challenge


## Solución
#### Se desarrollo un microservicio en Java 8 en el que consta de dos partes importantes:
 - Primero, se implemento un schedule(proceso programado) que cada 10 segundos llama a un componente(service) que obtiene los datos del servicio de BuenBit y los persiste en la base de datos.
   
    - Para obtener los datos desde el servicio de BuenBit se generaron las entidades/estrucutras de dato para mapear los mismos
     y luego persistirlos, como así obtenerlo desde la base de datos.
 - Segundo, se crearon tres endpoints para consumir los datos
   - Endpoint que obtiene el precio del bitcoin en pesos argentinos (se puede pasar el parametro btcType para obtener otro tipo de moneda por ejemplo btcType=daiusd)
>  http://localhost:8080/api/cryptocurrency?currentTime=26-07-2021T02:09:45Z
   - Endpoint que calcula el promedio entre dos timestamp
>  http://localhost:8080/api/cryptocurrency/averagePrice?startTime=26-07-2021T00:00:00Z&endTime=26-07-2021T05:00:00Z 
   - Endpoint que devuelve en forma pagina los datos almacenados
>  http://localhost:8080/api/cryptocurrency/allBtc?startTime=26-07-2021T01:00:00Z&endTime=26-07-2021T02:55:20Z&page=1&size=3
    
La solución esta desarrollada en Java 8 con SpringBoot, Lombok, una dependencia para realizar operaciones sobre la base de datos MongoDB.
Ademas, se implemenendo sobre docker una instancia de mongoDB, que es en donde, el microsservicio, persite y obtiene los datos.

## Instalacion de docker
Antes de realizar el start del microservicio se debe instalar docker

- Sobre Windows:
        
>    - Ir a -> https://docs.docker.com/docker-for-windows/install/

- Sobre Ubuntu:
>    - Ir a -> https://docs.docker.com/engine/install/ubuntu/

## Instalacion de MongoDB
Una vez instalado docker en la maquina local debemos instalar una instancia de MongoDB en docker

### Instalar mongo en docker
> docker pull mongo

## Antes de iniciar/levantar el microservicio tanto local como desde docker debemos ejecutar:
> mvn clean install

## Iniciar el microservicio en DOCKER
### Generar imagen e instalar en docker
Primero debemos ejecutar el siguiente comando desde root path de nuestro proyecto
> docker build --tag=wenancech:latest .

### Iniciar Micoservicio + MongoBD con docker-compose
Desde root path  del proyecto ejecutar:
> docker-compose up

### Para detener el Micoservicio + MongoBD con docker-compose.
Desde root path del proyecto ejecutar:
> docker-compose down

## Iniciar el microservicio de forma LOCAL (local machine)
#### Desde la linea de consola/comandos ejecutar:

> docker run  -p 27017:27017 --name some-mongo -d mongo:latest

y luego

> mvn spring-boot:run

Una vez ejecutado el servicio para deternerlo desde LOCAL ejecutamos:
> Ctrl + C

Una vez detenido el servicio realizamos el stop de MongoDB en el contendor:
> docker stop container some-mongo

## Test de Integración (Postman)
#### Una vez levantado el microservicio, se puede probar importando el archivo .json en Postman: 
> ./integrationTest/postman/Wenance Challenge.postman_collection.json


## API DOC con Swagger
> http://localhost:8080/swagger-ui.html