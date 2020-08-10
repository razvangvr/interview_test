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

    @Override
    public String toString() {
        return strVal;
    }
}
