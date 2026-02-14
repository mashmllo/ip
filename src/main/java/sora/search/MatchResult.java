package sora.search;

/**
 * Represents match tracking results used in Jaro-Winkler calculation.
 */
public class MatchResult {

    private final boolean[] candidateMatches;
    private final boolean[] searchMatches;
    private final int matchCount;

    /**
     * Constructs a MatchResult.
     *
     * @param searchMatches     Matched positions in search term
     * @param candidateMatches  Matched positions in candidate string
     * @param matchCount        Number of matching characters
     */
    public MatchResult(boolean[] searchMatches, boolean[] candidateMatches, int matchCount) {
        this.searchMatches = searchMatches;
        this.candidateMatches = candidateMatches;
        this.matchCount = matchCount;
    }

    public boolean[] getCandidateMatches() {
        return candidateMatches;
    }

    public boolean[] getSearchMatches() {
        return searchMatches;
    }

    public int getMatchCount() {
        return matchCount;
    }
}
