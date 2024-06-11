# Problema de Transporte y Transbordo

## Descripción del Problema

El objetivo es trasladar la mayor cantidad posible de materia prima desde los puntos de origen hasta los destinos, cumpliendo con las demandas mínimas requeridas en cada destino. Además, es posible transportar el material desde un origen a múltiples destinos. El objetivo principal es encontrar la política de transporte más económica.

## Características del Problema

- **Origen:** Puntos de inicio del transporte de material.
- **Destino:** Puntos finales que requieren la materia prima.
- **Demanda Mínima:** Cada destino tiene una cantidad mínima de material que debe recibir.
- **Costo de Transporte:** Costos asociados con el transporte de material desde un origen hasta un destino.

## Formulación Matemática

Supongamos que tenemos `m` orígenes y `n` destinos. Los elementos del problema son:

- `ai`: Cantidad de material disponible en el origen `i`.
- `bj`: Cantidad de material requerida en el destino `j`.
- `Cij`: Costo unitario de transporte desde el origen `i` al destino `j`.
- `Xij`: Cantidad transportada desde el origen `i` al destino `j`.

## Restricciones

- **Disponibilidad en los orígenes:** La suma de `Xij` para cada `j` desde 1 hasta `n` debe ser menor o igual a `ai` para cada `i` desde 1 hasta `m`.
  
- **Demanda en los destinos:** La suma de `Xij` para cada `i` desde 1 hasta `m` debe ser mayor o igual a `bj` para cada `j` desde 1 hasta `n`.
  
- **No negatividad:** `Xij` debe ser mayor o igual a 0 para todos los `i` y `j`.

## Objetivo

Minimizar el costo total de transporte: Minimizar `Z` igual a la suma de `Cij * Xij` para todos los `i` y `j`.

## Estrategias de Solución

Para resolver este problema, se pueden utilizar técnicas de optimización lineal como el método simplex o algoritmos específicos para problemas de transporte y transbordo. Para iniciar con el algoritmo de transporte, se recomienda obtener una solución básica factible inicial utilizando uno de los siguientes métodos:

1. **Método de la Esquina Noroeste (NWCR):** 
   Asigna el material empezando desde la esquina noroeste de la tabla de transporte y continúa hasta satisfacer las demandas y ofertas.

2. **Método de Costos Mínimos (MCM):** 
   Selecciona las celdas con los menores costos y asigna tanto material como sea posible hasta cumplir con las restricciones.

3. **Aproximación de Vogel (VAM):** 
   Considera las diferencias entre los dos menores costos en cada fila y columna para hacer asignaciones más eficientes.

4. **Aproximación de Russell:**
   Utiliza una fórmula heurística para hacer asignaciones iniciales que intenten minimizar el costo total.

## Ejemplo de Solución: Método de la Esquina Noroeste

Supongamos que tenemos 3 orígenes y 4 destinos con las siguientes disponibilidades y demandas:

| Orígenes/Destinos | D1 | D2 | D3 | D4 | Disponibilidad |
|-------------------|----|----|----|----|----------------|
| O1                |    |    |    |    | a1             |
| O2                |    |    |    |    | a2             |
| O3                |    |    |    |    | a3             |
| Demanda           | b1 | b2 | b3 | b4 |                |

1. Asigna `X11` igual al mínimo de `a1` y `b1`.
2. Resta la cantidad asignada de `a1` y `b1`.
3. Si la demanda `b1` se satisface, pasa al siguiente destino.
4. Si la disponibilidad `a1` se agota, pasa al siguiente origen.
5. Repite el proceso hasta que todas las demandas y disponibilidades se satisfagan.
  
## Ejemplo de Solución: Método de la Esquina Noroeste

Supongamos que tenemos 3 orígenes y 4 destinos con las siguientes disponibilidades y demandas:

| Orígenes/Destinos | D1 | D2 | D3 | D4 | Disponibilidad |
|-------------------|----|----|----|----|----------------|
| O1                |    |    |    |    | a1             |
| O2                |    |    |    |    | a2             |
| O3                |    |    |    |    | a3             |
| Demanda           | b1 | b2 | b3 | b4 |                |

1. Asigna `X11` igual al mínimo de `a1` y `b1`.
2. Resta la cantidad asignada de `a1` y `b1`.
3. Si la demanda `b1` se satisface, pasa al siguiente destino.
4. Si la disponibilidad `a1` se agota, pasa al siguiente origen.
5. Repite el proceso hasta que todas las demandas y disponibilidades se satisfagan.
  
## Ejemplo de Solución: Método de Costos Mínimos

Supongamos que tenemos 3 orígenes y 4 destinos con las siguientes disponibilidades y demandas:

### Tabla de Costos
| Orígenes/Destinos | D1 | D2 | D3 | D4 | Disponibilidad |
|-------------------|----|----|----|----|----------------|
| O1                | 2  | 3  | 1  | 4  | 30             |
| O2                | 3  | 2  | 4  | 2  | 40             |
| O3                | 4  | 1  | 5  | 3  | 50             |
| Demanda           | 20 | 30 | 40 | 30 |                |

1. Identificar el costo mínimo: El costo mínimo es 1 en la celda (O1, D3).
2. Asignar la cantidad posible: la cantidad posible es `min(30, 40) = 30`. Asignamos 30 unidades a (O1, D3).
3. Actualizar ofertas y demandas
   - Oferta de O1: 30 - 30 = 0
   - Demanda de D3: 40 - 30 = 10
4. Eliminar fila o columna satisfecha: Eliminamos la fila de O1 ya que su oferta se ha agotado.

### Tabla de Costos Actualizada
| Orígenes/Destinos | D1 | D2 | D3 | D4 | Disponibilidad |
|-------------------|----|----|----|----|----------------|
| O2                | 3  | 2  | 4  | 2  | 40             |
| O3                | 4  | 1  | 5  | 3  | 50             |
| Demanda           | 20 | 30 | 10 | 30 |                |

6. Identificar el costo mínimo: el nuevo costo mínimo es 1 en la celda (O3, D2).
7. Asignar la cantidad posible: la cantidad posible es `min(50, 30) = 30`. Asignamos 30 unidades a (O3, D2).
8. Actualizar ofertas y demandas
   - Oferta de O3: 50 - 30 = 20
   - Demanda de D2: 30 - 30 = 0
9. Eliminar fila o columna satisfecha: eliminamos la columna de D2 ya que su demanda se ha agotado.

### Tabla de Costos Actualizada
| Orígenes/Destinos | D1 | D3 | D4 | Disponibilidad |
|-------------------|----|----|----|----------------|
| O2                | 3  | 4  | 2  | 40             |
| O3                | 4  | 5  | 3  | 20             |
| Demanda           | 20 | 10 | 30 |                |

#### Repetir hasta que todas las demandas y ofertas se satisfagan.

