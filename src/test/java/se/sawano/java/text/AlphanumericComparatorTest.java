package se.sawano.java.text;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlphanumericComparatorTest {

    @Test
    public void should_regress_test() throws Exception {
        final List input = FileUtils.readLines(file("/unsorted.txt"));
        final List expected = FileUtils.readLines(file("/sorted.txt"));

        Collections.sort(input, new AlphanumericComparator());

        assertEquals(expected, input);
    }

    private File file(final String name) throws Exception {
        return new File(getClass().getResource(name).toURI());
    }
}
