import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Book class
class Book {
    String title;
    String author;
    String isbn;
    boolean isAvailable;

    Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true; // Book is available by default
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + isAvailable;
    }
}

// Member class
class Member {
    String name;
    int id;
    ArrayList<Book> borrowedBooks;

    Member(String name, int id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ID: " + id + ", Borrowed Books: " + borrowedBooks.size();
    }
}

// Library class
class Library {
    ArrayList<Book> books;
    HashMap<Integer, Member> members;

    Library() {
        books = new ArrayList<>();
        members = new HashMap<>();
    }

    // Add a book to the library
    void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.title);
    }

    // Remove a book by ISBN
    void removeBook(String isbn) {
        books.removeIf(book -> book.isbn.equals(isbn));
        System.out.println("Book with ISBN " + isbn + " removed.");
    }

    // Search for a book by title
    void searchBook(String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                System.out.println(book);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    // Borrow a book
    void borrowBook(int memberId, String isbn) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        if (member.borrowedBooks.size() >= 5) {
            System.out.println("Member has already borrowed 5 books.");
            return;
        }

        for (Book book : books) {
            if (book.isbn.equals(isbn) && book.isAvailable) {
                book.isAvailable = false;
                member.borrowedBooks.add(book);
                System.out.println("Book borrowed: " + book.title);
                return;
            }
        }
        System.out.println("Book is not available.");
    }

    // Return a book
    void returnBook(int memberId, String isbn) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        for (Book book : member.borrowedBooks) {
            if (book.isbn.equals(isbn)) {
                book.isAvailable = true;
                member.borrowedBooks.remove(book);
                System.out.println("Book returned: " + book.title);
                return;
            }
        }
        System.out.println("Book not found in member's borrowed list.");
    }

    // Add a member
    void addMember(Member member) {
        members.put(member.id, member);
        System.out.println("Member added: " + member.name);
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Example books
        library.addBook(new Book("Swami Vivekananda", "Swami Vivekanand", "ISBN001"));
        library.addBook(new Book("Ratan Tata", "Thomas Mathew", "ISBN002"));

        // Example members
        library.addMember(new Member("Abhi", 1));
        library.addMember(new Member("Brendon", 2));

        int choice;
        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Add Member");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: // Add Book
                    System.out.print("Ratan Tata: ");
                    String title = scanner.next();
                    System.out.print("Thomas Mathew: ");
                    String author = scanner.next();
                    System.out.print("ISBN002");
                    String isbn = scanner.next();
                    library.addBook(new Book(title, author, isbn));
                    break;
                case 2: // Remove Book
                    System.out.print("Enter ISBN: ");
                    isbn = scanner.next();
                    library.removeBook(isbn);
                    break;
                case 3: // Search Book
                    System.out.print("Enter title: ");
                    title = scanner.next();
                    library.searchBook(title);
                    break;
                case 4: // Borrow Book
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    System.out.print("Enter ISBN: ");
                    isbn = scanner.next();
                    library.borrowBook(memberId, isbn);
                    break;
                case 5: // Return Book
                    System.out.print("Enter Member ID: ");
                    memberId = scanner.nextInt();
                    System.out.print("Enter ISBN: ");
                    isbn = scanner.next();
                    library.returnBook(memberId, isbn);
                    break;
                case 6: // Add Member
                    System.out.print("Enter Member Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Member ID: ");
                    int id = scanner.nextInt();
                    library.addMember(new Member(name, id));
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 7);

        scanner.close();
    }
}