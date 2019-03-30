package com.duoxik.tasks.game2048;

public class MoveEfficiency implements Comparable<MoveEfficiency> {

    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        return (this.numberOfEmptyTiles != o.numberOfEmptyTiles)
                ? Integer.compare(this.numberOfEmptyTiles, o.numberOfEmptyTiles)
                : Integer.compare(this.score, o.score);

    }
}
