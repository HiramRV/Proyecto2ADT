import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {
	public static void main(String[] args) {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		File file= new File("C:/Users/AndreaMaybell/Documents/AMPE/deberes/Algoritmos y Estructuras de Datos/Proyecto2/Base2");
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);
		Funciones funciones= new Funciones();
		
		try (Transaction tx = db.beginTx()) {
			//creación Scanner
			Scanner scan= new Scanner(System.in);
			System.out.println("MOVIEST");
			System.out.println("");
			
			//vectores ids
			Vector<Integer> pelisId= funciones.IdsPeliculas(db);
			
			//ingreso
			System.out.println("Ingrese su nombre de usuario:");
			String nombre= scan.nextLine();
			Node usuario= funciones.ingreso(db, nombre);
			Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario);
			System.out.println("");

			//recomendación inicial
			Node pelicula = null;
			if (funciones.getIndicador()==1)
			{
				pelicula= funciones.recomendacion(db,pelisVistas,pelisId,usuario);
			}
			if (funciones.getIndicador()==2)
			{
				pelicula= funciones.recomendacionRandom(db, pelisId);
			}
			String titulo= (String) pelicula.getProperty("nombre");
			System.out.println(titulo);
			System.out.println("");
			System.out.println("Le gustó la recomendación?(S/N)");
			String o= scan.nextLine();
			/*while (o.equals("S")==false||o.equals("N")==false)
			{
				System.out.println("Debe ingresar S o N para indicar si le gustó la película (S=sí, N=no)");
				System.out.println("Le gustó la recomendación?(S/N)");
				o= scan.nextLine();
			}*/
			if (o.equals("S"))
			{
				usuario.createRelationshipTo(pelicula, Relaciones.Vio);
				funciones.actualizacionPerfilUsuario(db, usuario);
			}
			if (o.equals("N"))
			{
				System.out.println("no le gustó");
			}
			
			int s=0;
			while(s==0)
			{
				System.out.println("Desea que se le recomiende otra película? (S/N)");
				String op= scan.nextLine();
				if (op.equals("S"))
				{
					pelicula= funciones.recomendacion(db,pelisVistas,pelisId,usuario);
					String tit= (String) pelicula.getProperty("nombre");
					System.out.println(tit);
					System.out.println("");
				}
				else
					s=1;
			}
			System.out.println(" ");
			System.out.println("Gracias por utilizar MOVIEST!!!");
			scan.close();
			tx.success();
		}
	}}
