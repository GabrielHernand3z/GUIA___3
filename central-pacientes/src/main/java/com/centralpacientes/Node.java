package com.centralpacientes;

/**
 * Nodo simple para lista enlazada simple.
 * @param <T> tipo de dato almacenado
 */
public class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

