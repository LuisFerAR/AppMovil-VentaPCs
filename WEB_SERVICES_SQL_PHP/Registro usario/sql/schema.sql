create table Distrito(
	id_distrito int primary key AUTO_INCREMENT,
	nombre_distrito varchar(50)
);

create table Cliente(
	id_cliente int primary key AUTO_INCREMENT,
	dni char(8) unique,
	nombre varchar(50) not null,
	apellidos varchar(50) not null,
	fecha_nac date,
	sexo char(1),
	correo varchar(50) unique,
	clave varchar(128) not null,
	id_distrito int
);

alter table Cliente add foreign key (id_distrito) references Distrito (id_distrito);

