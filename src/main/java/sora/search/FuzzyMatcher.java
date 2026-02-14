package sora.search;

import java.util.Arrays;

/**
 * Performs fuzzy string matching using the Jaro-Winkler similarity algorithm.
 * <p>
 * A string is considered similar if at least one word in the input text
 * has a similarity score greater than or equal to the specified threshold.
 */
public class FuzzyMatcher {

    private static final double FUZZY_THRESHOLD = 0.85;

    private final String keyword;

    /**
     * Constructs a {@code FuzzyMatcher} with the specified keyword.
     *
     * @param keyword The keyword used for similarity comparison
     * @throws IllegalArgumentException if the keyword is {@code null} or blank
     */
    public FuzzyMatcher(String keyword)
            throws IllegalArgumentException {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be null or blank");
        }
        this.keyword = keyword.toLowerCase();
    }

    /**
     * Determines if the given text is fuzzy similar to the keyword.
     * <p>
     * The input text is split into words, and each word is compared
     * against the keyword using Jaro-Winkler similarity
     * @param searchString  The text to evaluate
     * @return {@code true} if at least one word meets the similarity threshold,
     *          {@code false} otherwise
     * @throws IllegalArgumentException if {@code searchString} is {@code null} or empty
     */
    public boolean isFuzzySimilar(String searchString)
            throws IllegalArgumentException {
        if (searchString == null || searchString.isBlank()) {
            throw new IllegalArgumentException("search text should not be empty");
        }
        return Arrays.stream(searchString.trim().split("\\s+"))
                .filter(word -> !word.isEmpty())
                .anyMatch(this::isSimilar);
    }

    private boolean isSimilar(String word) {
        double score = JaroWinkler.compute(word.toLowerCase(), this.keyword);
        assert score >= 0.0 && score <= 1.0;
        return score >= FUZZY_THRESHOLD;
    }
}
