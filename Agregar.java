import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Agregar {

	public static void main(String[] args) {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		File file= new File("C:/Users/AndreaMaybell/Documents/AMPE/deberes/Algoritmos y Estructuras de Datos/Proyecto2/Base2");
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);
		
		Scanner scan= new Scanner(System.in);
		
		try (Transaction tx = db.beginTx()) {
			
			System.out.println("Título película: ");
			String tp= scan.nextLine();
			System.out.println("Cant actores: ");
			int ca= scan.nextInt();
			String basura= scan.nextLine();
			Vector<String> nombresA= new Vector<String>();
			for (int i=0; i<ca; i++)
			{
				System.out.println("Actor: ");
				String a1= scan.nextLine();
				nombresA.addElement(a1);
			}
			System.out.println("Cant generos: ");
			int cg= scan.nextInt();
			basura= scan.nextLine();
			Vector<String> generos= new Vector<String>();
			for (int i=0; i<cg; i++)
			{
				System.out.println("Género: ");
				String a1= scan.nextLine();
				generos.addElement(a1);
			}
			/*System.out.println("Genero: ");
			String gp= scan.nextLine();*/
			System.out.println("Cant directores: ");
			int cd= scan.nextInt();
			basura= scan.nextLine();
			Vector<String> nombresD= new Vector<String>();
			for (int i=0; i<cd; i++)
			{
				System.out.println("Director: ");
				String a1= scan.nextLine();
				nombresD.addElement(a1);
			}
			System.out.println("Año: ");
			int an= scan.nextInt();
			System.out.println("Saga: ");
			int sp= scan.nextInt();
			basura= scan.nextLine();
			String ss= "No";
			if (sp== 1)
			{
				ss= "Si";
			}
			System.out.println("Lugar: ");
			String lp= scan.nextLine();

			Node n1= db.findNode(Etiquetas.Pelicula, "nombre", tp);
			if (n1==null)
			{
				n1= db.createNode(Etiquetas.Pelicula);
				n1.setProperty("nombre", tp);
			}
			
			/*Node n2= db.findNode(Etiquetas.Genero, "genero", gp);
			if (n2==null)
			{
				n2= db.createNode(Etiquetas.Genero);
				n2.setProperty("genero", gp);
			}
			n1.createRelationshipTo(n2, Relaciones.Del_genero);*/
			
			Node n3= db.findNode(Etiquetas.Lugar, "nombreL", lp);
			if (n3==null)
			{
				n3= db.createNode(Etiquetas.Lugar);
				n3.setProperty("nombreL", lp);
			}
			n1.createRelationshipTo(n3, Relaciones.Ocurre_en);
			Node n4= db.findNode(Etiquetas.Año, "fecha", an);
			if (n4==null)
			{
				n4= db.createNode(Etiquetas.Año);
				n4.setProperty("fecha", an);
			}
			n1.createRelationshipTo(n4, Relaciones.Lanzada_en);
			
			Node n5= db.findNode(Etiquetas.Saga, "nombreS", ss);
			if (n5==null)
			{
				n5= db.createNode(Etiquetas.Saga);
				n5.setProperty("nombreS", ss);
			}
			n1.createRelationshipTo(n5, Relaciones.Saga_);
			
			for (int k=0; k<cg; k++)
			{
				String a= generos.get(k);
				Node n2= db.findNode(Etiquetas.Genero, "genero", a);
				if (n2==null)
				{
					n2= db.createNode(Etiquetas.Genero);
					n2.setProperty("genero", a);
				}
				n1.createRelationshipTo(n2, Relaciones.Del_genero);
			}
			
			for (int k=0; k<ca; k++)
			{
				String a= nombresA.get(k);
				Node n6= db.findNode(Etiquetas.Actor, "nombreA", a);
				if (n6==null)
				{
					n6= db.createNode(Etiquetas.Actor);
					n6.setProperty("nombreA", a);
				}
				n1.createRelationshipTo(n6, Relaciones.Actor_principal);
			}
			for (int l=0; l<cd; l++)
			{
				String a= nombresD.get(l);
				Node n7= db.findNode(Etiquetas.Director, "nombreD", a);
				if (n7==null)
				{
					n7= db.createNode(Etiquetas.Director);
					n7.setProperty("nombreD", a);
				}
				n1.createRelationshipTo(n7, Relaciones.Dirigida_por);
			}
			
			System.out.println("EXITO! ;)");
		scan.close();
		tx.success();	
}}}
