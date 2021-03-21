package ru.academits.filimonov.array_list;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private int length;
    private T[] items;
    int modCount;

    public MyArrayList() {
        length = 0;
        modCount = 0;

        items = (T[]) new Object[10];
    }

    public MyArrayList(Collection<? extends T> list) {
        length = 0;
        modCount = 0;

        items = (T[]) new Object[10];

        addAll(list);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(items[i], o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, length);
    }

    @Override
    public <E> E[] toArray(E[] a) {
        E[] array = (E[]) new Object[length];

        for (int i = 0; i < length; i++) {
            array[i] = (E) items[i];
        }

        return array;
    }

    @Override
    public boolean add(T t) {
        if (length >= items.length) {
            increaseCapacity();
        }

        items[length] = t;

        modCount++;
        length++;

        return true;
    }

    private void increaseCapacity() {
        items = Arrays.copyOf(items, items.length * 2);
    }

    public void ensureCapacity(int capacity) {
        if (capacity < length) {
            throw new IllegalArgumentException("The list capacity can't be less than its current length");
        }

        items = Arrays.copyOf(items, capacity);
    }

    public void trimToSize(int size) {
        if (size < length) {
            throw new IllegalArgumentException("The new list size can't be less than its current length");
        }

        items = Arrays.copyOf(items, size);
    }

    @Override
    public boolean remove(Object element) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(items[i], element)) {
                System.arraycopy(items, i + 1, items, i, length - i - 1);

                modCount++;
                length--;

                return true;
            }
        }

        return false;
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
        for (T e : c) {
            add(e);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int currentIndex = index;

        for (T e : c) {
            add(currentIndex, e);

            currentIndex++;
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isListChanged = false;

        for (Object e : c) {
            for (int i = 0; i < length; i++) {
                if (Objects.equals(items[i], e)) {
                    remove(i);

                    isListChanged = true;
                }
            }
        }

        return isListChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isListChanged = false;

        for (int i = 0; i < length; i++) {
            if (!c.contains(items[i])) {
                remove(i);

                isListChanged = true;
            }
        }

        return isListChanged;
    }

    @Override
    public void clear() {
        items = (T[]) new Object[10];

        modCount++;
        length = 0;
    }

    @Override
    public T get(int index) {
        if (index < length) {
            return items[index];
        }

        return null;
    }

    @Override
    public T set(int index, T element) {
        if (index < length) {
            T oldElement = items[index];

            items[index] = element;

            modCount++;

            return oldElement;
        }

        return null;
    }

    @Override
    public void add(int index, T element) {
        if (length >= items.length) {
            increaseCapacity();
        }

        if (index <= length) {
            System.arraycopy(items, index, items, index + 1, length - index - 1);

            items[index] = element;

            modCount++;
            length++;
        }
    }

    @Override
    public T remove(int index) {
        if (index < length) {
            T oldElement = items[length];

            System.arraycopy(items, index + 1, items, index, length - index - 1);

            modCount++;
            length--;

            return oldElement;
        }

        return null;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(items[0], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;

        for (int i = 0; i < length; i++) {
            if (Objects.equals(items[i], o)) {
                lastIndex = i;
            }
        }

        return lastIndex;
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

    private class MyArrayListIterator implements ListIterator<T> {
        int currentIndex;
        int savedModCount;

        public MyArrayListIterator() {
            currentIndex = -1;
            savedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            return currentIndex < length - 1;
        }

        @Override
        public T next() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            if (currentIndex >= length - 1) {
                throw new NoSuchElementException("The iterator has not next element. The current index is " + currentIndex + ", but the list length is + " + length);
            }

            currentIndex++;

            return items[currentIndex];
        }

        @Override
        public boolean hasPrevious() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            return currentIndex != 0;
        }

        @Override
        public T previous() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            if (currentIndex >= length - 1) {
                throw new NoSuchElementException("The iterator has no previous element. It is at the beginning of the list");
            }

            currentIndex--;

            return items[currentIndex];
        }

        @Override
        public int nextIndex() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            return 0;
        }

        @Override
        public int previousIndex() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            if (currentIndex == 0) {
                return -1;
            }

            return currentIndex - 1;
        }

        @Override
        public void remove() {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            MyArrayList.this.remove(currentIndex);
        }

        @Override
        public void set(T t) {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            MyArrayList.this.set(currentIndex, t);
        }

        @Override
        public void add(T t) {
            if (savedModCount != modCount) {
                throw new ConcurrentModificationException("The list has changed, the new iterator is needed");
            }

            MyArrayList.this.add(t);
        }
    }
}