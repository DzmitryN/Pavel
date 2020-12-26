<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Student" %>
<% Student student =  (Student)request.getAttribute("studentDTO"); %>

<html>
<head>
    <title>Edit Student Main page</title>
</head>
<body>
<h1>Update Student</h1>
<form action='editStudent2' method='POST'>
<table>
    <tr><td></td><td><input type='hidden' name='id' value='<%= student.getId() %>'/></td></tr>
    <tr><td>Name:</td><td><input type='text' name='First Name' value='<%= student.getFirstName() %>'/></td></tr>
    <tr><td>Second Name:</td><td><input type='text' name='Second Name' value='<%= student.getSecondName() %>'/></td></tr>
    <tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>
</table>
</form>

<p>
    <a href="students"><input type= "button" value= "Press for return to previous page"></a>
</p>
</body>
</html>
