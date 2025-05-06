package lkdcode.wanted.ecommerce;

import org.springframework.boot.SpringApplication;

public class TestEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.from(EcommerceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
