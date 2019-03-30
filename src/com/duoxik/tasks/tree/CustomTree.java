package com.duoxik.tasks.tree;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;
    int size;

    public CustomTree() {
        this.root = new Entry<>("0");
        this.size = 0;
    }

    @Override
    public boolean add(String s) {

        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {

            Entry<String> current = queue.poll();

            if (current.leftChild != null) {
                queue.add(current.leftChild);
            } else {
                Entry<String> newEntry = new Entry(s);
                current.leftChild = newEntry;
                newEntry.parent = current;
                break;
            }

            if (current.rightChild != null) {
                queue.add(current.rightChild);
            } else {
                Entry<String> newEntry = new Entry(s);
                current.rightChild = newEntry;
                newEntry.parent = current;
                break;
            }
        }

        size++;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    public String getParent(String s) {

        Entry<String> entry = contLevelOrder(s);
        return (entry != null && entry.parent != null) ? entry.parent.elementName : null;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String))
            throw new UnsupportedOperationException();

        String element = (String) o;
        Entry<String> entry = contLevelOrder(element);

        if (entry != null && entry.parent != null) {

            Entry<String> parent = entry.parent;
            parent.leftChild = (parent.leftChild == entry) ? null : parent.leftChild;
            parent.rightChild = (parent.rightChild == entry) ? null : parent.rightChild;
            entry.parent = null;

            int childrenCount = getChildrenCount(entry);
            size -= (childrenCount + 1);

            return true;
        }

        return false;
    }

    private int getChildrenCount(Entry<String> entry) {

        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(entry);

        int count = 0;

        while (!queue.isEmpty()) {

            Entry<String> current = queue.poll();

            if (current.leftChild != null) {
                queue.add(current.leftChild);
                count++;
            }

            if (current.rightChild != null) {
                queue.add(current.rightChild);
                count++;
            }
        }

        return count;
    }

    private Entry<String> contLevelOrder(String element) {

        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {

            Entry<String> current = queue.poll();

            if (current.elementName.equals(element))
                return current;

            if (current.leftChild != null)
                queue.add(current.leftChild);

            if (current.rightChild != null)
                queue.add(current.rightChild);
        }

        return null;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    static class Entry<T> implements Serializable {

        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
