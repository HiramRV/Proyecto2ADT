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
		File file= new File("C:Users/Jacky Hidalgo/Desktop/UNIVERSIDAD/CUARTO CICLO/ESTRUCTURA DE DATOS/Proyecto2/Fase2/Proyecto2ADT-master/Proyecto2ADT-master/BP3");
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);
		Funciones funciones= new Funciones();
		
		try (Transaction tx = db.beginTx()) {
			//creación Scanner
			Scanner scan= new Scanner(System.in);
			
			//bienvenida al programa
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("----MM-------MM--OOOOOOO--V---------V--I--EEEEEEE--SSSSSS--TTTTTTT-------");
			System.out.println("----M--M---M--M--O-----O---V-------V---I--E--------S----------T----------");
			System.out.println("----M----M----M--O-----O----V-----V----I--EEEEEE---SSSSSS-----T----------");
			System.out.println("----M---------M--O-----O-----V---V-----I--E-------------S-----T----------");
			System.out.println("----M---------M--O-----O------V-V------I--E-------------S-----T----------");
			System.out.println("----M---------M--OOOOOOO-------V-------I--EEEEEEE--SSSSSS-----T----------");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("-------------------------------------------------------------------------");
			//menú
			System.out.println("");
			System.out.println("Seleccione una opción:");
			System.out.println("1. Solicitar una recomendación");
			System.out.println("2. Ingresar una película");
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
					System.out.println("---------------------------------------");
					System.out.println("Desea ingresar otra película? (S/N)");
					String op= scan.nextLine();
					if (op.equals("S"))
					{
						//se continúa en ciclo
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
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Ingrese su nombre de usuario:");
			String nombre= scan.nextLine();
			Node usuario= funciones.ingreso(db, nombre);
			Vector<Node> pelisVistas= funciones.actualizacionPerfilUsuario(db,usuario);
			System.out.println("");
			
			int s=0;
			while(s==0)
			{
				//recomendación
				Node pelicula = null;
				if (pelisVistas.size()>0)
				{
					//es usuario guardado, se realiza recomendacion en base a peliculas vistas
					pelicula= funciones.recomendacion(db,pelisVistas,pelisId,usuario);
				}
				else
				{
					//es usuario nuevo o no le ha gustado ninguna película aun, se realiza una recomendación random
					pelicula= funciones.recomendacionRandom(db, pelisId);
				}
				// se muestra la recomendación
				String titulo= (String) pelicula.getProperty("nombre");
				System.out.println(titulo);
				System.out.println("");
				
				//se verifica si al usuario le gustó la recomendacion o no
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("Le gustó la recomendación?(S/N)");
				String opc= scan.nextLine();
				/*while (opc.equals("S")==false||opc.equals("N")==false)
				{
					System.out.println("Debe ingresar S o N para indicar si le gustó la película (S=sí, N=no)");
					System.out.println("Le gustó la recomendación?(S/N)");
					o= scan.nextLine();
				}*/
				if (opc.equals("S"))
				{
					//si le gustó la recomendación se crea la relación entre esa película y el usuario
					usuario.createRelationshipTo(pelicula, Relaciones.Vio);
					funciones.actualizacionPerfilUsuario(db, usuario);
				}
				if (opc.equals("N"))
				{
					//System.out.println("no le gustó");
				}
				
				//pregunta para nueva recomendación
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("Desea que se le recomiende otra película? (S/N)");
				String op= scan.nextLine();
				if (op.equals("S"))
				{
					//se continúa en ciclo
				}
				else
					s=1; //se rompe el ciclo para salir del programa
			}
			}
			//mensaje final
			System.out.println(" ");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("------------GGGGG--RRRRR--AAAAA--CCCCC--II--AAAAA--SSSSS-----------------");
			System.out.println("------------G------R---R--A---A--C------II--A---A--S---------------------");
			System.out.println("------------G-GGG--R-RR---AAAAA--C------II--AAAAA--SSSSS-----------------");
			System.out.println("------------G---G--R--R---A---A--C------II--A---A------S-----------------");
			System.out.println("------------GGGGG--R---R--A---A--CCCCC--II--A---A--SSSSS-----------------");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("------------------------POR UTILIZAR MOVIEST-----------------------------");
			System.out.println("-------------------------------------------------------------------------");
			
			scan.close();//se cierra el scanner
			tx.success();//se cierra la base de datos
		}
	}}