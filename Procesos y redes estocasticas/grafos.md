# Grafo de accesibilidad 
    Vertices = estados 
    arcos = accecibilidades permitidias si p(i,j) > 0

## dada la cadena con matriz de transicion:
- Definicion: Un estado j es acceptable si un estado i si p (i,j) > 0 en cualquier numero de pasos en algun m.
- Definicion: Un estado i se comunica con un estado j si p(i,j) > 0 y pm(i,j) > 0 a su vez si i es accesible desde i y j es accesible desde i. 
- Definicion: Una clase de comunicante es un conjunto de estados que se comunican entre ellos.
- Definicion: Las clases cominicantes on disjuntas (no tienen elementos en comun).
- Definicion: Una clase es abierta si desde algun estado de la clase se tiene acceso a un estado de otra clase.
- Definicion: Una clase es cerrada cuando no es abierta (o sea no hay una comunicacion, cuando no tiene acceso a otro estado de la clase).

Consideramos las cadenas con las siguientes matrices de transicion
 ### para la calificacion de los estados:

 #### Ejemplo#1
 ```python
    E = [1,2,3,4]
    P= [[1/2,0,1/2,0],[1/3,0,0,2/3],[1/4,1/4,1/4,1/4],[1/3,1/3,0,1/3]]
```

<img src=matriz_transicion_grafo.png>

clase cerrada.


#### Ejemplo#2


```python
    E = [1,2,3,4]
    P= [
    [1, 0, 0, 0],
    [0, 1/2, 0, 1/2],
    [1/3, 0, 1/3, 1/3],
    [1/2, 1/2, 0, 0]
]
```
<img src=matriz_transicion_grafo2.png>

[1,3] clase abierta

[2] clase abierta

[0] clase cerrada


