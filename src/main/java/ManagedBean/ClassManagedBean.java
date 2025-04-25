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
@Named(value = "classManagedBean")
@ViewScoped
public class ClassManagedBean implements Serializable {

    @EJB
    private SchoolServicesLocal servicesLocal;
    private Classes selectedClass;
    private List<Classes> classList;

    @PostConstruct
    public void intiate() {
        classList = servicesLocal.getAllClass();
    }

    public void open() {
        selectedClass = new Classes();
    }

    public void saveClass() {
        if (selectedClass == null || selectedClass.getClassName() == null || selectedClass.getClassName().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class name is required"));
            PrimeFaces.current().ajax().update("form:messages");
            return;
        }

        boolean exists = servicesLocal.classNameExists(
                selectedClass.getClassName(),
                selectedClass.getClassId()
        );

        if (exists) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Already Exists"));
            PrimeFaces.current().ajax().update("form:messages");
            return;
        }

        boolean isNew = selectedClass.getClassId() == null;

        servicesLocal.addClass(selectedClass);

        if (isNew) {
            classList.add(selectedClass);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Added Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Updated"));
        }

        selectedClass = new Classes();
        PrimeFaces.current().executeScript("PF('manageClassDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-classes");
    }

    public void deleteClass() {
        if (!selectedClass.getStudentList().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class has students"));
            PrimeFaces.current().ajax().update("form:messages");
            return;
        }
        servicesLocal.removeClass(selectedClass);
        classList.remove(selectedClass);
        selectedClass = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-classes");
    }

    public void prepareToDelete(Classes c) {
        this.selectedClass = c;
        deleteClass();
    }

    public Classes getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(Classes selectedClass) {
        this.selectedClass = selectedClass;
    }

    public List<Classes> getClassList() {
        return classList;
    }

    public void setClassList(List<Classes> classList) {
        this.classList = classList;
    }

}
