package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class ListCommandParser implements Parser<ListCommand>{

	public ListCommand parse(String args) throws ParseException {
		String trimmedArgs = args.trim();
		if (trimmedArgs.isEmpty()){
			return new ListCommand();
		}
		ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

		String tagString = argMultimap.getValue(PREFIX_TAG).get();

		return  new ListCommand(tagString);
	}

}
