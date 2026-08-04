package rts.memento;

public class Memento {
    private final State savedState;

    public Memento(State state) {
        this.savedState = state;
    }

    public State getSavedState() {
        return savedState.copy();
    }
}