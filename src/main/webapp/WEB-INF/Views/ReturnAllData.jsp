<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.MarksSheet" import="java.util.ArrayList"%>
<%ArrayList<MarksSheet> marksSheets =  (ArrayList<MarksSheet>)request.getAttribute("marks_sheetDTO"); %>
<%String pages = (String)request.getAttribute("pages");%>

<html>
<head>
    <title>Return All Data page</title>
</head>
<body>
<h1>Consolidated View</h1>
<table border='1' width='100%'>
    <tr>
        <th>First Name</th>
        <th>Second Name</th>
        <th>Subject</th>
        <th>Mark</th>
    </tr>
    <% for (MarksSheet marksSheet: marksSheets) { %>
    <tr>
        <td><%= marksSheet.getFirstName()%></td>
        <td><%= marksSheet.getSecondName()%></td>
        <td><%= marksSheet.getSubject()%></td>
        <td><%= marksSheet.getMark() %></td></tr>
    <%}%>
</table>
<p>
    <a href="/returnDataForStudent"><input type= "button" value= "Get All Data for the Student"></a>
</p>
<p>
    <a href="/addNewDataToStudent"><input type= "button" value= "Add new subject and mark to the Student"></a>
</p>
<p>
    <a href="/"><input type= "button" value= "Return to start page"></a>
</p>
</body>
<footer>
    <%for(int i = 0; i < Integer.parseInt(pages); i++){%>
    <a href='/data?start=<%=i%>'><input type="button" value="<%=i + 1%>" style = "padding: 5px 7px"/></a>
    <%}%>
</footer>
</html>
