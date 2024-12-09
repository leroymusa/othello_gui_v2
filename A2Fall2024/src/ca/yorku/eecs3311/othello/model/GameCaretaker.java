package ca.yorku.eecs3311.othello.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameCaretaker {
    private List<GameMemento> history = new ArrayList<>();
    private int currentIndex = -1;

    public void save(GameMemento memento) {
        if (currentIndex < history.size() - 1) {
            history = history.subList(0, currentIndex + 1);
        }
        history.add(memento);
        currentIndex++;
    }

    public GameMemento undo() {
        if (currentIndex > 0) {
            return history.get(--currentIndex);
        }
        return null;
    }

    public GameMemento redo() {
        if (currentIndex < history.size() - 1) {
            return history.get(++currentIndex);
        }
        return null;
    }

    public void saveGame(GameMemento memento, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(memento);
        }
    }

    public GameMemento loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameMemento) ois.readObject();
        }
    }
}
