<?PHP
    header('Content-Type:Application/json; charset="utf-8"');

    if($_SERVER["REQUEST_METHOD"]=="GET"){
        $in_tipo = $_REQUEST['tipo'];
        $in_id_user = $_REQUEST['id_user'];
        $in_id_cita = $_REQUEST['id_cita'];
        
        require ('conexion.php');
        
      	$cnx = new Conexion();
    		
        $conexion = $cnx->AbrirConexion();
            
        mysqli_set_charset($conexion, 'utf8');	
    	
        switch($in_tipo){
            case 1: $query = "call SP_MostrarDistritos();"; break;
            case 2: break;
            case 3: break;
            default: $in_tipo = -1;
        }
        
        if($in_tipo != -1){
            $result = mysqli_query($conexion, $query);
            $cantidad = mysqli_num_rows($result);
            
            $temp_array = array();
                
            if($cantidad > 0){
                while (($fila = mysqli_fetch_array($result))!=NULL){
                    $temp_array[] = $fila;
                }
            }

            echo json_encode($temp_array);
        }
        else
            echo "ERROR: no data";
     	
     	
        $cnx->CerrarConexion($conexion); 	
    }
    else{
        echo "Error: Método GET requerido";
    }
?>