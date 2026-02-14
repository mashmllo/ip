package sora.search;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import sora.task.Task;

/**
 * Performs keyword-based matching on a collection of {@link Task}..
 * <p>
 * A task is considered a match if:
 * <ul>
 *     <li>The task's string representation contains the keyword (direct match)</li>
 *     <li>THe task is sufficiently similar to the keyword based on fuzzy matching</li>
 * </ul>
 */
public class TaskMatcher {

    private final String keyword;
    private final FuzzyMatcher fuzzyMatcher;

    /**
     * Constructs a {@code TaskManager} with the specified keyword.
     *
     * @param keyword The keyword used to search tasks
     * @throws IllegalArgumentException if the keyword is {@code null} or blank
     */
    public TaskMatcher(String keyword) throws IllegalArgumentException {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be empty");
        }

        this.keyword = keyword;
        this.fuzzyMatcher = new FuzzyMatcher(keyword);
    }

    /**
     * Returns all tasks matching the keyword either by direct substring
     * matching or by fuzzy similarity.
     *
     * @param tasks   All tasks to be evaluated
     * @return Tasks that match the search criteria
     */
    public ArrayList<Task> findMatchingTasks(ArrayList<Task> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("Task list must not be empty");
        }
        return tasks.stream()
                .filter(Objects::nonNull)
                .filter(task -> isMatch(task.toString().toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Determines if searchString matches keyword via substring or fuzzy matching.
     *
     * @param searchString  The text extracted from a task to evaluate
     * @return true if match is found
     */
    private boolean isMatch(String searchString) {
        return isDirectMatch(searchString)
                || fuzzyMatcher.isFuzzySimilar(searchString);
    }

    private boolean isDirectMatch(String searchString) {
        return searchString.contains(this.keyword);
    }
}
