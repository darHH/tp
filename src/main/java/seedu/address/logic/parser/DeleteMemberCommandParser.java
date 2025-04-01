package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteMemberCommand object
 */
public class DeleteMemberCommandParser implements Parser<DeleteMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMemberCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GROUP_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_GROUP_NAME);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_GROUP_NAME).get());

        return new DeleteMemberCommand(index, name.fullName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
