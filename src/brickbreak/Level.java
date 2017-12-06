package brickbreak;

import java.io.Serializable;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import javafx.geometry.Pos;

public class Level extends GridPane {
    public static final String[] LEVELS = { "0", "1", "2" };
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private int wide; private int high;
    private int currentLevel;
    public Level(int w, int h) {
        wide = w; high = h;

        ColumnConstraints cc = new ColumnConstraints(w);
        for (int i = 0; i < COLS; i++) { this.getColumnConstraints().add(cc); }
        RowConstraints rc = new RowConstraints(h);
        for (int i = 0; i < ROWS; i++) { this.getRowConstraints().add(rc); }
    }
    public char[][] exportMap() {
        char[][] l = new char[ROWS][COLS];
        for (char[] r : l) Arrays.fill(r, '-'); // Blank map
        // I'll be honest, this stream was more for fun than anything
        getChildren().parallelStream()
            .forEach(e -> l[GridPane.getRowIndex(e)][GridPane.getColumnIndex(e)] = ((Brick) e).toChar()
        );
        return l;
    }
    public void importMap(char[][] l) {
        getChildren().clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Brick b = parseBrick(l[i][j]);
                if (b == null) continue;
                add(b,j,i);
            }
        }
    }
    public void loadLevel(int l) {
        char[][] map = new char[ROWS][COLS];
        try (BufferedReader lv = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("lvs/"+LEVELS[l])))) {
            for (int i = 0; i < ROWS; i++) {
                String row = lv.readLine();
                map[i] = row.substring(0,COLS).toCharArray();
            }
            importMap(map);
            currentLevel = l;
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }

    public char collide(Ball ball) {
        char power = ' ';
        ListIterator<Node> bricker = getChildren().listIterator();
        while (bricker.hasNext()) {
            Brick b = (Brick) bricker.next();
            if (b.localToScene(b.getBoundsInLocal()).intersects(
                    ball.getHitBox().localToScene(ball.getHitBox().getBoundsInLocal()))) {
                b.collide(ball);
                ball.increaseSpeed(0.1);
                if (b.isDestroyed()) {
                    if (b instanceof PowerBrick) power = ((PowerBrick) b).toChar();
                    bricker.remove();
                }
            }
        }
        return power;
    }

    public void setLevel(int l) { currentLevel = l; }
    public int getLevel() { return currentLevel; }
    public int advanceLevel() {
        if (!getChildren().isEmpty()) return 0;
        if (++currentLevel < LEVELS.length) {
            loadLevel(currentLevel);
            return 1;
        }
        return -1; // next level is outside boundary, win!
    }

    public void clearRow() {
        int rowIndex = getChildren().parallelStream()
            .mapToInt(e -> GridPane.getRowIndex(e))
            .max().getAsInt();
        ListIterator<Node> bricker = getChildren().listIterator();
        while (bricker.hasNext()) {
            Node b = bricker.next();
            if (GridPane.getRowIndex(b) == rowIndex) bricker.remove();
        }
    }

    public void damageAll() {
        ListIterator<Node> bricker = getChildren().listIterator();
        while (bricker.hasNext()) {
            Brick b = (Brick) bricker.next();
            b.damage(1);
            if (b.isDestroyed()) bricker.remove();
        }
    }

    private Brick parseBrick(char c) {
        if (Character.isDigit(c))
            return new Brick(wide, high, c - '0');
        else if (Character.isLetter(c))
            return new PowerBrick(wide, high, c);
        return null;
    }
}
