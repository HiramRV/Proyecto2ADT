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
		File file= new File("C:/Users/AndreaMaybell/Documents/AMPE/deberes/Algoritmos y Estructuras de Datos/Proyecto2/BP3");
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);
		Funciones funciones= new Funciones();
		
		try (Transaction tx = db.beginTx()) {
			//creaci�n Scanner
			Scanner scan= new Scanner(System.in);
			
			//bienvenida al programa
			System.out.println("MOVIEST");
			System.out.println("");
			//men�
			System.out.println("Seleccione una opci�n:");
			System.out.println("1. Solicitar una recomendaci�n");
			System.out.println("2. Ingresar una pel�cula");
			int op1= scan.nextInt();
			String basura= scan.nextLine();
			//modo administrador
			if(op1==2)
			{
				int t=0;
				while (t==0)
				{
					funciones.Agregar(db, scan);
					//pregunta para nuevo ingreso
					System.out.println("Desea ingresar otra pel�cula? (S/N)");
					String op= scan.nextLine();
					if (op.equals("S"))
					{
						//se contin�a en ciclo
					}
					else
						t=1; //se rompe el ciclo para salir del programa
				}
			}
			
			//modo usuario
			if (op1==1){
			//vector con los Id's de los nodos "Pelicula"
			Vector<Integer> pelisId= funciones.IdsPeliculas(db);
			
			//ingreso
			System.out.println("Ingrese su nombre de usuario:");
			String nombre= scan.nextLine();
			Node usuario= funciones.ingreso(db, nombre);
			Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario);
			System.out.println("");
			
			int s=0;
			while(s==0)
			{
				//recomendaci�n
				Node pelicula = null;
				if (pelisVistas.size()>0)
				{
					//es usuario guardado, se realiza recomendacion en base a peliculas vistas
					pelicula= funciones.recomendacion(db,pelisVistas,pelisId,usuario);
				}
				else
				{
					//es usuario nuevo o no le ha gustado ninguna pel�cula aun, se realiza una recomendaci�n random
					pelicula= funciones.recomendacionRandom(db, pelisId);
				}
				// se muestra la recomendaci�n
				String titulo= (String) pelicula.getProperty("nombre");
				System.out.println(titulo);
				System.out.println("");
				
				//se verifica si al usuario le gust� la recomendacion o no
				System.out.println("Le gust� la recomendaci�n?(S/N)");
				String opc= scan.nextLine();
				/*while (opc.equals("S")==false||opc.equals("N")==false)
				{
					System.out.println("Debe ingresar S o N para indicar si le gust� la pel�cula (S=s�, N=no)");
					System.out.println("Le gust� la recomendaci�n?(S/N)");
					o= scan.nextLine();
				}*/
				if (opc.equals("S"))
				{
					//si le gust� la recomendaci�n se crea la relaci�n entre esa pel�cula y el usuario
					usuario.createRelationshipTo(pelicula, Relaciones.Vio);
					funciones.actualizacionPerfilUsuario(db, usuario);
				}
				if (opc.equals("N"))
				{
					//System.out.println("no le gust�");
				}
				
				//pregunta para nueva recomendaci�n
				System.out.println("Desea que se le recomiende otra pel�cula? (S/N)");
				String op= scan.nextLine();
				if (op.equals("S"))
				{
					//se contin�a en ciclo
				}
				else
					s=1; //se rompe el ciclo para salir del programa
			}
			}
			//mensaje final
			System.out.println(" ");
			System.out.println("Gracias por utilizar MOVIEST!!!");
			
			scan.close();//se cierra el scanner
			tx.success();//se cierra la base de datos
		}
	}}
