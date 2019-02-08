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
$result = mysql_query("SELECT * FROM add_startup") or die(mysql_error());
//echo mysql_num_rows($result);
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["add_startup"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $startup = array();
        $startup["ID"] = $row["ID"];
        $startup["StartupName"] = $row["StartupName"];
		$startup["Description"] = $row["Description"];
		$startup["StartupFounder"] = $row["StartupFounder"];
		$startup["Website"] = $row["Website"];
       
 
        // push single product into final response array
        array_push($response["add_startup"], $startup);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No startup found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
