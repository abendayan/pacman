package utils;

import java.util.ArrayList;

public class TupleIndex {
    public final float a;
    public final Tuple b;
    public TupleIndex(float a, Tuple b) {
        this.a = a;
        this.b = b;
    }

    public static Tuple maxList(ArrayList<TupleIndex> listTuples) {
        Tuple maxTuple = listTuples.get(0).b;
        float maxFloat = listTuples.get(0).a;
        for(int i = 1; i < listTuples.size(); i++) {
            if(listTuples.get(i).a > maxFloat) {
                maxFloat = listTuples.get(i).a;
                maxTuple = listTuples.get(i).b;
            }
        }
        return maxTuple;
    }
}
