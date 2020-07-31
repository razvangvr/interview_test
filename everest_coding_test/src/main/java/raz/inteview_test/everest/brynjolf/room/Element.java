package raz.inteview_test.everest.brynjolf.room;

/*
A room is nothing more then a matrix of Elements
* */

import java.util.Objects;

//idea: elements could react to events
//daca avem evenimentul UP, we fire this event and all elements react/update their state based on the current move(UP)
//update their state =  they move up (if possible)
// ZZA: instead of shifting the coordonates:x,y , it is better to just shift the value from the neighbour, based on the direction
public class Element {

    //what kind of Room Element is it?
    //- a space, a wall, a guard, is it Bryn?, is it exist?
    private ElementValue value;

    public Element(ElementValue value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return value == element.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "[" + value +
                ']';
    }

    public String prettyPrint() {
        return value.toString();
    }
}
