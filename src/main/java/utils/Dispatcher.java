package utils;

import controllers.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher {
    public void Dispatch(String  path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        switch (path) {
            case "/":
                MainViewController mainViewController = new MainViewController();
                mainViewController.OpenPage(req, resp);
                break;
            case "/HELLO":
                HelloController helloController = new HelloController();
                helloController.OpenPage(req, resp);
                break;
            case "/MAIN":
                MainController mainController = new MainController();
                mainController.OpenPage(req, resp);
                break;
            case "/ADDNEWSTUDENT":
                AddNewStudentMainController addNewStudentMainController = new AddNewStudentMainController();
                addNewStudentMainController.OpenPage(req, resp);
                break;
            case "/ADDNEWSTUDENT2":
                AddNewStudentController addNewStudentController = new AddNewStudentController();
                addNewStudentController.OpenPage(req, resp);
                break;
            case "/ADDNEWDATATOSTUDENT":
                AddSubjectAndMarkToStudentMainController addSubjectAndMarkToStudentMainController =
                        new AddSubjectAndMarkToStudentMainController();
                addSubjectAndMarkToStudentMainController.OpenPage(req, resp);
                break;
            case "/ADDNEWDATATOSTUDENT2":
                AddSubjectAndMarkToStudentController addSubjectAndMarkToStudentController =
                        new AddSubjectAndMarkToStudentController();
                addSubjectAndMarkToStudentController.OpenPage(req, resp);
                break;
            case "/DELETESTUDENT":
                DeleteStudentController deleteStudentController = new DeleteStudentController();
                deleteStudentController.OpenPage(req, resp);
                break;
            case "/EDITSTUDENT":
                EditStudentMainController editStudentMainController = new EditStudentMainController();
                editStudentMainController.OpenPage(req, resp);
                break;
            case "/EDITSTUDENT2":
                EditStudentController editStudentController = new EditStudentController();
                editStudentController.OpenPage(req, resp);
                break;
            case "/EDITSUBJECTSERVLET":
                EditSubjectMainController editSubjectMainController = new EditSubjectMainController();
                editSubjectMainController.OpenPage(req, resp);
                break;
            case "/EDITSUBJECT2":
                EditSubjectController editSubjectController = new EditSubjectController();
                editSubjectController.OpenPage(req, resp);
                break;
            case "/STUDENTS":
                ReturnAllStudentsController returnAllStudentsController = new ReturnAllStudentsController();
                returnAllStudentsController.OpenPage(req, resp);
                break;
            case "/RETURNDATAFORSTUDENT2":
                ReturnAllDataForStudent2Controller returnAllDataForStudent2Controller =
                        new ReturnAllDataForStudent2Controller();
                returnAllDataForStudent2Controller.OpenPage(req, resp);
                break;
            case "/SUBJECTS":
                ReturnAllSubjectsController returnAllSubjectsController = new ReturnAllSubjectsController();
                returnAllSubjectsController.OpenPage(req, resp);
                break;
            case "/RETURNDATAFORSTUDENT":
                ReturnDataForOneStudentController returnDataForOneStudentController =
                        new ReturnDataForOneStudentController();
                returnDataForOneStudentController.OpenPage(req, resp);
                break;
            case "/DATA":
                ReturnAllDataController returnAllDataController = new ReturnAllDataController();
                returnAllDataController.OpenPage(req, resp);
                break;
            default:
                ErrorController errorController = new ErrorController();
                errorController.OpenPage(req, resp);
                break;
        }
    }
}
