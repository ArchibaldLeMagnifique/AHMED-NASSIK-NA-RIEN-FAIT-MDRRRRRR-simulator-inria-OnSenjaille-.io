package moteur;

import java.util.ArrayList;
import java.util.List;


public class Peintre extends Personnage{
	
	public Peintre(int e,Automate a,Position p){
		super(e,a,p);
	}
	
	// TODO
	//1 = bleu
	// 2 = rouge
	
	public void peindre(int direction,int couleur){
		int x = this.position().getX();	//verif au niveau de l'ordonanceur.
		int y = this.position().getY();
		if (couleur==codes.rouge||couleur==codes.bleu){
			switch (direction){
			case 0 :	//Nord
				this.partie.decor()[y-1][x].peindre(couleur);
				break;
			case 1 : 	//Est
				this.partie.decor()[y][x+1].peindre(couleur);
				break;
			case 2 : 	//Sud
				this.partie.decor()[y+1][x].peindre(couleur);
				break;
			case 3 : 	//Ouest
				this.partie.decor()[y][x-1].peindre(couleur);
				break;
			default : this.partie.decor()[y][x].peindre(couleur); break;	//Sur Place
			}
		}
	}
	
	
	
	public boolean actionInterdite(){
		List<Integer> actionsInterdites=new ArrayList<Integer>();
		actionsInterdites.add(codes.frapper);

			return super.actionInterdite(actionsInterdites);
		
	}
	
	
}
