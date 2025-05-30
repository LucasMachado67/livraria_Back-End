package com.projetolivraria.livraria.model.user;

import com.projetolivraria.livraria.model.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(@NotNull String name,@NotNull String email,@NotNull String password,@NotNull String phone,@NotNull String sex,@NotNull UserRole role) {
    
}
