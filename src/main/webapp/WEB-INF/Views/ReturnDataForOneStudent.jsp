<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Student" import="java.util.ArrayList"%>
<%ArrayList<Student> list =  (ArrayList<Student>)request.getAttribute("students"); %>

<html>
<head>
    <title>Return Data For One Student page</title>
</head>
<body>
<h1>Return all data for selected Student</h1>
<form action='returnDataForStudent2' method='POST'>
    <table>
        <tr>
            <td>Student:</td>
            <td>
                <select name='student' style='width:150px'>
                    <% for( Student student : list){%>
                    <option value='<%= student.getId()%>'><%= student.getFirstName()%> <%= student.getSecondName() %></option>
                    <%}%>
                </select>
            </td>
        </tr>
    </table>

    <p>
        <tr>
            <td colspan='2'><input type='submit' value='Get All Data for Selected Student'/></td>
        </tr>
    </p>
</form>
<p>
    <a href='/data'><input type= 'button' value= 'Return to previous page'></a>
</p>
</body>
</html>
