//@@author LowGinWee
package seedu.address.model.tag;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueTagList implements Iterable<Tag>{
        private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

        /**
         * Returns true if the list contains an equivalent person as the given argument.
         */
        public boolean contains(Tag toCheck) {
            requireNonNull(toCheck);
            return internalList.stream().anyMatch(toCheck::equals);
        }

        /**
         * Adds a person to the list.
         * The person must not already exist in the list.
         */
        public void add(Tag toAdd) {
            requireNonNull(toAdd);
            if (contains(toAdd)) {
                throw new DuplicateTagException();
            }
            internalList.add(toAdd);
        }

        /**
         * Replaces the person {@code target} in the list with {@code editedPerson}.
         * {@code target} must exist in the list.
         * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
         */
        public void setTag(Tag target, Tag editedTag) {
            requireAllNonNull(target, editedTag);

            int index = internalList.indexOf(target);
            if (index == -1) {
                throw new TagNotFoundException();
            }

            if (!target.equals(editedTag) && contains(editedTag)) {
                throw new DuplicateTagException();
            }

            internalList.set(index, editedTag);
        }

        /**
         * Removes the equivalent person from the list.
         * The person must exist in the list.
         */
        public void remove(Tag toRemove) {
            requireNonNull(toRemove);
            if (!internalList.remove(toRemove)) {
                throw new TagNotFoundException();
            }
        }

        public void setTag(seedu.address.model.tag.UniqueTagList replacement) {
            requireNonNull(replacement);
            internalList.setAll(replacement.internalList);
        }

        /**
         * Replaces the contents of this list with {@code persons}.
         * {@code persons} must not contain duplicate persons.
         */
        public void setTag(List<Tag> tags) {
            requireAllNonNull(tags);
            if (!tagsAreUnique(tags)) {
                throw new DuplicateTagException();
            }

            internalList.setAll(tags);
        }

        /**
         * Returns the backing list as an unmodifiable {@code ObservableList}.
         */
        public ObservableList<Tag> asUnmodifiableObservableList() {
            return FXCollections.unmodifiableObservableList(internalList);
        }

        @Override
        public Iterator<Tag> iterator() {
            return internalList.iterator();
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof seedu.address.model.tag.UniqueTagList // instanceof handles nulls
                    && internalList.equals(((seedu.address.model.tag.UniqueTagList) other).internalList));
        }

        @Override
        public int hashCode() {
            return internalList.hashCode();
        }

        /**
         * Returns true if {@code persons} contains only unique persons.
         */
        private boolean tagsAreUnique(List<Tag> tags) {
            for (int i = 0; i < tags.size() - 1; i++) {
                for (int j = i + 1; j < tags.size(); j++) {
                    if (tags.get(i).equals(tags.get(j))) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

}
