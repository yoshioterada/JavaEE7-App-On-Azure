/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.cdis;


import com.microsoft.yoshio3.azuresql.entities.Employee;
import com.microsoft.yoshio3.azuresql.logic.EmployeeManager;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author tyoshio2002
 */
@Named(value = "create")
@ConversationScoped
public class CreateEmployeeManagedBean implements Serializable {

    @Inject
    private Conversation conversation;

    private String name;
    private Date birthDate;
    private String prefecture;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the prefecture
     */
    public String getPrefecture() {
        return prefecture;
    }

    /**
     * @param prefecture the prefecture to set
     */
    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String confirmCreateEmployee() {
      if (conversation.isTransient())
      {
          conversation.begin();
      }
        
        
        return "confirmCreateEmployee";
    }
    
    @EJB
    EmployeeManager empManage;

    public String createEmployee() {
        Employee regEmp = new Employee();
        regEmp.setPrefecture(prefecture);
        regEmp.setCity(city);
        regEmp.setAddress(address);
        regEmp.setBirthDate(birthDate);
        regEmp.setEmail(email);
        regEmp.setName(name);
        regEmp.setPhoneNumber(phoneNumber);
        regEmp.setDescription("ダミーデーターです");

        empManage.registEmployee(regEmp);


      if (conversation.isTransient())
      {
          conversation.end();
      }
        return "completeCreateEmployee";
    }
}
