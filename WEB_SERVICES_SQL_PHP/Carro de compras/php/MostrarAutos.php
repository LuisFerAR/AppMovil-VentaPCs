<?PHP
header('Content-Type:Application/json; charset="utf-8"');	

if($_SERVER["REQUEST_METHOD"]=="GET"){
    
    $ID = $_GET['ID'];
        
    require_once ('conexion.php');
    
    $cnx = new Conexion();
	
	$conexion = $cnx->AbrirConexion();
    mysqli_set_charset($conexion, 'utf8');	

	if (empty($ID))
        $ID = -1;
    
    $consulta = "call SP_MostrarAutos($ID);";
	
	$result = mysqli_query($conexion, $consulta);
 	$cantidad = mysqli_num_rows($result);
 	
 	$temp_array = array();
 	
 	if($cantidad > 0){
 		while($row = mysqli_fetch_assoc($result)){
 			$temp_array[] = $row;
 		}
 	}
   
 	echo json_encode($temp_array);
	$cnx->CerrarConexion($conexion);
}

?>