package com.centralpacientes;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Implementación genérica de una lista enlazada simple.
 * No usa colecciones de Java para resaltar el uso de nodos.
 */
public class SinglyLinkedList<T> {
    private Node<T> head;
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /** Inserta al inicio (O(1)). */
    public void addFirst(T data) {
        Node<T> n = new Node<>(data);
        n.next = head;
        head = n;
        size++;
    }

    /** Inserta al final (O(n)). */
    public void addLast(T data) {
        Node<T> n = new Node<>(data);
        if (head == null) {
            head = n;
        } else {
            Node<T> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = n;
        }
        size++;
    }

    /** Busca el primer elemento que cumpla el predicado (O(n)). */
    public T findFirst(Predicate<T> predicate) {
        Node<T> cur = head;
        while (cur != null) {
            if (predicate.test(cur.data)) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    /**
     * Elimina la primera coincidencia que cumpla el predicado (O(n)).
     * @return true si eliminó, false si no encontró.
     */
    public boolean removeFirstMatch(Predicate<T> predicate) {
        if (head == null) return false;

        if (predicate.test(head.data)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> prev = head;
        Node<T> cur = head.next;
        while (cur != null) {
            if (predicate.test(cur.data)) {
                prev.next = cur.next;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    /** Recorre la lista aplicando una acción a cada elemento. */
    public void forEach(Consumer<T> action) {
        Node<T> cur = head;
        while (cur != null) {
            action.accept(cur.data);
            cur = cur.next;
        }
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> cur = head;
        while (cur != null) {
            sb.append(cur.data);
            cur = cur.next;
            if (cur != null) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
