import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Demo {
    public static void main(String[] arguments) {
        System.out.println("Example for the Strategy pattern");
        System.out.println();
        System.out.println("This code uses two Strategy classes, NameSummarizer and OrganizationSummarizer,");
        System.out.println(" to provide a sorted, summarized list for a ContactList. The ContactList object");
        System.out.println(" maintains a collection of Contacts, and delegates the task of representing");
        System.out.println(" its information to an associated object which implements SummarizingStrategy.");
        System.out.println();

        System.out.println("Deserializing stored ContactList from the data.ser file");
        System.out.println();
        if (!(new File("data.ser").exists())) {
            DataCreator.serialize("data.ser");
        }
        ContactList list = (ContactList) (DataRetriever
                .deserializeData("data.ser"));

        System.out.println("Creating NameSummarizer for the ContactList");
        System.out.println("(this Strategy displays only the last and first name,");
        System.out.println(" and sorts the list by last name, followed by the first)");
        list.setSummarizer(new NameSummarizer());

        System.out.println("Name Summarizer Output for the ContactList:");
        System.out.println(list.summarize());
        System.out.println();

        System.out.println("Creating OrganizationSummarizer for the ContactList");
        System.out.println("(this Strategy displays the organization, followed by the first");
        System.out.println(" and last name. It sorts by the organization, followed by last name)");
        list.setSummarizer(new OrganizationSummarizer());

        System.out.println("Organization Summarizer Output for the ContactList:");
        System.out.println(list.summarize());
        System.out.println();
    }
}

interface Contact extends Serializable {
    public static final String SPACE = " ";
    public String getFirstName();
    public String getLastName();
    public String getTitle();
    public String getOrganization();
    public void setFirstName(String newFirstName);
    public void setLastName(String newLastName);
    public void setTitle(String newTitle);
    public void setOrganization(String newOrganization);
}

class ContactImpl implements Contact {
    private String firstName;
    private String lastName;
    private String title;
    private String organization;

    public ContactImpl() {
    }

    public ContactImpl(String newFirstName, String newLastName, String newTitle, String newOrganization) {
        firstName = newFirstName;
        lastName = newLastName;
        title = newTitle;
        organization = newOrganization;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getOrganization() {
        return organization;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setOrganization(String newOrganization) {
        organization = newOrganization;
    }

    public String toString() {
        return firstName + SPACE + lastName;
    }
}

class ContactList implements Serializable {
    private ArrayList contacts = new ArrayList();

    private SummarizingStrategy summarizer;

    public ArrayList getContacts() {
        return contacts;
    }

    public Contact[] getContactsAsArray() {
        return (Contact[]) (contacts.toArray(new Contact[1]));
    }

    public void setSummarizer(SummarizingStrategy newSummarizer) {
        summarizer = newSummarizer;
    }

    public void setContacts(ArrayList newContacts) {
        contacts = newContacts;
    }

    public void addContact(Contact element) {
        if (!contacts.contains(element)) {
            contacts.add(element);
        }
    }

    public void removeContact(Contact element) {
        contacts.remove(element);
    }

    public String summarize() {
        return summarizer.summarize(getContactsAsArray());
    }

    public String[] makeSummarizedList() {
        return summarizer.makeSummarizedList(getContactsAsArray());
    }
}

class DataCreator {
    private static final String DEFAULT_FILE = "data.ser";

    public static void main(String[] args) {
        String fileName;
        if (args.length == 1) {
            fileName = args[0];
        } else {
            fileName = DEFAULT_FILE;
        }
        serialize(fileName);
    }

    public static void serialize(String fileName) {
        try {
            serializeToFile(makeContactList(), fileName);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private static Serializable makeContactList() {
        ContactList list = new ContactList();
        list.addContact(new ContactImpl("David", "St. Hubbins", "Lead Guitar", "The New Originals"));
        list.addContact(new ContactImpl("Mick", "Shrimpton", "Drummer", "The New Originals"));
        list.addContact(new ContactImpl("Nigel", "Tufnel", "Lead Guitar", "The New Originals"));
        list.addContact(new ContactImpl("Derek", "Smalls", "Bass", "The New Originals"));
        list.addContact(new ContactImpl("Viv", "Savage", "Keyboards", "The New Originals"));
        list.addContact(new ContactImpl("Nick", "Shrimpton", "CEO", "Fishy Business, LTD"));
        list.addContact(new ContactImpl("Nickolai", "Lobachevski", "Senior Packer", "Fishy Business, LTD"));
        list.addContact(new ContactImpl("Alan", "Robertson", "Comptroller", "Universal Exports"));
        list.addContact(new ContactImpl("William", "Telle", "President", "Universal Exports"));
        list.addContact(new ContactImpl("Harvey", "Manfredjensenden", "Inspector", "Universal Imports"));
        list.addContact(new ContactImpl("Deirdre", "Pine", "Chief Mechanic", "The Universal Joint"));
        list.addContact(new ContactImpl("Martha", "Crump-Pinnett", "Lead Developer", "Avatar Inc."));
        list.addContact(new ContactImpl("Bryan", "Basham", "CTO", "IOVA"));
        return list;
    }

    private static void serializeToFile(Serializable content, String fileName) throws IOException {
        ObjectOutputStream serOut = new ObjectOutputStream(new FileOutputStream(fileName));
        serOut.writeObject(content);
        serOut.close();
    }
}

class DataRetriever {
    public static Object deserializeData(String fileName) {
        Object returnValue = null;
        try {
            File inputFile = new File(fileName);
            if (inputFile.exists() && inputFile.isFile()) {
                ObjectInputStream readIn = new ObjectInputStream(
                        new FileInputStream(fileName));
                returnValue = readIn.readObject();
                readIn.close();
            } else {
                System.err.println("Unable to locate the file " + fileName);
            }
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace();

        } catch (IOException exc) {
            exc.printStackTrace();

        }
        return returnValue;
    }
}

class NameSummarizer implements SummarizingStrategy {
    private Comparator comparator = new NameComparator();

    public String summarize(Contact[] contactList) {
        StringBuffer product = new StringBuffer();
        Arrays.sort(contactList, comparator);

        for (int i = 0; i < contactList.length; i++) {
            product.append(contactList[i].getLastName());
            product.append(COMMA);
            product.append(SPACE);
            product.append(contactList[i].getFirstName());
            product.append(EOL_STRING);
        }
        return product.toString();
    }

    public String[] makeSummarizedList(Contact[] contactList) {
        Arrays.sort(contactList, comparator);
        String[] product = new String[contactList.length];

        for (int i = 0; i < contactList.length; i++) {
            product[i] = contactList[i].getLastName() + COMMA + SPACE
                    + contactList[i].getFirstName() + EOL_STRING;
        }

        return product;
    }

    private class NameComparator implements Comparator {
        private Collator textComparator = Collator.getInstance();

        public int compare(Object o1, Object o2) {
            Contact c1, c2;
            if ((o1 instanceof Contact) && (o2 instanceof Contact)) {
                c1 = (Contact) o1;
                c2 = (Contact) o2;
                int compareResult = textComparator.compare(c1.getLastName(), c2
                        .getLastName());
                if (compareResult == 0) {
                    compareResult = textComparator.compare(c1.getFirstName(),
                            c2.getFirstName());
                }
                return compareResult;
            } else
                return textComparator.compare(o1, o2);
        }
        public boolean equals(Object o) {
            return textComparator.equals(o);
        }
    }
}

class OrganizationSummarizer implements SummarizingStrategy {
    private Comparator comparator = new OrganizationComparator();

    public String summarize(Contact[] contactList) {
        StringBuffer product = new StringBuffer();
        Arrays.sort(contactList, comparator);
        for (int i = 0; i < contactList.length; i++) {
            product.append(contactList[i].getOrganization());
            product.append(DELIMITER);
            product.append(SPACE);
            product.append(contactList[i].getFirstName());
            product.append(SPACE);
            product.append(contactList[i].getLastName());
            product.append(EOL_STRING);
        }
        return product.toString();
    }

    public String[] makeSummarizedList(Contact[] contactList) {
        Arrays.sort(contactList, comparator);
        String[] product = new String[contactList.length];
        for (int i = 0; i < contactList.length; i++) {
            product[i] = contactList[i].getOrganization() + DELIMITER + SPACE
                    + contactList[i].getFirstName() + SPACE
                    + contactList[i].getLastName() + EOL_STRING;
        }
        return product;
    }

    private class OrganizationComparator implements Comparator {
        private Collator textComparator = Collator.getInstance();

        public int compare(Object o1, Object o2) {
            Contact c1, c2;
            if ((o1 instanceof Contact) && (o2 instanceof Contact)) {
                c1 = (Contact) o1;
                c2 = (Contact) o2;
                int compareResult = textComparator.compare(
                        c1.getOrganization(), c2.getOrganization());
                if (compareResult == 0) {
                    compareResult = textComparator.compare(c1.getLastName(), c2
                            .getLastName());
                }
                return compareResult;
            } else
                return textComparator.compare(o1, o2);
        }
        public boolean equals(Object o) {
            return textComparator.equals(o);
        }
    }
}

interface SummarizingStrategy {
    public static final String EOL_STRING = System.getProperty("line.separator");
    public static final String DELIMITER = ":";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public String summarize(Contact[] contactList);
    public String[] makeSummarizedList(Contact[] contactList);
}

