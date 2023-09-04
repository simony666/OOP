package util;


public abstract class Person {
    private String name;
    private int age;
    private Role role;
    
    public Person(){
        this.name = "";
        this.age = 0;
        this.role = Role.Customer;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    
}
