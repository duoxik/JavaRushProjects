package com.duoxik.tasks.game2048;

import java.util.*;

public class Model {

    private final static int FIELD_WIDTH = 4;

    private Tile[][] gameTiles;
    private Stack<Tile[][]> previousStates;
    private Stack<Integer> previousScores;
    private boolean isSaveNeeded;

    public int score;
    public int maxTile;

    public Model() {
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        this.previousStates = new Stack<>();
        this.previousScores = new Stack<>();
        this.isSaveNeeded = true;
        this.score = 0;
        this.maxTile = 0;
        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public List<Tile> getEmptyTiles() {
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++)
            for (int j = 0; j < FIELD_WIDTH; j++)
                if (gameTiles[i][j].isEmpty())
                    result.add(gameTiles[i][j]);

        return result;
    }

    public void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            Tile randomTile = emptyTiles.get((int) (Math.random() * emptyTiles.size()));
            randomTile.value = (Math.random() < 0.9 ? 2 : 4);
        }
    }

    public void resetGameTiles() {
        for (int i = 0; i < FIELD_WIDTH; i++)
            for (int j = 0; j < FIELD_WIDTH; j++)
                gameTiles[i][j] = new Tile();

        addTile();
        addTile();
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean isChanged = false;
        int j = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value != 0) {
                if (i != j) isChanged = true;
                tiles[j++].value = tiles[i].value;
            }
        }

        for (; j < tiles.length; j++)
            tiles[j].value = 0;

        return isChanged;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isChanged = false;
        int j = 0;
        for (int i = 0; i < tiles.length; j++, i++) {

            if (tiles[i].value == 0) break;

            if (i + 1 != tiles.length && tiles[i].value == tiles[i + 1].value) {
                tiles[j].value = 2 * tiles[i++].value;
                isChanged = true;
                if (maxTile < tiles[j].value)
                    maxTile = tiles[j].value;
                score += tiles[j].value;
            } else {
                tiles[j].value = tiles[i].value;
            }
        }

        for (; j < tiles.length; j++)
            tiles[j].value = 0;

        return isChanged;
    }

    public void left() {

        if (isSaveNeeded)
            saveState(gameTiles);
        boolean isNeedToAddTile = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            isNeedToAddTile |= compressTiles(gameTiles[i]);
            isNeedToAddTile |= mergeTiles(gameTiles[i]);
        }

        if (isNeedToAddTile) {
            addTile();
            isSaveNeeded = true;
        }
    }

    public void right() {

        saveState(gameTiles);
        rotateField();
        rotateField();
        left();
        rotateField();
        rotateField();
    }

    public void up() {

        saveState(gameTiles);
        rotateField();
        rotateField();
        rotateField();
        left();
        rotateField();
    }

    public void down() {

        saveState(gameTiles);
        rotateField();
        left();
        rotateField();
        rotateField();
        rotateField();
    }

    public boolean canMove() {
        for (int count = 0; count < 4; count++) {
            for (int i = 0; i < FIELD_WIDTH; i++) {
                if (isMergeAvailable(gameTiles[i]))
                    return true;
                if (isCompressAvailable(gameTiles[i]))
                    return true;
            }
            rotateField();
        }
        return false;
    }

    public void rollback() {
        if (!previousStates.isEmpty() && !previousScores.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    public void randomMove() {

        switch ((int) (Math.random() * 4)) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }
    }

    public boolean hasBoardChanged() {

        if (previousStates.isEmpty())
            return true;

        Tile[][] previousGameTiles = previousStates.peek();
        int previousSumTiles = 0;
        int sumTiles = 0;
        for (int i = 0; i < FIELD_WIDTH; i++)
            for (int j = 0; j < FIELD_WIDTH; j++) {
                previousSumTiles += previousGameTiles[i][j].value;
                sumTiles += gameTiles[i][j].value;
            }

        return (previousSumTiles != sumTiles) ? true : false;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {

        move.move();
        boolean boardChanged = hasBoardChanged();
        int numberOfEmptyTiles = (boardChanged) ? getEmptyTiles().size() : -1;
        int score = (boardChanged) ? this.score : 0;
        MoveEfficiency moveEfficiency
                = new MoveEfficiency(numberOfEmptyTiles, score, move);
        rollback();
        return moveEfficiency;
    }


    public void autoMove() {

        PriorityQueue<MoveEfficiency> priorityQueue
                = new PriorityQueue<>(4, Collections.reverseOrder());

        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                left();
            }
        }));

        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                up();
            }
        }));

        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                right();
            }
        }));

        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                down();
            }
        }));

        priorityQueue.peek().getMove().move();
    }

    private void saveState(Tile[][] field) {
        Tile[][] tiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++)
            for (int j = 0; j < FIELD_WIDTH; j++)
                tiles[i][j] = new Tile(field[i][j].value);
        previousStates.push(tiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    private boolean isMergeAvailable(Tile[] tiles) {

        for (int i = 0; i < tiles.length; i++) {
            if (i + 1 < tiles.length &&
                    tiles[i].value == tiles[i + 1].value) {
                return true;
            }
        }
        return false;
    }

    private boolean isCompressAvailable(Tile[] tiles) {

        boolean zeroContains = false;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value == 0)
                zeroContains = true;

            if (zeroContains && tiles[i].value != 0)
                return true;
        }
        return false;
    }

    private void rotateField() {
        for (int i = 0; i < FIELD_WIDTH / 2; i++) {
            for(int j = i; j < FIELD_WIDTH - 1 - i; j++) {
                int value = gameTiles[i][j].value;
                gameTiles[i][j].value = gameTiles[FIELD_WIDTH - j -1][i].value;
                gameTiles[FIELD_WIDTH - j - 1][i].value
                        = gameTiles[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1].value;
                gameTiles[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1].value
                        = gameTiles[j][FIELD_WIDTH - i - 1].value;
                gameTiles[j][FIELD_WIDTH - i - 1].value = value;
            }
        }
    }
}
