<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT * FROM add_mentor") or die(mysql_error());
//echo mysql_num_rows($result);
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["add_mentor"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $mentor = array();
        $mentor["ID"] = $row["ID"];
        $mentor["Name"] = $row["Name"];
        $mentor["Designation"] = $row["Designation"];
		$mentor["URL"] = $row["URL"];
       
 
        // push single product into final response array
        array_push($response["add_mentor"], $mentor);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No mentors found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>