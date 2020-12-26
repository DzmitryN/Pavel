
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Student Main page</title>
</head>
<body>
<h1>Add Student</h1>
<form action='addNewStudent2' method='POST'>
    <table>
        <tr><td>First Name:</td><td><input type='text' name='First Name'/></td></tr>
        <tr><td>Second Name:</td><td><input type='text' name='Second Name'/></td></tr>
        <tr><td colspan='2'><input type='submit' value='Save New Student'/></td></tr>
    </table>
</form>
<p>
    <a href="students"><input type= "button" value= "Press for return to previous page"></a>
</p>
</body>
</html>
