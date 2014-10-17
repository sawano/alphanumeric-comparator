package se.sawano.java.text;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;

public class AlphanumericComparatorTest {

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

    @SuppressWarnings("unchecked")
    private List<String> readLines(final String fileName) throws IOException {
        return IOUtils.readLines(getClass().getResourceAsStream("/" + fileName));
    }

}
