package com.example.service;

import com.example.DTO.PlayerDTO;
import com.example.domain.Player;
import com.example.repository.PlayerRepository;
import com.example.security.ContextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlayerService {

	@Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public Player createNewPlayer(PlayerDTO playerDTO) {
        Player newPlayer = new Player();
        newPlayer.setUserName(playerDTO.getUserName());
        newPlayer.setPassword(new Argon2PasswordEncoder().encode(playerDTO.getPassword()));
        newPlayer.setEmail(playerDTO.getEmail());
        return playerRepository.save(newPlayer);
    }

    public Player getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return playerRepository.findOneByUserName(principal.getPlayer().getUserName());
    }

    public List<Player> listPlayers() {
        return (List<Player>) playerRepository.findAll();
    }

}

