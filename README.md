# MINIM1 Drones

Versión 1:

- Funciona añadir drones y pilotos
- Funciona listar drones y pilotos ordenados por horas de vuelo
- Funciona guardar un dron en el almacén y repararlo (pila)
- Funciona crear un plan de vuelo comprobando solapes y mantenimiento
- Funciona listar los planes de un piloto y de un dron
- Tests JUnit de 4 operaciones
- REST con Swagger: añadir dron, añadir piloto, crear plan y planes de un piloto

REST (http://localhost:8080/dsaApp), en JSON con DronTO, PilotoTO y PlanVueloTO:

- POST /drones: añadir dron (201, 400)
- POST /pilotos: añadir piloto (201, 400)
- POST /planes: crear plan de vuelo (201, 400, 404 si no existe, 409 si solapa o está en mantenimiento)
- GET /pilotos/{id}/planes: planes de un piloto (200, 404)

Problemas:

- La fecha (LocalDateTime) no se convertía sola en el JSON, en el REST va como texto (2026-11-10T10:00)

Pendiente:

- Nada, está hecho todo lo que pide el enunciado
