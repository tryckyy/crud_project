package services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import db.Connection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class UserService {

    private final MongoCollection<Document> userCollection;

    public UserService() {
        String uri = Connection.connect();
        MongoClient client = MongoClients.create(uri);
        this.userCollection = client.getDatabase("library").getCollection("users");
    }

    // Permet de liste les utilisateurs
    public void listUsers() {
        System.out.println("Liste des utilisateurs :");
        for (Document user : userCollection.find()) {
            System.out.println(user.toJson());
        }
    }

    public void findUserInCity(String city) {
        Document query = new Document("adresse.ville", city);
        for( Document user : userCollection.find(query)) {
            System.out.println(user.get("name"));
        }
    }

    // Permet de mettre a jour l'adresse email d'un utilisateur
    public void updateUserEmail(String name, String newEmail) {
        if (userCollection.find(eq("email", newEmail)).first() == null) {
            userCollection.updateOne(eq("name", name), new Document("$set", new Document("email", newEmail)));
            System.out.println("Email de " + name + " mis à jour à " + newEmail);
        } else {
            System.out.println("L'email '" + newEmail + "' est déjà utilisé.");
        }
    }

    // Permet de supprimer un utilisateur
    public void deleteUser(String name) {
        userCollection.deleteOne(eq("name", name));
        System.out.println("Utilisateur supprimé : " + name);
    }

    // Supprime les données de la collection "users"
    public void clearUsers() {
        userCollection.deleteMany(new Document());
        System.out.println("Toutes les données de la collection des utilisateurs ont été supprimées.");
    }
}
