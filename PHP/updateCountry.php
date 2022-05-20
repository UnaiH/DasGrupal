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
$country =$_POST["country"];
$stmt=mysqli_query($con,"SELECT * FROM Usuario WHERE email='$email'");
if(mysqli_num_rows($stmt)==1){
    mysqli_query($con,"UPDATE Usuario SET country='$country' WHERE email='$email'");
    $resultado[] = array('resultado' => true);
}else{
    $resultado[] = array('resultado' => false);
}


echo json_encode($resultado);
mysqli_close($con)

?>
