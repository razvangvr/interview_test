package raz.inteview_test.everest.brynjolf.room;

public enum ElementValue {
    EMPTY("."),
    WALL("x"),
    BRYN("b"),
    EXIT("e"),
    GUARD("g");

    private final String strVal;

    ElementValue(String s) {
        this.strVal = s;
    }

    public static ElementValue fromValue(String val) {
        for (ElementValue enumVal : ElementValue.values()) {
            if (enumVal.strVal.equals(val)) {
                return enumVal;
            }
        }
        throw new IllegalArgumentException("Invalid  Room Value:" + val);
    }

    /**
     * Is part of the Room Structure, it has nothing to do with movement,
     * e.g
     * 1/ it is fixed, it doesn't need to participate in movement logic
     * 2/ you can not change it's value and move into this Element (by transferring your current value to this this cell)
     */
    boolean isStructural() {
        return this == WALL || this == EXIT;
    }

    @Override
    public String toString() {
        return strVal;
    }
}
