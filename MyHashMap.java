package HashMap;

public class MyHashMap<K, T> {

    private Node<K, T>[] table;
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

    private int getIndex(Object key) {
        return hashFunction(key);
    }

    public void put(K key, T value) {
        if ((double) size / table.length > LOAD_FACTOR) {
            resizeTable();
        }

        int index = getIndex(key);
        Node<K, T> newNode = new Node<>(key, value);

        if (table[index] != null) {
            Node<K, T> current = table[index];
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

    public void remove(K key) {
        int index = getIndex(key);
        Node<K, T> current = table[index];
        Node<K, T> previous = null;

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

    public T get(K key) {
        int index = getIndex(key);
        Node<K, T> current = table[index];

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
        Node<K, T>[] newTable = new Node[newCapacity];

        for (Node<K, T> node : table) {
            while (node != null) {
                int newIndex = getIndex(node.key);
                Node<K, T> newNode = new Node<>(node.key, node.value);
                newNode.next = newTable[newIndex];
                newTable[newIndex] = newNode;
                node = node.next;
            }
        }

        table = newTable;
    }

    private static class Node<K, T> {
        private final K key;
        private T value;
        private Node<K, T> next;

        public Node(K key, T value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}
