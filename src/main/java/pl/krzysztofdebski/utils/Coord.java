package pl.krzysztofdebski.utils;

import pl.krzysztofdebski.utils.Tuple.Pair;

import static pl.krzysztofdebski.utils.Utils.inBounds;

public class Coord extends Pair<Integer, Integer> {
    public Coord(int y, int x) {
        super(y, x);
    }

    public int y() { return this.v0(); }
    public int x() { return this.v1(); }

    public boolean value(boolean[][] array) {
        return array[this.y()][this.x()];
    }

    public char value(char[][] array) {
        return array[this.y()][this.x()];
    }

    public Character valueIfInBounds(char[][] array) {
        return inBounds(x(), y(), array) ? array[this.y()][this.x()] : null;
    }

    public Character value(Character[][] array) {
        return array[this.y()][this.x()];
    }

    public int value(int[][] array) {
        return array[this.y()][this.x()];
    }

    public Integer value(Integer[][] array) {
        return array[this.y()][this.x()];
    }

    public Integer valueIfInBounds(int[][] array) {
        return inBounds(x(), y(), array) ? array[this.y()][this.x()] : null;
    }

    public long value(long[][] array) {
        return array[this.y()][this.x()];
    }

    public Long value(Long[][] array) {
        return array[this.y()][this.x()];
    }

    public Coord relative(Direction d, int n) {
        return new Coord(y() + (n * d.Δr()), x() + (n * d.Δc()));
    }
    public Coord relative(Direction d) { return relative(d, 1); }

    public Direction distance(Coord other) {
        return new Direction(other.y() - y(), other.x() - x());
    }
}
