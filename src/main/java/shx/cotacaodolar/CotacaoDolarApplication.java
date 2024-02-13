package shx.cotacaodolar;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class CotacaoDolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CotacaoDolarApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

        // Permitir todas as Origens de requisição
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");

        // Permitir métodos
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // Permitir cabeçalhos
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Access-Control-Request-Method");
        config.addAllowedHeader("Access-Control-Request-Headers");

        // Definir o tempo de vida da permissão
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}

}
