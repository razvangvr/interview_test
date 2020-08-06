package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Breadth First Search
 * - genereaza toate Path-urile de la Source la Dest
 * */
public class BFSSolver2 {

    private final Element[][] roomMatrix;

    Queue<Element> nodesToVisit = new LinkedList<>();

    public BFSSolver2() {
        roomMatrix = new Element[0][];
    }

    /**
     * cu metoda asta iterez/generez printre solutii incepand de la Shortest to the longest
     * */
    List<Element> generateNextSolution(){
        return null;
    }

    //mai am Path-uri? sau le-am epuizat pe toate?
    //boolean hasNext(){}
}
