package ru.academits.filimonov.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int count;

    public SinglyLinkedList() {
    }

    public int getCount() {
        return count;
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Can't get first element, because the list is empty");
        }

        return head.getData();
    }

    private ListItem<T> getListItem(int index) {
        checkIndex(index);

        ListItem<T> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem;
    }

    public T getData(int index) {
        return getListItem(index).getData();
    }

    public T setData(int index, T newData) {
        ListItem<T> item = getListItem(index);

        T oldData = item.getData();

        item.setData(newData);

        return oldData;
    }

    public T remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<T> previousItem = getListItem(index - 1);

        T removedData = previousItem.getNext().getData();

        previousItem.setNext(previousItem.getNext().getNext());

        count--;

        return removedData;
    }

    public void addFirst(T data) {
        count++;

        head = new ListItem<>(data, head);
    }

    public void add(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index can't be < 0, it is " + index);
        }

        if (index > count) {
            throw new IndexOutOfBoundsException("It is impossible to insert data on index (" + index + ") bigger than elements count (" + count + ")");
        }

        if (index == 0) {
            addFirst(data);

            return;
        }

        ListItem<T> previousItem = getListItem(index - 1);

        ListItem<T> newItem = new ListItem<>(data, previousItem.getNext());

        previousItem.setNext(newItem);

        count++;
    }

    public boolean removeByData(T data) {
        if (count == 0) {
            return false;
        }

        if (Objects.equals(head.getData(), data)) {
            removeFirst();

            return true;
        }

        ListItem<T> currentItem = head.getNext();
        ListItem<T> previousItem = head;

        for (int i = 1; i < count; i++) {
            if (Objects.equals(currentItem.getData(), data)) {
                previousItem.setNext(currentItem.getNext());

                count--;

                return true;
            }

            previousItem = currentItem;
            currentItem = currentItem.getNext();
        }

        return false;
    }

    public T removeFirst() {
        if (count == 0) {
            throw new NoSuchElementException("The list is empty!");
        }

        T removedData = head.getData();

        head = head.getNext();

        count--;

        return removedData;
    }

    public void reverse() {
        ListItem<T> newNext = null;

        ListItem<T> currentItem = head;

        for (int i = 0; i < count; i++) {
            ListItem<T> oldNext = currentItem.getNext();

            currentItem.setNext(newNext);

            newNext = currentItem;

            currentItem = oldNext;
        }

        head = newNext;
    }

    public SinglyLinkedList<T> getCopy() {
        SinglyLinkedList<T> listCopy = new SinglyLinkedList<>();

        if (count == 0) {
            return listCopy;
        }

        listCopy.addFirst(head.getData());

        ListItem<T> currentItem = head;
        ListItem<T> currentItemCopy = listCopy.head;

        for (int i = 1; i < count; i++) {
            currentItem = currentItem.getNext();

            currentItemCopy.setNext(new ListItem<>(currentItem.getData()));

            currentItemCopy = currentItemCopy.getNext();
        }

        listCopy.count = count;

        return listCopy;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index (" + index + ") can't be < 0, it is " + index);
        }

        if (index >= count) {
            throw new IndexOutOfBoundsException("The index (" + index + ") must be less than list elements count (" + count + ").");
        }
    }

    public String toString() {
        if (count == 0) {
            return "[]";
        }

        StringBuilder listElementsSequence = new StringBuilder();

        ListItem<T> currentItem = head;

        listElementsSequence.append("[");

        for (int i = 0; i < count - 1; i++) {
            listElementsSequence.append(currentItem.getData());
            listElementsSequence.append(", ");

            currentItem = currentItem.getNext();
        }

        listElementsSequence.append(currentItem.getData());

        listElementsSequence.append("]");

        return listElementsSequence.toString();
    }
}