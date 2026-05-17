package cl.gamecenter.usuario.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_auth")
@Data
public class TokenAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UsuarioEntity usuario;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TipoToken tipo;

    private LocalDateTime expiracion;

    private Boolean usado;

    public enum TipoToken {
        acces,
        refresh,
        reset_password
    }
}
