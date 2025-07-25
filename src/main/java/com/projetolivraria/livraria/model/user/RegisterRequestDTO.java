package com.projetolivraria.livraria.model.user;

import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(@NotNull String name,@NotNull String email,@NotNull String password,@NotNull String phone,@NotNull String gender) {
    
}
