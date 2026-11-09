-- ============================================================
-- Carranza Sofia - Algoritmos 2 - Segundo Parcial
-- Script de creación de la base de datos "veterinaria"
-- Ejecutar completo en phpMyAdmin (pestaña SQL) o MySQL Workbench
-- ============================================================

-- Si la base ya existía (con errores de pruebas anteriores) se borra
-- y se vuelve a crear desde cero
DROP DATABASE IF EXISTS veterinaria;
CREATE DATABASE veterinaria CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE veterinaria;

-- ------------------------------------------------------------
-- Tabla usuarios: se usa solo para el login
-- La clave se guarda con hash MD5 (32 caracteres hexadecimales)
-- Los nombres de columna "usuario" y "clave" son los que lee UsuariosDAO
-- ------------------------------------------------------------
CREATE TABLE usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    usuario   VARCHAR(50) NOT NULL UNIQUE,
    clave     CHAR(32)    NOT NULL
);

-- ------------------------------------------------------------
-- Tabla mascotas (cumple el rol de "clientes" del proyecto Turismo)
-- Las columnas del dueño usan ñ (nombreDueño) igual que los atributos
-- de la clase Mascotas; por eso la base se crea con utf8mb4
-- ------------------------------------------------------------
CREATE TABLE mascotas (
    idMascota       INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(50)  NOT NULL,
    edad            INT          NOT NULL DEFAULT 0,
    vacunada        BOOLEAN      NOT NULL DEFAULT FALSE,
    fechaNacimiento DATE         NOT NULL,
    nombreDueño    VARCHAR(100) NOT NULL,
    telefonoDueño  VARCHAR(20),
    emailDueño     VARCHAR(100)
);

-- ------------------------------------------------------------
-- Tabla veterinarias (cumple el rol de "destinos" del proyecto Turismo)
-- ------------------------------------------------------------
CREATE TABLE veterinarias (
    idVeterinaria INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    direccion     VARCHAR(150) NOT NULL,
    consultas     INT          NOT NULL DEFAULT 0,
    abierta       BOOLEAN      NOT NULL DEFAULT TRUE,
    fechaApertura DATE         NOT NULL
);

-- ------------------------------------------------------------
-- Tabla reservas: TABLA DE RELACIÓN entre mascotas y veterinarias
-- Cada reserva vincula una mascota con una veterinaria
-- (en el formulario se eligen con los combo box)
-- ------------------------------------------------------------
CREATE TABLE reservas (
    idReserva     INT AUTO_INCREMENT PRIMARY KEY,
    idMascota     INT  NOT NULL,
    idVeterinaria INT  NOT NULL,
    fechaReserva  DATE NOT NULL,
    numeroReserva INT  NOT NULL UNIQUE,
    precio        INT  NOT NULL,
    pagada        BOOLEAN NOT NULL DEFAULT FALSE,
    -- Claves foráneas: no se puede borrar una mascota o una veterinaria
    -- que tenga reservas asociadas (primero hay que borrar sus reservas)
    FOREIGN KEY (idMascota)     REFERENCES mascotas(idMascota),
    FOREIGN KEY (idVeterinaria) REFERENCES veterinarias(idVeterinaria)
);

-- ------------------------------------------------------------
-- Tabla urgencias: una urgencia pertenece a una veterinaria
-- fechaAlta puede quedar en NULL (internación en curso)
-- ------------------------------------------------------------
CREATE TABLE urgencias (
    idUrgencia    INT AUTO_INCREMENT PRIMARY KEY,
    idVeterinaria INT  NOT NULL,
    fechaIngreso  DATE NOT NULL,
    fechaAlta     DATE NULL,
    FOREIGN KEY (idVeterinaria) REFERENCES veterinarias(idVeterinaria)
);

-- ------------------------------------------------------------
-- Tabla urgencias_mascotas: TABLA DE RELACIÓN entre urgencias y mascotas
-- Hace falta porque una urgencia puede atender a varias mascotas
-- (en Java es el ArrayList<Mascotas> de la clase Urgencias)
-- ------------------------------------------------------------
CREATE TABLE urgencias_mascotas (
    idUrgencia INT NOT NULL,
    idMascota  INT NOT NULL,
    PRIMARY KEY (idUrgencia, idMascota),
    FOREIGN KEY (idUrgencia) REFERENCES urgencias(idUrgencia),
    FOREIGN KEY (idMascota)  REFERENCES mascotas(idMascota)
);

-- ============================================================
-- Datos de prueba
-- ============================================================

-- Usuario para ingresar al sistema: admin / admin123
-- La función MD5() de MySQL guarda la clave ya convertida a hash
INSERT INTO usuarios (usuario, clave) VALUES ('admin', MD5('admin123'));

INSERT INTO mascotas (nombre, edad, vacunada, fechaNacimiento, nombreDueño, telefonoDueño, emailDueño) VALUES
('Firulais', 3, TRUE,  '2023-02-10', 'Juan Perez',  '1145678901', 'juan.perez@mail.com'),
('Michi',    1, FALSE, '2025-06-01', 'Ana Gomez',   '1156789012', 'ana.gomez@mail.com'),
('Rocky',    5, TRUE,  '2021-09-15', 'Juan Perez',  '1145678901', 'juan.perez@mail.com');

INSERT INTO veterinarias (nombre, direccion, consultas, abierta, fechaApertura) VALUES
('Patitas Felices', 'Av. Presidente Peron 1234, San Miguel', 1, TRUE,  '2015-03-01'),
('Vet Norte',       'Paunero 567, San Miguel',               1, TRUE,  '2019-08-20');

INSERT INTO reservas (idMascota, idVeterinaria, fechaReserva, numeroReserva, precio, pagada) VALUES
(1, 1, '2026-11-05', 1001, 15000, TRUE),
(2, 2, '2026-11-06', 1002, 12000, FALSE);

INSERT INTO urgencias (idVeterinaria, fechaIngreso, fechaAlta) VALUES
(1, '2026-10-28', '2026-10-30'),
(2, '2026-11-02', NULL);

INSERT INTO urgencias_mascotas (idUrgencia, idMascota) VALUES
(1, 1),
(1, 3),
(2, 2);
