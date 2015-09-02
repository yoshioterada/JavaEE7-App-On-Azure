/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.cdis;
import com.microsoft.yoshio3.azuresql.entities.Employee;
import com.microsoft.yoshio3.azuresql.logic.EmployeeManager;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author tyoshio2002
 */
@ManagedBean(name  = "search")
@ViewScoped
public class SearchEmployeeManagedBean implements Serializable{

    private String searchString;

    private List<Employee> resultEmps;

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    

    /**
     * @return the resultEmps
     */
    public List<Employee> getResultEmps() {
        return resultEmps;
    }

    /**
     * @param resultEmps the resultEmps to set
     */
    public void setResultEmps(List<Employee> resultEmps) {
        this.resultEmps = resultEmps;
    }

    @EJB
    EmployeeManager empLogic;
   
    public void executeSearch() {
        String searchStr = searchString;
        System.out.println("入力：" + searchString);
        resultEmps = empLogic.getEmployeeByLikeName(searchStr);
    }
}
