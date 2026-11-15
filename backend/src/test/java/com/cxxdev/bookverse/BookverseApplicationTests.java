package com.cxxdev.bookverse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:contexto_teste;DB_CLOSE_DELAY=-1")
class BookverseApplicationTests {

    @Test
    void contextLoads() {
    }
}
