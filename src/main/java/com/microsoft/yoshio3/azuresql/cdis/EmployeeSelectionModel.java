/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.cdis;

import com.microsoft.yoshio3.azuresql.entities.Employee;
import com.microsoft.yoshio3.azuresql.logic.EmployeeManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author tyoshio2002
 */
public class EmployeeSelectionModel extends LazyDataModel<Employee> {

    EmployeeManager empManage;
    List<Employee> showEmpls;

    EmployeeSelectionModel(EmployeeManager empManage) {
        this.empManage = empManage;
        setPageSize(20);
        setRowCount(empManage.getNumberOfContents().intValue());
    }

    @Override
    public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        if (!filters.isEmpty()) {
            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                try {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);
                    String name = filterValue.toString();
                    
                    this.showEmpls = empManage.getFilteredEmployeeByLikeName(name, first, pageSize);
                    setRowCount(empManage.getNumberOfFindByLikeNameContents(name).intValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("**************first：" + first + "\tpageSize:" + pageSize);
            this.showEmpls = empManage.getEmployee(first, pageSize);
        }
        return this.showEmpls;
    }

    @Override
    public Object getRowKey(Employee employee) {
        System.out.println("GET ROW KEY is called:" + employee.getEmpId().toString());
        return employee.getEmpId().toString();
    }

    @Override
    public Employee getRowData(String rowKey) {
        System.out.println("GET ROW DATA INPUT DATA: " + rowKey);

        return empManage.getEmployeeByID(rowKey);
    }
}
