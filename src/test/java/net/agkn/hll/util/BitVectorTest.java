package net.agkn.hll.util;

/*
 * Copyright 2013 Aggregate Knowledge, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Unit tests for {@link BitVector}.
 *
 * @author rgrzywinski
 * @author timon
 */
public class BitVectorTest {
    /**
     * Tests {@link BitVector#getRegister(long)} and {@link BitVector#setRegister(long, long)}.
     */
    @Test
    public void getSetRegisterTest() {
        { // locally scoped for sanity
            // NOTE:  registers are only 5bits wide
            final BitVector vector1 = new BitVector(5/*width*/, 128/*count, 2^7*/);
            final BitVector vector2 = new BitVector(5/*width*/, 128/*count, 2^7*/);
            final BitVector vector3 = new BitVector(5/*width*/, 128/*count, 2^7*/);
            final BitVector vector4 = new BitVector(5/*width*/, 128/*count, 2^7*/);

            for(int i=0; i<128/*2^7*/; i++) {
                vector1.setRegister(i, 0x1F);
                vector2.setRegister(i, (i & 0x1F));
                vector3.setRegister(i, ((127 - i) & 0x1F));
                vector4.setRegister(i, 0x15);
            }

            for(int i=0; i<128/*2^7*/; i++) {
                assertEquals(vector1.getRegister(i), 0x1F);
                assertEquals(vector2.getRegister(i), (i & 0x1F));
                assertEquals(vector3.getRegister(i), ((127 - i) & 0x1F));
                assertEquals(vector4.getRegister(i), 0x15);
            }
        }
    }

    // ========================================================================
    /**
     * Tests {@link BitVector#registerIterator()}
     */
    @Test
    public void registerIteratorTest() {
        { // scoped locally for sanity
            // NOTE:  registers are only 5bits wide
            final BitVector vector1 = new BitVector(5/*width*/, 128/*count, 2^7*/);
            final BitVector vector2 = new BitVector(5/*width*/, 128/*count, 2^7*/);
            final BitVector vector3 = new BitVector(5/*width*/, 128/*count, 2^7*/);
            final BitVector vector4 = new BitVector(5/*width*/, 128/*count, 2^7*/);

            for(int i=0; i<128/*2^7*/; i++) {
                vector1.setRegister(i, 0x1F);
                vector2.setRegister(i, (i & 0x1F));
                vector3.setRegister(i, ((127 - i) & 0x1F));
                vector4.setRegister(i, 0x15);
            }

            final LongIterator registerIterator1 = vector1.registerIterator();
            final LongIterator registerIterator2 = vector2.registerIterator();
            final LongIterator registerIterator3 = vector3.registerIterator();
            final LongIterator registerIterator4 = vector4.registerIterator();
            for(int i=0; i<128/*2^7*/; i++) {
                assertEquals(registerIterator1.hasNext(), true);
                assertEquals(registerIterator2.hasNext(), true);
                assertEquals(registerIterator3.hasNext(), true);
                assertEquals(registerIterator4.hasNext(), true);

                assertEquals(registerIterator1.next(), 0x1F);
                assertEquals(registerIterator2.next(), (i & 0x1F));
                assertEquals(registerIterator3.next(), ((127 - i) & 0x1F));
                assertEquals(registerIterator4.next(), 0x15);
            }
            assertEquals(registerIterator1.hasNext(), false/*no more*/);
            assertEquals(registerIterator2.hasNext(), false/*no more*/);
            assertEquals(registerIterator3.hasNext(), false/*no more*/);
            assertEquals(registerIterator4.hasNext(), false/*no more*/);
        }
    }

    // ========================================================================
    /**
     * Tests {@link BitVector#setMaxRegister(long, long)}
     */
    @Test
    public void setMaxRegisterTest() {
        final BitVector vector = new BitVector(5/*width*/, 128/*count, 2^7*/);

        vector.setRegister(0, 10);
        // should replace with a larger value
        vector.setMaxRegister(0, 11);
        assertEquals(vector.getRegister(0), 11);
        // should not replace with a smaller or equal value
        vector.setMaxRegister(0, 9);
        assertEquals(vector.getRegister( 0), 11);
        vector.setMaxRegister(0, 11);
        assertEquals(vector.getRegister(0), 11);
    }

    // ========================================================================
    // fill
    /**
     * Tests {@link BitVector#fill(long)}
     */
    @Test
    public void fillTest() {
        final BitVector vector = new BitVector(5/*width*/, 128/*count, 2^7*/);

        for(int i=0; i<128/*2^7*/; i++) {
            vector.setRegister(i, i);
        }

        vector.fill(0L);

        for(int i=0; i<128/*2^7*/; i++) {
            assertEquals(vector.getRegister(i), 0);
        }

        vector.fill(17L/*arbitrary*/);

        for(int i=0; i<128/*2^7*/; i++) {
            assertEquals(vector.getRegister(i), 17/*arbitrary*/);
        }
    }
}