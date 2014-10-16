package se.sawano.java.text;

import java.nio.CharBuffer;
import java.util.Comparator;

import static java.lang.Character.isDigit;
import static java.lang.Math.min;

/*
 * The Alphanum Algorithm is an improved sorting algorithm for strings
 * containing numbers.  Instead of sorting numbers in ASCII order like
 * a standard sort, this algorithm sorts numbers in numeric order.
 *
 * The Alphanum Algorithm is discussed at http://www.DaveKoelle.com
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

/**
 * This comparator is based on the AlphanumComparator found at http://www.DaveKoelle.com and rewritten by Daniel Sawano.
 * <p>
 * This source can be found at https://github.com/sawano/alphanumeric-comparator
 *
 * @author Daniel Sawano
 */
public class AlphanumericComparator implements Comparator<String> {

    private static final int PRE_ALLOCATED_CHUNK_LENGTH = 100;

    @Override
    public int compare(final String s1, final String s2) {
        final CharBuffer b1 = CharBuffer.wrap(s1);
        final CharBuffer b2 = CharBuffer.wrap(s2);

        while (b1.remaining() > 0 && b2.remaining() > 0) {
            final int result = compareChunks(nextChunk(b1), nextChunk(b2));
            if (result != 0) {
                return result;
            }
        }

        return s1.length() - s2.length();
    }

    private int compareChunks(final String thisChunk, final String thatChunk) {
        if (isNumericChunk(thisChunk) && isNumericChunk(thatChunk)) {
            final int numericalResult = compareNumerically(thisChunk, thatChunk);
            if (numericalResult != 0) {
                return numericalResult;
            }
        }

        return thisChunk.compareTo(thatChunk);
    }

    private String nextChunk(final CharBuffer s) {
        final StringBuilder chunk = new StringBuilder(min(s.length(), PRE_ALLOCATED_CHUNK_LENGTH));
        final char first = s.get();
        chunk.append(first);
        final boolean firstWasDigit = isDigit(first);

        while (s.remaining() > 0 && firstWasDigit == isDigit(s.get(s.position()))) {
            chunk.append(s.get());
        }

        return chunk.toString();
    }

    private boolean isNumericChunk(final String chunk) {
        return isDigit(chunk.charAt(0));
    }

    private int compareNumerically(final String thisChunk, String thatChunk) {
        if (thisChunk.length() != thatChunk.length()) {
            return thisChunk.length() - thatChunk.length();
        }

        for (int i = 0; i < thisChunk.length(); i++) {
            final int result = Character.compare(thisChunk.charAt(i), thatChunk.charAt(i));
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }
}