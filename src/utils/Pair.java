package utils;

import java.util.Objects;

public class Pair<L,R> {
    private L l;
    private R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Pair && this.l.equals(((Pair) o).getL()) && this.r.equals(((Pair) o).getR());
    }

    @Override
    public int hashCode() {
        return Objects.hash(l, r);
    }
}
