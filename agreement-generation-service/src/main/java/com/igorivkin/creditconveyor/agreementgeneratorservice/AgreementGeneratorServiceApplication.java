package com.igorivkin.creditconveyor.agreementgeneratorservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@SpringBootApplication
public class AgreementGeneratorServiceApplication {

	public static void main(String[] args) {
		setupAgreementsDirectory();
		SpringApplication.run(AgreementGeneratorServiceApplication.class, args);
	}

	private static void setupAgreementsDirectory() {
		try {
			Path agreementsDirectoryPath = Paths.get("agreements");
			if (!Files.exists(agreementsDirectoryPath)) {
				Files.createDirectory(agreementsDirectoryPath);
			}
		} catch (IOException ex) {
			log.error("Cannot create directory for agreements, reason {}", ex.getMessage());
			throw new IllegalStateException("Cannot create directory for agreements by reason: " + ex.getMessage());
		}
	}

}
