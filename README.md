# Cowork API - Examen de Desarrollo
## Nombre: Daniel Arana Ocumbe
## Descripción
API REST para la gestión de un sistema de Cowork, permitiendo la administración de salas y la creación de reservas con validaciones de negocio, estados y eliminación en cascada.

## Tecnologías Utilizadas
* **Java 26**
* **Spring Boot 4.0.6**
* **Maven**
* **Persistencia en Memoria (AtomicLong)**

## Endpoints Principales

### Salas
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/salas` | Crea una nueva sala. |
| `GET` | `/api/salas` | Lista todas las salas. |
| `GET` | `/api/salas/{id}` | Obtiene detalle de una sala. |
| `PUT` | `/api/salas/{id}` | Actualiza una sala existente. |
| `DELETE` | `/api/salas/{id}` | Elimina sala y sus reservas asociadas (Cascada). |

### Reservas
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/reservas` | Crea una nueva reserva. |
| `GET` | `/api/reservas` | Lista reservas (con filtros: estado, fecha, salaId). |
| `PUT` | `/api/reservas/{id}/estado` | Cambia el estado de una reserva. |
| `DELETE` | `/api/reservas/{id}` | Elimina una reserva. |

## Arquitectura del Proyecto
El proyecto sigue un patrón de diseño en capas para asegurar la separación de responsabilidades:



* **Controller:** Maneja las peticiones HTTP y los códigos de respuesta.
* **Service:** Contiene la lógica de negocio (validaciones y cascada).
* **Repository:** Gestiona la persistencia de datos en memoria.
* 
La lógica de eliminación en cascada está implementada en ReservaService, asegurando la integridad de los datos al eliminar una sala.
## Instrucciones para ejecutar
1. Clona el repositorio.
2. Asegúrarse de tener instalado JDK 17 o superior.
3. Ejecuta el proyecto mediante Maven: `./mvnw spring-boot:run`.
4. La API estará disponible en `http://localhost:9090`.
