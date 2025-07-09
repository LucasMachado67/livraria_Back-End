package com.projetolivraria.livraria.model.user;

public record LoginRequestDTO(String token, String email, String name, String phone, String gender) {
}