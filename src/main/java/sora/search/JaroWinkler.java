package sora.search;

/**
 * Provides utility methods to compute Jaro-Winkler similarity between two strings.
 * <p>
 * Jaro-Winkler similarity measures the similarity between two strings based on:
 * <ul>
 *     <li>Number of matching characters</li>
 *     <li>Number of transpositions</li>
 *     <li>Common prefix length</li>
 * </ul>
 * <p>
 * The returned similarity score ranges from:
 * <ul>
 *     <li>{@code 1.0} -> exact match</li>
 *     <li>{@code 0.0} -> no similarity</li>
 * </ul>
 */
public class JaroWinkler {

    private static final double WINKLER_PREFIX_WEIGHT = 0.1;
    private static final int WINKLER_MAX_PREFIX = 4;
    private static final int MATCH_DISTANCE_DIVISOR = 2;
    private static final int MATCH_DISTANCE_ADJUST = 1;

    /**
     * Computes the Jaro-Winkler similarity score between two strings.
     *
     * @param candidate  The candidate string to compare.
     * @param searchTerm The reference string used as the search term.
     * @return Similarity score between {@code 0.0} and {@code 1.0}.
     * @throws IllegalArgumentException if either string is null.
     */
    public static double compute(String candidate, String searchTerm)
            throws IllegalArgumentException {
        if (candidate == null || searchTerm == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (candidate.equals(searchTerm)) {
            return 1.0;
        }

        int matchDistance = calculateMatchDistance(candidate, searchTerm);

        MatchResult matches = findMatches(candidate, searchTerm, matchDistance);

        if (matches.getMatchCount() == 0) {
            return 0.0;
        }

        double transpositions = countTranspositions(candidate, searchTerm, matches);
        double jaroScore = calculateJaroScore(candidate, searchTerm,
                matches.getMatchCount(), transpositions);

        return applyWinklerBoost(candidate, searchTerm, jaroScore);
    }

    private static MatchResult findMatches(String candidate,
                                           String searchTerm, int matchDistance) {

        boolean[] searchMatches = new boolean[searchTerm.length()];
        boolean[] candidateMatches = new boolean[candidate.length()];

        int matchCount = 0;
        for (int i = 0; i < searchTerm.length(); i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(i + matchDistance + 1, candidate.length());

            for (int j = start; j < end; j++) {
                if (candidateMatches[j]) {
                    continue;
                }

                if (searchTerm.charAt(i) != candidate.charAt(j)) {
                    continue;
                }

                candidateMatches[j] = true;
                searchMatches[i] = true;
                matchCount++;
                break;
            }
        }

        return new MatchResult(searchMatches, candidateMatches, matchCount);
    }

    /**
     * Counts the number of character transpositions between matched characters.
     * <p>
     * A transposition occurs when two matching characters appear in different positions relative to each other.
     *
     * @param candidate   The candidate string.
     * @param searchTerm  The search term string.
     * @param matches     The {@link MatchResult} containing match flags.
     * @return Number of transpositions divided by two, as required by the Jaro formula.
     */
    public static double countTranspositions(String candidate,
                                             String searchTerm, MatchResult matches) {
        boolean[] searchMatches = matches.getSearchMatches();
        boolean[] candidateMatches = matches.getCandidateMatches();

        int transpositions = 0;
        int index = 0;
        for (int i = 0; i < searchTerm.length(); i++) {
            if (!searchMatches[i]) {
                continue;
            }

            while (index < candidateMatches.length && !candidateMatches[index]) {
                index++;
            }

            if (index < candidateMatches.length
                    && searchTerm.charAt(i) != candidate.charAt(index)) {
                transpositions++;
            }

            index++;
        }

        return transpositions / 2.0;
    }

    private static double calculateJaroScore(String candidate,
                                             String searchTerm, int matchCount,
                                             double transpositions) {
        return ((matchCount / (double) searchTerm.length())
                + (matchCount / (double) candidate.length())
                + ((matchCount - transpositions) / matchCount)) / 3.0;
    }

    private static double applyWinklerBoost(String candidate, String searchTerm, double jaroScore) {
        int prefixLength = commonPrefixLength(candidate, searchTerm);

        return jaroScore + WINKLER_PREFIX_WEIGHT * prefixLength * (1 - jaroScore);
    }

    private static int calculateMatchDistance(String candidate,
                                              String searchTerm) {
        int maxLen = Math.max(candidate.length(), searchTerm.length());
        int half = maxLen / MATCH_DISTANCE_DIVISOR;
        return Math.max(0, half - MATCH_DISTANCE_ADJUST);
    }

    private static int commonPrefixLength(String s1, String s2) {
        int limit = Math.min(Math.min(s1.length(), s2.length()), WINKLER_MAX_PREFIX);
        int length = 0;
        while (length < limit && s1.charAt(length) == s2.charAt(length)) {
            length++;
        }
        return length;
    }
}
