package com.wimdeblauwe.examples.spendleaf;

import com.wimdeblauwe.examples.spendleaf.infrastructure.test.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class SpendleafApplicationTests {

	@Test
	void contextLoads() {
	}

}
