import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class Funciones {
	
	int indicador;
	
	public int getIndicador() {
		return indicador;
	}

	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}

	public Node ingreso(GraphDatabaseService db,String nombre)
	{
		Node usuario;
		usuario= db.findNode(Etiquetas.Persona, "nombreP", nombre);
		if (usuario==null)
		{
			usuario= db.createNode();
			usuario.addLabel(Etiquetas.Persona);
			usuario.setProperty("nombreP", nombre);
			setIndicador(2);
			System.out.println("Usuario nuevo");
		}
		else
		{
			setIndicador(1);
			System.out.println("Usuario guardado");
		}
		return usuario;
	}
	
	public Vector<Integer> IdsPeliculas(GraphDatabaseService db)
	{
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
		return pelisId;
	}
	
	public Node recomendacionRandom(GraphDatabaseService db, Vector<Integer>pelisId)
	{
		Random random= new Random();
		int r= random.nextInt(pelisId.size());
		Node pelicula= db.getNodeById(pelisId.get(r));
		return pelicula;
	}
	
	public Node recomendacion(GraphDatabaseService db, Vector<Node> pelisV, Vector<Integer>pelisId, Node usuario)
	{
		Node pelicula=null;
		Random random= new Random();
		int r= random.nextInt(6);
		switch(r)
		{
			case 0:
				System.out.println("DEBUG4a");
				//recomendacion por actores
				recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Actor_principal);
				break;
			case 1:
				System.out.println("DEBUG4g");
				//recomendación por género
				recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Del_genero);
				break;
			case 2:
				System.out.println("DEBUG4l");
				//recomendación por lugar
				recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Ocurre_en);
				break;
			case 3:
				System.out.println("DEBUG4d");
				//recomendación por director
				recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Dirigida_por);
				break;
			case 4:
				System.out.println("DEBUG4ñ");
				//recomendación por año
				recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Lanzada_en);
				break;
			case 5:
				System.out.println("DEBUG4s");
				//recomendación por saga
				recomendacionE(db, pelisV, pelisId, usuario, Relaciones.Saga_);
				break;
			
		}
		return pelicula;
	}
	
	public Vector<Node> actualizacionPerfilUsuario(GraphDatabaseService db, Node usuario)
	{
		Iterator<Relationship> IRelU= usuario.getRelationships().iterator();
		Vector<Relationship> relU= new Vector<Relationship>();
		while(IRelU.hasNext())
		{
			relU.add(IRelU.next());
		}
		Vector<Node> pelisV= new Vector<Node>();
		for (int j=0; j<relU.size(); j++)
		{
			Relationship r2= relU.get(j);
			pelisV.addElement(r2.getEndNode());
		}
		return pelisV;
	}
	
	public Node recomendacionE(GraphDatabaseService db,  Vector<Node> pelisV, Vector<Integer> pelisId, Node usuario, Relaciones type)
	{
		System.out.println("DEBUG5");
		Node pelicula;
		Random random= new Random();
		Vector<Node> caracPelis= new Vector<Node>();
		System.out.println("DEBUG6");
		for (int k=0; k<pelisV.size(); k++)
		{
			Iterator<Relationship> ir3= pelisV.get(k).getRelationships(type).iterator();
			Vector<Node> vc= new Vector<Node>();
			while(ir3.hasNext())
			{
				System.out.println("DEBUG7");
				vc.addElement(ir3.next().getEndNode());
			}
			int rm= random.nextInt(vc.size());
			//Relationship r3= pelisV.get(k).getSingleRelationship(type, Direction.OUTGOING);
			Node t= vc.get(rm);
			caracPelis.add(t);
			System.out.println("DEBUG8");
		}
		Vector<Integer> cOrd= new Vector<Integer>();
		for (int l=0; l<caracPelis.size(); l++)
		{
			System.out.println("DEBUG9");
			Node n= caracPelis.get(l);
			if (cOrd.contains(n)==false)
			{
				int y=0;
				System.out.println("DEBUG10");
				int a=caracPelis.indexOf(n,l);
				System.out.println(a);
				while (a>-1)
				{
					System.out.println("DEBUG11");
					a=caracPelis.indexOf(n, a+1);
					y++;
				}
				System.out.println(y);
				cOrd.add(l,y);
			}
		}
		System.out.println("DEBUG12");
		Vector<Node> caracOrd= new Vector<Node>();
		System.out.println(cOrd.size());
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
		System.out.println("DEBUG13");
		System.out.println(caracOrd.size());
		Node elegido= caracOrd.get(0);
		Iterator<Relationship> rels= elegido.getRelationships().iterator();
		Vector<Node> pRecom= new Vector<Node>();
		while(rels.hasNext())
		{
			pRecom.addElement(rels.next().getStartNode());
		}
		System.out.println("DEBUG14");
		System.out.println(pRecom.size());
		pelicula= pRecom.remove(0);
		System.out.println(pelicula.getProperty("nombre"));
		int v= pelisId.size();
		System.out.println("DEBUG15");
		System.out.println(v);
		while(pelisV.contains(pelicula))
		{
			System.out.println("DEBUG16");
			if(v>0){
				System.out.println("DEBUG17");
				if(pRecom.isEmpty()==false)
				{
					pelicula=pRecom.remove(0);
				}
				else
				{
					System.out.println("DEBUG18");
					pelicula=recomendacionRandom(db,pelisId);
				}
			}
			else
			{
				System.out.println("DEBUG19");
				pelicula=null;
				System.out.println("Ha visto todas las películas");
			}
			v--;
		}
		System.out.println("DEBUG20");
		System.out.println(pelicula.getProperty("nombre"));
		return pelicula;
	}

}
