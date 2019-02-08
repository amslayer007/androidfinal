<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['ID']) && isset($_POST['StartupName']) && isset($_POST['Description']) && ($_POST['StartupFounder']) &&($_POST['Website']) ) {
 
    $ID = $_POST['ID'];
    $StartupName = $_POST['StartupName'];
    $Description = $_POST['Description'];
	$StartupFounder = $_POST['StartupFounder'];
	$Website = $_POST['Website'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE add_startup SET StartupName = '$StartupName', Description = '$Description', StartupFounder = '$StartupFounder', Website = '$Website' WHERE ID = $ID");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "startup successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>