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
 * A Cache that implements  a most recently used (MRU) replacement policy upon
 * insertion into a full Cache.
 * @author DavidALawson
 */
public class MRUCache extends RUCache {

    /**
     * Initializer with the capacity of the Cache.
     * @param capacity the capacity of the Cache
     */
    public MRUCache(int capacity) {
        super(capacity);
    }
    
    /**
     * Selects the start/most recently used to be the object to be removed.
     */
    @Override
    RUCache.Node getNodeOfObjectToReplace() {
        return start;
    }
}
