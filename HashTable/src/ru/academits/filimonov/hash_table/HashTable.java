package ru.academits.filimonov.hash_table;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private final LinkedList<T>[] hashArray;
    private int size;
    private int modCount;

    @SuppressWarnings("unchecked")
    public HashTable() {
        final int DEFAULT_ARRAY_LENGTH = 100;

        hashArray = (LinkedList<T>[]) new LinkedList[DEFAULT_ARRAY_LENGTH];

        for (int i = 0; i < hashArray.length; i++) {
            hashArray[i] = new LinkedList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public HashTable(int arrayLength) {
        hashArray = (LinkedList<T>[]) new LinkedList[arrayLength];

        for (int i = 0; i < hashArray.length; i++) {
            hashArray[i] = new LinkedList<>();
        }
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
        return hashArray[o.hashCode() % hashArray.length].contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        int offset = 0;

        for (LinkedList<T> list : hashArray) {
            System.arraycopy(list.toArray(), 0, array, offset, list.size());

            offset += list.size();
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

        return hashArray[element.hashCode() % hashArray.length].add(element);
    }

    @Override
    public boolean remove(Object o) {
        if (hashArray[o.hashCode() % hashArray.length].remove(o)) {
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

        HashTableIterator hashTableIterator = new HashTableIterator();

        while (hashTableIterator.hasNext()) {
            if (!c.contains(hashTableIterator.next())) {
                hashTableIterator.remove();

                isTableChanged = true;
            }
        }

        return isTableChanged;
    }

    @Override
    public void clear() {
        for (LinkedList<T> list : hashArray) {
            list.clear();
        }
    }

    private class HashTableIterator implements Iterator<T> {
        private int currentHash;
        private Iterator<T> currentListIterator;
        private final int savedModCount;

        HashTableIterator() {
            currentHash = 0;

            currentListIterator = hashArray[currentHash].iterator();

            savedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModifications();

            while (currentHash < hashArray.length - 1) {
                if (currentListIterator.hasNext()) {
                    return true;
                }

                currentHash++;

                currentListIterator = hashArray[currentHash].iterator();
            }

            return currentListIterator.hasNext();
        }

        @Override
        public T next() {
            checkConcurrentModifications();

            if (hasNext()) {
                return currentListIterator.next();
            }

            return null;
        }

        private void checkConcurrentModifications() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The hash table has been modified, the new iterator is needed");
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

        for (int i = 0; i < hashArray.length - 1; i++) {
            sb.append(hashArray[i].toString());
            sb.append(", ");
        }

        sb.append(hashArray[hashArray.length - 1].toString());
        sb.append("]");

        return sb.toString();
    }
}