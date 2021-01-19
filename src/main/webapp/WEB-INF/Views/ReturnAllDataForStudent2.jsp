<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.MarksSheet" import="java.util.ArrayList"%>
<%ArrayList<MarksSheet> list =  (ArrayList<MarksSheet>)request.getAttribute("MS_DTO"); %>
<%String pages = (String)request.getAttribute("pages");%>
<%String sId = (String)request.getAttribute("StudentId");%>


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
    <a href="/returnDataForStudent"><input type= "button" value= "Return to Select Student Page"></a>
</p>
<p>
    <a href="/"><input type= "button" value= "Return to start page"></a>
</p>
</body>
<footer><%if(Integer.parseInt(pages) > 1){%>
    <%for(int i = 0; i < Integer.parseInt(pages); i++){%>
    <a href='/ReturnDataForStudent2?start=<%=i%>&student=<%=sId%>'><input type="button" value="<%=i + 1%>" style = "padding: 5px 7px"/></a>
    <%}}%>
</footer>
</html>
