<?php
    if($_SERVER["REQUEST_METHOD"]=="POST"){
        $marca = $_REQUEST['marca'];
        $modelo = $_REQUEST['modelo'];
        $placa = $_REQUEST['placa'];
        $precio = $_REQUEST['precio'];
        $imagen = $_REQUEST['imagen'];
        
        require_once ('conexion.php');
        
        $cnx = new Conexion();
		
		$conexion = $cnx->AbrirConexion();
		$consulta = "call SP_AgregarAuto('$marca', '$modelo', '$placa', $precio, '$imagen');";
		
		$resultado = mysqli_query($conexion, $consulta);
		echo $resultado;
		
		$cnx->CerrarConexion($conexion);
    }
?>