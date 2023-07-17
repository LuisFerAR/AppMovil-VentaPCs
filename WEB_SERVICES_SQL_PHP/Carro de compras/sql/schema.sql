create table Autos(
	ID integer primary key AUTO_INCREMENT,
    Marca varchar(50),
    Modelo varchar(50),
    Placa char(7) UNIQUE,
    Precio decimal(10,2),
    Imagen mediumblob
);
