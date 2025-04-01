package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super("No such person found in your address book!");
    }
}
