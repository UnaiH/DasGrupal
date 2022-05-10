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


$email = $_POST["email"];
$password = $_POST["password"];
$username = $_POST["user"];
$hashed_password=password_hash($password, PASSWORD_DEFAULT);
$stmt=mysqli_query($con,"SELECT * FROM Usuario WHERE email='$email'");

if (mysqli_num_rows ($stmt)==0){
	mysqli_query($con,"INSERT INTO Usuario VALUES ('$email','$hashed_password','$username')");
	$resultado[] = array('resultado' => true);
}else{
	$resultado[] = array('resultado' => false);
}
echo json_encode($resultado);
mysqli_close($con);

?>
