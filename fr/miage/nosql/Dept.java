// set MYPATH=D:\tpmongodb2021
// javac -g -cp %MYPATH%\mongojar\mongo-java-driver-3.12.7.jar;%MYPATH% %MYPATH%\tpjava\Dept.java
// java -Xmx256m -Xms256m -cp %MYPATH%\mongojar\mongo-java-driver-3.12.7.jar;%MYPATH% tpjava.Dept
/*
Mac OS
MYPATH=/Users/billy/Documents/Mopolo/tpmongodb
javac -cp "$MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH" "$MYPATH/tpjava/Employe.java"
java -Xmx256m -Xms256m -cp "$MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH" tpjava.Employe


export MYPATH=~/Bureau/tpmongodb
javac -g -cp $MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH $MYPATH/tpjava/Dept.java
java -cp $MYPATH/mongojar/mongo-java-driver-3.12.7.jar:$MYPATH tpjava.Dept

javac -cp "mongojar/mongo-java-driver-3.12.7.jar" tpjava/*.java -d bin
java -cp "bin;mongojar/mongo-java-driver-3.12.7.jar" tpjava.Employe


*/

package tpjava.fr.miage.nosql;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;
import org.bson.Document;
import java.util.Arrays;
import java.util.List;
import com.mongodb.client.FindIterable; 
import java.util.Iterator;

import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.UpdateOptions;



public class Dept { 
   private MongoDatabase database;
   private String dbName="RH";
   private String hostName="localhost";
   private int port=27017;
   private String userName="urh";
   private String passWord="passUrh";
   private String DeptCollectionName="colDepts";

   
   public static void main( String args[] ) {  
    try{
		Dept dept = new Dept();
		// TD1 : test des fonctions de gestion d'une collection et d'ajout de documents
		System.out.println("\n\nTD1 : ...");
		dept.dropCollectionDept(dept.DeptCollectionName);
		dept.createCollectionDept(dept.DeptCollectionName);
		//dept.deleteDepts(dept.DeptCollectionName, new Document());
		dept.testInsertOneDept();
		dept.testInsertManyDepts();
		dept.getDeptById(dept.DeptCollectionName, 10);
		
		// TD2 : Afficher tous les départements sans tri ni projection

		System.out.println("\n\nTD2 : ...");
		dept.getDepts(dept.DeptCollectionName, 
			new Document(), 
			new Document(),
			new Document());
		
		
		// TD3 : Afficher tous les Départements 
		// Trié en ordre croissant sur le dname et la localité loc
		// Projeter sur _id, dname et loc
		
		System.out.println("\n\nTD3 : ...");
		dept.getDepts(dept.DeptCollectionName, 
			new Document(),
			new Document("_id", 1).append("dname", 1).append("loc", 1),
			new Document("dname", 1).append("loc", 1)
		);
		
		
		
		// TD4 : Afficher tous les Departements
		// Triés en order décroissant sur _id et dname 
		// Avec projections sur les champs _id et dname

		System.out.println("\n\nTD4 : ...");

		dept.getDepts(dept.DeptCollectionName, 
			new Document(),
			new Document("_id", 1).append("dname", 1),
			new Document("_id", -1).append("dname", -1)
		);
		
		// TD5 : Afficher tous les Departements
		// Dont le nom est  "SALES" et la localité est "Chicago"
		// projection: tous les champs
		// tri : pas de tri
		
		System.out.println("\n\nTD5 : ...");
		
		dept.getDepts(dept.DeptCollectionName, 
			new Document("dname", "SALES").append("loc", "Chicago"),
			new Document(),
			new Document()
		);			
		
		// TD6: Afficher es Departements de nom : "FORMATION" et "SALES"
		// Trié en ordre croissant sur la localité
		// Projeté sur dname, loc
		System.out.println("\n\nTD6 : ...");

		dept.getDepts(dept.DeptCollectionName, 
		new Document("$or", 
		Arrays.asList(new Document("dname","FORMATION"), 
		new Document("dname","SALES"))),
			new Document("dname",1).append("loc", 1),
			new Document("loc", -1)
		);			
		

		// TD7 : modifier le département nr 30. Mettre sa localité à "Saratoga"
		
		System.out.println("\n\nTD7 : ...");

		dept.updateDepts(dept.DeptCollectionName, 
		new Document("_id", 30), 
		new Document ("$set", new Document("loc", "Saratoga") ),
		new UpdateOptions()
		);
		

	
		// TD8 : modifier la localité de tous les départements à "San Antnonio"
		
		System.out.println("\n\nTD8 : ...");

		dept.updateDepts(dept.DeptCollectionName, 
		new Document(), 
		new Document ("$set", new Document("loc", "Saratoga") ),
		new UpdateOptions()
		);

		// TD9 : Supprimer le département Nr 10
		
		System.out.println("\n\nTD9 : ...");

		dept.deleteDepts(
		dept.DeptCollectionName, 
		new Document("_id", 10)
		);	
		
		// TD10 : Supprimer tous les départements
		System.out.println("\n\nTD10 : ...");

		dept.deleteDepts(
		dept.DeptCollectionName, 
		new Document()
		);	
	}catch(Exception e){
		e.printStackTrace();
	}	
   } 
   
   /**
	Constructeur Dept.
	Dans ce constructeur sont effectuées les activités suivantes:
	- Création d'une instance du client MongoClient
	- Création d'une BD Mongo appelé RH
	- Création d'un utilisateur appelé 
	- Chargement du pointeur vers la base RH
   */
   Dept(){
		// FD1 : Creating a Mongo client
		
		MongoClient mongoClient = new MongoClient( hostName , port ); 

		// Creating Credentials 
		// RH : Ressources Humaines
		MongoCredential credential; 
		credential = MongoCredential.createCredential(userName, dbName, 
		 passWord.toCharArray()); 
		System.out.println("Connected to the database successfully"); 	  
		System.out.println("Credentials ::"+ credential);  
		// Accessing the database 
		database = mongoClient.getDatabase(dbName); 

   }

   /**
	FD2 : Cette fonction permet de créer une collection
	de nom nomCollection.
   */   
   public void createCollectionDept(String nomCollection){
		//Creating a collection 
		database.createCollection(nomCollection); 
		System.out.println("Collection Depts created successfully"); 

   }

   /**
	FD3 : Cette fonction permet de supprimer une collection
	de nom nomCollection.
   */
   
   public void dropCollectionDept(String nomCollection){
		//Drop a collection 
		MongoCollection<Document> colDepts=null; 
		System.out.println("\n\n\n*********** dans dropCollectionDept *****************");   

		System.out.println("!!!! Collection Dept : "+colDepts);

		colDepts=database.getCollection(nomCollection);
		System.out.println("!!!! Collection Dept : "+colDepts);
		// Visiblement jamais !!!
		if (colDepts==null)
			System.out.println("Collection inexistante");
		else {
			colDepts.drop();	
			System.out.println("Collection colDepts removed successfully !!!"); 
	  
		}
   }

   /**
	FD4 : Cette fonction permet d'insérer un Departement dans une collection.
   */
   
   public void insertOneDept(String nomCollection, Document dept){
		//Drop a collection 
		MongoCollection<Document> colDepts=database.getCollection(nomCollection);
		colDepts.insertOne(dept); 
		System.out.println("Document inserted successfully");     
   }


   /**
	FD5 : Cette fonction permet de tester la méthode insertOneDept.
   */

   public void testInsertOneDept(){
		Document dept = new Document("_id", 50) 
		.append("dname", "FORMATION")
		.append("loc", "Nice");
		this.insertOneDept(this.DeptCollectionName, dept);
		System.out.println("Document inserted successfully");     
   }

   /**
	FD6 : Cette fonction permet d'insérer plusieurs Départements dans une collection
   */

   public void insertManyDepts(String nomCollection, List<Document> depts){
		//Drop a collection 
		MongoCollection<Document> colDepts=database.getCollection(nomCollection);
		colDepts.insertMany(depts); 
		System.out.println("Many Documents inserted successfully");     
   }

   /**
	FD7 : Cette fonction permet de tester la fonction insertManyDepts
   */

   public void testInsertManyDepts(){
		List<Document> depts = Arrays.asList(
			new Document("_id", 10) 
			.append("dname", "ACCOUNTING")
			.append("loc", "New York"),
			new Document("_id", 20) 
			.append("dname", "RESEARCH")
			.append("loc", "Dallas"),
			new Document("_id", 30) 
			.append("dname", "SALES")
			.append("loc", "Chicago"),
			new Document("_id", 40) 
			.append("dname", "OPERATIONS")
			.append("loc", "Boston")
		);
		
		this.insertManyDepts(this.DeptCollectionName, depts);
   }

   /**
	FD8 : Cette fonction permet de rechercher un département dans une collection
	connaissant son id.
   */
   public void getDeptById(String nomCollection, Integer deptId){
		//Drop a collection 
		System.out.println("\n\n\n*********** dans getDeptById *****************");   

		MongoCollection<Document> colDepts=database.getCollection(nomCollection);

		//BasicDBObject whereQuery = new BasicDBObject();
		Document whereQuery = new Document();

		whereQuery.put("_id", deptId );
		//DBCursor cursor = colDepts.find(whereQuery);
		FindIterable<Document> listDept=colDepts.find(whereQuery);

		// Getting the iterator 
		Iterator it = listDept.iterator();
		while(it.hasNext()) {
				System.out.println(it.next());
		}		
   } 
   
   
    /**
	FD9 : Cette fonction permet de rechercher des départements dans une collection.
	Le paramètre whereQuery : permet de passer des conditions de rechercher
	Le paramètre projectionFields : permet d'indiquer les champs à afficher
	Le paramètre sortFields : permet d'indiquer les champs de tri.
   */   
   public void getDepts(String nomCollection, 
	Document whereQuery, 
	Document projectionFields,
	Document sortFields){
		//Drop a collection 
		System.out.println("\n\n\n*********** dans getDepts *****************");   

		MongoCollection<Document> colDepts=database.getCollection(nomCollection);

		FindIterable<Document> listDept=colDepts.find(whereQuery).sort(sortFields).projection(projectionFields);

		// Getting the iterator 
		Iterator it = listDept.iterator();
		while(it.hasNext()) {
				System.out.println(it.next());
		}		
   } 


    /**
	FD10 : Cette fonction permet de modifier des départements dans une collection.
	Le paramètre whereQuery : permet de passer des conditions de recherche
	Le paramètre updateExpressions : permet d'indiquer les champs à modifier
	Le paramètre UpdateOptions : permet d'indiquer les options de mise à jour :
		.upSert : insère si le document n'existe pas
   */
   public void updateDepts(String nomCollection, 
	Document whereQuery, 
	Document updateExpressions,
	UpdateOptions updateOptions
	){
		//Drop a collection 
		System.out.println("\n\n\n*********** dans updateDepts *****************");   

		MongoCollection<Document> colDepts=database.getCollection(nomCollection);
		UpdateResult updateResult = colDepts.updateMany(whereQuery, updateExpressions);
		
		System.out.println("\nRésultat update : "
		+"getUpdate id: "+updateResult
		+" getMatchedCount : "+updateResult.getMatchedCount() 
		+" getModifiedCount : "+updateResult.getModifiedCount()
		);


		//return updateResult.getUpsertedId() != null ||
		//		(updateResult.getMatchedCount() > 0 && updateResult.getModifiedCount() > 0);
		//FindIterable<Document> listEmp=colDepts.find(whereQuery).update(sortFields).projection(projectionFields);

		// Getting the iterator 
		//Iterator it = listdept.iterator();
		//while(it.hasNext()) {
		//		System.out.println(it.next());
		//}		
   }


   /**
	FD11 : Cette fonction permet de supprimer des départements dans une collection.
	Le paramètre filters : permet de passer des conditions de recherche des employés à supprimer
   */	    
   public void deleteDepts(String nomCollection, Document filters){
		
		System.out.println("\n\n\n*********** dans deleteDepts *****************");   
		FindIterable<Document> listDept;
		Iterator it;
		MongoCollection<Document> colDepts=database.getCollection(nomCollection);
		
		listDept=colDepts.find(filters).sort(new Document("_id", 1));
		it = listDept.iterator();// Getting the iterator
		this.displayIterator(it, "Dans deleteDepts: avant suppression");

		colDepts.deleteMany(filters);
		listDept=colDepts.find(filters).sort(new Document("_id", 1));
		it = listDept.iterator();// Getting the iterator
		this.displayIterator(it, "Dans deleteDepts: Apres suppression");
   } 	
   
   /**
	FD12 : Parcours un itérateur et affiche les documents qui s'y trouvent
   */
   public void displayIterator(Iterator it, String message){
	System.out.println(" \n #### "+ message + " ################################");
	while(it.hasNext()) {
		System.out.println(it.next());
	}		
   }
	
}
