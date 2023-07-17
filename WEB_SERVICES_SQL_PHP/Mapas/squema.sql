create table Marcadores(
	id int auto_increment PRIMARY KEY,
	titulo varchar(20),
    latitud varchar(20),
	longitud varchar(20)
);

insert into Marcadores values(null, 'SJL', '-11.9849215', '-77.0052265');
insert into Marcadores values(null, 'Breña', '-12.0582824', '-77.05855');
insert into Marcadores values(null, 'Los Olivos', '-11.9593392', '-77.0680394');
insert into Marcadores values(null, 'Comas', '-11.9342993', '-77.0586299');
insert into Marcadores values(null, 'Chorrillos', '-12.1794746', '-77.0032129');

DELIMITER $$
create PROCEDURE SP_MostrarMarcadores(
)
BEGIN
	SELECT * FROM Marcadores;
End$$
DELIMITER ;
