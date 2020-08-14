# Tiempo-Real Proyecto de Curso

## Descripcion
Proyecto de programacion en Java y Bluemix con Node Red. Se simulará el proceso de control de una planta y los datos seran visualizados en un dashboard almacenado en Bluemix con una cuenta de IBM Cloud. 

![](https://github.com/omarftt/Tiempo-Real/blob/master/Figura/Imagen2.PNG?raw=true)

## Funcionamiento
El proyecto utiliza el protocolo abierto MQTT para comunicar un Node Red Local instalado en un computador, con un Node Red vinculado a IBM Cloud. Los datos del Node Red Local seran obtenidos de un archivo .txt. que contiene los valores de los parametros de la simulacion del proceso de control de tanque de agua programado en Java. El Node Red Local publicara estos valores leidos como un mensaje con un topico que sera enviado a un broker MQTT,para posteriormente ser recibido por cualquier dispositivo suscrito su topico, que en este caso sera el Node Red vinculado a IBM Cloud. Del mismo modo, se enviaran mensajes desde el Node Red de IBM hacia el Node Red Local, pasando por el broker, los cuales contendran datos que seran guardados en otro .txt que será leido continuamente por el programa de Java, para modificar los estados de la planta.

![](https://github.com/omarftt/Tiempo-Real/blob/master/Figura/Imagen1.PNG?raw=true)

## Quick Start
1. Descargar el codigo Java incluido en el repositorio de Github. Inspeccionar el codigo, cambiar los valores y parametros a conveniencia, cambiar el PATH de la ubicacion del archivo .txt del cual se leeran los datos.

1. Seguir los pasos de instalacion de [Node Red Local](https://github.com/omarftt/Tiempo-Real/tree/master/Instalacion%20Node%20Red%20Local) y [Node Red vinculado a IBM Cloud](https://github.com/omarftt/Tiempo-Real/tree/master/Instalacion%20Node%20Red%20-%20Bluemix)

2. Programar los editores de los Node Red para el envio y recepcion de datos por protocolo MQTT . Programar el editor de Node Red local para la lectura de archivos .txt con la ubicacion del archivo programada en el codigo Java.

3. Adquirir un broker MQTT e ingresar el servidor en la configuracion de los nodos MQTT de ambos Node Red

4. Comprobar el envio y recepcion de datos entre el entorno local y el virtual.

5. Visualizar los valores de la planta en el siguiente [Dashboard](http://fideltitonode-red.mybluemix.net/ui/) creado.

![](https://github.com/omarftt/Tiempo-Real/blob/master/Figura/Imagen3.PNG?raw=true)

