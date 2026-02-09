package sora.command;

import java.util.ArrayList;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represents a command that finds a task based on keyword in the description
 * using normal and fuzzy search.
 *
 * The command first performs a substring match. If no substring match is found,
 * it applies Jaro-Winkler similarity to detect approximate matches.
 */
public class FindCommand implements Command {

    private static final double FUZZY_THRESHOLD = 0.85;
    private static final double WINKLER_PREFIX_WEIGHT = 0.1;
    private static final int WINKLER_MAX_PREFIX = 4;
    private static final int MATCH_DISTANCE_DIVISOR = 2;
    private static final int MATCH_DISTANCE_ADJUST = 1;
    private final String keyword;

    /**
     * Constructs a {@link FindCommand} with the given keyword.
     * @param keyword Keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.trim().toLowerCase();
    }

    /**
     * Executes the find command by searching tasks containing the keyword.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException If an error occurs during command execution.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        assert taskManager != null : "TaskManager object should not be null";
        assert ui != null : "Ui object should not be null";

        ArrayList<Task> tasks = taskManager.getTasks();
        ArrayList<Task> matches = findMatchingTasks(tasks);

        ui.showSearchResult(matches, keyword);
    }

    /**
     * Returns all tasks matching the keyword either by direct substring
     * matching or by fuzzy similarity.
     *
     * @param tasks   All tasks to be evaluated
     * @return Tasks that match the search criteria
     */
    private ArrayList<Task> findMatchingTasks(ArrayList<Task> tasks) {
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            String taskName = task.toString().toLowerCase();

            if (isMatch(taskName)) {
                matches.add(task);
            }
        }

        return matches;
    }

    /**
     * Determines if searchString matches keyword via substring or fuzzy matching.
     *
     * @param searchString  The keyword to match
     * @return true if match is found
     */
    private boolean isMatch(String searchString) {
        if (searchString.contains(this.keyword)) {
            return true;
        }

        return isFuzzySimilar(searchString);
    }

    private boolean isFuzzySimilar(String searchString) {
        String[] words = searchString.split("\\s+");

        for (String word : words) {
            double similarity = jaroWinkler(word, this.keyword);
            if (similarity >= FUZZY_THRESHOLD) {
                return true;
            }
        }

        return false;
    }
    /**
     * Computes Jaro-Winkler similarity between two strings.
     *
     * @param candidate   The text to be searched
     * @param searchTerm   The keyword entered by the user
     * @return Similarity score between 0.0 and 1.0
     */
    private static double jaroWinkler(String candidate, String searchTerm) {
        if (candidate.equals(searchTerm)) {
            return 1.0;
        }

        int matchDistance = calculateMatchDistance(candidate, searchTerm);
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

        if (matchCount == 0) {
            return 0.0;
        }

        double transpositions = 0;
        int index = 0;
        for (int i = 0; i < searchTerm.length(); i++) {
            if (!searchMatches[i]) {
                continue;
            }

            while (index < candidateMatches.length && !candidateMatches[index]) {
                index++;
            }

            if (searchTerm.charAt(i) != candidate.charAt(index)) {
                transpositions++;
            }

            index++;
        }

        transpositions /= 2.0;

        double jaroScore = ((matchCount / (double) searchTerm.length())
                + (matchCount / (double) candidate.length())
                + ((matchCount - transpositions) / matchCount)
                ) / 3.0;

        int prefixLength = commonPrefixLength(candidate, searchTerm);

        return jaroScore + WINKLER_PREFIX_WEIGHT * prefixLength * (1 - jaroScore);

    }

    private static int calculateMatchDistance(String candidate,
                                              String searchTerm) {
        int maxLen = Math.max(candidate.length(), searchTerm.length());
        int half = maxLen / MATCH_DISTANCE_DIVISOR;
        return half - MATCH_DISTANCE_ADJUST;
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
