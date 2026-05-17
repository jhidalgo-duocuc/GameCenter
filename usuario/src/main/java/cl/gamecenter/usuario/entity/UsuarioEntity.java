package cl.gamecenter.usuario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    private Long id;

    @ManyToOne
    private RolEntity rol;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String email;

    private String password;

    private String telefono;

    private Boolean activo;

    private LocalDateTime createdAt;
}
