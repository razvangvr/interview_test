package raz.inteview_test.everest.brynjolf.room;

/*
A room is nothing more then a matrix of Elements
* */

import java.util.Objects;

//idea: elements could react to events
//daca avem evenimentul UP, we fire this event and all elements react/update their state based on the current move(UP)
//update their state =  they move up (if possible)
// ZZA: instead of shifting the coordonates:x,y , it is better to just shift the value from/to the neighbour, based on the direction
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
        return Objects.hash(value);
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
        this.value = value;
    }

    public int rowIdx() {
        return rowIdx;
    }

    public int colIdx() {
        return colIdx;
    }
}
