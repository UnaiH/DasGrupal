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

$idGame = $_POST["idGame"];
$player1 = $_POST["player1"];
$player2 = $_POST["player2"];
$gameType = $_POST["gameType"];
$nextTurn = $_POST["nextTurn"];

# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "INSERT INTO Game (idGame, player1, player2, gameType, nextTurn) VALUES ('$idGame','$player1','$player2','$gameType','$nextTurn')");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) 
{
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}
?>