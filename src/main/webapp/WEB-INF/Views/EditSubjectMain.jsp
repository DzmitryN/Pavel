<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Subject" %>
<%@ page import="java.util.Map" %>
<% Subject subject =  (Subject) request.getAttribute("subjectDTO"); %>
<%String ifError = (String)request.getAttribute("errorPresent"); %>
<%Map<String, String> errors = (Map<String, String>)request.getAttribute("errors");%>

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
        <tr><td></td>
            <td style="font-size: xx-small">(Subject name cannot be empty, cannot contain digits or be less then three letters)</td></tr>
        <%try{%>
        <%if(ifError.equals("true") || errors.size() > 0){%>
        <td></td>
        <td style="color:red; font-size: small">
            <%if(errors.get("subject") != null){%>
            <%= errors.get("subject")%>
            <%}%>
        </td>
        <%}}catch(Exception e){}%>
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
