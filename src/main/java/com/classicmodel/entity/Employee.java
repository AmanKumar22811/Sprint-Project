package com.classicmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employeeNumber")
    @NotNull
    private Integer employeeNumber;

    @Column(name = "lastName")
    @NotBlank
    private String lastName;

    @Column(name = "firstName")
    @NotBlank
    private String firstName;

    @Column(name = "extension")
    @NotBlank
    private String extension;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "jobTitle")
    @NotBlank
    private String jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeCode")
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportsTo")
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employee> reportees = new ArrayList<>();

    @OneToMany(mappedBy = "salesRepEmployee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Customer> customers = new ArrayList<>();

    public Employee() {}

    public Integer getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(Integer employeeNumber) { this.employeeNumber = employeeNumber; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public Office getOffice() { return office; }
    public void setOffice(Office office) { this.office = office; }

    public Employee getManager() { return manager; }
    public void setManager(Employee manager) { this.manager = manager; }

    public List<Employee> getReportees() { return reportees; }
    public void setReportees(List<Employee> reportees) { this.reportees = reportees; }

    public List<Customer> getCustomers() { return customers; }
    public void setCustomers(List<Customer> customers) { this.customers = customers; }

    @Override
    public String toString() {
        return "Employee{employeeNumber=" + employeeNumber + ", lastName='" + lastName + "'}";
    }
}
