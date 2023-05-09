// Set MYPATH=D:\tpmongodb2021
// javac -g -cp %MYPATH%\mongojar\mongo-java-driver-3.12.7.jar;%MYPATH% %MYPATH%\tpjava\Client.java
// java -Xmx256m -Xms256m -cp %MYPATH%\mongojar\mongo-java-driver-3.12.7.jar;%MYPATH% tpjava.Client


// javac -g -cp %MYPATH%\mongojar\mongo-java-driver-3.12.7.jar;%MYPATH%\mongojar\commons-io-2.4.jar;%MYPATH% %MYPATH%\tpjava\Client.java
// java -Xmx256m -Xms256m -cp %MYPATH%\mongojar\mongo-java-driver-3.12.7.jar;%MYPATH%\mongojar\commons-io-2.4.jar;%MYPATH% tpjava.Client


/*
Mac OS
MYPATH=/Users/billy/Documents/Mopolo/tpmongodb
javac -cp "$MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH" "$MYPATH/tpjava/Client.java"
java -Xmx256m -Xms256m -cp "$MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH" tpjava.Client


export MYPATH=~/Bureau/tpmongodb
javac -g -cp $MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH $MYPATH/tpjava/Client.java
java -cp $MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH tpjava.Client



*/


/*
+++++++++++++++++++++++++++++++++++++++++ TRAVAIL A FAIRE : COMPLETER LES METHODES SUIVANTES ++++++++++++++++++++++++++++
public void createCollectionClient(String nomCollection);
public void dropCollectionClient(String nomCollection);
public void insertOneClient(String nomCollection, Document client);
public void testInsertOneClient();
public void insertManyClients(String nomCollection, List<Document> clients);
public void testInsertManyClients();
public void getClientById(String nomCollection, Integer ClientId);
public void getClients(String nomCollection, Document whereQuery, Document projectionFields, Document sortFields);
public void updateClients(String nomCollection, Document whereQuery, Document updateExpressions, UpdateOptions updateOptions);
public void deleteClients(String nomCollection, Document filters);
public void displayIterator(Iterator it, String message);
public void joinLocalAndforeignCollections(
    String localCollectionName,
    String foreignCollectionName,
    String localColJoinFieldName,
    String foreigColJoinFieldName,
    Document whereQuery
    );
public void loadClientsFromJsonArrayFile (
    String collectionName,
    String filePath,
    String fileName);

public void loadClientsFromCSVFile(
    String collectionName,
    String filePath,
    String fileName
    );

public void loadClientsFromJsonArrayFileWithMongoImport(
    String dbName,
    boolean isJsonArray,
    String collectionName,
    String filePath,
    String fileName
    );

public void loadClientsFromCSVFileWithMongoImport(
    String dbName,
    boolean isCsvFile,
    String collectionName,
    String filePath,
    String fileName
    );

public void ajouterUneAppreciationAUnVol();

*/

package tpjava.fr.miage.nosql;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Client {
    private MongoDatabase database;
    private String dbName = "airbaseDB";
    private String hostName = "localhost";
    private int port = 27017;
    private String userName = "uairbase";
    private String passWord = "UairbasePass";
    private String clientCollectionName = "colClients";
    private String volCollectionName = "colVols";

    private String clientsFilePath = "C:\\TRAVAUX_PRATIQUES\\TPMONGO\\2021_2022\\MBDS_EMSI\\2Exercices\\tpjava\\";
    private String clientsCsvFileName = "0_4Json_collection_Import_Clients_Airbase.csv";
    private String clientsJsonArrayFileName = "0_4Json_collection_Import_Clients_Airbase.json";
    private String destClientsCsvFileName = "0_45Json_collection_Import_Clients_Airbase.csv";


    public static void main(String args[]) {
        try {
            Client client = new Client();
            // TC1a : Supprimer et cr�er une collection, ins�rer un puis plusieurs clients
            System.out.println("\n\n TC1a ...");
            client.dropCollectionClient(client.clientCollectionName);
            client.createCollectionClient(client.clientCollectionName);
            client.deleteClients(client.clientCollectionName, new Document());

            // TC1b : ins�rer un puis plusieurs clients depuis des variables Java
            System.out.println("\n\n TC1b ...");

            client.testInsertOneClient();
            client.testInsertManyClients();

            // TC1c : Importer les clients depuis un fichier json1Array. Alternatif � Tc1b et d
            System.out.println("\n\n TC1c ...");


            // client.loadClientsFromJsonArrayFile(
            // client.clientCollectionName,
            // client.clientsFilePath,
            // client.clientsJsonArrayFileName);

            // TC1d : Importer les clients depuis un fichier JsonArray avec MongoImport. Alternatif � Tc1b et c
            System.out.println("\n\n TC1d ...");

            // client.loadClientsFromJsonArrayFileWithMongoImport(
            // client.dbName,
            // true,
            // client.clientCollectionName,
            // client.clientsFilePath,
            // client.clientsJsonArrayFileName
            // );


            // TC1e : Importer les clients depuis un fichier CSV. Alternatif � Tc1b, c, d
            System.out.println("\n\n TC1e ...");
            // client.loadClientsFromCSVFile(
            // client.clientCollectionName,
            // client.clientsFilePath,
            // client.clientsCsvFileName);
            // TC1a : Supprimer et cr�er une collection, ins�rer un puis plusieurs clients

            // TC2a: Afficher un client connaissant son num�ro
            System.out.println("\n\n TC2a ...");
            //client.getClientById(client.clientCollectionName, 10);


            //
            // TC2b: Afficher tous les clients sans tri ni projection
            System.out.println("\n\n TC2b ...");

            // client.getClients(
            // client.clientCollectionName,
            // new Document(),
            // new Document(),
            // new Document());


            // TC3 : Afficher tous les clients tri� en ordre d�croissant sur le nom
            System.out.println("\n\n TC3 ...");

            // client.getClients(client.clientCollectionName,
            // new Document(),
            // new Document(),
            // new Document(?)
            // );

            // TC4 : Modifier  la date de naissance et le t�l�phone d'un client connaissant son nr		System.out.println("\n\n TC4 ...");


            // client.updateClients(client.clientCollectionName,
            // new Document(?),
            // new Document ("$set", ?),
            // new UpdateOptions()
            // );


            //client.deleteClients(client.clientCollectionName, new Document());
            //client.deleteClients(client.clientCollectionName, new Document("_id", 7369));

            client.joinLocalAndforeignCollections(client.clientCollectionName, client.volCollectionName, "_id", "idClient", new Document("_id", 1));

            // TC1d : Importer les clients depuis un fichier JsonArray avec MongoImport. Alternatif � Tc1b et c
            System.out.println("\n\n TC3a ...");

            // client.exportClientsToJsonCsvFileWithMongoExport(
            // client.dbName,
            // true,
            // client.clientCollectionName,
            // client.clientsFilePath,
            // client.destClientsCsvFileName
            // );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * FC1 : Constructeur Client.
     * Dans ce constructeur sont effectu�es les activit�s suivantes:
     * - Cr�ation d'une instance du client MongoClient
     * - Cr�ation d'une BD Mongo appel� RH
     * - Cr�ation d'un utilisateur appel�
     * - Chargement du pointeur vers la base RH
     */
    Client() {
        // Creating a Mongo client

        MongoClient mongoClient = new MongoClient(hostName, port);

        // Creating Credentials
        // RH : Ressources Humaines
        MongoCredential credential;
        credential = MongoCredential.createCredential(userName, dbName, passWord.toCharArray());
        System.out.println("Connected to the database successfully");
        System.out.println("Credentials ::" + credential);
        // Accessing the database
        database = mongoClient.getDatabase(dbName);
    }

    /**
     * FC2 : Cette fonction permet de cr�er une collection
     * de nom nomCollection.
     */
    public void createCollectionClient(String nomCollection) {
        //Creating a collection
        database.createCollection(nomCollection);
        System.out.println("Collection Clients created successfully");

    }

    /**
     * FC3 : Cette fonction permet de supprimer une collection
     * connaissant son nom.
     */

    public void dropCollectionClient(String nomCollection) {
        //Drop a collection
        MongoCollection<Document> colClients = null;
        System.out.println("\n\n\n*********** dans dropCollectionClient *****************");

        System.out.println("!!!! Collection Client : " + colClients);

        colClients = database.getCollection(nomCollection);
        System.out.println("!!!! Collection Client : " + colClients);
        colClients.drop();
        System.out.println("Collection colClients removed successfully !!!");
    }

    /**
     * FC4 : Cette fonction permet d'ins�rer un client dans une collection.
     */

    public void insertOneClient(String nomCollection, Document client) {
        //Drop a collection
        MongoCollection<Document> colClients = database.getCollection(nomCollection);
        colClients.insertOne(client);
        System.out.println("Document inserted successfully");
    }


    /**
     * FC5 : Cette fonction permet de tester la m�thode insertOneClient.
     */

    public void testInsertOneClient() {
        Document client = new Document("_id", "07").append("nom", "Bond").append("prenom", Arrays.asList("James")).append("telephone", "673212293").append("DateNaiss", "03/02/1990").append("adresse", new Document("numero", 20).append("rue", "queens avenue").append("codePostal", "EC4R 2SU").append("ville", "Londres").append("pays", "Royaune Uni"));
        this.insertOneClient(this.clientCollectionName, client);
        System.out.println("Document inserted successfully");
    }

    /**
     * FC6 : Cette fonction permet d'ins�rer plusieurs clients dans une collection
     */

    public void insertManyClients(String nomCollection, List<Document> clients) {
        //Drop a collection
        MongoCollection<Document> colClients = database.getCollection(nomCollection);
        colClients.insertMany(clients);
        System.out.println("Many Documents inserted successfully");
    }

    /**
     * FC7 : Cette fonction permet de tester la fonction insertManyClients
     */

    public void testInsertManyClients() {
        List<Document> clients = Arrays.asList(new Document("_id", 1).append("nom", "Martin").append("prenom", Arrays.asList("Aaron", "Frida")).append("telephone", "673212284").append("DateNaiss", "01/01/1980").append("adresse", new Document("numero", 11).append("rue", "All�e Cavendish").append("codePostal", "06000").append("ville", "Nice").append("pays", "France")),

                new Document("_id", 2).append("nom", "Bernard").append("prenom", Arrays.asList("Abel")).append("telephone", "673212285").append("DateNaiss", "05/05/1984").append("adresse", new Document("numero", 12).append("rue", "All�e de la Chapelle Saint-Pierre").append("codePostal", "06000").append("ville", "Nice").append("pays", "France")),

                new Document("_id", 3).append("nom", "Dubois").append("prenom", Arrays.asList("Abella", "Mehdi")).append("telephone", "673212286").append("DateNaiss", "02/02/1990").append("adresse", new Document("numero", 13).append("rue", "Rue la Fontaine aux Oiseaux").append("codePostal", "06000").append("ville", "Nice").append("pays", "France")),

                new Document("_id", 4).append("nom", "Thomas").append("prenom", Arrays.asList("Ab�lard")).append("telephone", "673212287").append("DateNaiss", "01/06/1987").append("adresse", new Document("numero", 14).append("rue", "Rue La Palmeraie").append("codePostal", "France").append("ville", "Nice").append("pays", "France")),

                new Document("_id", 5).append("nom", "Walter").append("prenom", Arrays.asList("Robert")).append("telephone", "673212288").append("DateNaiss", "01/08/1983").append("adresse", new Document("numero", 15).append("rue", "Rue de la R�sistance").append("codePostal", "10001").append("ville", "New-york").append("pays", "USA")),

                new Document("_id", 6).append("nom", "Richard").append("prenom", Arrays.asList("Maria", "Abondance")).append("telephone", "673212289").append("DateNaiss", "12/01/1980").append("adresse", new Document("numero", 16).append("rue", "All�e des Citronniers").append("codePostal", "75001").append("ville", "Paris").append("pays", "France")),

                new Document("_id", 7).append("nom", "Petit").append("prenom", Arrays.asList("Abraham", "Leonard")).append("telephone", "673212290").append("DateNaiss", "01/08/1980").append("adresse", new Document("numero", 17).append("rue", "All�e des Faunes").append("codePostal", "69001").append("ville", "Lyon").append("pays", "France")),

                new Document("_id", 8).append("nom", "Durand").append("prenom", Arrays.asList("Mari", "Achille")).append("telephone", "673212291").append("DateNaiss", "01/09/1989").append("adresse", new Document("numero", 18).append("rue", "Rue des Isnards").append("codePostal", "75001").append("ville", "Paris").append("pays", "France")),

                new Document("_id", 9).append("nom", "Leroy").append("prenom", Arrays.asList("Ada", "Mousse")).append("telephone", "673212292").append("DateNaiss", "28/07/1985").append("adresse", new Document("numero", 19).append("rue", "Rue des Lucioles").append("codePostal", "13001").append("ville", "Marseille").append("pays", "France")),

                new Document("_id", 10).append("nom", "Moreau").append("prenom", Arrays.asList("Adam")).append("telephone", "673212293").append("DateNaiss", "03/02/1990").append("adresse", new Document("numero", 20).append("rue", "All�e des Palmiers").append("codePostal", "31000").append("ville", "Toulouse").append("pays", "France")));
        this.insertManyClients(this.clientCollectionName, clients);
    }

    /**
     * FC8 : Cette fonction permet de rechercher un client dans une collection
     * connaissant son id.
     */
    public void getClientById(String nomCollection, Integer ClientId) {
        //Drop a collection
        System.out.println("\n\n\n*********** dans getClientById *****************");

        MongoCollection<Document> colClients = database.getCollection(nomCollection);

        //BasicDBObject whereQuery = new BasicDBObject();
        Document whereQuery = new Document();

        whereQuery.put("_id", ClientId);
        //DBCursor cursor = colClients.find(whereQuery);
        FindIterable<Document> listClient = colClients.find(whereQuery);

        // Getting the iterator
        Iterator it = listClient.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }


    /**
     * FC9 : Cette fonction permet de rechercher des clients dans une collection.
     * Le param�tre whereQuery : permet de passer des conditions de rechercher
     * Le param�tre projectionFields : permet d'indiquer les champs � afficher
     * Le param�tre sortFields : permet d'indiquer les champs de tri.
     */
    public void getClients(String nomCollection, Document whereQuery, Document projectionFields, Document sortFields) {
        //Drop a collection
        System.out.println("\n\n\n*********** dans getClients *****************");

        MongoCollection<Document> colClients = null;
        System.out.println("Dans getClients 2.1 *****************" + colClients);
        System.out.println("Dans getClients 2.2 : database:" + database);

        colClients = database.getCollection(nomCollection);
        System.out.println("Dans getClients 2.1 *****************" + colClients);

        FindIterable<Document> listClient = colClients.find(whereQuery).sort(sortFields).projection(projectionFields);
        //System.out.println("colClients.count():"+colClients.count());
        // Getting the iterator
        Iterator it = listClient.iterator();
        System.out.println("Dans getClients 2.1 *****************");

        while (it.hasNext()) {
            System.out.println("Dans getClients 2.3 *****************");

            System.out.println(it.next());
        }
    }


    /**
     * FC10 : Cette fonction permet de modifier des clients dans une collection.
     * Le param�tre whereQuery : permet de passer des conditions de recherche
     * Le param�tre updateExpressions : permet d'indiquer les champs � modifier
     * Le param�tre UpdateOptions : permet d'indiquer les options de mise � jour :
     * .upSert : ins�re si le document n'existe pas
     */
    public void updateClients(String nomCollection, Document whereQuery, Document updateExpressions, UpdateOptions updateOptions) {
        //Drop a collection
        System.out.println("\n\n\n*********** dans updateClients *****************");

        MongoCollection<Document> colClients = database.getCollection(nomCollection);
        UpdateResult updateResult = colClients.updateMany(whereQuery, updateExpressions);

        System.out.println("\nR�sultat update : " + "getUpdate id: " + updateResult + " getMatchedCount : " + updateResult.getMatchedCount() + " getModifiedCount : " + updateResult.getModifiedCount());


        //return updateResult.getUpsertedId() != null ||
        //		(updateResult.getMatchedCount() > 0 && updateResult.getModifiedCount() > 0);
        //FindIterable<Document> listEmp=colClients.find(whereQuery).update(sortFields).projection(projectionFields);

        // Getting the iterator
        //Iterator it = listClient.iterator();
        //while(it.hasNext()) {
        //		System.out.println(it.next());
        //}
    }


    /**
     * FC11 : Cette fonction permet de supprimer des clients dans une collection.
     * Le param�tre filters : permet de passer des conditions de recherche des employ�s � supprimer
     */
    public void deleteClients(String nomCollection, Document filters) {

        System.out.println("\n\n\n*********** dans deleteClients *****************");
        FindIterable<Document> listClient;
        Iterator it;
        MongoCollection<Document> colClients = database.getCollection(nomCollection);

        listClient = colClients.find(filters).sort(new Document("_id", 1));
        it = listClient.iterator();// Getting the iterator
        this.displayIterator(it, "Dans deleteClients: avant suppression");

        colClients.deleteMany(filters);
        listClient = colClients.find(filters).sort(new Document("_id", 1));
        it = listClient.iterator();// Getting the iterator
        this.displayIterator(it, "Dans deleteClients: Apres suppression");
    }

    /**
     * FC12 : Parcours un it�rateur et affiche les documents qui s'y trouvent
     */
    public void displayIterator(Iterator it, String message) {
        System.out.println(" \n #### " + message + " ################################");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }


    /**
     * FC13 :
     * .6.4 Afficher les informations sur 1 client ainsi que ses appr�ciations
     * sur les vols
     * Trouver les bons param�tres.
     */
    public void joinLocalAndforeignCollections(String localCollectionName, String foreignCollectionName, String localColJoinFieldName, String foreigColJoinFieldName, Document whereQuery) {
        String arr1 = "clientVol";
        // int filterValue=0;
        // if (filterFieldType=="ENTIER"){
        // filterValue=Integer.parseInt(filterFieldValue);
        // }
        //Drop a collection
        System.out.println("\n\n\n*********** dans joinTwoCollections *****************");
        //MongoCollection<Document> col1=database.getCollection(nomCollection1);
        MongoCollection<Document> localCollection = database.getCollection(localCollectionName);
        AggregateIterable<Document> output = localCollection.aggregate(Arrays.asList(Aggregates.match(whereQuery), Aggregates.lookup(foreignCollectionName, localColJoinFieldName, foreigColJoinFieldName, arr1)));
        for (Document dbObject : output) {
            System.out.println(dbObject);
        }

    }

    /**
     * FC14 :
     * charger plusieurs documents (clients) JSON depuis un fichier vers une collection mongoDB
     * Utilisez le fichier 2Json_collection_Import_Clients_Airbase.json vu dans le cours
     * Trouver les bons param�tres.
     */

    public void loadClientsFromJsonArrayFile(String collectionName, String filePath, String fileName) {
        System.out.println(" \n ####  DANS loadClientsFromJsonArrayFile ################################");
        JSONParser jsonParser = new JSONParser();
        MongoCollection<Document> colClients = database.getCollection(collectionName);

        try {
            JSONArray jsonClientArray = (JSONArray) jsonParser.parse(new FileReader(filePath + fileName));
            List<Document> clientList = new ArrayList<Document>();

            for (Object clientObj : jsonClientArray) {
                JSONObject client = (JSONObject) clientObj;
                Document docClient = Document.parse(client.toString());
                clientList.add(docClient);
            }
            colClients.insertMany(clientList);

        } catch (ParseException e) {
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * FC15 :
     * charger des clients contenu dans un fichier CSV vers une collection mongoDB
     * Construisez un fichier CSV � partir du fichier json 2Json_collection_Import_Clients_Airbase
     * Trouver les bons param�tres.
     */

    public void loadClientsFromCSVFile(String collectionName, String filePath, String fileName) {
        System.out.println(" \n ####  DANS loadClientsFromCSVFile ################################");
    }

    /**
     * FC16 :
     * charger plusieurs documents (clients) JSON depuis un fichier vers une collection mongoDB
     * Utilisez le fichier 2Json_collection_Import_Clients_Airbase.json vu dans le cours.
     * Invoquer l'utilitaire MongoImport.
     */

    public void loadClientsFromJsonArrayFileWithMongoImport(String dbName, boolean isJsonArray, String collectionName, String filePath, String fileName) {
        System.out.println(" \n ####  DANS loadClientsFromJsonArrayFileWithMongoImport ################################");

        Runtime r = Runtime.getRuntime();
        Process p = null;
        String command = "mongoimport " + "  --db=" + dbName + "  --collection=" + collectionName + "  --jsonArray  " + "  --file=" + filePath + fileName;

        System.out.println("La commande : " + command);
        try {
            p = r.exec(command);
            //r.runFinalization();
            System.out.println("JsonArray file readed into Database");
            //p.destroy();

        } catch (Exception e) {
            System.out.println("Error executing " + command + e.toString());
        }
    }

    /**
     * FC17 :
     * charger des clients contenu dans un fichier CSV vers une collection mongoDB
     * Construisez un fichier CSV � partir du fichier json 2Json_collection_Import_Clients_Airbase
     * Trouver les bons param�tres. Invoquer l'utilitaire MongoImport.
     */

    public void loadClientsFromCSVFileWithMongoImport(String dbName, boolean isCsvFile, String collectionName, String filePath, String fileName) {
        System.out.println(" \n ####  DANS loadClientsFromCSVFileWithMongoImport ################################");

        Runtime r = Runtime.getRuntime();
        Process p = null;
        String command = "mongoimport " + "--db=" + dbName + "  --collection=" + collectionName + "  --type csv  " + "  --file=" + filePath + fileName;

        System.out.println("La commande : " + command);
        try {
            p = r.exec(command);
            //r.runFinalization();
            System.out.println("JsonArray file readed into Database");
            //p.destroy();

        } catch (Exception e) {
            System.out.println("Error executing " + command + e.toString());
        }
    }

    /**
     * FC18 :
     * Ajouter des appr�ciations pour un client donn� et un vol donn�.
     * Trouver les bons param�tres.
     */
    public void ajouterUneAppreciationAUnVol() {

    }

    /**
     * FC19 :
     * Exporter plusieurs documents (clients) JSON d'une collection mongoDB vers un fichier vers un fichier CSV.
     * Invoquer l'utilitaire MongoExport.
     */
    //  String command = "mongoexport --host Host --port Port --db " + db + " //--collection " + col + " --csv --out " + fileName + "";
    public void exportClientsToJsonCsvFileWithMongoExport(String dbName, boolean isJsonArray, String collectionName, String filePath, String destFileName) {
        System.out.println(" \n ####  DANS exportClientsToJsonCsvFileWithMongoExport ################################");

        Runtime r = Runtime.getRuntime();
        Process p = null;
        //String command = "mongoexport --host:localhost --port:27017 "

        String command = "mongoexport " + "--db=" + dbName + "  --collection=" + collectionName + "  --type=csv  " + "  --fields=_id,nom,prenom,telephone,DateNaiss,adresse " + "  --out=" + filePath + destFileName;

        System.out.println("La commande : " + command);
        try {
            p = r.exec(command);
            //r.runFinalization();
            System.out.println("Commande export ex�cugt�e : p = r.exec(command)");
            //p.destroy();

            int waitFor = p.waitFor();
            System.out.println("waitFor:1: " + waitFor);
            BufferedReader success = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String s = "";
            System.out.println("waitFor:2: " + waitFor);
            while ((s = success.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("waitFor:3: " + waitFor);
            while ((s = error.readLine()) != null) {
                System.out.println("Std ERROR : " + s);
            }
            System.out.println("waitFor:4: " + waitFor);

        } catch (Exception e) {
            System.out.println("Error executing " + command + e.toString());
        }
    }
}

