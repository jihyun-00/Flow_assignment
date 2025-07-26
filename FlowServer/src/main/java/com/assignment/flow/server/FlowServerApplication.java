package com.assignment.flow.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션 진입점
 * - 서버 실행 및 설정 자동화
 */
@SpringBootApplication
public class FlowServerApplication {

	// 서버 실행
	public static void main(String[] args) {
		SpringApplication.run(FlowServerApplication.class, args);
	}

}
