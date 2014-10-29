/*
 * Copyright 2014 Daniel Sawano
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

package se.sawano.java.text;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AlphanumericComparatorTest {

    @Parameterized.Parameters(name = "{index} {1}")
    public static Collection<Object[]> data() {
        return asList(new Object[][]{

                {givenComparator(), whenSorting("image02.jpg", "image1.jpg"), thenOrderShouldBe("image1.jpg", "image02.jpg")},
                {givenComparator(), whenSorting("Hard drive 20GB", "Hard drive 2GB"), thenOrderShouldBe("Hard drive 2GB", "Hard drive 20GB")},
                {givenComparator(), whenSorting("iPhone", "jPhone", "IPhone"), thenOrderShouldBe("IPhone", "iPhone", "jPhone")},
                {givenSwedishComparator(), whenSorting("iPhone", "jPhone", "IPhone"), thenOrderShouldBe("iPhone", "IPhone", "jPhone")},
                {givenComparator(), whenSorting("A", "a"), thenOrderShouldBe("A", "a")},
                {givenComparator(), whenSorting("a", "a"), thenOrderShouldBe("a", "a")},
                {givenSwedishComparator(), whenSorting("A", "a"), thenOrderShouldBe("a", "A")},
                {givenSwedishComparator(), whenSorting("a", "a"), thenOrderShouldBe("a", "a")},
                {givenSwedishComparator(), whenSorting("ä", "a"), thenOrderShouldBe("a", "ä")},
                {givenDanishComparator(), whenSorting("A", "a"), thenOrderShouldBe("A", "a")},
                {givenFrenchComparator(), whenSorting("A", "a"), thenOrderShouldBe("a", "A")},
                {givenComparator(), whenSorting("ä", "b"), thenOrderShouldBe("b", "ä")},
                {givenGermanComparator(), whenSorting("ä", "b"), thenOrderShouldBe("ä", "b")},
                {givenComparator(), whenSorting("10", "1", "-1", "-a", "20", "02", "a"), thenOrderShouldBe("-1", "-a", "1", "02", "10", "20", "a")},
                {givenComparator(), whenSorting("", ""), thenOrderShouldBe("", "")},
                {givenComparator(), whenSorting("0", "0text"), thenOrderShouldBe("0", "0text")},
                {givenComparator(), whenSorting("text0", "text10"), thenOrderShouldBe("text0", "text10")},
                {givenComparator(), whenSorting("some text", "some other text"), thenOrderShouldBe("some other text", "some text")}

        });
    }

    private static List<String> thenOrderShouldBe(final String... strings) {
        return asList(strings);
    }

    private static List<String> whenSorting(final String... strings) {
        return asList(strings);
    }

    private static AlphanumericComparator givenSwedishComparator() {
        return new AlphanumericComparator(new Locale("sv", "SE"));
    }

    private static AlphanumericComparator givenDanishComparator() {
        return new AlphanumericComparator(new Locale("da", "DK"));
    }

    private static AlphanumericComparator givenFrenchComparator() {
        return new AlphanumericComparator(Locale.FRENCH);
    }

    private static AlphanumericComparator givenGermanComparator() {
        return new AlphanumericComparator(Locale.GERMAN);
    }

    private static AlphanumericComparator givenComparator() {
        return new AlphanumericComparator();
    }

    private final Comparator<String> comparator;
    private final List<String> unsorted;
    private final List<String> expectedResult;

    public AlphanumericComparatorTest(final Comparator<String> comparator, final List<String> unsorted, final List<String> expectedResult) {
        this.comparator = comparator;
        this.unsorted = unsorted;
        this.expectedResult = expectedResult;
    }

    @Test
    public void should_sort_correctly() throws Exception {
        unsorted.sort(comparator);
        assertEquals(expectedResult, unsorted);
    }
}
