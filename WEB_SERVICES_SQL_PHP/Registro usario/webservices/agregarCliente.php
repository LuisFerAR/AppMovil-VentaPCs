<?php
    header('Content-Type:Application/json; charset="utf-8"');
    if($_SERVER["REQUEST_METHOD"]=="POST"){
        $dni = $_REQUEST['dni'];
        $nombre = $_REQUEST['nombre'];
        $apellidos = $_REQUEST['apellidos'];
        $fecha_nac = $_REQUEST['fecha_nac'];
        $sexo = $_REQUEST['sexo'];
        $correo = $_REQUEST['correo'];
        $clave = $_REQUEST['clave'];
        $id_distrito = $_REQUEST['id_distrito'];
        
        require_once ('conexion.php');
        
        $cnx = new Conexion();
		
		$conexion = $cnx->AbrirConexion();

        mysqli_set_charset($conexion, 'utf8');  
		
        $consulta = "call SP_AgregarCliente('$dni', '$nombre', '$apellidos', '$fecha_nac', '$sexo', '$correo', '$clave', $id_distrito);";

		$resultado = mysqli_query($conexion, $consulta);
		
		echo $resultado;
		
		$cnx->CerrarConexion($conexion);
    }
    else{
        echo "ERROR: se requiere POST";
    }
?>