<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.MarksSheet" import="java.util.ArrayList"%>
<%ArrayList<MarksSheet> list =  (ArrayList<MarksSheet>)request.getAttribute("MS_DTO"); %>


<html>
<head>
    <title>ReturnAllDataForStudent2</title>
</head>
<body>
<h1>All data for selected Student</h1>
<table border='1' width='100%'>
    <tr>
        <th>First Name</th>
        <th>Second Name</th>
        <th>Subject</th>
        <th>Mark</th>
    </tr>
    <%for (MarksSheet markSheet : list){%>
    <tr>
        <td><%= markSheet.getFirstName()%></td>
        <td><%= markSheet.getSecondName() %></td>
        <td><%= markSheet.getSubject()%></td>
        <td><%= markSheet.getMark()%></td>
    </tr>
    <%}%>
</table>
<p>
    <a href="returnDataForStudent"><input type= "button" value= "Return to previous page"></a>
</p>
<p>
    <a href="/"><input type= "button" value= "Return to start page"></a>
</p>
</body>
</html>
