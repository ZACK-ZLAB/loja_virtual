package br.com.zlab.user.dtos;

import br.com.zlab.model.Acesso;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String login,@NotNull String senha, @NotNull Acesso acessos ) {
    
}
