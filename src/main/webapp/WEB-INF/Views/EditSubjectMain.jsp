<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Subject" %>
<%@ page import="java.util.Map" import="java.util.ArrayList" %>
<% Subject subject =  (Subject) request.getAttribute("subjectDTO"); %>
<%Map<String, ArrayList<String>> errors = (Map<String, ArrayList<String>>)request.getAttribute("errors");%>

<html>
<head>
    <title>Edit Subject Page</title>
</head>
<body>
<h1>Update Student</h1>
<form action='/editSubject2' method='POST'>
    <table>
        <tr>
            <td></td><td><input type='hidden' name='id' value='<%= subject.getId()%>'/></td>
        </tr>
        <tr>
            <td>Name:</td><td><input <%if(errors != null && errors.size() > 0 && errors.get("subject") != null && errors.get("subject").size() > 0){%>
                style="border-color: red"
                <%}%> type='text' name='Subject' value='<%= subject.getSubject()%>'/></td>
        </tr>
        <tr><td></td>
            <td style="font-size: xx-small">(Subject name cannot be empty, cannot contain digits or be less then three letters)</td></tr>
        <%if(errors != null && errors.size() > 0){%>
        <td></td>
        <td style="color:red; font-size: small">
            <%if(errors.get("subject") != null && errors.get("subject").size() > 0){%>
                <%ArrayList<String> list = errors.get("subject"); %>
                    <%for(String errorMessage : list) {%>
                <%= errorMessage%><br>
            <%}%>
            <%}%>
        </td>
        <%}%>
        <tr>
            <td colspan='2'><input type='submit' value='Edit & Save'/></td>
        </tr>
    </table>
</form>
<p>
    <a href="/subjects"><input type= "button" value= "Return to previous page"></a>
</p>

</body>
</html>
