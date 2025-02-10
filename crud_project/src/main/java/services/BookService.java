package services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import db.Connection;
import org.bson.Document;

public class BookService {

    private final MongoCollection<Document> collection;

    public BookService() {
        String uri = Connection.connect();
        MongoClient client = MongoClients.create(uri);
        this.collection = client.getDatabase("library").getCollection("books");
    }

    // Affiche la liste les livres disponibles
    public void listAvailableBooks() {
        System.out.println("Livres disponibles :");
        for (Document book : collection.find(new Document("available", true))) {
            System.out.println(book.toJson());
        }
    }

    // Supprime le contenu de la collection "books"
    public void clearBooks() {
        collection.deleteMany(new Document());
        System.out.println("Toutes les données de la collection des livres ont été supprimées.");
    }
}
