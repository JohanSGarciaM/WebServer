# APLICACIONES DISTRIBUIDAS

    Las aplicaciones distribuidas son sistemas de software compuestos por múltiples componentes 
    que se ejecutan en diferentes máquinas y se comunican entre sí a través de una red. Estas 
    pueden variar en complejidad y se pueden comunicar teniendo en cuenta diferentes patrones 
    de conexión entre un cliente y un servidor. En el siguiente proyecto vamos a poder evidenciar 
    el desarrollo de un servidor web y la respuesta a múltiples solicitudes por medio del 
    protocolo de aplicación HTTP para su respectivo funcionamiento.

## Protocolo HTTP 

    Es el protocolo fundamental que permite la comunicación y el intercambio de información en 
    la web. Define cómo los clientes y los servidores se comunican, solicitando y entregando 
    recursos como páginas web, imagenes, archivos, entre otros contenidos.
    Sigue un modelo de cliente-servidor, donde el cliente (navegador web) realiza **solicitudes**
    al servidor web, quien da **respuesta** con los datos solicitados los cuales estan 
    determinados por **(método, URL, version)** ademas de opcionalmente encabezados y cuerpo,
    estos **métodos** (get, post, put y delete) indican la acción que el cliente desea 
    realizar con el recurso solicitado.

## Introducción

    En el siguiente proyecto podemos encontrar un servidor web que tendrá la capacidad de esperar y 
    recibir solicitudes del navegador o cliente y nos mostratá como respuesta los archivos solicitados 
    y ubicados en el directorio local que en este caso es una aplicación sencilla realizada con Html,Css 
    y javascript que nos muestra una página con imágen y texto

## Disponibilidad de la aplicación

Para hacer disposición de la aplicación primero es necesario descargar el contenido de este repositorio en la ubicación del directorio donde desee con el siguiente comando:

```
git clone https://github.com/JohanSGarciaM/WebServer.git
```

Ahora es necesario dirigirnos a la carpeta del proyecto que se acabó de clonar:

```
cd WebServer
```

Debemos compilar el código fuente con el siguiente comando

```
mvn clean install
```
Para hacer uso de la aplicación utilizamos el comando (el cuál va a ejecutar la clase Main que se defina en el build del Pom):

```
mvn exec:java
```

A partir de este momento el socket esta activo para recibir la información al puerto 35000 por lo que nos dirijimos desde nuestro browser al puerto, seguido del nombre del archivo que deseamos obtener junto con su extensión.

```
localhost:35000
```

Para poder revisar la documentación del proyecto es necesario ejecutar el siguiente comando en la terminar para que se ejecute en el navegador.

```
mvn javadoc:javadoc
```

Una vez solicitemos por medio del puerto 35000 el archivo que deseamos obtener, la página busca dentro del directorio indicado y hace la solicitud del contenido de dicho archivo. Para el caso de documentos de texto con extención .hmtl:

![](/ReadmeImages/html.png)

## Archivos de imagen .jpg

![](/ReadmeImages/jpg.png)


## Archivo no existente:

![](/ReadmeImages/error.png)


## Estratégia utilizada.

Al momento de realizar la conexión por medio de sockets entre servidor y cliente, lo que se hace es utilizar un buffered Reader para el ingreso de los datos que se estan solicitando por medio de un getImputStream al Socket del cliente:

```
BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
```

Esto hará que se las lineas de entrada sean la respuesta del protocolo Http por donde obtenemos el path de destino que estamos solicitando desde el browser por ejemplo "Cliente.html"

En este punto, se debe validar la extención del path que se esta recibiendo y a cada uno se le va a destinar un "Content-Type" diferente dependiendo del tipo de archivo que se desea obtener ya que no es el mismo para un archivo de Texto a uno de Imagen.

![](/ReadmeImages/ContentTypes.png)

Ahora lo que hace falta es armar el String de respuesta con el contenido del archivo que se desea solicitar y con el OutputStream se utiliza el método __write__ donde como parámetro se le envían los bytes de dicha respuesta como por ejemplo __Response.getBytes()__

## Conclusiones

La comunicación REST para la transferencia de archivos en aplicaciones distribuidas ofrece una solución eficiente y escalable, aprovechando métodos HTTP estándar para una transferencia eficiente y compatible con múltiples plataformas. Su simplicidad de implementación y la capacidad de manejar un gran volumen de solicitudes simultáneas hacen que sea una opción atractiva. Sin embargo, es crucial implementar medidas de seguridad sólidas, junto con mecanismos de gestión de errores y documentación detallada, para garantizar la confidencialidad de los datos, la fiabilidad del sistema y la facilidad de uso para los desarrolladores.


## Realizado Por

Johan Sebastian Garcia Martinez

