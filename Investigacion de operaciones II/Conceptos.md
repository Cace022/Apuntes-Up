# Conceptos teóricos en la teoría de grafos 

> Consideremos un conjunto $X = \{x_1, x_2, \ldots, x_n\}$ cuyos elementos llamaremos vértices y una aplicación multívoca $\Gamma$ de $X$ en $X$, al par $G = (X, \Gamma)$ le llamaremos grafo.

#### Definición adaptada a informática

- Un grafo es una estructura de datos que consiste en dos elementos principales: un conjunto de vértices (también llamados nodos) y un conjunto de conexiones entre estos vértices (llamadas aristas o edges).

### Ejemplo 1:

Dado el grafo $G = (X, \Gamma)$, donde $X = \{x_1, x_2, x_3, x_4\}$ y 2la aplicación $\Gamma: X \to X$ se define como sigue:

$$
\begin{align*}
\Gamma(x_1) &= \{x_2, x_3, x_4\} \\
\Gamma(x_2) &= \{x_4\} \\
\Gamma(x_3) &= \{x_2, x_3, x_4\} \\
\Gamma(x_4) &= \{x_1\}
\end{align*}
$$

También es usual la notación $G = (U, \Gamma)$, donde $U$ es el conjunto de pares ordenados determinados por la relación $\Gamma$. Es claro que $U \subseteq X \times X$.

> En el contexto de la teoría de grafos, un grafo $G = (X, \Gamma)$ se define como un conjunto de vértices $X = \{x_1, x_2, x_3, x_4\}$ y una aplicación $\Gamma$ que especifica las conexiones entre estos vértices. Por ejemplo, $\Gamma(x_1) = \{x_2, x_3, x_4\}$ indica que el vértice $x_1$ está conectado a $x_2$, $x_3$ y $x_4$. De manera similar, $\Gamma(x_2) = \{x_4\}$, $\Gamma(x_3) = \{x_2, x_3, x_4\}$ y $\Gamma(x_4) = \{x_1\}$. Alternativamente, el grafo puede representarse como $G = (U, \Gamma)$, donde $U$ es el conjunto de pares ordenados que representan las conexiones entre los vértices, y es un subconjunto del producto cartesiano $X \times X$.

### Definición 2:

Sea $x_j \in \Gamma(x_i)$, al par ordenado $(x_i, x_j)$ le llamaremos arco y lo representaremos gráficamente mediante un segmento dirigido de $x_i$ a $x_j$.

Al vértice $x_i$ le llamaremos extremo inicial y a $x_j$ extremo terminal.

#### Definición adaptada a informática:

En el contexto de estructuras de datos y algoritmos, un arco en un grafo dirigido se representa como un par ordenado $(x_i, x_j)$, donde $x_i$ es el vértice de origen (extremo inicial) y $x_j$ es el vértice de destino (extremo terminal). Este arco se visualiza como una flecha que apunta desde $x_i$ hacia $x_j$. En términos de implementación, esto puede representarse mediante una lista de adyacencia o una matriz de adyacencia, donde cada entrada indica la existencia de una conexión dirigida entre dos nodos.

### Tomar en cuenta 

- Si no se considera el orden de los vértices en cada pareja, dichos pares se denominan **aristas**, y se dice que el grafo es **no orientado**.
- Si se consideran las relaciones, el par de aristas se llama **arco** y el grafo es **orientado**. Un grafo no orientado puede siempre convertirse en orientado, expresando la doble relación entre los vértices.
- La representación gráfica es adecuada para la interpretación y resolución de problemas en grafos pequeños o medianos.
- La representación mediante **matriz asociada** o **matriz de adyacencia** es adecuada para el tratamiento de problemas de grafos con programas informáticos.

### Definición 2.1:

En un grafo $G = (X, \Gamma)$, el número de vértices de $G$, denotado como $n = |V|$, se denomina **orden del grafo**. El número de lados de $G$, denotado como $m = |E|$, se conoce como **tamaño del grafo**.

#### Adaptación a informática:

- **Orden del grafo**: En términos de estructuras de datos, el orden del grafo ($n$) es el número de nodos o vértices en el grafo.
- **Tamaño del grafo**: El tamaño del grafo ($m$) es el número de conexiones o aristas entre los nodos.

### Ejemplo 2.1:

El grafo de la figura 1 es de grado $|V| = 4$, mientras que su tamaño es $|V| = 7$.

#### Definición adaptada a informática:

- **Grado del grafo**: En un grafo con 4 nodos, el grado se refiere al número total de nodos.
- **Tamaño del grafo**: Con 7 aristas, el tamaño se refiere al número total de conexiones entre los nodos.

### Definición 2.2:

Un grafo es **finito** si $|V|$ y $|E|$ son finitos. El grafo de la figura 1 es finito.

#### Definición adaptada a informática:

- **Grafo finito**: En informática, un grafo finito tiene un número limitado de nodos y aristas, lo que lo hace manejable para algoritmos y estructuras de datos.

### Definición 2.3:

En un grafo dirigido $G = (X, \Gamma)$, para cualquier lado $e = (i, j)$ se dice que es **incidente** en los vértices $i$ y $j$, los cuales son sus **vértices extremos**. $i$ es **adyacente hacia** $j$, mientras que $j$ es **adyacente desde** $i$. Además, el vértice $i$ es el **origen** o **fuente** del lado $(i, j)$ y el vértice $j$ es el **término** o **vértice terminal** de dicho lado.

#### Definición adaptada a informática:

- **Grafo dirigido**: En estructuras de datos, un grafo dirigido tiene aristas con una dirección específica, representando relaciones unidireccionales.
- **Incidencia y adyacencia**: Un nodo $i$ es adyacente hacia $j$ si hay una arista dirigida de $i$ a $j$. $i$ es el nodo de origen y $j$ es el nodo de destino.

### Observación:

En un grafo no dirigido $G = (X, \Gamma)$, para todo lado $e = (i, j)$ se dice que $e$ es **incidente** en los vértices $i$ y $j$, los cuales son sus **vértices extremos**. Además, se dice que los vértices $i$ y $j$ son **vértices adyacentes**. Se puede decir que dos vértices son adyacentes si están unidos por un mismo lado.

#### Observacion adaptada a informática:

- **Grafo no dirigido**: En informática, un grafo no dirigido tiene aristas sin dirección, representando relaciones bidireccionales.
- **Incidencia y adyacencia**: Dos nodos $i$ y $j$ son adyacentes si están conectados por una arista, sin importar la dirección.

### Definición 2.4:

Se dice que un grafo $G = (X, \Gamma)$ es **nulo** si tiene todos sus vértices aislados. Por **vértice aislado** se entiende aquel que no es extremo de ningún lado o que no tiene ningún lado incidente sobre sí. Es decir, $|E| = 0$.

#### Adaptación a informática:

- **Grafo nulo**: En términos de estructuras de datos, un grafo nulo no tiene aristas, lo que significa que todos los nodos están aislados y no hay conexiones entre ellos.
  
### Definicion 3:

Diremos que un grafo $G = (X. Γ)$ es simétrico si:
$(a_1, a_2) \in Γ \leftrightarrow (a_2, a_1) \in Γ$ para todo $(a_1, a_2) \in X \times X$

#### Adaptacion a la informatica:

En el contexto de redes de computadoras o bases de datos relacionales, un grafo simétrico representa conexiones bidireccionales. Si el nodo A tiene una conexión al nodo B, entonces B también tiene una conexión a A. Esto es común en redes peer-to-peer o en relaciones de amistad en redes sociales.

### Defincion 4:

Diremos que un grafo $G = (X. Γ)$ es antisimétrico si para todo $(a_1, a_2) \in X \times X$ se verifica que:
$(a_1, a_2) \in Γ \rightarrow (a_2, a_1) \notin Γ$

#### Adaptacion a la informatica:

En sistemas informáticos, un grafo antisimétrico puede representar relaciones unidireccionales estrictas. Por ejemplo, en una jerarquía de clases en programación orientada a objetos, si la clase A hereda de la clase B, B no puede heredar de A. También se aplica en flujos de trabajo donde las tareas deben seguir un orden estricto sin ciclos.

### Defincion 5:

Sea $x_i \in X$, para todo arco que se inicia en $x_i$, llamaremos incidente en $x_i$ hacia el exterior en $x_i$, lo denominaremos 
incidente en $x_i$ hacia el interior.

#### Adaptacion a la informatica:

En el diseño de redes o arquitecturas de software, los arcos incidentes representan flujos de datos o llamadas a funciones. Un arco incidente hacia el exterior de un nodo puede representar una llamada a una API externa o el envío de datos, mientras que un arco incidente hacia el interior puede representar la recepción de datos o 
una callback.

### Defincion 6:

Llamaremos camino a una secuencia $(u_1, u_2, \ldots)$ de arcos, tal que el extremo terminal de cada uno coincida con el extremo inicial 
del siguiente.

#### Adaptacion a la informatica:
En algoritmos de búsqueda o navegación, un camino representa una secuencia de operaciones o transiciones de estado. Por ejemplo, en un sistema de navegación GPS, un camino sería la secuencia de calles o intersecciones que se deben seguir para llegar de un punto A a un punto B.

### Definición 7:
Un camino se denominará simple cuando no se repiten arcos en su constitución; en caso contrario se llamará camino compuesto.

#### Adaptación a la informática:
En algoritmos de recorrido de grafos, un camino simple representa una secuencia de operaciones donde no se repite ninguna acción. Esto es crucial en optimización de rutas o en la detección de ciclos en estructuras de datos.

### Definición 8:
Un camino se llamará elemental si no se repiten vértices en su constitución y no elemental en el caso contrario.

#### Adaptación a la informática:
En el diseño de algoritmos, un camino elemental es una secuencia de operaciones donde no se visita dos veces el mismo estado o nodo. Esto es fundamental en problemas como el del viajante de comercio o en la búsqueda de rutas sin bucles en redes.

### Definición 9:
Llamaremos longitud del camino $(μ_1, μ_2, …, μ_n)$ al número de arcos que este contiene, y se denotará $l(μ)$.

#### Adaptación a la informática:
En análisis de algoritmos, la longitud del camino puede representar la complejidad de una secuencia de operaciones. Por ejemplo, en estructuras de datos como árboles o grafos, la longitud del camino puede indicar el número de pasos necesarios para alcanzar un nodo específico.

### Definición 10:
Llamaremos circuito a un camino $(μ_1, μ_2, …, μ_n)$ tal que el vértice inicial del arco $μ_1$ coincida con el vértice terminal del arco $μ_m$.

#### Adaptación a la informática:
En programación, un circuito puede representar un bucle en la ejecución de un programa o un ciclo en una estructura de datos. Es crucial en la detección de deadlocks en sistemas operativos o en la optimización de algoritmos recursivos.

### Definición 11:
Diremos que un circuito es elemental si todos los vértices que recorre son distintos en caso contrario se le dirá no elemental.

#### Adaptación a la informática:
En el diseño de algoritmos de routing, un circuito elemental representa una ruta cíclica que pasa por cada nodo una sola vez. Esto es útil en problemas de optimización como el del ciclo hamiltoniano o en la planificación de rutas eficientes en redes de distribución.

### Definición 12:
Un circuito constituido por un sólo arco y un sólo vértice se llamará bucle, lazo o anillo.

#### Adaptación a la informática:
En programación, un bucle representa una operación que se repite sobre sí misma. Puede ser una estructura de control como un loop 'while' que se ejecuta continuamente, o una referencia circular en una estructura de datos que puede llevar a problemas de memoria o stack overflow.

### Definición 13:
Un camino se le denominará Hamiltoniano si pasa por cada vértice del grafo.

#### Adaptación a la informática:
En algoritmia, un camino hamiltoniano representa una secuencia de operaciones que visita cada estado o nodo del sistema exactamente una vez. Es fundamental en problemas de optimización como la planificación de rutas eficientes en logística o en el diseño de circuitos integrados.

### Definición 14:
Un grafo tal que para cualquier par de vértices $(x_i, x_j)$ existe un camino que contiene estos vértices, se llamará fuertemente conexo.

#### Adaptación a la informática:
En redes de computadoras, un grafo fuertemente conexo representa una red donde existe una ruta de comunicación entre cualquier par de nodos. Esto es crucial para garantizar la robustez y la resistencia a fallos en sistemas distribuidos o en el diseño de topologías de red resilientes.

### Definición 15:
Un vértice $x_i$ unido a los demás vértices determinados con la relación Γ se llamará componente conexa con respecto a $x_i$ del grafo G, y se simbolizará $Cx_i$.

#### Adaptación a la informática:
En análisis de redes sociales o sistemas distribuidos, una componente conexa representa un subconjunto de nodos que pueden comunicarse entre sí. Identificar estas componentes es crucial para entender la estructura de grandes redes, detectar comunidades en redes sociales o analizar la propagación de información en sistemas complejos.

### Definición 16:
Una cadena es un camino no orientado. Es una sucesión de aristas tal que el nodo extremo de cada una (exceptuando la última) coincide con el nodo extremo de la siguiente en la sucesión. Dos nodos que no están conectados directamente pueden estarlo indirectamente una cadena.

#### Adaptación a la informática:
En el diseño de protocolos de red, una cadena puede representar una ruta de comunicación entre dos dispositivos que no están directamente conectados. Esto es fundamental en el enrutamiento de paquetes en Internet, donde los datos pueden pasar por múltiples dispositivos intermedios antes de llegar a su destino.

### Definición 17:
Un ciclo es una cadena que se inicia y termina en el mismo vértice.

#### Adaptación a la informática:
En programación, un ciclo puede representar una secuencia de operaciones que retorna al estado inicial. Esto es común en la implementación de algoritmos iterativos, en la gestión de recursos en sistemas operativos, o en la detección de deadlocks en sistemas concurrentes.

### Definición 18:
El grado de un nodo se define como el número total de arcos o aristas que inciden en dicho nodo y evalúa la intensidad de las conexiones directas con el resto de los nodos del grafo.

#### Adaptación a la informática:
En el análisis de redes de computadoras o redes sociales, el grado de un nodo puede representar el número de conexiones directas que tiene un dispositivo o usuario. Esto es crucial para identificar nodos centrales o influyentes en la red, lo que puede ser útil para optimizar el flujo de información o para estrategias de seguridad.

### Definición 19:
El semigrado interior de un nodo representa el número de arcos con destino en el nodo.

#### Adaptación a la informática:
En sistemas de bases de datos o arquitecturas de microservicios, el semigrado interior puede representar el número de servicios o componentes que dependen de un nodo específico. Esto es útil para analizar la criticidad de ciertos componentes y planificar estrategias de escalabilidad o tolerancia a fallos.

### Definición 20:
Llamaremos semigrado exterior de un nodo al número de arcos con origen en el nodo.

#### Adaptación a la informática:
En el diseño de APIs o interfaces de programación, el semigrado exterior puede representar el número de métodos o funciones que un módulo expone a otros componentes del sistema. Esto es importante para evaluar la complejidad y el acoplamiento entre diferentes partes de un sistema de software.

### Definición 21:
Denominaremos árbol a un grafo conexo sin circuitos en grafos orientados o sin ciclos en grafos no orientados.

#### Adaptación a la informática:
En estructuras de datos, un árbol representa una organización jerárquica de información sin ciclos. Esto es fundamental en la implementación de estructuras como árboles binarios de búsqueda, árboles B+ en bases de datos, o en la representación de estructuras de directorios en sistemas de archivos.

### Definición 22:
Una arborescencia es un grafo orientado, fuertemente conexo, sin ciclos, en el que todos los nodos tendrán semigrado interior igual a la unidad, excepto uno: la raíz de arborescencia cuyo semigrado interior es 0.

#### Adaptación a la informática:
En el diseño de sistemas jerárquicos, una arborescencia puede representar una estructura organizativa con un único punto de entrada (raíz) y relaciones unidireccionales entre los elementos. Esto es común en la implementación de estructuras de control en compiladores, en la representación de árboles de decisión en inteligencia artificial, o en la modelación de jerarquías de clases en programación orientada a objetos.