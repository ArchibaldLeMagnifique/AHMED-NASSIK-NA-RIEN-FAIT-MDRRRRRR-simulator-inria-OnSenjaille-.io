package moteur;

public class Cellule {

	private int valeur;
	private int couleur;
	
	public Cellule(){
		valeur = 0;
		couleur = 0;
	}
	
	public Cellule(int i,int c){
		valeur = i;
		couleur = c;
	}
	
	public Cellule(int i){
		valeur = i;
		couleur = codes.blanc;
	}
	
	public int valeur(){
		return valeur;
	}
	
	public void setValeur(int v){
		this.valeur=v;
		if(v==codes.mur){
			couleur=codes.couleurMur;
		}
	}
	
	public int couleur(){
		return couleur;
	}
	
	public void peindre(int c){
		couleur = c;
		
		if(couleur==codes.rouge){
			if(this.valeur>=codes.peindreB&&this.valeur<=codes.peindreB+4){
				this.valeur=this.valeur-codes.peindreB+codes.peindreR;
				System.out.println(codes.peindreR+" : "+this.valeur);
			}

			/*else if(valeur>=codes.caseBlancheEloigneeNord&&valeur<=codes.caseBlancheEloigneeOuest){
				this.valeur=this.valeur - codes.caseBlancheEloigneeNord + codes.caseRougeEloigneeNord;
			}
			
			else if(valeur>=codes.caseBlancheNord&&valeur<=codes.caseBlancheCentre){
				this.valeur=this.valeur - codes.caseBlancheNord + codes.caseRougeNord;
			}
			
			else if(valeur>=codes.caseBleuEloigneeNord&&valeur<=codes.caseBleuEloigneeOuest){
				this.valeur=this.valeur - codes.caseBleuEloigneeNord + codes.caseRougeEloigneeNord;
			}
			
			else if(valeur>=codes.caseBleuNord&&valeur<=codes.caseBleuOuest){
				this.valeur=this.valeur - codes.caseBleuNord + codes.caseRougeNord;
			}	*/
		
		}
		if(couleur==codes.bleu){
			if(this.valeur>=codes.peindreR&&this.valeur<=codes.peindreR+4){
				this.valeur=(this.valeur)-(codes.peindreR)+(codes.peindreB);
				System.out.println(codes.peindreB+" : "+this.valeur);
			}
			/*
			else if(valeur>=codes.caseBlancheEloigneeNord&&valeur<=codes.caseBlancheEloigneeOuest){
				this.valeur=this.valeur - codes.caseBlancheEloigneeNord + codes.caseBleuEloigneeNord;
			}
			
			else if(valeur>=codes.caseBlancheNord&&valeur<=codes.caseBlancheCentre){
				this.valeur=this.valeur - codes.caseBlancheNord + codes.caseBleuNord;
			}
			
			else if(valeur>=codes.caseRougeEloigneeNord&&valeur<=codes.caseRougeEloigneeOuest){
				this.valeur=this.valeur - codes.caseRougeEloigneeNord + codes.caseBleuEloigneeNord;
			}
			
			else if(valeur>=codes.caseRougeNord&&valeur<=codes.caseRougeOuest){
				this.valeur=this.valeur - codes.caseRougeNord + codes.caseBleuNord;
			}	*/
		}
		
		
	}
	
}
