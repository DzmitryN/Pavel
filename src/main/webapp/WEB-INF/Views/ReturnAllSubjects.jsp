<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Subject" import="java.util.ArrayList"%>
<%ArrayList<Subject> list =  (ArrayList<Subject>)request.getAttribute("subjects"); %>


<html>
<head>
    <title>Return All Subjects page</title>
</head>
<body>
<h1>All Subjects</h1>
<table border='1' width='100%'>
    <tr><th>Id</th><th>Subject</th><th>Edit</th></tr>
    <% for( Subject subject : list){%>
    <tr>
        <td><%= subject.getId() %></td>
        <td><%= subject.getSubject() %></td>
        <td><a href='EditSubjectServlet?id=<%=subject.getId()%>'>edit</a></td>
    </tr>
    <%}%>
</table>
<p><a href="/"><input type= "button" value= "Press for return to start page"></a></p>
</body>
</html>
