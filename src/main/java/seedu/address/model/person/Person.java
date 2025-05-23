package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.LocationUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {


    private final Name name;
    private final Phone phone;
    private final Email email;


    private final Address address;
    private final Location location;
    private final Set<Tag> tags = new HashSet<>();
    private final SportList sports = new SportList();

    /**
     * Constructor for Person class returns an immutable Person object.
     */
    public Person(Name name, Phone phone, Email email, Address address, String postalCode, Set<Tag> tags,
                  List<Sport> sports) {
        requireAllNonNull(name, phone, email, address, tags, sports);
        requireNonNull(postalCode, "Postal code cannot be null");
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        for (Sport sport : sports) {
            this.sports.add(sport);
        }
        this.location = LocationUtil.createLocation(address, postalCode);
    }

    /**
     * Constructor for Person class returns an immutable Person object. Left here for compatibility.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, List<Sport> sports) {
        requireAllNonNull(name, phone, email, address, tags, sports);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        for (Sport sport : sports) {
            this.sports.add(sport);
        }
        this.location = LocationUtil.createLocation(address, "018935");
    }
    /**
     * Constructor for Person class returns an immutable Person object. Left here for compatibility.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        this.location = LocationUtil.createLocation(address, "018935");
    }

    /**
     * Constructor for Person class with SportList parameter.
     */
    public Person(Name name, Phone phone, Email email, Address address, String postalCode, Set<Tag> tags,
                 SportList sports) {
        requireAllNonNull(name, phone, email, address, tags, sports);
        requireNonNull(postalCode, "Postal code cannot be null");
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        for (Sport sport : sports) {
            this.sports.add(sport);
        }
        this.location = LocationUtil.createLocation(address, postalCode);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns the postal code from the person's location.
     * This method retrieves the postal code from the Location object rather than storing it separately
     * since Location encapsulates both the address and postal code information.
     * Always use this method to access the postal code instead of direct field access.
     */
    public String getPostalCode() {
        return location.getPostalCode();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable list of sports.
     */
    public List<Sport> getSports() {
        return sports.asUnmodifiableList();
    }

    /**
     * Returns a completely independent copy of the SportList object.
     */
    public SportList getSportList() {

        List<Sport> sportsCopy = new ArrayList<>();
        for (Sport sport : sports.asUnmodifiableList()) {
            sportsCopy.add(new Sport(sport.sportName));
        }
        return new SportList(sportsCopy);
    }

    public Location getLocation() {
        return this.location;
    }

    /**
     * Delete a sport from the person's list of sports.
     */
    public void deleteSport(Sport sport) {
        sports.remove(sport);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && getPostalCode().equals(otherPerson.getPostalCode())
                && tags.equals(otherPerson.tags)
                && sports.equals(otherPerson.sports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, getPostalCode(), tags, sports);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("postalCode", getPostalCode())
                .add("tags", tags)
                .add("sports", "[" + sports.toString() + "]")
                .toString();
    }
}
