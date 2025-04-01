package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Deletes a member from an existing group in the address book.
 */
public class DeleteMemberCommand extends Command {
    public static final String COMMAND_WORD = "deletemember";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a member from the group identified "
            + "by the index number used in the displayed group list. "
            + "Parameters: INDEX (must be a positive integer) n/PERSON_NAME\n"
            + "Example: " + COMMAND_WORD + " g/1 n/John Doe";
    public static final String MESSAGE_SUCCESS = "Deleted member: %1$s from group: %2$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person: %1$s is not in group: %2$s";


    private final Index targetIndex;
    private final String member;

    /**
     * Creates a DeleteMemberCommand to remove the specified member from the group at the given index.
     *
     * @param targetIndex The index of the group in the filtered group list.
     * @param member The member to be deleted.
     */
    public DeleteMemberCommand(Index targetIndex, String member) {
        requireNonNull(member);
        this.targetIndex = targetIndex;
        this.member = member;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Retrieve the filtered group list
        List<Group> lastShownList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid Group index");
        }

        Group groupToEdit = lastShownList.get(targetIndex.getZeroBased());

        // Get person from the addressbook
        Person personToRemove = model.getPerson(member);

        // If person exist in adddess book,
        // Check if the person exists in the group with exact name matching (case-sensitive)
        boolean doesPersonExist = groupToEdit.getMembers().stream()
                .anyMatch(person -> person.getName().equals(personToRemove.getName()));

        if (doesPersonExist) {
            groupToEdit.removeMember(personToRemove);
            model.sortFilteredGroupList();
            return new CommandResult(String.format(MESSAGE_SUCCESS, member, groupToEdit.getGroupName().fullName));
        } else {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, member,
                groupToEdit.getGroupName().fullName));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof DeleteMemberCommand otherCommand)) {
            return false;
        }
        return targetIndex == otherCommand.targetIndex && member.equals(otherCommand.member);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("sport", member)
                .toString();
    }
}
