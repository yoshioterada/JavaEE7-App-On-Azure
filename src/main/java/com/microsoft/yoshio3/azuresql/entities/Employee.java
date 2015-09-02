/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "dbo", name = "EMPLOYEE")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e ORDER BY e.empId ASC"),
    @NamedQuery(name = "Employee.findAllCount", query = "SELECT COUNT(e) FROM Employee e"),
    @NamedQuery(name = "Employee.findByEmpId", query = "SELECT e FROM Employee e WHERE e.empId = :empId ORDER BY e.empId ASC"),
    @NamedQuery(name = "Employee.findByLikeName", query = "SELECT e FROM Employee e WHERE e.name LIKE :name ORDER BY e.empId ASC"),
    @NamedQuery(name = "Employee.findByLikeNameCount", query = "SELECT COUNT(e) FROM Employee e WHERE e.name LIKE :name"),
    
    @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name ORDER BY e.empId ASC"),
    @NamedQuery(name = "Employee.findByAddress", query = "SELECT e FROM Employee e WHERE e.address = :address"),
    @NamedQuery(name = "Employee.findByPhoneNumber", query = "SELECT e FROM Employee e WHERE e.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Employee.findByCity", query = "SELECT e FROM Employee e WHERE e.city = :city"),
    @NamedQuery(name = "Employee.findByPrefecture", query = "SELECT e FROM Employee e WHERE e.prefecture = :prefecture"),
    @NamedQuery(name = "Employee.findByDescription", query = "SELECT e FROM Employee e WHERE e.description = :description")})
public class Employee implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "seqgen", sequenceName = "APP_USERS_SEQ", allocationSize=100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqgen")
    @Column(name = "EMP_ID", nullable = false)
    private Long empId;

    @Size(max = 20)
    @Column(name = "NAME", columnDefinition = "NVARCHAR(20)")
    private String name;

    @Size(max = 30)
    @Column(name = "ADDRESS", columnDefinition = "NVARCHAR(30)")
    private String address;

    @Size(max = 20)
    @Column(name = "PHONE_NUMBER", columnDefinition = "NVARCHAR(20)")
    private String phoneNumber;

    @Size(max = 10)
    @Column(name = "CITY", columnDefinition = "NVARCHAR(10)")
    private String city;

    @Size(max = 10)
    @Column(name = "PREFECTURE", columnDefinition = "NVARCHAR(10)")
    private String prefecture;

    @Basic(optional = false)
    @Size(min = 1, max = 80)
    @Column(name = "DESCRIPTION", columnDefinition = "NVARCHAR(80)")
    private String description;


    @NotNull
    @Column(name = "BIRTHDATE")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Size(max = 255)
    @Column(name = "EMAIL", columnDefinition = "NVARCHAR(255)")
    private String email;
    

    public Employee() {
    }

    public Employee(Long empId) {
        this.empId = empId;
    }

    public Employee(Long empId, String description) {
        this.empId = empId;
        this.description = description;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.yoshio3.javaee6.weblogic.on.azure.entity.Employee[ empId=" + empId + " ]";
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

}
