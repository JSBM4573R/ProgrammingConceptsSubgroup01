 # Proyecto Subgrupo 01 - Evolutivo Entregable Final - Fundamentos de Programación en Java Politecnico Grancolombiano

##  Integrantes
- Juan Sebastián Bustos Marroquín 
- Didier Rafael Tabares Giraldo 
- Claudia Jineth Aguirre Botero 
- Miche Sánchez Daza 

##  Descripción General

Este proyecto tiene como objetivo aplicar los conceptos fundamentales de programación en Java, incluyendo estructuras de control, clases, métodos, lectura y escritura de archivos, uso de colecciones, generación de datos pseudoaleatorios, buenas prácticas de codificación y uso de Git.

Se desarrollaron dos clases principales con métodos `main`:

- `GenerateInfoFiles`: genera archivos de prueba con datos pseudoaleatorios (vendedores, productos y ventas) en la ruta de la raiz del proyecto: files/input/**
- `Main`: procesa estos archivos para generar reportes en formato `.csv` ordenados según criterios específicos y con los test correspondientes en la ruta de la raiz del proyecto: files/output/**

Como punto extra para la entrega final se implementó en el código la posibilidad de procesar más de un archivo por vendedor.
La dinámica es la siguiente:
Si un vendedor tiene otros archivos de ventas suponiendo que esta dividido en ventas por mes del año, estos archivos se almacenan con la particularidad principal de identificación la cual es el ID, con ese dato el programa main suma las ventas totales del vendedor unificando todas las carpetas para poder obtener un resultado total de ventas por cada vendedor.
