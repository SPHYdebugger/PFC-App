<p align="center">
<img width="200px" src="https://github.com/SPHYdebugger/PFC-App/blob/master/app/src/main/res/drawable/logoapp.jpg">
<h1 align="center"> Personal Fuel Controller APP</h1>
</p>

# App de Gestión de Vehículos y Repostajes

### Descripción

Esta es una aplicación Android desarrollada para la gestión de vehículos y repostajes, diseñada para ayudar a los usuarios a llevar un seguimiento detallado de sus vehículos, el consumo de combustible, los kilómetros recorridos, y las estaciones de servicio utilizadas.

### Características Principales

- **Gestión de vehículos**: Añade, edita y elimina información de tus vehículos, como matrícula, marca, modelo, tipo de combustible, kilometraje, y consumo medio.
- **Registro de repostajes**: Añade repostajes, incluyendo detalles como la cantidad de combustible, el precio, la estación de servicio y los kilómetros actuales del vehículo.
- **Estaciones de servicio**: Gestiona tus estaciones favoritas, encuentra repostajes asociados a cada estación, y registra el uso de estaciones con distintos tipos de combustible.
- **Cálculo de consumo medio**: La app calcula automáticamente el consumo medio de combustible por cada repostaje.
- **Historial de repostajes**: Accede al historial completo de repostajes para cada vehículo, filtrado por fechas o estaciones de servicio.

### Tecnologías Utilizadas

- **Lenguaje**: Java para el desarrollo de la aplicación Android.
- **Bases de datos**: Utilización de PostgreSQL o Room para almacenamiento local de datos.
- **Arquitectura**: Modelo MVVM (Model-View-ViewModel) para una mejor organización y separación de responsabilidades.
- **Frameworks y Librerías**:
    - Retrofit para consumo de APIs.

### Endpoints de API

La app se conecta a una API externa para la gestión de vehículos y repostajes. A continuación algunos endpoints importantes:

- **POST** `/vehicles`: Crear un nuevo vehículo.
- **GET** `/vehicles`: Obtener una lista de todos los vehículos.
- **PUT** `/refuels/{id}`: Actualizar un repostaje existente.
- **DELETE** `/stations/{stationIdentifier}`: Eliminar una estación de servicio por ID o nombre.

### Resumen pantallas APP
﻿<p align="center">
<img width="100%" src="https://github.com/SPHYdebugger/PFC-App/blob/master/app/src/main/pantallas.jpg">
<h1 align="center"> Personal Fuel Controller APP</h1>
</p>

