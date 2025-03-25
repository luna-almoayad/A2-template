package ca.mcmaster.se2aa4.island.team44.navigation;

public enum Compass {
    N, // North
    E, // East
    S, // South
    W; // West

    // Method to get the direction to the right of the current direction
    public Compass right() {
        if (this == N) {
            return E;
        } else if (this == E) {
            return S;
        } else if (this == S) {
            return W;
        } else if (this == W) {
            return N;
        } else {
            return this;
        }
    }

    // Method to get the direction to the left of the current direction
    public Compass left() {
        if (this == N) {
            return W;
        } else if (this == E) {
            return N;
        } else if (this == S) {
            return E;
        } else if (this == W) {
            return S;
        } else {
            return this;
        }
    }

    // Method to convert a string representation of a direction to the corresponding enum value
    @SuppressWarnings("ConvertToStringSwitch")
    public static Compass toEnum(String direction) {
        if ("N".equals(direction)) {
            return N;
        } else if ("E".equals(direction)) {
            return E;
        } else if ("S".equals(direction)) {
            return S;
        } else if ("W".equals(direction)) {
            return W;
        } else {
            return null;
        }
    }
}