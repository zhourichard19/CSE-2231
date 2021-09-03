import components.map.Map;
import components.map.Map1L;

/**
 * Interface for email accounts provided by Ohio State. Each account instance is
 * associated with 3 pieces of information: first name, last name, and email
 * address. First and last names contain only alphabetic characters (hyphenated
 * names and apostrophes, for example, are disallowed). Email addresses are
 * strings of the form "name.N@osu.edu", where name is the person's last name,
 * and N is a positive integer that ensures uniqueness of each email address.
 *
 * @mathsubtypes <pre>
 * EMAIL_ACCOUNT_MODEL is (
 *   firstName: string of character,
 *   lastName: string of character,
 *   emailAddress: string of character
 *  )
 *  exemplar a
 *  constraint
 *   [a.firstName contains only alphabetical characters]  and
 *   [a.lastName contains only alphabetical characters]  and
 *   [a.emailAddress is of the form name.N@osu.edu, where name is a.lastName
 *    all in lower case letters and N is a unique positive integer]
 * </pre>
 * @mathmodel type EmailAccount is modeled by EMAIL_ACCOUNT_MODEL
 * @initially <pre>
 * String first, String last:
 *  requires
 *   [first contains only alphabetical characters]  and
 *   [last contains only alphabetical characters]
 *  ensures
 *   this =
 *    (first, last,
 *     [email address of the form name.N@osu.edu where name is last all in
 *      lower case letters and N is the smallest positive integer that has
 *      not been used yet for the given last name])
 * </pre>
 */
public interface EmailAccount {

    /**
     * Returns the {@code EmailAccount}'s full name.
     *
     * @return the full name
     * @ensures name = [this full name]
     */
    String name();

    /**
     * Returns the {@code EmailAccount}'s email address.
     *
     * @return the email address
     * @ensures emailAddress = [this email address]
     */
    String emailAddress();

    /**
     * Returns a {@code String} representation of the {@code EmailAccount}.
     *
     * @return a representation of {@code this}
     * @ensures <pre>
     * toString = "Name: " * [full name] * ", Email: " * [email address]
     * </pre>
     */
    @Override
    String toString();

    /**
     * Implementation of {@code EmailAccount}.
     *
     * @author Put your name here
     *
     */
    public final class EmailAccount1 implements EmailAccount {

        /*
         * Private members
         * --------------------------------------------------------
         */

        // TODO - declare static and instance data members
        String firstName;
        String lastName;
        String email;
        static Map<String, Integer> map = new Map1L<>();

        /*
         * Constructor
         * ------------------------------------------------------------
         */

        /**
         * Constructor.
         *
         * @param firstName
         *            the first name
         * @param lastName
         *            the last name
         */
        public EmailAccount1(String firstName, String lastName) {

            this.firstName = firstName;
            this.lastName = lastName;

            Integer dotNumber = map.value(lastName.toLowerCase());

            if (map.hasKey(lastName.toLowerCase())) {
                dotNumber++;
                this.email = lastName.toLowerCase() + "." + dotNumber
                        + "@buckeyemail.osu.edu";
                map.replaceValue(lastName.toLowerCase(), dotNumber);
            } else {
                map.add(lastName.toLowerCase(), 1);
                this.email = lastName.toLowerCase() + ".1@buckeyemail.osu.edu";
            }

        }

        /*
         * Methods
         * ----------------------------------------------------------------
         */

        @Override
        public String name() {

            String name = this.firstName + " " + this.lastName;
            return name;

        }

        @Override
        public String emailAddress() {

            return this.email;

        }

        @Override
        public String toString() {

            String full = "Full name is" + this.firstName + " " + this.lastName
                    + \n + "Email is " + this.email;
            return full;
        }

    }
}