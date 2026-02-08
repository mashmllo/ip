package sora.parser;

import sora.exception.UnknownCommandException;

/**
 * Enum representing all valid user commands in the system.
 * <p>
 * Each command is identified by a keyword string. This enum
 * provides a method to map user input to the corresponding {@link CommandType}.
 */
public enum CommandType {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    ON("on"),
    FIND("find"),
    TODO("todo"),
    EVENT("event"),
    DEADLINE("deadline");

    private final String keyword;

    CommandType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    /**
     * Maps user input string to the corresponding {@link CommandType}.
     *
     * @param input User input string
     * @return the corresponding {@link CommandType}
     * @throws UnknownCommandException if no matching command type exists
     */
    public static CommandType fromString(String input)
            throws UnknownCommandException {
        assert input != null : "Command input cannot be null";

        for (CommandType type : values()) {
            if (type.keyword.equals(input)) {
                return type;
            }
        }

        throw new UnknownCommandException();

    }

}
