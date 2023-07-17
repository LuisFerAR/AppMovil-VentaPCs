<?php
class Conexion
{
	private $usuario;
	private $clave;
	private $servidor;
	private $basedatos;
	
	function __construct()
	{
		$this->usuario = "3491979_proyecto";
		$this->clave= "yugioh23";
		$this->servidor= "fdb1022.awardspace.net";
		$this->basedatos = "3491979_proyecto";
	}
	
	function AbrirConexion()
	{
		$cadena = mysqli_connect($this->servidor,$this->usuario,$this->clave,$this->basedatos);
		
		if($cadena)
		{
			return $cadena;
		}
		else
		{
			return "Error ".mysqli_error();
		}
	}
	
	function CerrarConexion($cadena)
	{
		mysqli_close($cadena);
		$cadena = null;
	}
}
?>