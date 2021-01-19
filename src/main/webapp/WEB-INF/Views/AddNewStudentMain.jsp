<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" import="java.util.ArrayList"%>
<%Map<String, ArrayList<String>> errors = (Map<String, ArrayList<String>>)request.getAttribute("errors");%>

<html>
<head>
    <title>Add New Student Main page</title>
</head>
<body>
<h1>Add Student</h1>
<form action='/addNewStudent2' method='POST'>
    <table>
        <tr>
            <td>
                First Name:
            </td>
            <td>
                <input <%if(errors != null && errors.size() > 0 && errors.get("firstName") != null && errors.get("firstName").size() > 0){%>
                    style="border-color: red"
                <%}%>type='text' name='First Name' placeholder="First name"/>
            </td>
        </tr>
        <tr><td></td>
            <td style="font-size: xx-small">(First name cannot be empty, cannot contain digits or be less then three letters)</td></tr>
        <%if(errors != null && errors.size() > 0){%>
        <tr>
        <td></td>
        <td style="color:red; font-size: small">
            <%if(errors.get("firstName") != null && errors.get("firstName").size() > 0){%>
                <%ArrayList<String> list = errors.get("firstName"); %>
                    <%for(String errorMessage : list) {%>
                        <%= errorMessage%><br>
                    <%}%>
            <%}%>
        </td>
        </tr>
        <%}%>
        <tr>
            <td>Second Name:</td><td><input <%if(errors != null && errors.size() > 0 && errors.get("secondName") !=null && errors.get("secondName").size() > 0){%>
                style="border-color: red"
                <%}%> type='text' name='Second Name' placeholder="Second name"/></td>
        </tr>
        <tr><td></td>
            <td style="font-size: xx-small">(Second name cannot be empty, cannot contain digits or be less then three letters)</td></tr>
        <%if(errors != null && errors.size() > 0){%>
        <tr>
        <td></td>
        <td style="color:red; font-size: small">
            <%if(errors.get("secondName") !=null && errors.get("secondName").size() > 0){%>
            <%ArrayList<String> list = errors.get("secondName"); %>
                <%for(String errorMessage : list) {%>
                    <%= errorMessage%><br>
                <%}%>
            <%}%>
        </td>
        </tr>
        <%}%>
        <tr>
            <td colspan='2'><input type='submit' value='Save New Student'/></td>
        </tr>
    </table>
</form>
<p>
    <a href="/students"><input type= "button" value= "Return to previous page"></a>
</p>
</body>
</html>
