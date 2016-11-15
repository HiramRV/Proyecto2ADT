import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class Funciones {

	/**
 	 * Este m�todo realiza el ingreso del usuario al sistema
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
			//si no se encontr� el usuario, se crea un usuario nuevo
			usuario= db.createNode();
			usuario.addLabel(Etiquetas.Persona);
			usuario.setProperty("nombreP", nombre);
			System.out.println("Usuario nuevo");
		}
		else
		{
			//se encontr� el usuario
			System.out.println("Usuario guardado");
		}
		return usuario;
	}
	
	/**
 	 * Este m�todo genera un vector con los Ids de los nodos "pel�cula" en la database
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
				//si efectua esta acci�n implica que el nodo es pel�cula
			}
			catch(Exception e){
				//el nodo no es pel�cula 
			}
		}
		return pelisId;
	}
	
	/**
 	 * Este m�todo genera una recomendaci�n Random de pel�cula
 	 * @param db: Database
 	 * 	 	  pelisId: Vector con los Ids de las pel�culas
 	 * @return pelicula: Nodo de la pel�cula recomendada
 	 */
	public Node recomendacionRandom(GraphDatabaseService db, Vector<Integer>pelisId)
	{
		Random random= new Random(); //generador Random
		int r= random.nextInt(pelisId.size()); //n�mero random
		Node pelicula= db.getNodeById(pelisId.get(r));
		return pelicula;
	}
	
	/**
 	 * Este m�todo realiza una recomendaci�n de pel�cula en base al perfil del usuario
 	 * @param db: Database
 	 * 	 	  pelisV: Vector con los nodos de las pel�culas que le han gustado al usuario
 	 * 		  pelisId: Vector con los Ids de las pel�culas
 	 * 		  usuario: Nodo usuario
 	 * @return pelicula: Nodo de la pel�cula recomendada
 	 */
	public Node recomendacion(GraphDatabaseService db, Vector<Node> pelisV, Vector<Integer>pelisId, Node usuario)
	{
		Node pelicula=null; //se inicializa el nodo
		Random random= new Random(); //generador random
		int r= random.nextInt(6); //n�mero random
		switch(r)
		{
			case 0:
				System.out.println("Recomendaci�n por actor");
				//recomendacion por actores
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Actor_principal);
				break;
			case 1:
				System.out.println("Recomendaci�n por genero");
				//recomendaci�n por g�nero
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Del_genero);
				break;
			case 2:
				System.out.println("Recomendaci�n por lugar");
				//recomendaci�n por lugar
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Ocurre_en);
				break;
			case 3:
				System.out.println("Recomendaci�n por director");
				//recomendaci�n por director
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Dirigida_por);
				break;
			case 4:
				System.out.println("Recomendaci�n por a�o");
				//recomendaci�n por a�o
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Lanzada_en);
				break;
			case 5:
				System.out.println("Recomendaci�n por saga");
				//recomendaci�n por saga
				pelicula= recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Saga_);
				break;
			
		}
		return pelicula;
	}
	
	/**
 	 * Este m�todo actualiza la lista de pel�culas vistas por el usuario
 	 * @param db: Database
 	 * 		  usuario: Nodo usuario
 	 * @return pelisV: Vector con los nodos de las pel�culas vistas
 	 */
	public Vector<Node> actualizacionPerfilUsuario(GraphDatabaseService db, Node usuario)
	{
		Iterator<Relationship> IRelU= usuario.getRelationships().iterator();
		Vector<Relationship> relU= new Vector<Relationship>(); //vector con las relaciones del usuario
		while(IRelU.hasNext())
		{
			relU.add(IRelU.next());
		}
		Vector<Node> pelisV= new Vector<Node>(); //vector de las pel�culas vistas por el usuario
		for (int j=0; j<relU.size(); j++)
		{
			Relationship r2= relU.get(j);
			pelisV.addElement(r2.getEndNode());
		}
		return pelisV;
	}
	
	/**
 	 * Este m�todo realiza una recomendaci�n espec�fica para un tipo de caracter�stica de las pel�culas
 	 * @param db: Database
 	 * 		  pelisV: Vector con las pel�culas vistas por el usuario
 	 * 		  pelisId: Vector con los Ids de las pel�culas
 	 * 		  usuario: Nodo usuario
 	 * 		  type: Tipo de relaci�n entre la pel�cula y la caracter�stica espec�fica
 	 * @return pelicula: Nodo de la pel�cula recomendada
 	 */
	public Node recomendacionE(GraphDatabaseService db,  Vector<Node> pelisV, Vector<Integer> pelisId, Node usuario, Relaciones type)
	{
		Node pelicula;
		Random random= new Random(); //generador random
		Vector<Node> caracPelis= new Vector<Node>(); //nodos con caracter�sticas de las pel�culas
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
		Vector<Node> caracOrd= new Vector<Node>(); //caracter�sticas ordenadas de la que tiene m�s apariciones a la que menos
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
		Node elegido= caracOrd.get(0); //nodo elegido seg�n la caracter�stica de mayor aparici�n
		Iterator<Relationship> rels= elegido.getRelationships().iterator();
		Vector<Node> pRecom= new Vector<Node>(); //vector de pel�culas "recomendables" 
		while(rels.hasNext())
		{
			pRecom.addElement(rels.next().getStartNode());
		}
		pelicula= pRecom.remove(0); //remover primer elemento del vector
		System.out.println("Primera recomendaci�n: "+pelicula.getProperty("nombre"));//DEBUG
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
					//ya no hay pel�culas en el vector, se realiza una recomendaci�n random
					pelicula=recomendacionRandom(db,pelisId);
				}
			}
			else
			{
				//ya no hay pel�culas que no est�n en la lista de las pel�culas vistas por el usuario
				pelicula=null;
				System.out.println("Ha visto todas las pel�culas");
			}
			v--;
		}
		return pelicula;
	}
	
	/**
 	 * Este m�todo actualiza la lista de pel�culas vistas por el usuario
 	 * @param db: Database
 	 * 		  usuario: Nodo usuario
 	 * @return pelisV: Vector con los nodos de las pel�culas vistas
 	 */
	public void Agregar(GraphDatabaseService db, Scanner scan)
	{
		System.out.println("T�tulo pel�cula: ");
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
			System.out.println("G�nero: ");
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
		System.out.println("A�o: ");
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
		Node n4= db.findNode(Etiquetas.A�o, "fecha", an);
		if (n4==null)
		{
			n4= db.createNode(Etiquetas.A�o);
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
