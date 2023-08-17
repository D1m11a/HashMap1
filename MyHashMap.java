package HashMap;

public class MyHashMap<T> {

    private Node<T>[] table;
    private int size;
    private static final int DEFAULT_SIZE = 16;
    private static final double LOAD_FACTOR = 0.75;

    public MyHashMap() {
        table = new Node[DEFAULT_SIZE];
        size = 0;
    }

    private int hashFunction(Object key) {
        return Math.abs(key.hashCode() % table.length);
    }

    public void put(Object key, T value) {
        if ((double) size / table.length > LOAD_FACTOR) {
            resizeTable();
        }

        int index = hashFunction(key);
        Node<T> newNode = new Node<>(key, value);

        if (table[index] != null) {
            Node<T> current = table[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            newNode.next = table[index];
        }
        table[index] = newNode;
        size++;
    }

    public void remove(Object key) {
        int index = hashFunction(key);
        Node<T> current = table[index];
        Node<T> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    public void clear() {
        table = new Node[DEFAULT_SIZE];
        size = 0;
    }

    public int size() {
        return size;
    }

    public T get(Object key) {
        int index = hashFunction(key);
        Node<T> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    private void resizeTable() {
        int newCapacity = table.length * 2;
        Node<T>[] newTable = new Node[newCapacity];

        for (Node<T> node : table) {
            while (node != null) {
                int newIndex = hashFunction(node.key);
                Node<T> newNode = new Node<>(node.key, node.value);
                newNode.next = newTable[newIndex];
                newTable[newIndex] = newNode;
                node = node.next;
            }
        }

        table = newTable;
    }

    private static class Node<T> {
        private final Object key;
        private T value;
        private Node<T> next;

        public Node(Object key, T value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}