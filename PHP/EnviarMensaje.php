<?php
$DB_SERVER="127.0.0.1"; #la dirección del servidor
$DB_USER="Xprehecho001"; #el usuario para esa base de datos
$DB_PASS="*NnqYQE8a10"; #la clave para ese usuario
$DB_DATABASE="Xprehecho001_Chess"; #la base de datos a la que hay que conectarse

# Se establece la conexión:
$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

#Comprobamos conexión
if (mysqli_connect_errno($con)) {
echo 'Error de conexion: ' . mysqli_connect_error();
exit();
}

$parametro = $_POST["player"];

# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "SELECT token FROM Token WHERE player='$parametro'");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}

#Acceder al resultado
$fila = mysqli_fetch_row($resultado)
$registration_id = $fila[0];

$cabecera = array(
'Authorization: key=AAAAMmsJRMQ:APA91bGMQHwQUxvxyUsV3ZrFYNKpC4Cb7NJWEYq2s94FcY05zMTMRnt6QXun4hPWNo7PGnFBA2D6A_4PBcRt-HoEWp0QYK4dPtJNtTjJywgfEETM8_Bg8T-tforeku3NlX7koyw-7U6Z',
'Content-Type: application/json'
);

$msg = array(
'to' => $registration_id,
'notification' => array (
"body" => "¡Es tu turno!",
"title" => "¡Te han retado a jugar una partida!",
"icon" => "ic_stat_ic_notification"
)
);

$msgJSON = json_encode($msg);

$ch = curl_init(); #inicializar el handler de curl
#indicar el destino de la petición, el servicio FCM de google
curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
#indicar que la conexión es de tipo POST
curl_setopt($ch, CURLOPT_POST, true );
#agregar las cabeceras
curl_setopt($ch, CURLOPT_HTTPHEADER, $cabecera);
#Indicar que se desea recibir la respuesta a la conexión en forma de string
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
#agregar los datos de la petición en formato JSON
curl_setopt($ch, CURLOPT_POSTFIELDS, $msgJSON );
#ejecutar la llamada
$resultado = curl_exec($ch);
#cerrar el handler de curl
curl_close($ch);

if (curl_errno($ch)) {
print curl_error($ch);
}
echo $resultado;

?>