/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package ManagedBean;

import com.mycompany.entities.Classes;
import com.mycompany.entities.Student;
import com.mycompany.testapp.services.SchoolServicesLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Emoooo
 */
@Named(value = "studentManagedBean")
@ViewScoped
public class StudentManagedBean implements Serializable {

    @EJB
    private SchoolServicesLocal schoolServicesLocal;

    private List<Student> studentList;
    private List<Classes> classList;

    private Student selectedStudent;

    @PostConstruct
    public void start() {
        studentList = schoolServicesLocal.getAllStudents();
        classList = schoolServicesLocal.getAllClass();
    }

    public void open() {
        selectedStudent = new Student();
    }

    public void saveStudent() {
        try {
            // Check if the student has a class
            if (selectedStudent == null || selectedStudent.getClassId() == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Class is required");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }

            // Save or update the student in the database
            if (selectedStudent.getStudentId() == null) {
                schoolServicesLocal.addStudent(selectedStudent);
                // Reload the student list
                studentList.add(selectedStudent);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student added successfully");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                schoolServicesLocal.updateStudent(selectedStudent);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student updated successfully");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }


            selectedStudent = new Student();  // Reset selectedStudent for new data entry

            // Close the dialog
            PrimeFaces.current().executeScript("PF('manageStudentDialog').hide()");

            // Update UI components
            PrimeFaces.current().ajax().update("form:messages", "form:dt-students", "dialogs:manage-student-content");
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save student");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
        public void deleteStudent(Student student) {
        try {
            if (student != null && student.getStudentId() != null) {
                schoolServicesLocal.deleteStudent(student.getStudentId());
                studentList.remove(student);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student deleted successfully");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid student data");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete student");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public List<Classes> getClassList() {
        return classList;
    }

    public void setClassList(List<Classes> classList) {
        this.classList = classList;
    }

}
