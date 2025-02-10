package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import db.Connection;
import org.bson.Document;

import java.util.Arrays;
import java.util.Date;

public class DataInitializer {

    // Initialise des données
    public static void initializeData() {
        String uri = Connection.connect();
        MongoClient client = MongoClients.create(uri);

        MongoCollection<Document> bookCollection = client.getDatabase("library").getCollection("books");
        MongoCollection<Document> userCollection = client.getDatabase("library").getCollection("users");
        MongoCollection<Document> borrowCollection = client.getDatabase("library").getCollection("borrows");

        bookCollection.insertMany(Arrays.asList(
                new Document("title", "1984").append("author", "George Orwell").append("available", true),
                new Document("title", "Brave New World").append("author", "Aldous Huxley").append("available", true),
                new Document("title", "To Kill a Mockingbird").append("author", "Harper Lee").append("available", true),
                new Document("title", "The Great Gatsby").append("author", "F. Scott Fitzgerald").append("available", true),
                new Document("title", "Moby Dick").append("author", "Herman Melville").append("available", true)
        ));
        System.out.println("Livres insérés dans la collection.");

        Document adresseAlice = new Document("zip", "70123").append("ville", "Paris");

        userCollection.insertMany(Arrays.asList(
                new Document("name", "Alice").append("email", "alice@example.com").append("adresse", adresseAlice),
                new Document("name", "Bob").append("email", "bob@example.com"),
                new Document("name", "Charlie").append("email", "charlie@example.com"),
                new Document("name", "David").append("email", "david@example.com"),
                new Document("name", "Eve").append("email", "eve@example.com")
        ));
        System.out.println("Utilisateurs insérés dans la collection.");

        borrowCollection.insertOne(new Document("bookTitle", "1984")
                .append("userName", "Alice")
                .append("borrowDate", new Date()));

        borrowCollection.insertOne(new Document("bookTitle", "Brave New World")
                .append("userName", "Bob")
                .append("borrowDate", new Date()));

        System.out.println("Exemples d'emprunts insérés dans la collection.");

        client.close();
    }
}
