package com.example.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.DTO.GameDTO;
import com.example.DTO.HumanMoveDTO;
import com.example.domain.Game;
import com.example.domain.Move;
import com.example.domain.Player;
import com.example.repository.GameRepository;
import com.example.repository.MoveRepository;
import com.example.repository.PlayerRepository;
import com.example.service.GameService;
import com.example.service.MoveService;
import com.example.service.PlayerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import static org.mockito.BDDMockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTests {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MoveRepository moveRepository;

    @InjectMocks
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;

    @InjectMocks
    private MoveService moveService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFindAllPlayersThenReturnPlayerList() {
        Player newPlayer = new Player("alex", "alex@alex.com", "alexpasswd");

        List<Player> expectedProducts = Arrays.asList(newPlayer);

        when(playerRepository.findAll()).thenReturn(expectedProducts);

        List<Player> actualProducts = playerService.listPlayers();

        assertThat(actualProducts).isEqualTo(expectedProducts);
    }

    @Test
    public void testGameCreation() {
        when(gameRepository.save(any(Game.class))).thenReturn(new Game());
        assertNotNull(gameService.createNewGame(new Player(), new GameDTO()));
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    public void testMoveCreation() {
        Move move = new Move();
        move.setId(3);
        when(moveRepository.save(any(Move.class))).thenReturn(move);
        assertEquals(moveService.createMove(new Game(), new Player(), new HumanMoveDTO(1,1)).getId(), 3);
    }

}
