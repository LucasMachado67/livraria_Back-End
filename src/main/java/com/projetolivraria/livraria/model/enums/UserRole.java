package com.projetolivraria.livraria.model.enums;

//Class enum to create a role for User or Admin
public enum UserRole {

    ADMIN("admin"),
    USER("user");
    private final String role;

    UserRole (String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
