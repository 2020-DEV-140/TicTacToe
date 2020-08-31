package com.example.security;

import java.util.Arrays;
import java.util.HashSet;

import com.example.domain.Player;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class ContextUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 5331154965323479727L;
	private final Player player;

    public ContextUser(Player player) {
        super(player.getUserName(), player.getPassword(), true, true, true, true, new HashSet<>(Arrays.asList(new SimpleGrantedAuthority("create"))));
        this.player = player;
    }

    public Player getPlayer() {
        return  player;
    }
}
