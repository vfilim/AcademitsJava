package ru.academits.filimonov.list;

public class SinglyLinkedList<T> {
    private ListItem<T> head;

    private int count;

    public int getCount() {
        return count;
    }

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(T headData) {
        head = new ListItem<T>(headData);

        count = 1;
    }

    public T getFirst() {
        return head.getData();
    }

    private ListItem<T> getNode(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index (" + index + ") can't be < 0");
        }

        if (index > count - 1) {
            throw new IndexOutOfBoundsException("The index (" + index + ") can't be more than list elements count (" + count + ").");
        }

        ListItem<T> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem;
    }

    public T getData(int index) {
        return getNode(index).getData();
    }

    public T setData(int index, T newData) {
        ListItem<T> item = getNode(index);

        T oldData = item.getData();

        item.setData(newData);

        return oldData;
    }

    public T remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index (" + index + ") can't be < 0");
        }

        if (index > count - 1) {
            throw new IndexOutOfBoundsException("The index (" + index + ") can't be more than list elements count (" + count + ").");
        }

        if (index == 0) {
            return removeHead();
        }

        ListItem<T> previousItem = getNode(index - 1);

        T removedData = previousItem.getNext().getData();

        previousItem.setNext(previousItem.getNext().getNext());

        count--;

        return removedData;
    }

    public void addFirst(T data) {
        count++;

        if (count == 0) {
            head = new ListItem<T>(data);
        } else {
            ListItem<T> newHead = new ListItem<T>(data, head);

            head = newHead;
        }
    }

    public void insertData(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index can't be < 0");
        }

        if (index > count) {
            throw new IndexOutOfBoundsException("It is impossible to insert data on index (" + index + ") bigger than elements count (" + count + ")");
        }

        if (index == 0) {
            addFirst(data);

            return;
        }

        ListItem<T> previousItem = getNode(index - 1);

        ListItem<T> newItem = new ListItem<T>(data, previousItem.getNext());

        previousItem.setNext(newItem);

        count++;
    }

    public boolean removeByData(T data) {
        ListItem<T> currentItem = head;

        for (int i = 0; i < count; i++) {
            if (data == null ? getData(i) == null : data.equals(getData(i))) {
                remove(i);

                return true;
            }

            currentItem = currentItem.getNext();
        }

        return false;
    }

    public T removeHead() {
        if (count == 0) {
            throw new IllegalStateException("The list is empty!");
        }

        T headData = head.getData();

        head = head.getNext();

        count--;

        return headData;
    }

    public void reverse() {
        ListItem<T> oldNext;
        ListItem<T> newNext = null;

        ListItem<T> currentItem = head;

        for (int i = 0; i < count; i++) {
            oldNext = currentItem.getNext();

            currentItem.setNext(newNext);

            newNext = currentItem;

            currentItem = oldNext;
        }

        head = newNext;
    }

    public SinglyLinkedList<T> getCopy() {
        ListItem<T> currentItem = head;

        SinglyLinkedList<T> listCopy = new SinglyLinkedList<T>();

        for (int i = 0; i < count; i++) {
            listCopy.insertData(i, currentItem.getData());

            currentItem = currentItem.getNext();
        }

        return listCopy;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < count - 1; i++) {
            string.append(getData(i) + ", ");
        }

        string.append(getData(count - 1));

        return string.toString();
    }
}
