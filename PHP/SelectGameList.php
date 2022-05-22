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

$currentPlayer = $_POST["currentPlayer"];
$finished = $_POST["finished"];

# Ejecutar la sentencia SQL
if ($finished=='true') {
	$resultado = mysqli_query($con, "SELECT * FROM Game WHERE (player1='$currentPlayer' OR player2='$currentPlayer') AND nextTurn IS NULL");
}
else {
	$resultado = mysqli_query($con, "SELECT * FROM Game WHERE (player1='$currentPlayer' OR player2='$currentPlayer') AND nextTurn IS NOT NULL");
} 

# Comprobar si se ha ejecutado correctamente
if (!$resultado) 
{
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}

$i = 0;
#Acceder al resultado
while (($fila = mysqli_fetch_row($resultado))!=null) {

# Generar el array con los resultados con la forma Atributo - Valor
$arrayresultados[$i] = array(
'idGame' => $fila[0],
'player1' => $fila[1],
'player2' => $fila[2],
'gameType' => $fila[3],
'nextTurn' => $fila[4],
'winner' => $fila[5],
);
$i++;
}

#Devolver el resultado en formato JSON
echo json_encode($arrayresultados);

?>