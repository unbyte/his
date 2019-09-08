package lib;

public class Tuple {
    private final Object[] array;

    public Tuple(Object a) {
        this.array = new Object[]{a};
    }

    public Tuple(Object a, Object b) {
        this.array = new Object[]{a, b};
    }

    public Tuple(Object a, Object b, Object c) {
        this.array = new Object[]{a, b, c};
    }

    public Tuple(Object a, Object b, Object c, Object d) {
        this.array = new Object[]{a, b, c, d};
    }

    public Tuple(Object a, Object b, Object c, Object d, Object e) {
        this.array = new Object[]{a, b, c, d, e};
    }

    public <T> T get(int index, Class<T> type) {
        if (index >= array.length || index < 0)
            throw new ArrayIndexOutOfBoundsException();
        if (!type.isInstance(array[index]))
            throw new ClassCastException();
        return type.cast(array[index]);
    }

    public int size() {
        return array.length;
    }

}
