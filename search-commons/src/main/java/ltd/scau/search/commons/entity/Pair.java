package ltd.scau.search.commons.entity;

import java.util.Map;

/**
 * @author Weijie Wu
 */
public class Pair<T1, T2> implements Map.Entry<T1, T2> {

    private T1 a;

    private T2 b;

    public T1 getA() {
        return a;
    }

    public void setA(T1 a) {
        this.a = a;
    }

    public T2 getB() {
        return b;
    }

    public void setB(T2 b) {
        this.b = b;
    }

    @Override
    public T1 getKey() {
        return a;
    }

    @Override
    public T2 getValue() {
        return b;
    }

    @Override
    public T2 setValue(T2 value) {
        T2 old = this.b;
        this.b = value;
        return old;
    }
}
