/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


/**
 *
 * @author Zy
 */
public class Artist {
    enum Role{
        ADMIN,
        CUSTOMER,
        ARTIST
    }
    
    // non-static field
    private int id;
    private String name;
    private int age;
    private Role role;
    
    // static field
    private static int nextId = 1;
    
    // no arg constructor
    public Artist(){
    };
    
    // Constructor with 4 parameters
    public Artist(String name, int age, Role role){
        this.id = nextId;
        this.name = name;
        this.age = age;
        this.role = role;
        // Increment nextId for the next artist
        nextId++;
    };  
    
    // getter / Accessors for id, name, age, and role
    public int getId(){
        return id;
    }
    
    
    public String getName(){
        return name;
    }
    
    public int getAge(){
        
        return age;
    }
    
    public Role getRole(){
        return role;
    }
    
    public static int getNextId(){
        return nextId;
    }
    
    
    // setter / Mutuator for id, name, age, and role
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setAge(int age){
         this.age = age;
    }
   
}
