/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.cdis;

import com.microsoft.yoshio3.azuresql.entities.Employee;
import com.microsoft.yoshio3.azuresql.logic.EmployeeManager;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

/**
 *
 * @author tyoshio2002
 */
@Named(value = "delete")
@RequestScoped
public class DeleteEmployee implements Serializable{

    
    @EJB
    EmployeeManager empManage;

    private Long deleteEmpID;

    public String deleteEmployee() {
        empManage.deleteEmployee(deleteEmpID);
        return "index?faces-redirect=true";
    }
    
    private Employee deleteEmp ;


    public void onPageLoad() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        setDeleteEmpID((Long) flash.get("selectedEmployeeID"));
        deleteEmp = empManage.getEmployeeByID(getDeleteEmpID().toString());
        
        System.out.println("削除対象のID："+ deleteEmp.getEmpId());
    }

    /**
     * @return the deleteEmpID
     */
    public Long getDeleteEmpID() {
        return deleteEmpID;
    }

    /**
     * @param deleteEmpID the deleteEmpID to set
     */
    public void setDeleteEmpID(Long deleteEmpID) {
        this.deleteEmpID = deleteEmpID;
    }

    /**
     * @return the deleteEmp
     */
    public Employee getDeleteEmp() {
        return deleteEmp;
    }

    /**
     * @param deleteEmp the deleteEmp to set
     */
    public void setDeleteEmp(Employee deleteEmp) {
        this.deleteEmp = deleteEmp;
    }
    
    
}
