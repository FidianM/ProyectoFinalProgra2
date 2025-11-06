# ProyectoFinalProgra2
El Comité Olímpico Guatemalteco (COG) busca modernizar y fortalecer su sistema actual de control del rendimiento deportivo mediante el desarrollo de una plataforma integral de gestión de atletas de alto nivel.
Esta nueva versión deberá permitir la administración centralizada de la información deportiva, financiera y logística de cada atleta, facilitando tanto el seguimiento del desempeño como la gestión administrativa y los procesos de análisis de datos.

El sistema combinará una API REST desarrollada en Java (Spring Boot) con una interfaz de usuario (opcional: Web o Android), garantizando acceso rápido, almacenamiento seguro y respaldo de la información en diferentes formatos.

Objetivo general
Desarrollar un sistema integral para el monitoreo, control y gestión de atletas de alto rendimiento, que permita registrar entrenamientos, calcular estadísticas de desempeño, procesar planillas de pago y generar respaldos de información en diferentes medios, con persistencia en una base de datos relacional.

Objetivos específicos
Permitir el registro y administración de atletas, incluyendo información personal, disciplina y nacionalidad.

Gestionar el registro de sesiones de entrenamiento, con datos de rendimiento y localización (nacional o internacional).

Implementar un módulo de estadísticas para analizar la evolución de los atletas, calcular promedios, mejores marcas y comparaciones entre entrenamientos nacionales e internacionales.

Desarrollar un módulo de planilla financiera, que calcule los pagos mensuales de los atletas según su rendimiento y bonificaciones establecidas.

Implementar mecanismos de persistencia y respaldo de datos en archivos JSON y CSV, además de una base de datos relacional (MySQL o PostgreSQL) como almacenamiento principal.

Desarrollar una API REST que exponga todas las funcionalidades principales del sistema.

Implementar una interfaz gráfica (Web o Android) que consuma la API, permitiendo la interacción de los usuarios de manera intuitiva.

Aplicar principios de Programación Orientada a Objetos y patrones de diseño (Repository, Service, Strategy, Factory, DTO, Adapter) para asegurar mantenibilidad y escalabilidad del sistema.

Requerimientos funcionales
1. Gestión de Atletas
Registrar un nuevo atleta con:

Nombre completo

Edad

Disciplina (atletismo, natación, pesas, etc.)

Departamento de Guatemala

Nacionalidad

Fecha de ingreso al comité

Consultar, editar o eliminar atletas.

Importar y exportar información de atletas en formato JSON.

2. Entrenamientos
Registrar entrenamientos con:

Fecha

Tipo de entrenamiento (resistencia, técnica, fuerza, etc.)

Valor de rendimiento (tiempo, peso, distancia, etc.)

Ubicación (nacional o internacional, con país si aplica)

Listar, filtrar o eliminar entrenamientos por atleta.

Exportar reportes de entrenamientos en formato CSV.

3. Estadísticas deportivas
Mostrar historial completo de entrenamientos.

Calcular y visualizar:

Promedio de rendimiento.

Mejor marca.

Evolución de rendimiento a lo largo del tiempo.

Comparación de rendimiento nacional vs. internacional.

4. Gestión financiera (planilla)
Calcular el pago mensual de un atleta según:

Número total de entrenamientos.

Bonificación por entrenamientos en el extranjero.

Bonificación por mejora de marca personal.

Registrar y consultar pagos procesados.

Generar reportes de planilla por mes y atleta.

5. Persistencia de datos
Guardar y cargar información desde:

Archivos JSON (respaldo completo).

Archivos CSV (reportes y análisis externo).

Base de datos relacional (MySQL o PostgreSQL) como fuente principal.

6. API REST
Debe proveer endpoints para:

CRUD de atletas.

CRUD de entrenamientos.

Consulta de estadísticas.

Cálculo y registro de planilla.

Importación/exportación de datos JSON y CSV.

Implementar validaciones, manejo de errores y respuestas estandarizadas (HTTP status codes, JSON response).

7. Interfaz de usuario
Opción A: Web (React o similar)
o

Opción B: Android (Kotlin/Java con Retrofit)

Funciones principales:

Menú general con acceso a módulos.

Formularios de registro y edición.

Visualización de estadísticas (tablas y gráficos).

Generación y descarga de reportes.

Interacción con la API REST.

Requerimientos no funcionales
Desarrollado en Java, aplicando Programación Orientada a Objetos (POO).

Arquitectura multicapa (Controlador, Servicio, Repositorio, DTOs).

Uso de patrones de diseño para modularidad y extensibilidad.

Persistencia con JPA/Hibernate.

Documentación técnica y manual de usuario.

Exportes con bibliotecas estándar (Jackson para JSON, Apache Commons CSV para CSV).

Pruebas unitarias y de integración (JUnit).

Seguridad básica (autenticación JWT o control de roles).
