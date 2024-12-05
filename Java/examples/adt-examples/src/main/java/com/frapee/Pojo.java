package com.frapee;

public class Pojo {
    private int number;
    private String name;

    // Constructor    
    public Pojo() {
        this.number = 0;
        this.name = "";
    }

    public Pojo(int number, String name) {
        this.number = number;
        this.name = name;
    }
    
    // Getters
    public int getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }
    
    // Setters
    public void setNumber(int num) {
        this.number = num;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number: ");
        sb.append(this.number);
        sb.append(", ");
        sb.append("Name: ");
        sb.append(this.name);
        return sb.toString();
    }
}
