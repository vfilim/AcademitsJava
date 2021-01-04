package ru.academits.filimonov.list;

public class Test {
    public static void main(String[] ags) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();

        for (int i = 9; i >= 0; i--) {
            list.addFirst(i);
        }

        System.out.println("The elements count is " + list.getCount());

        System.out.println("The list head is " + list.getFirst());

        System.out.println("Let's change third element to 20, it was " + list.setData(2, 20));
        System.out.println("Now it is " + list.getData(2));

        list.insertData(4, 40);
        System.out.println("We inserted element 40 on index 4, now it is " + list.getData(4) + ", the next element is " + list.getData(5));

        System.out.println("Let's remove the element on index 5, it was " + list.remove(5) + ", now it is " + list.getData(5));

        System.out.println(list);

        System.out.println("Let's remove the element 6. Is it successfully deleted? " + list.removeByData(6));

        System.out.println(list);

        System.out.println("What's about the element 12? " + list.removeByData(12));

        System.out.println("Let's reverse the list and print it");
        list.reverse();

        System.out.println(list);

        var stringsList = new SinglyLinkedList<String>();

        stringsList.addFirst("last");
        stringsList.addFirst(null);
        stringsList.addFirst("second");
        stringsList.addFirst("first");

        stringsList.setData(1, null);

        stringsList.removeByData(null);

        System.out.println(stringsList);

        var listCopy = list.getCopy();

        System.out.println("Let's print numbers list copy");

        System.out.println(listCopy);
    }
}

