package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {

        assertTrue(ALICE.isSamePerson(ALICE));


        assertFalse(ALICE.isSamePerson(null));


        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));


        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));


        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));


        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {

        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));


        assertTrue(ALICE.equals(ALICE));


        assertFalse(ALICE.equals(null));


        assertFalse(ALICE.equals(5));


        assertFalse(ALICE.equals(BOB));


        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));


        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));


        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));


        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));


        editedAlice = new PersonBuilder(ALICE).withPostalCode(VALID_POSTAL_CODE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));


        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        Person alice = new PersonBuilder(ALICE).build();
        String expected = Person.class.getCanonicalName() + "{name=" + alice.getName() + ", phone=" + alice.getPhone()
                + ", email=" + alice.getEmail() + ", address=" + alice.getAddress()
                + ", postalCode=" + alice.getPostalCode()
                + ", tags=" + alice.getTags()
                + ", sports=" + alice.getSports().toString() + "}";
        assertEquals(expected, alice.toString());
    }

    @Test
    public void getSports_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getSports().remove(0));
    }

    @Test
    public void hasSport_personWithNoSports_returnsFalse() {
        Person person = new PersonBuilder().build();
        Sport sport = new Sport("basketball");
        assertFalse(person.getSports().contains(sport));
    }

    @Test
    public void hasSport_personWithSport_returnsTrue() {
        Person person = new PersonBuilder().withSport("soccer").build();
        assertTrue(person.getSports().contains(new Sport("soccer")));
    }

    @Test
    public void addSport_success() {
        Person originalPerson = new PersonBuilder().build();
        List<Sport> updatedSports = new ArrayList<>(originalPerson.getSports());
        updatedSports.add(new Sport("tennis"));

        Person updatedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                originalPerson.getTags(),
                updatedSports);

        assertTrue(updatedPerson.getSports().contains(new Sport("tennis")));
        assertEquals(originalPerson.getSports().size() + 1, updatedPerson.getSports().size());
    }

    @Test
    public void deleteSport_success() {
        Person originalPerson = new PersonBuilder().withSports("volleyball", "cricket").build();
        List<Sport> updatedSports = new ArrayList<>(originalPerson.getSports());
        updatedSports.remove(new Sport("cricket"));

        Person updatedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                originalPerson.getTags(),
                updatedSports);

        assertFalse(updatedPerson.getSports().contains(new Sport("cricket")));
        assertEquals(originalPerson.getSports().size() - 1, updatedPerson.getSports().size());
    }

    @Test
    public void constructor_nullPostalCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Person(
                new Name("Valid Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Address("Valid Address"),
                null,
                new HashSet<>(),
                new ArrayList<>()));
    }

    @Test
    public void getPostalCode_returnsCorrectPostalCode() {
        String expectedPostalCode = "018906";
        Person person = new PersonBuilder().withPostalCode(expectedPostalCode).build();
        assertEquals(expectedPostalCode, person.getPostalCode());
    }

    @Test
    public void withDifferentPostalCode_createsNewPersonWithDifferentPostalCode() {
        String originalPostalCode = "018906";
        String newPostalCode = "018935";

        Person originalPerson = new PersonBuilder().withPostalCode(originalPostalCode).build();
        Person newPerson = new PersonBuilder(originalPerson).withPostalCode(newPostalCode).build();

        assertEquals(originalPostalCode, originalPerson.getPostalCode());
        assertEquals(newPostalCode, newPerson.getPostalCode());
        assertFalse(originalPerson.equals(newPerson));
    }

    @Test
    public void equals_samePostalCodeDifferentAddresses_returnsFalse() {
        String samePostalCode = "018906";

        Person person1 = new PersonBuilder()
                .withPostalCode(samePostalCode)
                .withAddress("1 Test Street")
                .build();

        Person person2 = new PersonBuilder()
                .withPostalCode(samePostalCode)
                .withAddress("2 Different Street")
                .build();

        assertFalse(person1.equals(person2));
    }

    @Test
    public void toString_includesPostalCode() {
        String postalCode = "018906";
        Person person = new PersonBuilder().withPostalCode(postalCode).build();


        assertTrue(person.toString().contains(postalCode));
    }

    @Test
    public void getSportList_modifyReturnedList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getSportList().asUnmodifiableList().add(
            new Sport("tennis")));
    }

    @Test
    public void getSportList_modifyOriginalSportList_doesNotAffectPerson() {

        Person originalPerson = new PersonBuilder().withSports("soccer").build();

        SportList sportList = originalPerson.getSportList();


        sportList.add(new Sport("tennis"));


        System.out.println("Original person's sports: " + originalPerson.getSports());
        System.out.println("Modified SportList: " + sportList);


        assertFalse(originalPerson.getSports().contains(new Sport("tennis")));
        assertEquals(1, originalPerson.getSports().size());
    }

    @Test
    public void addSport_withSportList_success() {
        Person originalPerson = new PersonBuilder().build();
        SportList updatedSports = originalPerson.getSportList();
        updatedSports.add(new Sport("tennis"));

        Person updatedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                originalPerson.getPostalCode(),
                originalPerson.getTags(),
                updatedSports);

        assertTrue(updatedPerson.getSports().contains(new Sport("tennis")));
        assertEquals(originalPerson.getSports().size() + 1, updatedPerson.getSports().size());
    }

    @Test
    public void deleteSport_withSportList_success() {
        Person originalPerson = new PersonBuilder().withSports("volleyball", "cricket").build();
        SportList updatedSports = originalPerson.getSportList();
        updatedSports.remove(new Sport("cricket"));

        Person updatedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                originalPerson.getPostalCode(),
                originalPerson.getTags(),
                updatedSports);

        assertFalse(updatedPerson.getSports().contains(new Sport("cricket")));
        assertEquals(originalPerson.getSports().size() - 1, updatedPerson.getSports().size());
    }
}
