<?php
$conn = mysqli_connect("localhost","root","","food_db");
if($conn){ echo "connected";}
else{echo "not connected";}
?>