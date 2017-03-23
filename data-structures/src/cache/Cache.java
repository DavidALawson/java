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

/**
 * <p>
 * An object that stores other objects for future use.
 * </p>
 * <p>
 * Implementations of Cache will have to determine how they will insert
 * objects when the capacity is reached. Possible implementations include random 
 * replacement (RR), least recently used (LRU), and most recently used (MRU).
 * </p>
 * @author DavidALawson
 * @param <K> represents the type of key that will be used to retrieve the object
 * stored in the Cache.
 * @param <V> represents the type of the object stored in the Cache.
 */
public interface Cache<K,V> {
    
    /**
     * Gets the object from the Cache that corresponds to the key passed in.
     * @param key the key that is linked to the object
     * @return the object the key is linked to, or null if no object is
     * linked to the key
     */
    V get(K key);

    /**
     * Puts the value in the Cache at the given key. If the Cache is full, then
     * a value in the Cache will be replaced. The replaced value is returned.
     * Implementors will determine the way in which the value to
     * replace is chosen.
     * @param key the key to link the object to
     * @param value the value to add to the Cache
     * @return the value that this replaces, or null if no object was replaced
     */
    V put(K key, V value);
    
    /**
     * Returns true if the key links to a stored object in the Cache.
     * @param key the key to check if an object is stored under
     * @return true if an object is stored under this key, false if there is not
     * an object stored under this key
     */
    boolean contains(K key);
    
    /**
     * Flushes the Cache of all stored objects. Returns the Cache to its initial
     * state of empty with the same capacity as before the flush occurred.
     */
    void flush();
    
    /**
     * Returns the capacity of the Cache. This is the maximum number of objects
     * the Cache can contain.
     * @return the capacity of the Cache
     */
    int getCapacity();
    
    /**
     * Returns the number of objects stored in the Cache. This is the number of
     * objects the Cache has stored within.
     * @return the number of objects stored in the Cache
     */
    int getNumberStored();
    
    /**
     * Returns true if the Cache has no stored objects.
     * @return true if the cache is empty, false if it is not
     */
    boolean isEmpty();
}