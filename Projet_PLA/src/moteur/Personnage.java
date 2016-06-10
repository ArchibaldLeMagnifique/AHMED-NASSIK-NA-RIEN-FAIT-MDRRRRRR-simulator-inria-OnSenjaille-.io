package moteur;

public abstract class Personnage {
	
	private int sante=1;
	private int etat; //TODO limite des états de l'automate.
	private Automate automate;
	private Position pos;
	
	Personnage(int e,Automate a,Position p){
		etat=e;
		automate=a;
		pos = p;
	}
	
	public int etat(){
		return etat;
	}
	
	public Automate automate(){
		return automate;
	}
	
	public Position position(){
		return pos;
	}
	
	public void agir(int action){
		switch(action){
		case 1 : this.avancerNord();
		}
	}
	
	public void avancerNord(){
		pos.setY(pos.getY()-1);	//verif au niveau de l'ordonanceur.
	}
	
	/*
	 * @ensure i<= nombre max d'etat
	 */
	public void setEtat(int e){
		etat = e;
	}
	

}