# CRUD de Expedientes – Backend (Spring Boot + JWT + PostgreSQL)

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- Maven
- Lombok
- Validation

## Endpoitns para auntenticacion

### **POST /auth/login**
Login request
```json
{
  "email": "ejemplo@gmail.com",
  "password": "Contras3ña?"
}
```

### **POST /auth/register**
Register request
```json
{
  "email": "ejemplo@gmail.com",
  "password": "Contras3ña?"
}
```

#### Auth response
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJob2xhgkjdfhguyrth34fGFDFeG4.hgfgertdgdfGE%$G$FFHHF#g",
    "userId": "190e2279-fertdfg-ejemplo-234"
}
```

## Endpoitns para CRUD - expediente (requiere Bearer token - JWT)

#### **GET /expediente**
Obtiene todos los expedientes.

Response ejemplo:
```json
[
    {
        "id": "99fd10db-64e5-4880-bc83-054b75d10a66",
        "nombre": "csao 1",
        "descripcion": "expediente en revision",
        "estado": "PENDIENTE"
    },
    {
        "id": "f51026ee-7938-4f6c-b4c9-d36a1b95c326",
        "nombre": "caso 2",
        "descripcion": "expediente sobre caso X",
        "estado": "APROBADO"
    }
]
```

#### **GET /expediente/{id}**
Obtiene un expediente por UUID.

#### **POST /expediente**
Crea un expediente nuevo.

Body ejemplo:
```json
{
  "nombre": "Caso 01",
  "descripcion": "Expediente inicial"
}
```
Estado -> "PENDIENTE" por defecto

Response ejemplo: 
````json
{
    "id": "1ea0e459-490f-48e4-acdc-8802e33afd76",
    "nombre": "caso nuevo/actualizado",
    "descripcion": "descripcion",
    "estado": "PENDIENTE"
}
````

#### **PATCH /expediente/{id}**
Actualiza un expedientes, campos opcionales: nombre, descripcion, estado.

#### **DELETE /expediente**
Elimina un expediente.

## Instalacion y ejecucion

1. Crear una DB PostgreSQL
2. Clonar el repositorio
````bash
git clone https://github.com/Riot-21/expedientes-backend.git
````
3. Construir proyecto - dependencias
**Maven -> Clean -> Build**
4. Crear archivo ``application.properties`` en base a ``application.properties.template`` y configurarlo.
5. Ejecutar el proyecto, configurado en puerto 
``http:localhost:8080/api``