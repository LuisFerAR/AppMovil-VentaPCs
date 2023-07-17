<?PHP

if($_SERVER["REQUEST_METHOD"]=="DELETE"){
    $ID = $_POST['ID'];
        
    require_once ('conexion.php');
    
    $cnx = new Conexion();
	
	$conexion = $cnx->AbrirConexion();
	
	$consulta = "call SP_EliminarAuto($ID);";
	
	$resultado = mysqli_query($conexion, $consulta);
	echo $resultado;
	
	$cnx->CerrarConexion($conexion);
}

?>