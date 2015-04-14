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

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.Collator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static se.sawano.java.text.Locales.DANISH;
import static se.sawano.java.text.Locales.SWEDISH;

public class AlphanumericComparatorIT {

    @Test
    public void should_demonstrate_sorting() throws Exception {
        givenStrings("HD 20GB", "HD 2GB");

        whenSorting();

        thenOrderShouldBe("HD 2GB", "HD 20GB");
    }

    @Test
    public void should_sort_file_names() throws Exception {
        givenStrings(readLines("files_unsorted.txt"));

        whenSorting();

        thenOrderShouldBe(readLines("files_sorted.txt"));
    }

    @Test
    public void should_sort_words_without_collator() throws Exception {
        givenStrings(readLines("words_unsorted.txt"));

        whenSorting();

        thenOrderShouldBe(readLines("words_sorted.txt"));
    }

    @Test
    public void should_sort_words_with_swedish_collator() throws Exception {
        givenStrings(readLines("words_unsorted.txt"));

        whenSortingWith(Collator.getInstance(SWEDISH.locale));

        thenOrderShouldBe(readLines("words_sorted.txt"));
    }

    @Test
    public void should_sort_words_with_danish_collator() throws Exception {
        givenStrings(readLines("words_unsorted.txt"));

        whenSortingWith(Collator.getInstance(DANISH.locale));

        thenOrderShouldBe(readLines("words_sorted_dk.txt"));
    }

    private void whenSorting() {
        stringsToSort.sort(new AlphanumericComparator());
    }

    private void whenSortingWith(final Collator collator) {
        stringsToSort.sort(new AlphanumericComparator(collator));
    }

    @SuppressWarnings("unchecked")
    private List<String> readLines(final String fileName) throws IOException {
        return IOUtils.readLines(getClass().getResourceAsStream("/" + fileName));
    }

    private void thenOrderShouldBe(final String... expected) {
        thenOrderShouldBe(asList(expected));
    }

    private void thenOrderShouldBe(final List<String> expected) {
        assertEquals(expected, stringsToSort);
    }

    private void givenStrings(final String... strings) {
        stringsToSort = asList(strings);
    }

    private void givenStrings(final List<String> strings) {
        stringsToSort = strings;
    }

    private List<String> stringsToSort;

}
