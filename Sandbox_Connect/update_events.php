<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['ID']) && isset($_POST['Date']) && isset($_POST['Title']) && isset($_POST['Description']) && ($_POST['Time']) && ($_POST['Link']) &&($_POST['Venue']) ) {
 
    $ID = $_POST['ID'];
    $Date = $_POST['Date'];
    $Title = $_POST['Title'];
    $Description = $_POST['Description'];
	$Time = $_POST['Time'];
	$Link = $_POST['Link'];
	$Venue = $_POST['Venue'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE add_event SET Date = '$Date', Title = '$Title', Description = '$Description', Time = '$Time', Link = '$Link', Venue = '$Venue' WHERE ID = $ID");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Event successfully updated.";
 
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