package utils;

public class State {
    public final Tuple pos;
    public final Object other;
    public State(Tuple pos) {
        this.pos = pos;
        this.other = null;
    }

    public State(Tuple pos, Object other) {
        this.pos = pos;
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof State) {
            if (other == null && ((State) o).other == null) {
                return pos.equals(((State) o).pos);
            } else
                return other != null && pos.equals(((State) o).pos) && other.equals(((State) o).other);
        }
        return false;
    }
}
