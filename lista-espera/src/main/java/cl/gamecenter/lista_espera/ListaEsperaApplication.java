package cl.gamecenter.lista_espera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ListaEsperaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaEsperaApplication.class, args);
	}

}
