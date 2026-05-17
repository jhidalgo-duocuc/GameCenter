package cl.gamecenter.pago.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromocionClientDTO {

    private Long id;
    private Boolean activo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
