package ca.yorku.eecs3311.othello.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The GameCaretaker class manages the history of game states using the Memento design pattern.
 * It provides functionality to save, undo, redo, and persist game states to a file.
 */
public class GameCaretaker {
    private List<GameMemento> history = new ArrayList<>();
    private int currentIndex = -1;

    
    /**
     * Saves the given game state memento to the history.
     * If there are undone states, they are removed before saving.
     *
     * @param memento the game state to save
     */
    public void save(GameMemento memento) {
        if (currentIndex < history.size() - 1) {
            history = history.subList(0, currentIndex + 1);
        }
        history.add(memento);
        currentIndex++;
    }

    
    /**
     * Reverts to the previous game state in the history.
     *
     * @return the previous game state, or {@code null} if there is no previous state
     */
    public GameMemento undo() {
        if (currentIndex > 0) {
            return history.get(--currentIndex);
        }
        return null;
    }

    
    
    /**
     * Advances to the next game state in the history if available.
     *
     * @return the next game state, or {@code null} if there is no next state
     */
    public GameMemento redo() {
        if (currentIndex < history.size() - 1) {
            return history.get(++currentIndex);
        }
        return null;
    }

    
    /**
     * Saves the given game state memento to a file.
     *
     * @param memento  the game state to save
     * @param filename the file to save the game state to
     * @throws IOException if an I/O error occurs
     */
    public void saveGame(GameMemento memento, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(memento);
        }
    }

    
    /**
     * Loads a game state memento from a file.
     *
     * @param filename the file to load the game state from
     * @return the loaded game state memento
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    public GameMemento loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameMemento) ois.readObject();
        }
    }
}
