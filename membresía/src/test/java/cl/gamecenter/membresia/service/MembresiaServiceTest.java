package cl.gamecenter.membresia.service;

import cl.gamecenter.membresia.dto.MembresiaRequestDTO;
import cl.gamecenter.membresia.dto.MembresiaResponseDTO;
import cl.gamecenter.membresia.entity.MembresiaEntity;
import cl.gamecenter.membresia.entity.TipoMembresiaEntity;
import cl.gamecenter.membresia.repository.MembresiaRepository;
import cl.gamecenter.membresia.repository.TipoMembresiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembresiaServiceTest {

    @Mock
    private MembresiaRepository membresiaRepository;

    @Mock
    private TipoMembresiaRepository tipoMembresiaRepository;

    @InjectMocks
    private MembresiaService membresiaService;

    private MembresiaRequestDTO requestDTO;
    private TipoMembresiaEntity tipoMembresia;
    private MembresiaEntity entidadGuardada;

    @BeforeEach
    void setUp() {
        requestDTO = new MembresiaRequestDTO();
        requestDTO.setUsuarioId(1L);
        requestDTO.setTipoMembresiaId(2L);

        tipoMembresia = new TipoMembresiaEntity();
        tipoMembresia.setId(2L);
        tipoMembresia.setNombre("Plan Gamer");
        tipoMembresia.setPrecioMensual(new BigDecimal("15000.00"));
        tipoMembresia.setHorasIncluidas(20);
        tipoMembresia.setDescuentoPct(new BigDecimal("10.00"));
        tipoMembresia.setActivo(true);

        entidadGuardada = new MembresiaEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setUsuarioId(1L);
        entidadGuardada.setTipoMembresia(tipoMembresia);
        entidadGuardada.setEstado(MembresiaEntity.EstadoMembresia.ACTIVA);
        entidadGuardada.setFechaInicio(LocalDate.now());
        entidadGuardada.setFechaFin(LocalDate.now().plusMonths(1));
        entidadGuardada.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("contratar() - debe contratar membresía cuando usuario no tiene una activa")
    void contratar_debeContratarMembresia_cuandoUsuarioSinMembresiaActiva() {
        when(membresiaRepository.existsByUsuarioIdAndEstado(1L, MembresiaEntity.EstadoMembresia.ACTIVA))
                .thenReturn(false);
        when(tipoMembresiaRepository.findById(2L)).thenReturn(Optional.of(tipoMembresia));
        when(membresiaRepository.save(any(MembresiaEntity.class))).thenReturn(entidadGuardada);

        MembresiaResponseDTO resultado = membresiaService.contratar(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("ACTIVA");
        assertThat(resultado.getTipoMembresiaNombre()).isEqualTo("Plan Gamer");
        verify(membresiaRepository).save(any(MembresiaEntity.class));
    }

    @Test
    @DisplayName("contratar() - debe lanzar excepción cuando usuario ya tiene membresía activa")
    void contratar_debeLanzarExcepcion_cuandoUsuarioYaTieneMembresiaActiva() {
        when(membresiaRepository.existsByUsuarioIdAndEstado(1L, MembresiaEntity.EstadoMembresia.ACTIVA))
                .thenReturn(true);

        assertThatThrownBy(() -> membresiaService.contratar(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ya tiene una membresía activa");

        verify(membresiaRepository, never()).save(any());
    }

    @Test
    @DisplayName("contratar() - debe lanzar excepción cuando plan no existe")
    void contratar_debeLanzarExcepcion_cuandoPlanNoExiste() {
        when(membresiaRepository.existsByUsuarioIdAndEstado(1L, MembresiaEntity.EstadoMembresia.ACTIVA))
                .thenReturn(false);
        when(tipoMembresiaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> membresiaService.contratar(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Plan no encontrado");

        verify(membresiaRepository, never()).save(any());
    }

    @Test
    @DisplayName("contratar() - debe lanzar excepción cuando plan no está disponible")
    void contratar_debeLanzarExcepcion_cuandoPlanNoDisponible() {
        tipoMembresia.setActivo(false);
        when(membresiaRepository.existsByUsuarioIdAndEstado(1L, MembresiaEntity.EstadoMembresia.ACTIVA))
                .thenReturn(false);
        when(tipoMembresiaRepository.findById(2L)).thenReturn(Optional.of(tipoMembresia));

        assertThatThrownBy(() -> membresiaService.contratar(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está disponible");

        verify(membresiaRepository, never()).save(any());
    }

    @Test
    @DisplayName("buscarPorId() - debe retornar membresía cuando existe")
    void buscarPorId_debeRetornarMembresia_cuandoExiste() {
        when(membresiaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        MembresiaResponseDTO resultado = membresiaService.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("cancelar() - debe cancelar membresía activa")
    void cancelar_debeCancelarMembresiaActiva() {
        when(membresiaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));
        entidadGuardada.setEstado(MembresiaEntity.EstadoMembresia.CANCELADA);
        when(membresiaRepository.save(any(MembresiaEntity.class))).thenReturn(entidadGuardada);

        MembresiaResponseDTO resultado = membresiaService.cancelar(1L);

        assertThat(resultado.getEstado()).isEqualTo("CANCELADA");
        verify(membresiaRepository).save(any(MembresiaEntity.class));
    }

    @Test
    @DisplayName("cancelar() - debe lanzar excepción cuando membresía no está activa")
    void cancelar_debeLanzarExcepcion_cuandoMembresiaNoActiva() {
        entidadGuardada.setEstado(MembresiaEntity.EstadoMembresia.CANCELADA);
        when(membresiaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        assertThatThrownBy(() -> membresiaService.cancelar(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Solo se puede cancelar una membresía activa");
    }

    @Test
    @DisplayName("listarPorUsuario() - debe retornar membresías del usuario")
    void listarPorUsuario_debeRetornarMembresiasDelUsuario() {
        when(membresiaRepository.findByUsuarioId(1L)).thenReturn(List.of(entidadGuardada));

        List<MembresiaResponseDTO> resultado = membresiaService.listarPorUsuario(1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUsuarioId()).isEqualTo(1L);
    }
}
