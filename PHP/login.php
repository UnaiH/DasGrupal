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
$password =$_POST["password"];

$stmt = mysqli_query($con,"SELECT * FROM Usuario WHERE email='$email'");
if ($stmt->num_rows > 0){
	while($row = $stmt->fetch_assoc()){	
		$hashed_pass = $row['password'];
	}
	if(password_verify($password,$hashed_pass)){
		$resultado[] = array('resultado' => true);
	}else{
        $resultado[] = array('resultado' => false);
    }
}
echo json_encode($resultado);
mysqli_close($con)

?>