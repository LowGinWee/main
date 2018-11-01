package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = CliSyntax.COMMAND_DELETE;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Persons: %1$s\n%2$s";

    private final ArrayList<Index> targetIndices;

    public DeleteCommand(ArrayList<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> deletedPersons = new ArrayList<>();

        UserPrefs userPref = new UserPrefs();
        FileEncryptor fe = new FileEncryptor(userPref.getAddressBookFilePath().toString());

        if (fe.isLocked()) {
            throw new CommandException(FileEncryptor.MESSAGE_ADDRESS_BOOK_LOCKED);
        }

        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index : targetIndices) {
            Person personToDelete = lastShownList.get(index.getZeroBased());
            model.deletePerson(personToDelete);
            model.removePersonFromPrediction(personToDelete);
            deletedPersons.add(personToDelete);
        }
        model.commitAddressBook();

        return new CommandResult(buildMessage(deletedPersons));
    }

    private String buildMessage(List<Person> deletedPersons) {
        StringBuilder output = new StringBuilder();
        for (Person person : deletedPersons) {
            output.append(person.getName().fullName).append("\n");
        }
        return String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetIndices.size(), output);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndices.equals(((DeleteCommand) other).targetIndices)); // state check
    }
}
