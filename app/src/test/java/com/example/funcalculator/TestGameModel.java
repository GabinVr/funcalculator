package com.example.funcalculator;

import com.example.funcalculator.model.game.GameModel;
import com.example.funcalculator.model.pair.Pair;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import android.util.Log;

import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class TestGameModel {
    private static MockedStatic<Log> logMockedStatic;

    @BeforeClass
    public static void setUp() {
        logMockedStatic = mockStatic(Log.class);
        logMockedStatic.when(() -> Log.d(anyString(), anyString())).thenAnswer(invocation -> {
            System.out.println(Arrays.toString(invocation.getArguments()));
            return null;
        });
    }

    @AfterClass
    public static void tearDown() {
        if (logMockedStatic != null) {
            logMockedStatic.close();
        }
    }

    @Test
    public void testGameModel() {
        GameModel gameModel = new GameModel("2+2=4");
        gameModel.add('2');
        gameModel.add('+');
        gameModel.add('2');
        gameModel.add('=');
        gameModel.add('4');
        assertTrue(gameModel.isMathematicallyCorrect());
    }

    @Test
    public void testGameModel2() {
        GameModel gameModel = new GameModel("2+2=4");
        gameModel.add('2');
        gameModel.add('+');
        gameModel.add('2');
        gameModel.add('=');
        assertFalse(gameModel.isMathematicallyCorrect());
    }

    @Test
    public void testGameModel3() {
        GameModel gameModel = new GameModel("2+2=4");
        gameModel.add('2');
        gameModel.add('+');
        gameModel.add('2');
        gameModel.add('=');
        gameModel.add('4');
        gameModel.add('4');
        gameModel.add('7');
        assertTrue(gameModel.isMathematicallyCorrect());
    }

    @Test
    public void testIsValid() {
        GameModel gameModel = new GameModel("2+2=4");
        gameModel.add('2');
        gameModel.add('+');
        gameModel.add('2');
        gameModel.add('=');
        gameModel.add('4');
        assertTrue(gameModel.isMathematicallyCorrect());
        assertTrue(gameModel.isValid());
    }

    @Test
    public void testGetStates() {
        GameModel gameModel = new GameModel("3+3=6");
        gameModel.add('3');
        gameModel.add('+');
        gameModel.add('3');
        gameModel.add('=');
        gameModel.add('6');
        assertTrue(gameModel.isMathematicallyCorrect());
        assertTrue(gameModel.isValid());
        gameModel.validate();
        assertTrue(gameModel.isWon());
        assertTrue(gameModel.isGameFinished());
        List<List<Pair>> states = gameModel.getStates();
        List<Pair> row = states.get(0);
        assertEquals(5, row.size());
        assertEquals(1, row.get(0).getColor());
        assertEquals('3', row.get(0).getContent().charValue());
        assertEquals(1, row.get(1).getColor());
        assertEquals('+', row.get(1).getContent().charValue());
        assertEquals(1, row.get(2).getColor());
        assertEquals('3', row.get(2).getContent().charValue());
        assertEquals(1, row.get(3).getColor());
        assertEquals('=', row.get(3).getContent().charValue());
        assertEquals(1, row.get(4).getColor());
        assertEquals('6', row.get(4).getContent().charValue());
    }

    @Test
    public void testGetStates2() {
        GameModel gameModel = new GameModel("3+3=6");
        gameModel.add('3');
        gameModel.add('+');
        gameModel.add('3');
        gameModel.add('=');
        gameModel.add('6');
        gameModel.add('7');
        assertTrue(gameModel.isMathematicallyCorrect());
        assertTrue(gameModel.isValid());
        gameModel.validate();
        assertTrue(gameModel.isWon());
        assertTrue(gameModel.isGameFinished());
        List<List<Pair>> states = gameModel.getStates();
        List<Pair> row = states.get(0);
        assertEquals(5, row.size());
        assertEquals(1, row.get(0).getColor());
        assertEquals('3', row.get(0).getContent().charValue());
        assertEquals(1, row.get(1).getColor());
        assertEquals('+', row.get(1).getContent().charValue());
        assertEquals(1, row.get(2).getColor());
        assertEquals('3', row.get(2).getContent().charValue());
        assertEquals(1, row.get(3).getColor());
        assertEquals('=', row.get(3).getContent().charValue());
        assertEquals(1, row.get(4).getColor());
        assertEquals('6', row.get(4).getContent().charValue());
    }

    @Test
    public void testGetStates3() {
        GameModel gameModel = new GameModel("3+3=6");
        gameModel.add('3');
        gameModel.add('+');
        gameModel.add('6');
        gameModel.add('=');
        gameModel.add('3');
        assertFalse(gameModel.isValid());
        assertFalse(gameModel.isMathematicallyCorrect());
        gameModel.validate();
        assertFalse(gameModel.isWon());
        assertFalse(gameModel.isGameFinished());
        List<List<Pair>> states = gameModel.getStates();
        List<Pair> row = states.get(0);
        assertEquals(5, row.size());
        assertEquals(0, row.get(0).getColor());
        assertEquals('3', row.get(0).getContent().charValue());
        assertEquals(0, row.get(1).getColor());
        assertEquals('+', row.get(1).getContent().charValue());
        assertEquals(0, row.get(2).getColor());
        assertEquals('6', row.get(2).getContent().charValue());
        assertEquals(0, row.get(3).getColor());
        assertEquals('=', row.get(3).getContent().charValue());
        assertEquals(0, row.get(4).getColor());
        assertEquals('3', row.get(4).getContent().charValue());
    }

    @Test
    public void testGetStates4() {
        GameModel gameModel = new GameModel("3+3=6");
        gameModel.add('3');
        gameModel.add('+');
        gameModel.add('5');
        gameModel.add('=');
        gameModel.add('8');
        gameModel.add('3');
        gameModel.validate();
        assertFalse(gameModel.isWon());
        assertFalse(gameModel.isMathematicallyCorrect());
        int nbTries = gameModel.getNbTries();
        assertEquals( 1, nbTries);
        gameModel.validate();
        assertEquals(nbTries, gameModel.getNbTries());
        gameModel.add('3');
        gameModel.add('+');
        gameModel.add('3');
        gameModel.add('=');
        gameModel.add('6');
        gameModel.validate();
        assertTrue(gameModel.isWon());
        assertTrue(gameModel.isGameFinished());
        assertEquals(gameModel.getNbTries(), nbTries + 1);
    }

    @Test
    public void testGetStates5() {
        GameModel gameModel = new GameModel("3+3=6");
        for (int i = 0; i < 10; i++) {
            gameModel.add('3');
            gameModel.add('+');
            gameModel.add('4');
            gameModel.add('=');
            gameModel.add('7');
            if ( i < 4 ) {
                assertTrue(gameModel.isMathematicallyCorrect());
            }
            gameModel.validate();
        }
        assertTrue(gameModel.isGameFinished());
        assertFalse(gameModel.isWon());
    }

    @Test
    public void testGetStates6() {
        GameModel gameModel = new GameModel("3+3=6");
        for (int i = 0; i < 3; i++) {
            gameModel.add('3');
            gameModel.add('+');
            gameModel.add('4');
            gameModel.add('=');
            gameModel.add('7');
            gameModel.validate();
        }
        gameModel.add('3');
        gameModel.add('+');
        gameModel.add('3');
        gameModel.add('=');
        gameModel.add('6');
        gameModel.validate();
        assertTrue(gameModel.isGameFinished());
        assertTrue(gameModel.isWon());
    }

    @Test
    public void testAdd() {
        GameModel gameModel = new GameModel("3+3=6");
        gameModel.add('3');
        List<List<Pair>> states = gameModel.getStates();
        List<Pair> row = states.get(0);
        assertEquals(0, row.get(0).getColor());
        assertEquals('3', row.get(0).getContent().charValue());
        gameModel.add('+');
        states = gameModel.getStates();
        row = states.get(0);
        assertEquals(0, row.get(0).getColor());
        assertEquals('3', row.get(0).getContent().charValue());
        assertEquals(0, row.get(1).getColor());
        assertEquals('+', row.get(1).getContent().charValue());
        gameModel.add('3');
        gameModel.add('=');
        gameModel.add('6');
        gameModel.add('7');
        
    }
}
