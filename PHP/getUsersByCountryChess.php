<?php
$DB_SERVER="localhost";
$DB_USER="Xprehecho001";
$DB_PASS="*NnqYQE8a10"; 
$DB_DATABASE="Xprehecho001_Chess";

$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

if (mysqli_connect_errno($con)){
        echo 'Error de conexion: ' . mysqli_connect_error();
        exit();
}



$stmt = mysqli_query($con,"SELECT * FROM Usuario ORDER BY Usuario.eloChess Desc");
if ($stmt->num_rows > 0){
	while($row = $stmt->fetch_assoc()){
		$arrayresultado =array(
		'email'=>$row['email'],
		'username'=>$row['username'],		
		'eloChess'=>$row['eloChess'],
		'country'=>$row['country']);	
		$resultado[] = array('resultado' => $arrayresultado);
	}	
}else{
	$resultado[] = array('resultado' => false);
}
echo json_encode($resultado);
mysqli_close($con)

?>
