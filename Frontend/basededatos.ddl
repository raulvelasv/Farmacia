--Script per a la creació de la base de dades del projecte de Entorns de Desenvolupament--

CREATE DATABASE farmacia;
USE farmacia;

--Taula Doctor--
CREATE TABLE IF NOT EXISTS doctor 
(mail varchar(50),
pass varchar(100) NOT NULL,
name varchar(100) NOT NULL,
last_log date,
session bigint(10),
PRIMARY KEY(mail));

--Taula Pacient--
CREATE TABLE IF NOT EXISTS patient
(mail varchar(50),
name varchar(100) NOT NULL,
PRIMARY KEY(mail));

--Taula Medicament--
CREATE TABLE IF NOT EXISTS medicine
(med_id int AUTO_INCREMENT,
name varchar(50) NOT NULL,
tmax int NOT NULL,
tmin int NOT NULL,
PRIMARY KEY(med_id));

--Taula Xip--
CREATE TABLE IF NOT EXISTS xip
(xip_id int(10),
doctor_mail varchar(50),
id_medicine int,
patient_mail varchar(50),
end_date date NOT NULL,
PRIMARY KEY(xip_id),
FOREIGN KEY (doctor_mail) REFERENCES doctor (mail)
ON UPDATE cascade,
FOREIGN KEY (id_medicine) REFERENCES medicine (med_id)
ON UPDATE cascade,
FOREIGN KEY (patient_mail) REFERENCES patient (mail)
ON UPDATE cascade);

-- Inserting data into the "doctor" table
INSERT INTO doctor (mail, pass, name, session) VALUES
('raul@gmail.com','1234','Raúl',1938463),
('luisa@gmail.com', '1234', 'Luisa', 123456),
('natalia@gmail.com', '1234', 'Natalia', 789012),
('fernando@gmail.com', '1234', 'Fernando', 345678),
('carolina@gmail.com', '1234', 'Carolina', 901234),
('sergio@gmail.com', '1234', 'Sergio', 567890);

-- Inserting data into the "patient" table
INSERT INTO patient (mail, name) VALUES
('dani@gmail.com', 'Dani'),
('pepe@gmail.com', 'Pepe'),
('ana@gmail.com', 'Ana'),
('maria@gmail.com', 'María'),
('juan@gmail.com', 'Juan'),
('laura@gmail.com', 'Laura'),
('marcos@gmail.com', 'Marcos'),
('sandra@gmail.com', 'Sandra'),
('pedro@gmail.com', 'Pedro'),
('ricardo@gmail.com', 'Ricardo'),
('javier@gmail.com', 'Javier'),
('isabel@gmail.com', 'Isabel'),
('carlos@gmail.com', 'Carlos'),
('marta@gmail.com', 'Marta'),
('andres@gmail.com', 'Andrés')

-- Inserting data into the "medicine" table
INSERT INTO medicine (name, tmax, tmin)
VALUES ('Amoxicilina', 10, 5),
('Medicine X',8,4),
('Ibuprofeno', 25, 5),
('Paracetamol', 30, 10),
('Amoxicilina', 30, 5),
('Omeprazol', 25, 10),
('Lorazepam', 20, 5);

