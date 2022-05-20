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

$parametro = $_POST["email"];
$parametro2 = $_POST["newElo"];
$parametro3 = $_POST["eloType"];


# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "UPDATE Usuario SET $parametro3=$parametro2 WHERE email='$parametro'");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) 
{
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}
?>