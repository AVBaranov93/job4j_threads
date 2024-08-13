package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SearchIndexTest {
    @Test
    void whenSmallArrayThenLineSearch() {
        Integer[] source = new Integer[] {1, 2, 3, 4, 5};
        int target = 3;
        int expected = 2;
        assertThat(SearchIndex.findIndex(source, target)).isEqualTo(expected);
    }

    @Test
    void whenBigArrayThenRecursiveSearch() {
        String[] source = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        String target = "f";
        int expected = 5;
        assertThat(SearchIndex.findIndex(source, target)).isEqualTo(expected);
    }

    @Test
    void whenElementDoesNotExistThenReturnMinusOne() {
        String[] source = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        String target = "r";
        int expected = -1;
        assertThat(SearchIndex.findIndex(source, target)).isEqualTo(expected);
    }

    @Test
    void whenMathcesLastElementThenReturnIndexOfLastElement() {
        String[] source = new String[] {"a", "b", "c", "d"};
        String target = "d";
        int expected = 3;
        assertThat(SearchIndex.findIndex(source, target)).isEqualTo(expected);
    }



}