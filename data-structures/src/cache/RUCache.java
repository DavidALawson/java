/*
 * The MIT License
 *
 * Copyright 2017 DavidALawson.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Cache that takes the approach of removing a recently used object.
 * Stores the objects internally in a doubly linked list for fast replacement
 * when the cache is full. Requires a mapping of key to node where node stores
 * both the key and the object to allow for the linked list to work in O(1).
 * Both key and object are required. Another way would be to have it map to a Pair
 * that contains the storedObject and a Node that only contains a key. However I
 * Chose this way instead of making or using another class.
 * @author DavidALawson
 * @param <K> type of key
 * @param <V> type of stored objects
 */
abstract class RUCache<K,V> implements Cache<K,V> {
    private final Map<K,Node> storedObjects;
    Node start;
    Node end;
    int capacity;

    /**
     * Initializer with the given capacity. If the capacity is not greater than
     * zero, than it is defaulted to 1. Initializes the underlying Map as 
     * well.
     * @param capacity the capacity of the Cache
     */
    RUCache(int capacity) {
        this.capacity = (capacity > 0 ? capacity : 1);
        storedObjects = new HashMap<>(capacity);
    }
    
    @Override
    public V get(K key) {
        Node node = storedObjects.get(key);
        
        // removes the node from its spot in the list and puts it at the start
        // takes O(1) to find and move node.
        if(node != null) {
            removeNode(node);
            addNode(node);
            return node.storedObject;
        }
        else {
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        Node node;
        V replacedValue = null;
        
        if(contains(key)) {
            node = storedObjects.get(key);
            removeNode(node);
            replacedValue = node.storedObject;
            node.storedObject = value;
            addNode(node);
        }
        else if(isAtCapacity()) {
            // If the cache is at capacity, then it finds the node of the object
            // to replace and removes the mapping from storedObjects and replaces
            // the key and storedObject in the node with the new key and value
            node = getNodeOfObjectToReplace();
            removeNode(node);
            replacedValue = node.storedObject;
            K oldKey = node.key;
            node.key = key;
            node.storedObject = value;
            addNode(node);
            storedObjects.remove(oldKey);
            storedObjects.put(key, node);
        }
        else {
            node = new Node(key,value);
            addNode(node);
            storedObjects.put(key, node);
        }
        return replacedValue;
    }

    @Override
    public boolean contains(K key) {
        return storedObjects.get(key) != null;
    }

    @Override
    public void flush() {
        storedObjects.clear();
        start = null;
        end = null;
    }

    @Override
    public boolean isAtCapacity() {
        return storedObjects.size() == capacity;
    }
    
    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getNumberStored() {
        return storedObjects.size();
    }

    @Override
    public boolean isEmpty() {
        return storedObjects.isEmpty();
    }
    
    private void addNode(Node node) {
        if(start == null && end == null) {
            start = node;
            end = node;
            node.next = null;
            node.previous = null;
        }
        else {
            start.previous = node;
            node.next = start;
            node.previous = null;
            start = node;
        }
    }
    
    private void removeNode(Node node) {
        if(start.equals(node)) {
            start = node.next;
        }
        if(end.equals(node)) {
            end = node.previous;
        }
        node.remove();
    }
    
    /**
     * Override to determines what object will be replaced. Returns the Node
     * that wraps the object and key so that the node may be reused.
     * @return the node that wraps the object and key to be replaced
     */
    abstract Node getNodeOfObjectToReplace();
    
    class Node {
        Node next;
        Node previous;
        K key;
        V storedObject;
        
        private Node(K key, V storedObject) {
            this.key = key;
            this.storedObject = storedObject;
        }
        
        // Removes the node from next.previous and previous.next if they exist
        // and replaces them with previous and next respectfully.
        private void remove() {
            if(next != null) {
                next.previous  = previous;
            }
            if(previous != null) {
                previous.next = next;
            }
        }
    }
}
