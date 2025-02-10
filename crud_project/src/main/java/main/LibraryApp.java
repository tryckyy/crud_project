package main;

import services.BookService;
import services.BorrowService;
import services.UserService;

public class LibraryApp {
  public static void main(String[] args) {

    // Initialise les objets
    UserService userService = new UserService();
    BookService bookService = new BookService();
    BorrowService borrowService = new BorrowService();

    // Vide les collections
    bookService.clearBooks();
    userService.clearUsers();
    borrowService.clearBorrows();

    // Initalise les données
    DataInitializer.initializeData();

    userService.listUsers();

    bookService.listAvailableBooks();
    System.out.println("\nHistorique d'emprunt :\n");
    borrowService.listBorrowedBooks();

    // L'utilisateur Alice emprunte le livre "1984"
    borrowService.borrowBook("1984", "Alice");

    System.out.println("\nHistorique d'emprunt :\n");
    borrowService.listBorrowedBooks();

    // Permet de trouver un utilisateur dans une ville
    System.out.println("\nUtilisateur dans la ville de Paris :\n");
    userService.findUserInCity("Paris");

    System.out.println("\nListe des livres disponibles après emprunt:");
    bookService.listAvailableBooks();

    // Le livre "1984" est retourné
    borrowService.returnBook("1984");

    System.out.println("\nListe des livres disponibles après retour:");
    bookService.listAvailableBooks();

    // L'adresse email de Alice est mise à jour
    userService.updateUserEmail("Alice", "alice.new@example.com");

    // Supprime l'utilisateur Bob
    userService.deleteUser("Bob");

    System.out.println("\nListe des utilisateurs après mise à jour et suppression:");
    userService.listUsers();
  }
}
