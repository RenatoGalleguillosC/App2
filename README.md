# App2

## Autores
- Vicente Zapata (Vizapata@alumnos.uai.cl)
- Renato Galleguillos (Regalleguillos@alumnos.uai.cl)
- José Pablo Bernal (Josbernal@alumnos.uai.cl)

## Tarea 2: Gestión Agrícola en Java

### Objetivo
Desarrollar un programa en Java que lea un archivo CSV con datos sobre cultivos junto con características y, a partir de un menú, se puedan observar, agregar, eliminar o editar. Las características incluyen tipo de registro (siempre "Cultivo"), nombre del cultivo, variedad, superficie (en hectáreas), código de parcela, fecha de siembra, estado (activo, en riesgo o cosechado) y lista de actividades con sus respectivas fechas.

### Requerimientos
El programa debe:

- Recibir un archivo CSV como entrada.
- Permitir al usuario acceder al menú y utilizar sus funciones.
- Estar dividido en varios archivos fuente para modularidad.

### Instrucciones de Compilación
1. Descargar la carpeta "App2".
2. Añadir la carpeta "App2" al área de trabajo.
3. Abrir el área de trabajo, para este caso, se explicará con Visual Studio Code.
4. Abrir la terminal para compilar el programa:
5. Crear las clases para los archivos .java
```bash
javac *.java
```

## Instrucciones de Uso
Para ejecutar el programa, use el siguiente formato:
```bash
java App2 cultivos.csv
```
Una vez ejecutado aparecerá en la terminal el siguiente menú:

==== MENÚ PRINCIPAL =====
1. Lista de cultivos
2. Buscar cultivo por nombre
3. Crear nuevo cultivo
4. Registrar actividad a cultivo
5. Ver actividades de un cultivo
6. Eliminar cultivo (solo si no tiene actividades)
7. Editar cultivo (nombre, variedad, superficie)
8. Buscar cultivos por variedad
9. Ver cultivos por estado
10. Marcar actividad como completada
11. Eliminar actividad de un cultivo
12. Listar parcelas con sus cultivos
13. Crear nueva parcela
14. Editar una parcela
15. Eliminar parcela (solo si no tiene cultivos)
16. Reasignar cultivo a otra parcela
17. Salir
Selecciona una opción: 

## Ejemplo de uso
*Tras ejecutar*
```bash
Seleccione una opción:  5
Nombre exacto del cultivo: Trigo
```
```bash
Actividades para Trigo:
- RIEGO:2023-02-25:No completada
```

## Formato del Archivo CSV
El archivo CSV debe tener el siguiente formato:
```csv
Cultivo,"Maíz","Variedad Dulce",32.5,"PARCELA-A01","2023-03-01","ACTIVO",["RIEGO:2023-03-10","FERTILIZACION:2023-03-20"]
Cultivo,"Trigo","Variedad Premium",45.2,"PARCELA-B03","2023-02-15","ACTIVO",["RIEGO:2023-02-25","COSECHA:2023-06-15"]
Cultivo,"Tomate","Cherry",10.0,"PARCELA-C02","2023-04-05","EN_RIESGO",["FUMIGACION:2023-04-20"]
Cultivo,"Lechuga","Romana",8.5,"PARCELA-A02","2023-03-10","COSECHADO",["RIEGO:2023-03-20","COSECHA:2023-05-01"]
```
## Opciones Disponibles
*(Repetimos aquí las opciones del menú para mayor claridad)*

1. Lista de cultivos
2. Buscar cultivo por nombre
3. Crear nuevo cultivo
4. Registrar actividad a cultivo
5. Ver actividades de un cultivo
6. Eliminar cultivo (solo si no tiene actividades)
7. Editar cultivo (nombre, variedad, superficie)
8. Buscar cultivos por variedad
9. Ver cultivos por estado
10. Marcar actividad como completada
11. Eliminar actividad de un cultivo
12. Listar parcelas con sus cultivos
13. Crear nueva parcela
14. Editar una parcela
15. Eliminar parcela (solo si no tiene cultivos)
16. Reasignar cultivo a otra parcela
17. Salir

## Estructura del Proyecto
- **`App2/`**: Contiene el archivos principal **`App2.java`**, y las carpetas, **`utils`** (donde está el archivo .csv), **`models`** y **`services`**
