![Build Status](https://github.com/sawano/alphanumeric-comparator/actions/workflows/ci.yaml/badge.svg?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.sawano.java/alphanumeric-comparator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.sawano.java/alphanumeric-comparator)


# Alphanumeric Comparator 
An implementation of a comparator that sort strings in an order that makes sense for a human. I.e. it performs an "alphabetical"/"alphanumeric" comparison instead of a lexicographical comparison.  
It is mainly intended to be used to sort information that are to be read by humans (in UI's etc). So keep in mind that what the "correct" ordering should be is entirely subjective and application specific. 
It should however be straightforward to modify the comparator to suit any specific needs.


## Examples

### Sorting files
Given file names:

    image-1.jpg
    image-4.jpg
    image-10.jpg
    image-02.jpg
    image-22.jpg
    image-9.jpg
    image-11.jpg
    
A standard lexicographical sort would produce the following:

    image-02.jpg
    image-1.jpg
    image-10.jpg
    image-11.jpg
    image-22.jpg
    image-4.jpg
    image-9.jpg
  
But as a human, the numbers in the file name has a meaning an the file "9" should come before the file "10", and "02" should not come before "1".
Using the `AlphanumericComparator` would produce the following result instead:

    image-1.jpg
    image-02.jpg
    image-4.jpg
    image-9.jpg
    image-10.jpg
    image-11.jpg
    image-22.jpg

### Another example

If we sort the strings:

    Hard drive 20GB
    Hard drive 2GB
    
A standard lexicographical sort would produce the following:

    Hard drive 20GB
    Hard drive 2GB

While the `AlphanumericComparator` would produce:

    Hard drive 2GB
    Hard drive 20GB


This comparator also supports the use of a locale-sensitive `Collator` for string comparison. This makes this comparator very flexible for sorting.


### Code example

```java
    @Test
    public void example() {
        final List<String> stringsToSort = asList("HD 20GB", "HD 2GB");

        stringsToSort.sort(new AlphanumericComparator(Locale.ENGLISH));
        
        assertEquals(asList("HD 2GB", "HD 20GB"), stringsToSort);
    }
```

And an example of how to sort using language specific collation rules:

```java
    @Test
    public void should_demonstrate_sorting_with_swedish_collation() {
        final List<String> stringsToSort = asList("Ö", "Ø", "O");

        stringsToSort.sort(new AlphanumericComparator(new Locale("sv", "SE")));

        assertEquals(asList("O", "Ö", "Ø"), stringsToSort);
    }
```

## Download

Releases are available at the Maven central repository. Or you can just use the source code directly if you prefer that.

You can find the latest releases here:

* [alphanumeric-comparator @ Maven Central](https://search.maven.org/search?q=g:se.sawano.java%20AND%20a:alphanumeric-comparator)
* [alphanumeric-comparator @ mvnrepository.com](https://mvnrepository.com/artifact/se.sawano.java/alphanumeric-comparator)
