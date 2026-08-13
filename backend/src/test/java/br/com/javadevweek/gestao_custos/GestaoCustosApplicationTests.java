package br.com.javadevweek.gestao_custos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import backend.GestaoCustosApplication;

@SpringBootTest(
		classes = GestaoCustosApplication.class,
		properties = "spring.datasource.url=jdbc:h2:mem:contexto_teste;DB_CLOSE_DELAY=-1")
class GestaoCustosApplicationTests {

	@Test
	void contextLoads() {
	}

}
