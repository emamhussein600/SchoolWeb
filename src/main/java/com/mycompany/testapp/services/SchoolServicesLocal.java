/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.mycompany.testapp.services;

import com.mycompany.entities.Classes;
import com.mycompany.entities.Student;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Emoooo
 */
@Local
public interface SchoolServicesLocal {
    
    //Class Entity CRUD
    List<Classes> getAllClass();
    void addClass(Classes newClass);
    public boolean classNameExists(String className,Integer excludeId);
    void removeClass(Classes removedClass);
    Classes findClassById(Integer classId);
    
    
    //Student CRUD
    
    List<Student> getAllStudents();
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Integer studentId);
    
}
