# CC2008 - Ejercicio 3: Editor de filtros de imágenes

Aplicación de escritorio en Java Swing organizada con el patrón MVC. Permite abrir una imagen, aplicar filtros de forma consecutiva, consultar el historial, deshacer, reiniciar y guardar el resultado.

## Requisitos

- JDK 11 o superior.
- La librería FlatLaf incluida en `lib/`.

## Compilar

```bash
javac -cp "lib/*" -d bin src/*.java
```

## Ejecutar

Windows:

```bash
java -cp "bin;lib/*" Main
```

Linux o macOS:

```bash
java -cp "bin:lib/*" Main
```
