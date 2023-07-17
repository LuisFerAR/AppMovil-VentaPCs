DELIMITER $$
create PROCEDURE SP_AgregarAuto(
in_marca varchar(50),
in_modelo varchar(50),
in_placa char(7),
in_precio decimal(10,2),
in_imagen mediumblob
)
BEGIN
	INSERT INTO Autos VALUES (null, in_marca, in_modelo, in_placa, in_precio, in_imagen);
End$$
DELIMITER ;

DELIMITER $$
create PROCEDURE SP_MostrarAutos(
in_id integer
)
BEGIN
	if(in_id = -1) then
		SELECT * FROM Autos;
	else
		SELECT * FROM Autos where ID = 	in_id;
	end if;
End$$
DELIMITER ;

DELIMITER $$
create PROCEDURE SP_EliminarAuto(
in_id integer
)
BEGIN
	DELETE FROM Autos where ID = in_id;
End$$
DELIMITER ;

DELIMITER $$
create PROCEDURE SP_ActualizarAuto(
in_marca varchar(50),
in_modelo varchar(50),
in_placa char(7),
in_precio decimal(10,2),
in_imagen mediumblob,
in_id integer
)
BEGIN
	UPDATE Autos 
	SET Marca = in_marca, Modelo = in_modelo, Placa = in_placa, Precio = in_precio, Imagen = in_imagen
	where ID = in_id;
End$$
DELIMITER ;