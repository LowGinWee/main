package seedu.address.logic.parser;

//TODO REMOVE THIS GW
//<<<<<<< HEAD
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//
//import seedu.address.logic.commands.ListCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.tag.Tag;
//
//public class ListCommandParser implements Parser<ListCommand>{
//
//	public ListCommand parse(String args) throws ParseException {
//		String trimmedArgs = args.trim();
//		if (trimmedArgs.isEmpty()){
//			return new ListCommand();
//		}
//		ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
//
//		String tagString = argMultimap.getValue(PREFIX_TAG).get();
//
//		return  new ListCommand(tagString);
//	}
//
//=======
import static seedu.address.logic.parser.CliSyntax.PREFIX_KPI;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the list command arguments to determine which attributes to filter for listing.
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME,
                PREFIX_TAG,
                PREFIX_KPI);

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            return new ListCommand(ListCommand.TYPE_TAG, ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));
        } else if (argMultimap.getValue(PREFIX_KPI).isPresent()) {
            return new ListCommand(ListCommand.TYPE_KPI, argMultimap.getAllValues(PREFIX_KPI));
        } else {
            return new ListCommand(ListCommand.TYPE_ALL, new ArrayList<>());
        }
    }
}
