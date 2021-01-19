package utils;

import controllers.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher {
    public void dispatch(String  path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        switch (path) {
            case "/":
                MainViewController mainViewController = new MainViewController();
                mainViewController.openPage(req, resp);
                break;
            case "/HELLO":
                HelloController helloController = new HelloController();
                helloController.openPage(req, resp);
                break;
            case "/ADDNEWSTUDENT":
                AddNewStudentMainController addNewStudentMainController = new AddNewStudentMainController();
                addNewStudentMainController.openPage(req, resp);
                break;
            case "/ADDNEWSTUDENT2":
                AddNewStudentController addNewStudentController = new AddNewStudentController();
                addNewStudentController.openPage(req, resp);
                break;
            case "/ADDNEWDATATOSTUDENT":
                AddSubjectAndMarkToStudentMainController addSubjectAndMarkToStudentMainController =
                        new AddSubjectAndMarkToStudentMainController();
                addSubjectAndMarkToStudentMainController.openPage(req, resp);
                break;
            case "/ADDNEWDATATOSTUDENT2":
                AddSubjectAndMarkToStudentController addSubjectAndMarkToStudentController =
                        new AddSubjectAndMarkToStudentController();
                addSubjectAndMarkToStudentController.openPage(req, resp);
                break;
            case "/DELETESTUDENT":
                DeleteStudentController deleteStudentController = new DeleteStudentController();
                deleteStudentController.openPage(req, resp);
                break;
            case "/EDITSTUDENT":
                EditStudentMainController editStudentMainController = new EditStudentMainController();
                editStudentMainController.openPage(req, resp);
                break;
            case "/EDITSTUDENT2":
                EditStudentController editStudentController = new EditStudentController();
                editStudentController.openPage(req, resp);
                break;
            case "/EDITSUBJECTSERVLET":
                EditSubjectMainController editSubjectMainController = new EditSubjectMainController();
                editSubjectMainController.openPage(req, resp);
                break;
            case "/EDITSUBJECT2":
                EditSubjectController editSubjectController = new EditSubjectController();
                editSubjectController.openPage(req, resp);
                break;
            case "/STUDENTS":
                ReturnAllStudentsController returnAllStudentsController = new ReturnAllStudentsController();
                returnAllStudentsController.openPage(req, resp);
                break;
            case "/RETURNDATAFORSTUDENT2":
                ReturnAllDataForStudent2Controller returnAllDataForStudent2Controller =
                        new ReturnAllDataForStudent2Controller();
                returnAllDataForStudent2Controller.openPage(req, resp);
                break;
            case "/SUBJECTS":
                ReturnAllSubjectsController returnAllSubjectsController = new ReturnAllSubjectsController();
                returnAllSubjectsController.openPage(req, resp);
                break;
            case "/RETURNDATAFORSTUDENT":
                ReturnDataForOneStudentController returnDataForOneStudentController =
                        new ReturnDataForOneStudentController();
                returnDataForOneStudentController.openPage(req, resp);
                break;
            case "/DATA":
                ReturnAllDataController returnAllDataController = new ReturnAllDataController();
                returnAllDataController.openPage(req, resp);
                break;
            default:
                ErrorController errorController = new ErrorController();
                errorController.openPage(req, resp);
                break;
        }
    }
}
