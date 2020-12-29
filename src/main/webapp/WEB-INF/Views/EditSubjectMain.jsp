<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Subject" %>
<% Subject subject =  (Subject) request.getAttribute("subjectDTO"); %>
<html>
<head>
    <title>Edit Subject Page</title>
</head>
<body>
<h1>Update Student</h1>
<form action='editSubject2' method='POST'>
    <table>
        <tr>
            <td></td><td><input type='hidden' name='id' value='<%= subject.getId()%>'/></td>
        </tr>
        <tr>
            <td>Name:</td><td><input type='text' name='Subject' value='<%= subject.getSubject()%>'/></td>
        </tr>
        <tr>
            <td colspan='2'><input type='submit' value='Edit & Save'/></td>
        </tr>
    </table>
</form>
<p>
    <a href="subjects"><input type= "button" value= "Return to previous page"></a>
</p>

</body>
</html>
