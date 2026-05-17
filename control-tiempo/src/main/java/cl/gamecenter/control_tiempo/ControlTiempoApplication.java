package cl.gamecenter.control_tiempo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ControlTiempoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlTiempoApplication.class, args);
	}

}
