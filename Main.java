import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {
	public static void main(String[] args) {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		File file= new File("C:/Users/AndreaMaybell/Documents/AMPE/deberes/Algoritmos y Estructuras de Datos/BaseP1");
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);
		
		try (Transaction tx = db.beginTx()) {
			
			Scanner scan= new Scanner(System.in);
			System.out.println("MOVIEST");
			System.out.println("");
			System.out.println("Ingrese su nombre de usuario:");
			String nombre= scan.nextLine();
			try{
				Node usuario= db.findNode(Etiquetas.Persona, "nombreP", nombre);
			}
			catch(Exception e){
				Node usuario= db.createNode();
				usuario.addLabel(Etiquetas.Persona);
				usuario.setProperty("nombreP", nombre);
			}
			System.out.println("");
			System.out.println("Le recomendamos la siguiente pelicula: ");
			
			Node nodo1;
			Vector<Integer> pelisId= new Vector<Integer>();
			for (int i=0; i<900; i++)
			{
				try{
					nodo1= db.getNodeById(i);
					nodo1.getProperty("nombre");
					pelisId.add(i);
				}
				catch(Exception e){
					
				}
			}
			Random random= new Random();
			int r= random.nextInt(pelisId.size());
			String titulo= (String) db.getNodeById(pelisId.get(r)).getProperty("nombre");
			System.out.println(titulo);
			System.out.println("");
			tx.success();
		}
	}}
