import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class Funciones {

	/**
 	 * Este método realiza el ingreso del usuario al sistema
 	 * @param db: Database
 	 * 		  nombre: Nombre del usuario
 	 * @return usuario: Nodo usuario
 	 */
	public Node ingreso(GraphDatabaseService db,String nombre)
	{
		Node usuario;
		//se busca usuario por el nombre ingresado
		usuario= db.findNode(Etiquetas.Persona, "nombreP", nombre);
		if (usuario==null)
		{
			//si no se encontró el usuario, se crea un usuario nuevo
			usuario= db.createNode();
			usuario.addLabel(Etiquetas.Persona);
			usuario.setProperty("nombreP", nombre);
			System.out.println("Usuario nuevo");
		}
		else
		{
			//se encontró el usuario
			System.out.println("Usuario guardado");
		}
		return usuario;
	}
	
	/**
 	 * Este método genera un vector con los Ids de los nodos "película" en la database
 	 * @param db: Database
 	 * @return pelisId: vector con los Ids
 	 */
	public Vector<Integer> IdsPeliculas(GraphDatabaseService db)
	{
		Node nodo1;
		Vector<Integer> pelisId= new Vector<Integer>(); //vector en el que se guardaran los Ids
		for (int i=0; i<9000; i++)
		{
			try{
				nodo1= db.getNodeById(i);
				nodo1.getProperty("nombre");
				pelisId.add(i);
				//si efectua esta acción implica que el nodo es película
			}
			catch(Exception e){
				//el nodo no es película 
			}
		}
		return pelisId;
	}
	
	/**
 	 * Este método genera una recomendación Random de película
 	 * @param db: Database
 	 * 	 	  pelisId: Vector con los Ids de las películas
 	 * @return pelicula: Nodo de la película recomendada
 	 */
	public Node recomendacionRandom(GraphDatabaseService db, Vector<Integer>pelisId)
	{
		Random random= new Random(); //generador Random
		int r= random.nextInt(pelisId.size()); //número random
		Node pelicula= db.getNodeById(pelisId.get(r));
		return pelicula;
	}
	
	/**
 	 * Este método realiza una recomendación de película en base al perfil del usuario
 	 * @param db: Database
 	 * 	 	  pelisV: Vector con los nodos de las películas que le han gustado al usuario
 	 * 		  pelisId: Vector con los Ids de las películas
 	 * 		  usuario: Nodo usuario
 	 * @return pelicula: Nodo de la película recomendada
 	 */
	public Node recomendacion(GraphDatabaseService db, Vector<Node> pelisV, Vector<Integer>pelisId, Node usuario)
	{
		Node pelicula=null; //se inicializa el nodo
		Random random= new Random(); //generador random
		int r= random.nextInt(6); //número random
		switch(r)
		{
			case 0:
				System.out.println("Recomendación por actor");
				//recomendacion por actores
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Actor_principal);
				break;
			case 1:
				System.out.println("Recomendación por genero");
				//recomendación por género
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Del_genero);
				break;
			case 2:
				System.out.println("Recomendación por lugar");
				//recomendación por lugar
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Ocurre_en);
				break;
			case 3:
				System.out.println("Recomendación por director");
				//recomendación por director
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Dirigida_por);
				break;
			case 4:
				System.out.println("Recomendación por año");
				//recomendación por año
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Lanzada_en);
				break;
			case 5:
				System.out.println("Recomendación por saga");
				//recomendación por saga
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Saga_);
				break;
			
		}
		return pelicula;
	}
	
	/**
 	 * Este método actualiza la lista de películas vistas por el usuario
 	 * @param db: Database
 	 * 		  usuario: Nodo usuario
 	 * @return pelisV: Vector con los nodos de las películas vistas
 	 */
	public Vector<Node> actualizacionPerfilUsuario(GraphDatabaseService db, Node usuario)
	{
		Iterator<Relationship> IRelU= usuario.getRelationships().iterator();
		Vector<Relationship> relU= new Vector<Relationship>(); //vector con las relaciones del usuario
		while(IRelU.hasNext())
		{
			relU.add(IRelU.next());
		}
		Vector<Node> pelisV= new Vector<Node>(); //vector de las películas vistas por el usuario
		for (int j=0; j<relU.size(); j++)
		{
			Relationship r2= relU.get(j);
			pelisV.addElement(r2.getEndNode());
		}
		return pelisV;
	}
	
	/**
 	 * Este método realiza una recomendación específica para un tipo de característica de las películas
 	 * @param db: Database
 	 * 		  pelisV: Vector con las películas vistas por el usuario
 	 * 		  pelisId: Vector con los Ids de las películas
 	 * 		  usuario: Nodo usuario
 	 * 		  type: Tipo de relación entre la película y la característica específica
 	 * @return pelicula: Nodo de la película recomendada
 	 */
	public Node recomendacionE(GraphDatabaseService db,  Vector<Node> pelisV, Vector<Integer> pelisId, Node usuario, Relaciones type)
	{
		Node pelicula;
		Random random= new Random(); //generador random
		Vector<Node> caracPelis= new Vector<Node>(); //nodos con características de las películas
		for (int k=0; k<pelisV.size(); k++)
		{
			Iterator<Relationship> ir3= pelisV.get(k).getRelationships(type).iterator();
			Vector<Node> vc= new Vector<Node>();
			while(ir3.hasNext())
			{
				vc.addElement(ir3.next().getEndNode());
			}
			int rm= random.nextInt(vc.size());
			Node t= vc.get(rm);
			caracPelis.add(t);
		}
		Vector<Integer> cOrd= new Vector<Integer>(); 
		for (int l=0; l<caracPelis.size(); l++)
		{
			Node n= caracPelis.get(l);
			if (cOrd.contains(n)==false)
			{
				int y=0;
				int a=caracPelis.indexOf(n,l);
				while (a>-1)
				{
					a=caracPelis.indexOf(n, a+1);
					y++;
				}
				cOrd.add(l,y);
			}
		}
		Vector<Node> caracOrd= new Vector<Node>(); //características ordenadas de la que tiene más apariciones a la que menos
		int w=-1;
		for (int m=0; m<cOrd.size(); m++)
		{
			int c=0;
			for (int o=0; o<cOrd.size(); o++)
			{
				int d=cOrd.get(o);
				if (d>=c)
				{
					d=c;
					w=o;
				}
			}
			caracOrd.addElement(caracPelis.get(w));
			cOrd.set(w, 0);
		}
		Node elegido= caracOrd.get(0); //nodo elegido según la característica de mayor aparición
		Iterator<Relationship> rels= elegido.getRelationships().iterator();
		Vector<Node> pRecom= new Vector<Node>(); //vector de películas "recomendables" 
		while(rels.hasNext())
		{
			pRecom.addElement(rels.next().getStartNode());
		}
		pelicula= pRecom.remove(0); //remover primer elemento del vector
		System.out.println("Primera recomendación: "+pelicula.getProperty("nombre"));//DEBUG
		int v= pelisId.size();//DEBUG
		System.out.println(v);
		while(pelisV.contains(pelicula))
		{
			if(v>0){
				if(pRecom.isEmpty()==false)
				{
					//remover primer elemento del vector
					pelicula=pRecom.remove(0);
				}
				else
				{
					//ya no hay películas en el vector, se realiza una recomendación random
					pelicula=recomendacionRandom(db,pelisId);
				}
			}
			else
			{
				//ya no hay películas que no estén en la lista de las películas vistas por el usuario
				pelicula=null;
				System.out.println("Ha visto todas las películas");
			}
			v--;
		}
		return pelicula;
	}
	
	/**
 	 * Este método actualiza la lista de películas vistas por el usuario
 	 * @param db: Database
 	 * 		  usuario: Nodo usuario
 	 * @return pelisV: Vector con los nodos de las películas vistas
 	 */
	public void Agregar(GraphDatabaseService db, Scanner scan)
	{
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
	}

}
