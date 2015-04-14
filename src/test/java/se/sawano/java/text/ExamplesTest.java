/*
 * Copyright 2015 Daniel Sawano
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

import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static se.sawano.java.text.Locales.DANISH;

public class ExamplesTest {

    @Test
    public void should_demonstrate_sorting_with_swedish_collation() {
        final List<String> stringsToSort = asList("Ö", "Ø", "O");

        stringsToSort.sort(new AlphanumericComparator(new Locale("sv", "SE")));

        assertEquals(asList("O", "Ö", "Ø"), stringsToSort);
    }

    @Test
    public void should_demonstrate_sorting_with_danish_collation() {
        final List<String> stringsToSort = asList("Ö", "Ø", "O");

        stringsToSort.sort(new AlphanumericComparator(DANISH.locale));

        assertEquals(asList("O", "Ø", "Ö"), stringsToSort);
    }

    @Test
    public void should_sort_with_english_locale() {
        final List<String> stringsToSort = asList("HD 20GB", "HD 2GB");

        stringsToSort.sort(new AlphanumericComparator(Locale.ENGLISH));

        assertEquals(asList("HD 2GB", "HD 20GB"), stringsToSort);
    }
}
