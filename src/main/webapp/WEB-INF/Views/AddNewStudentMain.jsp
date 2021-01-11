<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%String ifError = (String)request.getAttribute("errorPresent"); %>
<%Map<String, String> errors = (Map<String, String>)request.getAttribute("errors");%>

<html>
<head>
    <title>Add New Student Main page</title>
</head>
<body>
<h1>Add Student</h1>
<form action='addNewStudent2' method='POST'>
    <table>
        <tr>
            <td>
                First Name:
            </td>
            <td>
                <input type='text' name='First Name' placeholder="First name"/>
            </td>
        </tr>
        <tr><td></td>
            <td style="font-size: xx-small">(First name cannot be empty, cannot contain digits or be less then three letters)</td></tr>
        <%try{%>
        <%if(ifError.equals("true") || errors.size() > 0){%>
        <td></td>
        <td style="color:red; font-size: small">
            <%if(errors.get("firstname") != null){%>
            <%= errors.get("firstname")%>
            <%}%>
        </td>
        <%}}catch(Exception e){}%>
        <tr>
            <td>Second Name:</td><td><input type='text' name='Second Name' placeholder="Second name"/></td>
        </tr>
        <tr><td></td>
            <td style="font-size: xx-small">(Second name cannot be empty, cannot contain digits or be less then three letters)</td></tr>
        <%try{%>
        <%if(ifError.equals("true") || errors.size() > 0){%>
        <td></td>
        <td style="color:red; font-size: small">
            <%if(errors.get("secondname") !=null){%>
            <%= errors.get("secondname") %>
            <%}%>
        </td>
        <%}}catch(Exception e){}%>
        <tr>
            <td colspan='2'><input type='submit' value='Save New Student'/></td>
        </tr>
    </table>
</form>
<p>
    <a href="students"><input type= "button" value= "Return to previous page"></a>
</p>
</body>
</html>
