package services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import db.Connection;
import org.bson.Document;

import java.util.Date;

import static com.mongodb.client.model.Filters.*;

public class BorrowService {

    private final MongoCollection<Document> bookCollection;
    private final MongoCollection<Document> borrowCollection;

    public BorrowService() {
        String uri = Connection.connect();
        MongoClient client = MongoClients.create(uri);
        this.bookCollection = client.getDatabase("library").getCollection("books");
        this.borrowCollection = client.getDatabase("library").getCollection("borrows");
    }

    public void listBorrowedBooks() {
        for( Document borrow : borrowCollection.find() ) {
            System.out.println(borrow.toJson());
        }
    }

    // Permet d'emprunter un livre si il est marqué comme disponible
    public void borrowBook(String bookTitle, String userName) {
        Document book = bookCollection.find(and(eq("title", bookTitle), eq("available", true))).first();

        if (book != null) {
            bookCollection.updateOne(eq("title", bookTitle), Updates.set("available", false));

            Document borrowRecord = new Document("bookTitle", bookTitle)
                    .append("userName", userName)
                    .append("borrowDate", new Date());
            borrowCollection.insertOne(borrowRecord);

            System.out.println("Le livre '" + bookTitle + "' a été emprunté par " + userName + ".");
        } else {
            System.out.println("Le livre '" + bookTitle + "' n'est pas disponible.");
        }
    }

    // Permet de retourner un livre emprunté
    public void returnBook(String bookTitle) {
        Document borrowRecord = borrowCollection.find(eq("bookTitle", bookTitle)).first();

        if (borrowRecord != null) {
            bookCollection.updateOne(eq("title", bookTitle), Updates.set("available", true));
            borrowCollection.deleteOne(eq("bookTitle", bookTitle));

            System.out.println("Le livre '" + bookTitle + "' a été retourné.");
        } else {
            System.out.println("Aucun emprunt trouvé pour le livre '" + bookTitle + "'.");
        }
    }

    // Supprime les données de la collection "borrow"
    public void clearBorrows() {
        borrowCollection.deleteMany(new Document());
        System.out.println("Toutes les données de la collection des emprunts ont été supprimées.");
    }
}
