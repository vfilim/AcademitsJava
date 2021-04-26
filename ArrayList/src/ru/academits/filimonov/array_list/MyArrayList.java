package ru.academits.filimonov.array_list;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private T[] items;
    private int modCount;

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        items = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity (" + capacity + ") can't be less than 0");
        }

        //noinspection unchecked
        items = (T[]) new Object[capacity];
    }

    public MyArrayList(Collection<? extends T> collection) {
        //noinspection unchecked
        items = (T[]) new Object[collection.size()];

        addAll(collection);
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
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <E> E[] toArray(E[] targetArray) {
        if (size > targetArray.length) {
            //noinspection unchecked
            return (E[]) Arrays.copyOf(items, size, targetArray.getClass());
        }

        //noinspection unchecked
        E[] castedArray = (E[]) items;

        System.arraycopy(castedArray, 0, targetArray, 0, size);

        if (size < targetArray.length) {
            targetArray[size] = null;
        }

        return targetArray;
    }

    @Override
    public boolean add(T item) {
        add(size, item);

        return true;
    }

    @Override
    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index " + index + " can't be less than 0 or be bigger than list size " + size);
        }

        if (size >= items.length) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);

        items[index] = item;

        modCount++;
        size++;
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            //noinspection unchecked
            items = (T[]) new Object[DEFAULT_CAPACITY];

            return;
        }

        items = Arrays.copyOf(items, items.length * 2);
    }

    public void ensureCapacity(int capacity) {
        if (capacity > items.length) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    public void trimToSize() {
        if (size < items.length) {
            items = Arrays.copyOf(items, size);
        }
    }

    @Override
    public boolean remove(Object object) {
        int objectIndex = indexOf(object);

        if (objectIndex == -1) {
            return false;
        }

        remove(objectIndex);

        return true;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index " + index + " can't be less than 0 or be equal or bigger than list size " + size);
        }

        T oldItem = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);

        modCount++;
        size--;

        items[size] = null;

        return oldItem;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index " + index + " can't be less than 0 or be bigger than list size " + size);
        }

        if (c.isEmpty()) {
            return false;
        }

        int collectionSize = c.size();

        ensureCapacity(size + collectionSize);

        System.arraycopy(items, index, items, index + collectionSize, size - index);

        int currentIndex = index;

        for (T e : c) {
            items[currentIndex] = e;

            currentIndex++;
        }

        size += c.size();

        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }

        boolean isListChanged = false;

        for (int i = 0; i < size; i++) {
            if (c.contains(items[i])) {
                remove(i);

                i--;

                isListChanged = true;
            }
        }

        return isListChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isListChanged = false;

        for (int i = 0; i < size; i++) {
            if (!c.contains(items[i])) {
                remove(i);

                i--;

                isListChanged = true;
            }
        }

        return isListChanged;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        if (size != 0) {
            modCount++;
        }

        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index " + index + " can't be less than 0 or be equal or bigger than list size " + size);
        }

        return items[index];
    }

    @Override
    public T set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index " + index + " can't be less than 0 or be equal or bigger than list size " + size);
        }

        T oldItem = items[index];

        items[index] = item;

        return oldItem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("The class doesn't support the operation");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("The class doesn't support the operation");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("The class doesn't support the operation");
    }

    private class MyArrayListIterator implements Iterator<T> {
        private int currentIndex;
        private final int savedModCount;

        public MyArrayListIterator() {
            currentIndex = -1;
            savedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size - 1;
        }

        @Override
        public T next() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("The iterator has not next item. It's size is " + size);
            }

            currentIndex++;

            return items[currentIndex];
        }
    }

    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (int i = 0; i < size - 1; i++) {
            sb.append(items[i]);
            sb.append(", ");
        }

        sb.append(items[size - 1]);
        sb.append("]");

        return sb.toString();
    }
}