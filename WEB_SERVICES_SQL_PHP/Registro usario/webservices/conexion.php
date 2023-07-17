<?php
class Conexion
{
	private $usuario;
	private $clave;
	private $servidor;
	private $basedatos;
	
	function __construct()
	{
		$this->servidor = "localhost";
		$this->usuario = "id20958807_fer";
		$this->clave = "Hercules123*";
		$this->basedatos = "id20958807_luis";
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