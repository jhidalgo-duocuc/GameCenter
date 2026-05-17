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
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TipoToken tipo;

    @Column(name = "expira_en")
    private LocalDateTime expiracion;

    private Boolean usado;

    public enum TipoToken {
        ACCESS,
        REFRESH,
        RESET_PASSWORD
    }
}
