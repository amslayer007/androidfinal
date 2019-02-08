<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();

//echo "HELKLO";
 
// check for required fields
if (isset($_POST['ID']) && isset($_POST['Name']) && isset($_POST['Designation']) && isset($_POST['URL'])) {
 
    $ID = $_POST['ID'];
    $Name = $_POST['Name'];
    $Designation = $_POST['Designation'];
	$URL = $_POST['URL'];
    
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO add_mentor(Name, Designation,URL) VALUES( '$Name', '$Designation','$URL' )");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Mentor successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>