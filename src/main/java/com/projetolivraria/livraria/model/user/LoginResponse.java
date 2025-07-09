package com.projetolivraria.livraria.model.user;

public class LoginResponse {

    private final String token;
    private final String email;
    private final String name;
    private final String phone;
    private final String gender;

    public LoginResponse(String token, String email, String name, String phone, String gender) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
    }

    // Getters e setters
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }
}
