<?php
    $respuesta = true;
    $tema= $_POST["tema"];
    $titulo = "Turno";
    $url = "https://fcm.googleapis.com/fcm/send";
    $topic = "/topics/$tema";
    $serverKey = "AAAAj9LHwxU:APA91bGwDxsVnTEng2YMgEXzSWzXxV7n69BxLNz-GgrZRg84DbJGOOIs-unw4tOMtYCpGPWgGoC_JaSVp9jO19jNEcjFW8NecBYJgPVbyz87jW0oHAcP1bvZwkaMclyxc6qn-3s6L81Y";
    $notification = array('title' =>$titulo , 'body' => $_POST["men"]);
    $aEnviar = array('to' => $topic, 'notification' => $notification,'priority'=>'high');
    $json = json_encode($aEnviar);
    $cabeceras = array();
    $cabeceras[] = 'Content-Type: application/json';
    $cabeceras[] = 'Authorization: key='. $serverKey;
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST,"POST");
    curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
    curl_setopt($ch, CURLOPT_HTTPHEADER,$cabeceras);
    //Send the request
    $response = curl_exec($ch);
    //Close request
    if ($response === FALSE) {
        $respuesta=false;
    die('FCM Send Error: ' . curl_error($ch));
    }
    else{
        $respuesta=true;
    }
    curl_close($ch);
?>