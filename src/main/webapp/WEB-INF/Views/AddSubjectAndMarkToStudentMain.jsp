<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entities.Student" import="entities.Student" import="entities.Subject" %>
<%@ page import="entities.Mark" %>
<%ArrayList<Student> students =  (ArrayList<Student>)request.getAttribute("studentsList"); %>
<%ArrayList<Subject> subjects =  (ArrayList<Subject>)request.getAttribute("subjectList"); %>
<%ArrayList<Mark> marks =  (ArrayList<Mark>)request.getAttribute("marksList"); %>


<html>
<head>
    <title>AddSubjectAndMarkToStudentMain</title>
</head>
<body>
<h1>Add Subject And Mark For The Student</h1>
<form action='/addNewDataToStudent2' method='POST'>
    <table>
        <tr>
            <td>Student:</td>
            <td>
                <select name='student' style='width:150px'>
                    <%for (Student student : students) {%>
                    <option value=<%= student.getId()%>> <%= student.getFirstName()%> <%= student.getSecondName()%> </option>
                    <%}%>
                </select>
            </td>
        </tr>
        <tr>
            <td>Subject:</td>
            <td>
                <select name='subject' style='width:150px'>
                    <%for (Subject subject : subjects){%>
                    <option value=<%= subject.getId()%> > <%= subject.getSubject()%> </option>
                    <%}%>
                </select>
            </td>
        </tr>
        <tr>
            <td>Mark:</td>
            <td>
                <select name='mark' style='width:150px'>
                    <%for (Mark mark : marks){%>
                    <option value=<%= mark.getID()%>> <%= mark.getMark()%> </option>
                    <%}%>
                </select>
            </td>
        </tr>
    </table>
    <p>
        <tr>
            <td colspan='2'><input type='submit' value='Save Changes'/></td>
        </tr>
    </p>
</form>
<p>
    <a href=/data><input type= button value='Return to previous page'></a>
</p>

</body>
</html>
