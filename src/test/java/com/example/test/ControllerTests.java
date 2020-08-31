package com.example.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.example.controller.GameController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.DTO.GameDTO;
import com.example.DTO.MoveDTO;
import com.example.domain.Game;
import com.example.domain.Move;
import com.example.domain.Player;
import com.example.enums.Piece;
import com.example.service.GameService;
import com.example.service.MoveService;
import com.example.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes =  {GameController.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ControllerTests {

    private Logger logger = LoggerFactory.getLogger(ControllerTests.class);

    @MockBean
    private PlayerService playerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private MoveService moveService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRetrieveGameMoves() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("gameId", 1L);

        when(gameService.getGame(1L)).thenReturn(new Game());
        when(moveService.getMovesInGame(any())).thenReturn(new ArrayList<>(Arrays.asList(new MoveDTO(new Date(), "COMPUTER", Piece.X), new MoveDTO(new Date(), "george", Piece.O))));

        MvcResult result = mockMvc.perform(get("/game/list").session(session)).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void testCreateGame() throws Exception {
        MockHttpSession session = new MockHttpSession();

        final Player mockPlayer = mock(Player.class);
        final Game mockGame = new Game();
        mockGame.setId(1L);
        final GameDTO gameDto = new GameDTO(1, Piece.O);
        final Move move = new Move();
        move.setBoardColumn(1);
        move.setBoardRow(1);
        move.setCreated(new Date());

        when(playerService.getLoggedUser()).thenReturn(mockPlayer);
        when(gameService.createNewGame(eq(mockPlayer), any(GameDTO.class))).thenReturn(mockGame);
        when(gameService.getGame(1L)).thenReturn(mockGame);
        when(moveService.autoCreateMove(mockGame)).thenReturn(move);

        mockMvc.perform(post("/game/create").content(new ObjectMapper().writeValueAsString(gameDto)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).session(session)).andDo(print()).andExpect(status().isOk()).andReturn();
    }

}
