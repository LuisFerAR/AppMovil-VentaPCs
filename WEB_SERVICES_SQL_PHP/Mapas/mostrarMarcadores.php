<?PHP
header('Content-Type:Application/json; charset="utf-8"');	

if($_SERVER["REQUEST_METHOD"]=="GET"){
        require ('conexion.php');
    
  	$cnx = new Conexion();
		
        $conexion = $cnx->AbrirConexion();
        
        mysqli_set_charset( $conexion, 'utf8');	
	
        $query = "call SP_MostrarMarcadores();";
 	
 	$result = mysqli_query($conexion, $query);
 	$cantidad = mysqli_num_rows($result);
 	
 	$temp_array = array();
        
        if($cantidad > 0){
                while (($fila = mysqli_fetch_array($result))!=NULL){
                        $temp_array[] = $fila;
                }
 	}

 	echo json_encode($temp_array);
 	
        $cnx->CerrarConexion($conexion); 	
 }
 else{
         echo "Marcadores: Se requiere GET";
 }
?>