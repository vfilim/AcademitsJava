package ru.academits.filimonov.hash_table;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private static final int DEFAULT_ARRAY_LENGTH = 100;

    private final LinkedList<T>[] lists;
    private int size;
    private int modCount;

    public HashTable() {
        this(DEFAULT_ARRAY_LENGTH);
    }

    public HashTable(int arrayLength) {
        if (arrayLength < 1) {
            throw new IllegalArgumentException("Hash table lists array length can't be lesser than 1, now it is " + arrayLength);
        }

        //noinspection unchecked
        lists = (LinkedList<T>[]) new LinkedList[arrayLength];
    }

    private int getListIndex(Object o) {
        return Objects.hashCode(o) % lists.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getListIndex(o);

        return lists[index] != null && lists[index].contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        int index = 0;

        for (T element : this) {
            array[index] = element;

            index++;
        }

        return array;
    }

    @Override
    public <E> E[] toArray(E[] targetArray) {
        //noinspection unchecked
        E[] array = (E[]) toArray();

        if (size > targetArray.length) {
            //noinspection unchecked
            return (E[]) Arrays.copyOf(array, size, targetArray.getClass());
        }

        System.arraycopy(array, 0, targetArray, 0, size);

        if (size < targetArray.length) {
            targetArray[size] = null;
        }

        return targetArray;
    }

    @Override
    public boolean add(T element) {
        size++;
        modCount++;

        int index = getListIndex(element);

        if (lists[index] == null) {
            lists[index] = new LinkedList<>();
        }

        return lists[index].add(element);
    }

    @Override
    public boolean remove(Object o) {
        int index = getListIndex(o);

        if (lists[index] == null) {
            return false;
        }

        if (lists[index].remove(o)) {
            size--;
            modCount++;

            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }

        for (T e : c) {
            add(e);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int savedModCount = modCount;

        for (Object obj : c) {
            remove(obj);
        }

        return savedModCount != modCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isTableChanged = false;

        for (LinkedList<T> list : lists) {
            if (list == null) {
                continue;
            }

            int savedListSize = list.size();

            if (list.retainAll(c)) {
                isTableChanged = true;

                size -= savedListSize - list.size();
            }
        }

        if (isTableChanged) {
            modCount++;

            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (LinkedList<T> list : lists) {
            if (list != null) {
                list.clear();
            }
        }

        modCount++;
        size = 0;
    }

    private class HashTableIterator implements Iterator<T> {
        private int currentListIndex;
        private int currentGeneralIndex;
        private Iterator<T> currentListIterator;
        private final int savedModCount;

        public HashTableIterator() {
            currentListIterator = lists[currentListIndex].iterator();

            savedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return currentGeneralIndex < size();
        }

        @Override
        public T next() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The hash table has been modified, the new iterator is needed");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("The iterator can't get next element, because the collection is over");
            }

            while (true) {
                if (currentListIterator.hasNext()) {
                    currentGeneralIndex++;

                    return currentListIterator.next();
                }

                do {
                    currentListIndex++;
                } while (lists[currentListIndex] == null);

                currentListIterator = lists[currentListIndex].iterator();
            }
        }

        public void remove() {
            currentListIterator.remove();
            size--;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (int i = 0; i < lists.length - 1; i++) {
            sb.append(lists[i]);
            sb.append(", ");
        }

        sb.append(lists[lists.length - 1]);
        sb.append("]");

        return sb.toString();
    }
}