<?php
    if($_SERVER["REQUEST_METHOD"]=="POST"){
        $marca = $_REQUEST['marca'];
        $modelo = $_REQUEST['modelo'];
        $placa = $_REQUEST['placa'];
        $precio = $_REQUEST['precio'];
        $imagen = $_REQUEST['imagen'];
        $ID = $_REQUEST['ID'];
        
        require_once ('conexion.php');
        
        $cnx = new Conexion();
		
		$conexion = $cnx->AbrirConexion();
		$consulta = "call SP_ActualizarAuto('$marca', '$modelo', '$placa', $precio, '$imagen', $ID)";
		
		$resultado = mysqli_query($conexion, $consulta);
		echo $resultado;
		
		$cnx->CerrarConexion($conexion);
    }
?>