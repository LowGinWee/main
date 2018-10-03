//@@author LowGinWee
package seedu.address.model.tag;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
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
public class UniqueTagList {
    public final ObservableMap<Tag, UniquePersonList> internalList = FXCollections.observableHashMap();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.containsKey(toCheck);
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
        internalList.put(toAdd, new UniquePersonList());
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    //TODO refactor this
    public void add(Person person) {
        requireNonNull(person);
        for (Tag tag : person.getTags()) {
            requireNonNull(tag);
            if (!contains(tag)) {
                internalList.put(tag, new UniquePersonList());
            }

            internalList.get(tag).add(person);
            //TODO to remove
            for(Person p : internalList.get(tag)){
                System.out.println("person with tag " + tag.tagName + " " + p.getName().toString());
            }
        }

    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        if (!internalList.containsKey(target)) {
            throw new TagNotFoundException();
        }

        if (!target.equals(editedTag) && contains(editedTag)) {
            throw new DuplicateTagException();
        }

        internalList.put(editedTag, internalList.get(target));
        internalList.remove(target);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Tag toRemove) {
        requireNonNull(toRemove);
        if (!internalList.containsKey(toRemove)) {
            throw new TagNotFoundException();
        }
        internalList.remove(toRemove);
    }

    //TODO make this method to replace entire hashmap
//    public void setTag(seedu.address.model.tag.UniqueTagList replacement) {
//        requireNonNull(replacement);
//        internalList.setAll(replacement.internalList);
//    }
//
//    /**
//     * Replaces the contents of this list with {@code persons}.
//     * {@code persons} must not contain duplicate persons.
//     */
//    public void setTag(List<Tag> tags) {
//        requireAllNonNull(tags);
//        if (!tagsAreUnique(tags)) {
//            throw new DuplicateTagException();
//        }
//        internalList.setAll(tags);
//    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableMap<Tag, UniquePersonList> asUnmodifiableObservableMap() {
        return FXCollections.unmodifiableObservableMap(internalList);
    }

    //TODO replace iterate
//    @Override
//    public Iterator<Tag> iterator() {
//        return internalList.iterator();
//    }

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
        for (Tag tag : tags) {
            if (internalList.containsKey(tag)) {
                return false;
            }
        }
        return true;
    }



}
