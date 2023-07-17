<?php
        header('Content-Type:Application/json; charset="utf-8"');	
        if($_SERVER["REQUEST_METHOD"]=="POST"){
                $correo = $_REQUEST['correo'];
		$clave = $_REQUEST['clave'];
        
                require ('conexion.php');
        
                $json=array();
        
                $cnx = new Conexion();
		
		$conexion = $cnx->AbrirConexion();
		mysqli_set_charset($conexion, 'utf8');	
		$consulta="call SP_Login('$correo', '$clave');";
		$resultado=mysqli_query($conexion, $consulta);
        
                if($consulta){
                        if($reg=mysqli_fetch_array($resultado)){
                                $json[]=$reg;
                                $cnx->CerrarConexion($conexion);
                                echo json_encode($json);
                        }
                        else{
                                echo json_encode($json);
                        }
		}
	}
	else{
		echo "ERROR: se requiere POST";
	}
?>