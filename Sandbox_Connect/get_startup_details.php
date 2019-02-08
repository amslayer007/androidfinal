<?php
 
/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["ID"])) {
    $ID = $_GET['ID'];
 
    // get a product from products table
    $result = mysql_query("SELECT * FROM add_startup WHERE ID = $ID");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $startup = array();
            $startup["ID"] = $result["ID"];
            $startup["StartupName"] = $result["StartupName"];
            $startup["Description"] = $result["Description"];
			$startup["StartupFounder"] = $result["StartupFounder"];
			$startup["Website"] = $result["Website"];
            
            // success
            $response["success"] = 1;
 
            // user node
            $response["add_startup"] = array();
 
            array_push($response["add_startup"], $startup);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No startup found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No startup found";
 
        // echo no users JSON
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