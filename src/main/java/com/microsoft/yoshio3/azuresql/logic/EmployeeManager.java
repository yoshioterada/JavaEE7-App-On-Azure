/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.logic;

import com.microsoft.yoshio3.azuresql.entities.Employee;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author tyoshio2002
 */
@Stateless
public class EmployeeManager {

    @PersistenceContext(unitName = "AZURE_PU")
    EntityManager em;

    public void insertEmployee(Employee emp) {
        em.persist(emp);
    }

    public List<Employee> getEmployee(int page, int size) {
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findAll", Employee.class);
        query.setFirstResult(page);
        query.setMaxResults(size);

        return query.getResultList();
    }

    public List<Employee> getAllEmployee() {
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findAll", Employee.class);
        return query.getResultList();
    }

    public Long getNumberOfContents() {
        Long number = em.createNamedQuery("Employee.findAllCount", Long.class).getSingleResult();
        if (number == null) {
            number = 0L;
        }
        return number;
    }

    public Employee getEmployeeByID(String id) {
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findByEmpId", Employee.class);
        Long lid = Long.valueOf(id);
        query.setParameter("empId", lid);
        return query.getSingleResult();
    }

    public List<Employee> getEmployeeByLikeName(String name) {
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findByLikeName", Employee.class);
        query.setParameter("name", name + "%");
        List<Employee> emps = query.getResultList();
        return emps;
    }

    public List<Employee> getFilteredEmployeeByLikeName(String name, int page, int size) {
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findByLikeName", Employee.class);
        query.setParameter("name", name + "%");
        query.setFirstResult(page);
        query.setMaxResults(size);

        List<Employee> emps = query.getResultList();
        return emps;
    }

    public Long getNumberOfFindByLikeNameContents(String name) {
        TypedQuery<Long> query = em.createNamedQuery("Employee.findByLikeNameCount", Long.class);
        query.setParameter("name", name + "%");
        return query.getSingleResult();
    }

    public void registEmployee(Employee emp) {
        em.persist(emp);
    }

    public void deleteEmployee(Long empId) {
        Employee employee = em.find(Employee.class, empId);
        em.remove(employee);
    }

}
