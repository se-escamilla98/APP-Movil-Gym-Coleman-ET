# APP-Movil-Gym-Coleman-ET
# Gym Coleman - Aplicación de Gestión Deportiva

## 📋 Descripción del Proyecto
**Gym Coleman** es una aplicación móvil nativa desarrollada en Kotlin bajo la arquitectura **MVVM (Model-View-ViewModel)**. El proyecto soluciona la problemática de la gestión de entrenamientos y control de acceso en gimnasios, permitiendo la operación offline mediante persistencia local (**Room**) y la sincronización en tiempo real con microservicios propios.

El sistema está diseñado para dar soporte a 4 roles de usuario distintos, integrando recursos nativos del dispositivo y consumo de APIs externas.

---

## 👥 Integrantes del Equipo
Livan Sepulveda
Jorge Alvarez 
Sebastian Escamilla

---

## 📱 Funcionalidades y Roles

1.  **Administrador:** Gestión de usuarios y configuración del sistema.
2.  **Entrenador:** Creación y asignación de rutinas personalizadas a los socios.
3.  **Cliente (Socio):** Visualización de rutinas diarias, historial de ejercicios y compra de suplementos.
4.  **Recepcionista:** control básico de pagos y manejo de inventario.

**Características Técnicas:**
**Arquitectura:** MVVM + Clean Architecture para desacoplar la lógica de la vista
**Persistencia Local:** Base de datos **Room** para funcionamiento sin conexión
**Integración Externa:** Consumo de API REST para guía de ejercicios
**Backend Propio:** Microservicios para autenticación y persistencia remota

---

## 🔗 Endpoints y APIs Utilizadas

### 1. API Externa (Pública)
Se utiliza la API de **wger (Workout Manager)** para obtener el catálogo de ejercicios e imágenes.
* **URL Base:** `https://wger.de/api/v2/`
* **Endpoint Consumido:**
    * `GET /exerciseinfo/?limit=50` : Obtiene listado detallado de ejercicios (músculos, descripción, imágenes).

### 2. Microservicios Propios (Gym Coleman API)
Backend desarrollado por el equipo para la lógica de negocio y base de datos central.
* **URL Base:** `http://[IP_DE_TU_PC]:[PUERTO]/api/`
* **Endpoints Principales:**
    * `POST /auth/login` : Autenticación de usuarios y obtención de Token.
    * `POST /auth/register` : Registro de nuevos socios.
    * `GET /rutinas/usuario/{id}` : Obtención de las rutinas asignadas al cliente.
    * `POST /clases/reserva` : Inscripción a clases grupales.
    * `GET /membresia/estado` : Verificación de pagos al día.

---

##  Instrucciones de Aprovisionamiento y Ejecución

Siga estos pasos estrictamente para levantar el entorno de desarrollo y pruebas.

### 1. Clonar el Repositorio
```bash
git clone (https://github.com/se-escamilla98/APP-Movil-Gym-Coleman-ET.git)
cd [Nombre de la Carpeta en la cual va a guardar el proyecto]
Se abrira directamente en el IDE o clonar el repositorio con la URL
