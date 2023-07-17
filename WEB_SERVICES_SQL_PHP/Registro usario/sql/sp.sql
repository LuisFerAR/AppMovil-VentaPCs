
DELIMITER $$
create PROCEDURE SP_MostrarDistritos()
BEGIN
	SELECT nombre_distrito from Distrito;
END $$

DELIMITER $$
create PROCEDURE SP_Login(
    in_correo varchar(50),
    in_clave varchar(128)
)
BEGIN
DECLARE res INT;
	select count(*) into res from Cliente where correo like in_correo and clave like in_clave;
	IF res = 0 THEN
		select -1 as id_cliente;
	ELSE
		select * from Cliente where correo like in_correo and clave like in_clave;
	END IF;
END $$

DELIMITER $$
create PROCEDURE SP_AgregarCliente(
in_dni char(8),
in_nombre varchar(50),
in_apellidos varchar(50),
in_fecha_nac date,
in_sexo char(1),
in_correo varchar(50),
in_clave varchar(128),
in_id_distrito int
)
BEGIN
	INSERT INTO Cliente VALUES (null, in_dni, in_nombre, in_apellidos, in_fecha_nac, in_sexo, in_correo, in_clave, in_id_distrito);
End$$
DELIMITER ;
