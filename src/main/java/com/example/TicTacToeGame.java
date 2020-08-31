package com.example;


import com.example.domain.Player;
import com.example.repository.PlayerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;


@SpringBootApplication
public class TicTacToeGame {

	public static void main(String[] args) {
		SpringApplication.run(TicTacToeGame.class, args);
	}

    @Bean
    public CommandLineRunner demo(PlayerRepository playerRepository) {
        return args -> {
            playerRepository.save(new Player("george", "george@george.com", new Argon2PasswordEncoder().encode("george")));
            playerRepository.save(new Player("bnptest", "bnptest@bnptest.com",  new Argon2PasswordEncoder().encode("bnptest")));
        };
    }
    
}
