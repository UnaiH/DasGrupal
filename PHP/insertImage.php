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
$image =$_POST["image"];
$resultado[] = array('resultado' => false);
$stmt = mysqli_query($con,"SELECT * FROM Images WHERE User='$email'");
if(mysqli_num_rows($stmt)==0){
    mysqli_query($con,"INSERT INTO Images(User, Image) VALUES ('$email','$image')");
    $resultado[] = array('resultado' => true);
}else{
    mysqli_query($con,"UPDATE Images SET Image='$image' WHERE User='$email'");
    $resultado[] = array('resultado' => true);
}


echo json_encode($resultado);
mysqli_close($con)

?>