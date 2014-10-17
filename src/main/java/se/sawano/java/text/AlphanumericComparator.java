package se.sawano.java.text;

import java.nio.CharBuffer;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import static java.lang.Character.isDigit;
import static java.nio.CharBuffer.wrap;
import static java.util.Objects.requireNonNull;

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
 * ---------
 *
 * Author of AlphanumericComparator:  Daniel Sawano, 2014, https://github.com/sawano/alphanumeric-comparator
 * Original authors of tha Alphanum Java implementation include: Daniel Migowski, Andre Bogus, and David Koelle
 *
 */

/**
 * An implementation of a comparator that sort strings in an order that makes sense for a human.
 * <p>
 * This comparator is based on the AlphanumComparator found at http://www.DaveKoelle.com and has been rewritten and modified by Daniel Sawano.
 * <p>
 * This source can be found at https://github.com/sawano/alphanumeric-comparator
 * <p>
 * Note: this algorithm does not take into account numeric notation such as decimals, negative numbers, scientific notation etc. Any non-trivial number will be compared as text rather than numbers.
 *
 * @author Daniel Sawano
 */
public class AlphanumericComparator implements Comparator<String> {

    private final Collator collator;

    /**
     * Creates a comparator that will use lexicographical sorting of the non-numerical parts of the compared strings.
     */
    public AlphanumericComparator() {
        collator = null;
    }

    /**
     * Creates a comparator that will use locale-sensitive sorting of the non-numerical parts of the compared strings.
     *
     * @param locale
     *         the locale to use
     */
    public AlphanumericComparator(final Locale locale) {
        this(Collator.getInstance(requireNonNull(locale)));
    }

    /**
     * Creates a comparator that will use the given collator to sort the non-numerical parts of the compared strings.
     *
     * @param collator
     *         the collator to use
     */
    public AlphanumericComparator(final Collator collator) {
        this.collator = requireNonNull(collator);
    }

    @Override
    public int compare(final String s1, final String s2) {
        final CharBuffer b1 = wrap(s1).asReadOnlyBuffer();
        final CharBuffer b2 = wrap(s2).asReadOnlyBuffer();

        while (b1.remaining() > 0 && b2.remaining() > 0) {
            final int result = compare(nextToken(b1), nextToken(b2));
            if (result != 0) {
                return result;
            }
        }

        return s1.length() - s2.length();
    }

    private int compare(final CharBuffer s1, final CharBuffer s2) {
        if (isNumeric(s1) && isNumeric(s2)) {
            final int numericalResult = compareNumerically(s1, s2);
            if (numericalResult != 0) {
                return numericalResult;
            }
        }

        return compareStrings(s1.toString(), s2.toString());
    }

    private int compareStrings(final String s1, final String s2) {
        if (collator == null) {
            return s1.compareTo(s2);
        }
        return collator.compare(s1, s2);
    }

    private CharBuffer nextToken(final CharBuffer s) {
        final int endOfToken = positionOfNextDigitBoundary(s);
        final CharBuffer chunk = s.subSequence(0, endOfToken - s.position());
        s.position(endOfToken);
        return chunk;
    }

    private int positionOfNextDigitBoundary(final CharBuffer s) {
        int endPos = s.position();
        final boolean firstWasDigit = isDigit(s.get(s.position()));
        while (endPos < s.limit() && firstWasDigit == isDigit(s.get(endPos))) {
            ++endPos;
        }
        return endPos;
    }

    private boolean isNumeric(final CharBuffer string) {
        return isDigit(string.get(string.position()));
    }

    private int compareNumerically(final CharBuffer s1, CharBuffer s2) {
        trimLeadingZeros(s1);
        trimLeadingZeros(s2);

        if (s1.remaining() != s2.remaining()) {
            return s1.remaining() - s2.remaining();
        }

        while (s1.hasRemaining()) {
            final int result = Character.compare(s1.get(), s2.get());
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }

    private void trimLeadingZeros(final CharBuffer numericalString) {
        while (numericalString.remaining() > 0 && numericalString.get(numericalString.position()) == '0') {
            numericalString.position(numericalString.position() + 1);
        }
    }
}