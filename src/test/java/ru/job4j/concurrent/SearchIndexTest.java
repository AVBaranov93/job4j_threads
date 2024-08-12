package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SearchIndexTest {
    @Test
    void whenSmallArrayThenLineSearch() {
        Integer[] source = new Integer[] {1, 2, 3, 4, 5};
        int target = 3;
        int expected = 2;
        SearchIndex<Integer> searchIndex = new SearchIndex<>(source, target, 0, source.length - 1);
        assertThat(searchIndex.findIndex(source, target)).isEqualTo(expected);
    }

    @Test
    void whenBigArrayThenRecursiveSearch() {
        String[] source = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        String target = "f";
        int expected = 5;
        SearchIndex<String> searchIndex = new SearchIndex<>(source, target, 0, source.length - 1);
        assertThat(searchIndex.findIndex(source, target)).isEqualTo(expected);
    }

    @Test
    void whenElementDoesNotExistThenReturnMinusOne() {
        String[] source = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        String target = "r";
        int expected = -1;
        SearchIndex<String> searchIndex = new SearchIndex<>(source, target, 0, source.length - 1);
        assertThat(searchIndex.findIndex(source, target)).isEqualTo(expected);
    }

    @Test
    void whenMathcesLastElementThenReturnIndexOfLastElement() {
        String[] source = new String[] {"a", "b", "c", "d"};
        String target = "d";
        int expected = 3;
        SearchIndex<String> searchIndex = new SearchIndex<>(source, target, 0, source.length - 1);
        assertThat(searchIndex.findIndex(source, target)).isEqualTo(expected);
    }



}