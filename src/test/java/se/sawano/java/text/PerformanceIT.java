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
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static java.time.Instant.now;

public class PerformanceIT {

    private List<String> stringsToSort;

    @Test
    public void should_test_performance() throws Exception {
        final List<String> list = readLines("dictionary.txt");

        final long total = sortWith(list, new AlphanumericComparator());

        System.out.printf("Sorted %d words 500 times in %d ms\n", list.size(), total);
    }

    @Test
    //@Ignore("Collation is slow")
    public void should_test_performance_with_collation() throws Exception {
        final List<String> list = readLines("dictionary.txt");

        final long total = sortWith(list, new AlphanumericComparator(Locale.ENGLISH));

        System.out.printf("Sorted %d words 500 times in %d ms\n", list.size(), total);
    }

    private long sortWith(final List<String> list, final Comparator<CharSequence> comparator) {
        long totalNew = 0;
        for (int i = 0; i < 500; ++i) {
            Collections.sort(list); // reset
            final Duration duration = whenSorting(list).using(comparator);
            totalNew += duration.toMillis();
        }
        return totalNew;
    }

    private PerformanceIT whenSorting(final List<String> stringsToSort) {
        this.stringsToSort = stringsToSort;
        return this;
    }

    @SuppressWarnings("unchecked")
    private List<String> readLines(final String fileName) throws IOException {
        return IOUtils.readLines(getClass().getResourceAsStream("/" + fileName));
    }

    private Duration using(final Comparator<CharSequence> comparator) {
        final Instant start = now();
        stringsToSort.sort(comparator);
        return Duration.between(start, now());
    }

}
