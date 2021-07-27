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

### Iniciar el servidor de mongo
Exponemos el puerto (solo con fines practicos) para que podamos acceder desde afuera, más que nada para cuando levantemos
el servicio de manera local se pueda conectar a la base de datos.
> docker run  -p 27017:27017 --name some-mongo -d mongo:latest

### Iniciar la consola de mongo
> docker exec -it some-mongo mongo

### Ahora estamos en la consola de MONGODB
#### Para Mostrar las base de datos
> show dbs

#### Si queremos crear una base de datos podemos hacer lo siguiete:
> use {databaseName} (Ejemplo)

> use test

#### La base de datos no se crea por completo hasta que ingrese algo.
Esto es solo a modo de ejemplo, si queremos probar desde la consola
> db.test.insert({name:"Hello World"})

y luego para visualizar el dato insertado de ejemplo, ejecutamos:

> db.test.find()

#### Una vez que realizamos el primer insert vamos a ver la base de datos creada

### Para saber en que base de datos estamos parados
> db



## Antes de iniciar/levantar el microservicio tanto local como desde docker debemos ejecutar:
> mvn clean install

## Si queremos iniciar el microservicio de forma local (local machine)
#### Desde la linea de consola ejecutar:
> mvn spring-boot:run

## Si queremos iniciar el microservicio en DOCKER
### Generar imagen e instalar en docker
Primero debemos ejecutar el siguiente comando desde root path de nuestro proyecto
> docker build --tag=wenancech:latest .

### Iniciar Micoservicio + MongoBD con docker-compose
Desde root path  del proyecto ejecutar:
> docker-compose up

### Para detener el Micoservicio + MongoBD con docker-compose.
Desde root path del proyecto ejecutar:
> docker-compose down

## Test de Integración (Postman)
####Una vez levantado el microservicio, se puede probar importando el archivo en Postman: 
> ./integrationTest/postman/Wenance Challenge.postman_collection.json
