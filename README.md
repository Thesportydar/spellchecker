# Spellchecker
Este proyecto se creo en el marco del trabajo final del taller de programacion en Java
Consiste en la implementación de un corrector ortográfico en Java, que abarca diversas funcionalidades para el procesamiento de texto, la detección de errores y la sugerencia de correcciones.

Se implementaron 3 tipos de correctores ortográficos:
- **FileCorrector**: Utiliza un HashMap para almacenar un diccionario de palabras mal escritas y sus posibles correcciones. Este diccionario se carga desde un archivo de errores, donde cada línea contiene una palabra incorrecta y sus correcciones separadas por comas. Las palabras se almacenan y buscan en minúsculas.
- **SwapCorrector**: Este corrector se basa en un diccionario de palabras correctas. Para una palabra dada, se prueban todos los posibles intercambios de letras adyacentes y se sugieren aquellas que se encuentran en el diccionario.
- **Levenshtein basado en HashMap**: Este corrector sugiere palabras que están a una distancia de Levenshtein de uno de la palabra incorrecta. Implementa tres tipos de correcciones: eliminaciones, sustituciones e inserciones, donde para cada una se itera sobre cada letra de la palabra, agregando las letras del alfabeto en las diferentes posiciones y se verifica si la nueva palabra existe en el diccionario.
- **Levenshtein basado en Trie**: Es una implementación del corrector Levenshtein optimizada para trabajar con la estructura Trie. Utiliza el método para obtener subarboles, lo que permite hacer podas y reducir el espacio de búsqueda.

## Tests
El proyecto incluye una serie de tests para verificar el correcto funcionamiento de cada clase. Estos tests cubren diferentes escenarios, como la lectura de archivos vacíos o con un solo token, la búsqueda de palabras en el diccionario y el funcionamiento de los correctores.

## Benchmark
El proyecto incluye benchmarks para comparar la eficiencia de las diferentes implementaciones de diccionarios y correctores. Se mide el tiempo de creación, búsqueda (hit y miss) de palabras y el tiempo de ejecución de cada tipo de corrector para diferentes tamaños de diccionarios y palabras. Los benchmarks permitieron analizar el rendimiento de cada estructura y algoritmo en diferentes contextos. Por ejemplo, se compara la búsqueda en HashSet contra Trie, y se analizan las implementaciones del corrector Levenshtein, aprovechando o no la estructura del trie.

## Uso
El programa principal se encuentra en la clase `SpellCheckerRunner`. Se puede ejecutar desde la línea de comandos con el siguiente comando:
```bash
java SpellCheckerRunner <in> <out> <dictionary> <corrector>
```

- **in**: archivo de entrada
- **out**: archivo de salida
- **dictionary**: diccionario
- **corrector**: SWAP (para SwapCorrector), LEV (para Levenshtein), o nombre de archivo (para FileCorrector)
