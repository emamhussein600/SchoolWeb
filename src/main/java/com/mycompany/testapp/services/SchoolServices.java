/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.mycompany.testapp.services;

import com.mycompany.entities.Classes;
import com.mycompany.entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import static org.primefaces.component.graphicimage.GraphicImageBase.PropertyKeys.name;

/**
 *
 * @author Emoooo
 */
@Stateless
public class SchoolServices implements SchoolServicesLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager entityManager;

    @Override
    public List<Classes> getAllClass() {
        return entityManager.createNamedQuery("Classes.findAll", Classes.class).getResultList();
    }

    @Override
    public void addClass(Classes newClass) {
        if (newClass.getClassId() == null) {
            entityManager.persist(newClass);
        } else {
            entityManager.merge(newClass);
        }
    }

    @Override
    public boolean classNameExists(String name, Integer excludeId) {
        String jpql;
        TypedQuery<Long> query;

        if (excludeId == null) {
            jpql = "SELECT COUNT(c) FROM Classes c WHERE c.className = :name";
            query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("name", name);
        } else {
            jpql = "SELECT COUNT(c) FROM Classes c WHERE c.className = :name AND c.classId <> :id";
            query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("name", name);
            query.setParameter("id", excludeId);
        }

        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public void removeClass(Classes removedClass) {
        removedClass = entityManager.merge(removedClass);
        entityManager.remove(removedClass);
    }

    @Override
    public Classes findClassById(Integer classId) {
        return entityManager.find(Classes.class, classId);
    }

    @Override
    public List<Student> getAllStudents() {
        return entityManager.createNamedQuery("Student.findAll", Student.class).getResultList();
    }

    @Override
    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void updateStudent(Student student) {
        entityManager.merge(student);
    }
    @Override
    public void deleteStudent(Integer studentId) {
    try {
        Student student = entityManager.find(Student.class, studentId);
        if (student != null) {
            entityManager.remove(student);
        }
    } catch (Exception e) {
        // Handle exception
    }
}

}
