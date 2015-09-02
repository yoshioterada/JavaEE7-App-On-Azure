/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.cdis;


import com.microsoft.yoshio3.azuresql.entities.Employee;
import com.microsoft.yoshio3.azuresql.logic.EmployeeManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author tyoshio2002
 */
@Named
@ConversationScoped
public class IndexManagedBean implements Serializable {

    @Inject
    Conversation conversation;

    private boolean buttonDisabled = true;

    private String input1;

    /**
     * @return the input1
     */
    public String getInput1() {
        return input1;
    }

    /**
     * @param input1 the input1 to set
     */
    public void setInput1(String input1) {
        this.input1 = input1;
    }

    private List<Employee> employees;

    private LazyDataModel<Employee> model;

    private Employee selectedEmployee;

    @EJB
    EmployeeManager empManage;

    @PostConstruct
    public void onPageLoad() {
        this.setModel(new EmployeeSelectionModel(empManage));
        if (this.conversation.isTransient()) {
            conversation.begin();
        }
    }

    @PreDestroy
    public void onPreDestroy() {
        if (this.conversation.isTransient()) {
            conversation.end();
        }
        System.out.println("DESTROY HAD CALLED");
    }

    public void onRowSelect(SelectEvent event) {
        System.out.println("onRowSelect is called");
        Employee emp = (Employee) event.getObject();

        selectedEmployee = emp;
        buttonDisabled = false;
        System.out.println("選択された人" + emp.getName());
    }

    /**
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * @param employees the employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * @return the model
     */
    public LazyDataModel<Employee> getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(LazyDataModel<Employee> model) {
        this.model = model;
    }

    /**
     * @return the selectedEmployee
     */
    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    /**
     * @param selectedEmployee the selectedEmployee to set
     */
    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }


    public String createDummyData2() {
        FileSystem fileSystem = FileSystems.getDefault();
        Path data = fileSystem.getPath("/Users/tyoshio2002/NetBeansProjects/OLD/CreateDummyDataTOJavaDVB/data.txt");
        try (BufferedReader reader = Files.newBufferedReader(data, Charset.forName("UTF-8"))) {
            String username;
            int i = 0;

            while ((username = reader.readLine()) != null) {
                int amari = i % 3;
                int phone = 1000 + i;

                Employee customer = new Employee();
                switch (amari) {
                    case 0:
                        customer.setPrefecture("東京都");
                        customer.setCity("港区");
                        customer.setAddress("北青山 2-5-8-" + i);
                        customer.setBirthDate(new Date());
                        customer.setEmail("Hanako" + i + "Tokyo@Oracle.com");
                        customer.setName(username);
                        customer.setPhoneNumber("03-1234-" + phone);
                        customer.setDescription("ダミーデータです");
                        break;
                    case 1:
                        customer.setPrefecture("大阪府");
                        customer.setCity("大阪市北区");
                        customer.setAddress("堂島2-4-27" + i);
                        customer.setBirthDate(new Date());
                        customer.setEmail("Taro" + i + "Osaka@Oracle.com");
                        customer.setName(username);
                        customer.setPhoneNumber("06-6442-" + phone);
                        customer.setDescription("ダミーデータです");
                        break;
                    case 2:
                        customer.setPrefecture("北海道");
                        customer.setCity("札幌市中央区");
                        customer.setAddress("北一条西4-1-2" + i);
                        customer.setBirthDate(new Date());
                        customer.setEmail("Yoshio" + i + "Sapporo@Oracle.com");
                        customer.setName(username);
                        customer.setPhoneNumber("011-252" + phone);
                        customer.setDescription("ダミーデータです");
                        break;
                    default:
                        break;
                }
                empManage.insertEmployee(customer);
                i++;

                System.out.println(username);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }                    
        return "next";
    }

    /**
     * @return the buttonDisabled
     */
    public boolean isButtonDisabled() {
        return buttonDisabled;
    }

    /**
     * @param buttonDisabled the buttonDisabled to set
     */
    public void setButtonDisabled(boolean buttonDisabled) {
        this.buttonDisabled = buttonDisabled;
    }

    public String modifyEmployee() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("selectedEmployee", selectedEmployee);

        return "modifyEmployee?faces-redirect=true";
    }

    public String deleteEmployee() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("selectedEmployeeID", selectedEmployee.getEmpId());
        if (this.conversation.isTransient()) {
            conversation.end();
        }
        selectedEmployee = null;
        return "deleteEmployee?faces-redirect=true";
    }


}
