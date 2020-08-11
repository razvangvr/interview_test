package raz.inteview_test.everest.brynjolf.room;

/*
A room is nothing more then a matrix of Elements
* */

import java.util.Objects;

import static raz.inteview_test.everest.brynjolf.room.ElementValue.*;

public class Element {

    //what kind of Room Element is it?
    //- a space, a wall, a guard, is it Bryn?, is it the exit?
    private ElementValue value;

    private final int rowIdx;
    private final int colIdx;

    public Element(ElementValue value, int rowIdx, int colIdx) {
        this.value = value;
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null /*|| getClass() != o.getClass()*/) return false;
        Element other = (Element) o;
        return rowIdx == other.rowIdx &&
                colIdx == other.colIdx &&
                value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, rowIdx, colIdx);
    }

    @Override
    public String toString() {
        return "[" + rowIdx + "][" + colIdx + "] " + value;
    }

    public String prettyPrint() {
        return value.toString();
    }

    public ElementValue getValue() {
        return value;
    }

    public void setValue(ElementValue value) {
        if (isStructural())
            throw new UnsupportedOperationException("I am part of the game Structure:" + this.toString() + " - you can't change me!");
        this.value = value;
    }

    public int rowIdx() {
        return rowIdx;
    }

    public int colIdx() {
        return colIdx;
    }

    public boolean isWall() {
        return getValue() == WALL;
    }

    public boolean isEmpty() {
        return getValue() == EMPTY;
    }

    public boolean isGuard() {
        return getValue() == GUARD;
    }

    public boolean isBryn() {
        return getValue() == BRYN;
    }

    public boolean isExit() {
        return getValue() == EXIT;
    }

    /**
     * Is part of the Room Structure, it has nothing to do with movement,
     * That is:
     * 1/ IT CAN MOVE (it is fixed, it doesn't need to participate in movement logic)
     * 2/ you can not change it's value and move into this Element (by transferring your current value to this this cell)
     */
    public boolean isStructural() {
        return isWall() || isExit();
    }
}
