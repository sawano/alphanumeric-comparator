package se.sawano.java.text;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.time.Instant.now;
import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;

public class AlphanumericComparatorTest {

    private List<String> stringsToSort;

    @Test
    public void should_sort_as_expected() throws Exception {
        final List<String> input = readLines("unsorted.txt");
        final List<String> expected = readLines("sorted.txt");

        input.sort(new AlphanumericComparator());

        assertEquals(expected, input);
    }

    @Test
    public void should_sort_file_names() throws Exception {
        final List<String> fileNames = readLines("files_unsorted.txt");
        final List<String> expected = readLines("files_sorted.txt");

        fileNames.sort(new AlphanumericComparator(ENGLISH));

        assertEquals(expected, fileNames);
    }

    @Test
    public void should_compare_performance() throws Exception {

        final List<String> list = readLines("dictionary.txt");

        long totalOriginal = sortWith(list, new AlphanumComparator());
        long totalNew = sortWith(list, new AlphanumericComparator());

        System.out.printf("Original: %d, Current: %d\n", totalOriginal, totalNew);
    }

    private long sortWith(final List<String> list, final Comparator comparator) {
        long totalNew = 0;
        for (int i = 0; i < 500; ++i) {
            Collections.sort(list); // reset
            final Duration duration = sort(list).using(comparator);
            totalNew += duration.toMillis();
        }
        return totalNew;
    }

    private Duration using(final Comparator<String> comparator) {
        final Instant start = now();
        stringsToSort.sort(comparator);
        return Duration.between(start, now());
    }

    private AlphanumericComparatorTest sort(final List<String> stringsToSort) {
        this.stringsToSort = stringsToSort;
        return this;
    }

    @SuppressWarnings("unchecked")
    private List<String> readLines(final String fileName) throws IOException {
        return IOUtils.readLines(getClass().getResourceAsStream("/" + fileName));
    }

}
