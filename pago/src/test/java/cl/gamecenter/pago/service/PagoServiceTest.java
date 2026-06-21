package cl.gamecenter.pago.service;

import cl.gamecenter.pago.client.MembresiaClient;
import cl.gamecenter.pago.client.PromocionClient;
import cl.gamecenter.pago.client.SesionClient;
import cl.gamecenter.pago.client.UsuarioClient;
import cl.gamecenter.pago.dto.*;
import cl.gamecenter.pago.entity.PagoEntity;
import cl.gamecenter.pago.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock private PagoRepository pagoRepository;
    @Mock private UsuarioClient usuarioClient;
    @Mock private SesionClient sesionClient;
    @Mock private MembresiaClient membresiaClient;
    @Mock private PromocionClient promocionClient;

    @InjectMocks
    private PagoService pagoService;

    private PagoRequestDTO requestDTO;
    private PagoEntity entidadGuardada;
    private UsuarioClientDTO usuarioActivo;

    @BeforeEach
    void setUp() {
        // Request base — pago de sesión sin promoción
        requestDTO = new PagoRequestDTO();
        requestDTO.setUsuarioId(1L);
        requestDTO.setTipo("SESION");
        requestDTO.setSesionId(10L);
        requestDTO.setMembresiaId(null);
        requestDTO.setPromocionId(null);
        requestDTO.setMontoBruto(new BigDecimal("3000.00"));
        requestDTO.setDescuentoAplicado(new BigDecimal("0.00"));
        requestDTO.setMontoFinal(new BigDecimal("3000.00"));
        requestDTO.setMetodoPago("EFECTIVO");
        requestDTO.setEstado("COMPLETADO");
        requestDTO.setReferenciaExterna("REF-001");
        requestDTO.setFechaPago(LocalDateTime.now());

        // Usuario activo simulado
        usuarioActivo = new UsuarioClientDTO();
        usuarioActivo.setId(1L);
        usuarioActivo.setActivo(true);

        // Entidad guardada simulada
        entidadGuardada = new PagoEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setUsuarioId(1L);
        entidadGuardada.setTipo(PagoEntity.TipoPago.SESION);
        entidadGuardada.setSesionId(10L);
        entidadGuardada.setMontoBruto(new BigDecimal("3000.00"));
        entidadGuardada.setDescuentoAplicado(new BigDecimal("0.00"));
        entidadGuardada.setMontoFinal(new BigDecimal("3000.00"));
        entidadGuardada.setMetodoPago(PagoEntity.MetodoPago.EFECTIVO);
        entidadGuardada.setEstado(PagoEntity.EstadoPago.COMPLETADO);
        entidadGuardada.setReferenciaExterna("REF-001");
        entidadGuardada.setFechaPago(LocalDateTime.now());
    }
    // TESTS: crear()
    @Test
    @DisplayName("crear() - debe crear pago cuando usuario activo y sesión válida")
    void crear_debeCrearPago_cuandoUsuarioActivoYSesionValida() {
        SesionClientDTO sesion = new SesionClientDTO();
        sesion.setId(10L);
        sesion.setUsuarioId(1L);
        sesion.setEstado("CERRADA");

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(sesionClient.buscarPorId(10L)).thenReturn(sesion);
        when(pagoRepository.save(any(PagoEntity.class))).thenReturn(entidadGuardada);

        PagoResponseDTO resultado = pagoService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getUsuarioId()).isEqualTo(1L);
        assertThat(resultado.getTipo()).isEqualTo("SESION");
        verify(pagoRepository).save(any(PagoEntity.class));
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando usuario no está activo")
    void crear_debeLanzarExcepcion_cuandoUsuarioNoActivo() {
        // GIVEN
        usuarioActivo.setActivo(false);
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);

        // WHEN & THEN
        assertThatThrownBy(() -> pagoService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está activo");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando sesión no pertenece al usuario")
    void crear_debeLanzarExcepcion_cuandoSesionNoPertenecAUsuario() {
        SesionClientDTO sesionDeOtroUsuario = new SesionClientDTO();
        sesionDeOtroUsuario.setId(10L);
        sesionDeOtroUsuario.setUsuarioId(99L); // usuario diferente
        sesionDeOtroUsuario.setEstado("CERRADA");

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(sesionClient.buscarPorId(10L)).thenReturn(sesionDeOtroUsuario);

        assertThatThrownBy(() -> pagoService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no pertenece al usuario");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando membresía no pertenece al usuario")
    void crear_debeLanzarExcepcion_cuandoMembresiaNoPertenecAUsuario() {
        requestDTO.setSesionId(null);
        requestDTO.setMembresiaId(5L);
        requestDTO.setTipo("MEMBRESIA");

        MembresiaClientDTO membresiaDeOtro = new MembresiaClientDTO();
        membresiaDeOtro.setId(5L);
        membresiaDeOtro.setUsuarioId(99L); // usuario diferente

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(membresiaClient.buscarPorId(5L)).thenReturn(membresiaDeOtro);

        assertThatThrownBy(() -> pagoService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no pertenece al usuario");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando promoción no está activa")
    void crear_debeLanzarExcepcion_cuandoPromocionNoActiva() {
        requestDTO.setPromocionId(3L);

        SesionClientDTO sesion = new SesionClientDTO();
        sesion.setId(10L);
        sesion.setUsuarioId(1L);

        PromocionClientDTO promocionInactiva = new PromocionClientDTO();
        promocionInactiva.setId(3L);
        promocionInactiva.setActivo(false);
        promocionInactiva.setFechaInicio(LocalDate.now().minusDays(10));
        promocionInactiva.setFechaFin(LocalDate.now().plusDays(10));

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(sesionClient.buscarPorId(10L)).thenReturn(sesion);
        when(promocionClient.buscarPorId(3L)).thenReturn(promocionInactiva);

        assertThatThrownBy(() -> pagoService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está activa");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando promoción no está vigente")
    void crear_debeLanzarExcepcion_cuandoPromocionNoVigente() {
        requestDTO.setPromocionId(3L);

        SesionClientDTO sesion = new SesionClientDTO();
        sesion.setId(10L);
        sesion.setUsuarioId(1L);

        PromocionClientDTO promocionVencida = new PromocionClientDTO();
        promocionVencida.setId(3L);
        promocionVencida.setActivo(true);
        promocionVencida.setFechaInicio(LocalDate.now().minusDays(30));
        promocionVencida.setFechaFin(LocalDate.now().minusDays(1)); // ya venció

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(sesionClient.buscarPorId(10L)).thenReturn(sesion);
        when(promocionClient.buscarPorId(3L)).thenReturn(promocionVencida);

        assertThatThrownBy(() -> pagoService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está vigente");

        verify(pagoRepository, never()).save(any());
    }

    // TESTS: obtenerPorId()

    @Test
    @DisplayName("obtenerPorId() - debe retornar pago cuando existe")
    void obtenerPorId_debeRetornarPago_cuandoExiste() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        PagoResponseDTO resultado = pagoService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("COMPLETADO");
    }

    @Test
    @DisplayName("obtenerPorId() - debe lanzar excepción cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_cuandoNoExiste() {
        // GIVEN
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThatThrownBy(() -> pagoService.obtenerPorId(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Pago no encontrado");
    }

    // TESTS: listar()

    @Test
    @DisplayName("listar() - debe retornar todos los pagos")
    void listar_debeRetornarTodosLosPagos() {
        when(pagoRepository.findAll()).thenReturn(List.of(entidadGuardada));

        List<PagoResponseDTO> resultado = pagoService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    // TESTS: eliminar()

    @Test
    @DisplayName("eliminar() - debe eliminar pago cuando existe")
    void eliminar_debeEliminarPago_cuandoExiste() {
        when(pagoRepository.existsById(1L)).thenReturn(true);

        pagoService.eliminar(1L);

        verify(pagoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() - debe lanzar excepción cuando pago no existe")
    void eliminar_debeLanzarExcepcion_cuandoNoExiste() {
        when(pagoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> pagoService.eliminar(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Pago no encontrado");

        verify(pagoRepository, never()).deleteById(any());
    }
}