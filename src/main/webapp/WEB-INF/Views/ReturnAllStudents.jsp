<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Student" import="java.util.ArrayList"%>
<%ArrayList<Student> list =  (ArrayList<Student>)request.getAttribute("students"); %>
<%String pages = (String)request.getAttribute("pages");%>


<html>
<head>
    <title>Return All Students Page</title>
</head>
<body>

<h1>All Students</h1>
<table border='1' width='100%'>
    <tr>
        <th>Name</th>
        <th>Second Name</th>
        <th>Edit</th>
        <th>Delete</th></tr>
    <% for( Student student : list){%>
    <tr>
        <td><%= student.getFirstName() %></td>
        <td><%= student.getSecondName() %></td>
        <td><a href='editStudent?id=<%= student.getId() %>'>Edit</a></td>
        <td><a href='deleteStudent?id=<%= student.getId() %>'>Delete</a></td>
    </tr>
    <%}%>

</table>

<p>
    <a href="addNewStudent"><input type= "button" value= "Add new Student"></a>
</p>
<p>
    <a href="/"><input type= "button" value= "Return to start page"></a>
</p>

</body>
<footer>
    <%for(int i = 0; i < Integer.parseInt(pages); i++){%>
    <a href='/students?start=<%=i%>'><input type="button" value="<%=i + 1%>" style = "padding: 5px 7px"/></a>
    <%}%>
</footer>
</html>