<?php

$DB_SERVER="127.0.0.1"; #la dirección del servidor
$DB_USER="Xprehecho001"; #el usuario para esa base de datos
$DB_PASS="*NnqYQE8a10"; #la clave para ese usuario
$DB_DATABASE="Xprehecho001_Chess"; #la base de datos a la que hay que conectarse
# Se establece la conexión:
$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) 
{
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
}

$parametro = $_POST["idGame"];

# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "SELECT * FROM Game WHERE idGame=$parametro");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) 
{
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}

$pieces = array();

while($row = $resultado->fetch_assoc())
{
	$pieces[] = array('player1' => $row['player1'], 'player2' => $row['player2'], 'nextTurn' => $row['nextTurn']);
}
#Devolver el resultado en formato JSON
echo json_encode($pieces);
?>