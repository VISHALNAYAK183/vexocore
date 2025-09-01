package com.vexocore.taskmanager.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity               
@Table(name = "users") 
public class User {
  
  @Id
  private Integer id;

  @Column(nullable = false, length = 100)
  private String name;

  
  @Column(nullable = false, length = 100)
  private String email;

   @Column(nullable = false, length = 100)
  private String password;
  private Integer age;
  private LocalDate dob;

 @Column(length = 10)
    private String gender;
  private LocalDateTime createdAt=LocalDateTime.now();
  private LocalDateTime updatedAt=LocalDateTime.now();

  
   public User() {}

    public User(Integer id, String name, String email, String password,
                Integer age, LocalDate dob, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
