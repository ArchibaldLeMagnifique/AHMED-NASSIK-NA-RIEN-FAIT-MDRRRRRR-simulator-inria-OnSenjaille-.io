package moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordonnanceur {
	
	
	private Partie part;
	
	Ordonnanceur(Partie p){
		part=p;
	}
	
	public void tour(){
		List<Integer> conds;
		int act;
		List<ActionFutur> actions = new ArrayList<ActionFutur>();
		for(Personnage p : part.personnages()){
			conds = this.calculConditions(p);
			act = p.automate().action(p.etat(), conds);
			actions.add(new ActionFutur(p,act));
		}
		Collections.sort(actions);
		double roll;
		for(ActionFutur a : actions){
			for(ActionFutur b : actions){
				if(a.type()==TypeAction.FRAPPE&&b.type()==TypeAction.FRAPPE){
					if(a.cible()==b.perso().position()&&b.cible()==a.perso().position()){ //Si les guerriers s'attaque tout les deux
						roll = Math.random();
						if (roll<=0.33){
							a.setType(TypeAction.RATE);
						}else if(roll>=0.66){
							b.setType(TypeAction.RATE);
						} //si 0.33<roll<0.66 les guerrier s'entretue 
					}
				}
				if(a.type()==TypeAction.PEINT&&b.type()==TypeAction.PEINT){
					if(a.cible()==b.cible()){ //Si deux peintre peignent la même case
						if (!a.sameColor(b)){
							roll = Math.random();
							if (roll<=0.5){
								a.setType(TypeAction.RATE);
							}else{
								b.setType(TypeAction.RATE);
							}
						}
					}
				}
				if(a.type()==TypeAction.MOUVEMENT&&b.type()==TypeAction.MOUVEMENT){	
					if(a.cible()==b.cible()){ //Si deux personnages avance sur la même case.
						roll = Math.random();
						if (roll<=0.50){
							a.setCible(4); //Avancer sur place.
						}else{
							b.setCible(4);
						}
					}
				}
			}
		}
		for(ActionFutur a : actions){
			a.executer();
		}
	}
	
	//Calcul les conditions a tester pour un personnages
	//TODO
	
	public List<Integer> calculConditions(Personnage p){
		List<Integer> conditions = new ArrayList<Integer>();
		
		/*
		 * if (p.position().getY()!=0){
		 * conditions.add(11);
		 * }
		*/
		int X=p.partie().decor().length;
		int Y=p.partie().decor()[0].length;
	
		boolean nord_accessible = false;
		boolean est_accessible = false;
		boolean sud_accessible = false;
		boolean ouest_accessible = false;
		
		Personnage p_NO=null;
		Personnage p_NE=null;
		Personnage p_SE=null;
		Personnage p_SO=null;
		
		Personnage p_N=null;
		Personnage p_E=null;
		Personnage p_S=null;
		Personnage p_O=null;
		
		boolean EnnemiN=false;
		boolean EnnemiE=false;
		boolean EnnemiS=false;
		boolean EnnemiO=false;
		
		Cellule c_N=null;
		Cellule c_E=null;
		Cellule c_S=null;
		Cellule c_O=null;

		Cellule c_NO=null;
		Cellule c_NE=null;
		Cellule c_SE=null;
		Cellule c_SO=null;
		
		boolean BlancEl_N=false;
		boolean BlancEl_E=false;
		boolean BlancEl_S=false;
		boolean BlancEl_O=false;
		
		boolean RougeEl_N=false;
		boolean RougeEl_E=false;
		boolean RougeEl_S=false;
		boolean RougeEl_O=false;
		
		boolean BleueEl_N=false;
		boolean BleueEl_E=false;
		boolean BleueEl_S=false;
		boolean BleueEl_O=false;

		
		if(p.position().getY()-1>=0)
		{
			//Personnage au nord
			if (p.partie().occupe(p.position().getX(), p.position().getY()-1)!=null){
				
				//Allie
				if(p.partie().occupe(p.position().getX(), p.position().getY()-1).equipe()==p.equipe()){
					conditions.add(codes.allieNord);
				}
				else //Ennemi 
				{
					conditions.add(codes.ennemieNord);
				}
			}
			
			//CaseBlanche(N)
			if(p.partie().decor()[p.position().getX()][p.position().getY()-1].couleur()==0)
			{
				conditions.add(codes.caseBlancheNord);
			}
			else if(p.partie().decor()[p.position().getX()][p.position().getY()-1].couleur()==1)
			//CaseBleu(N)
			{
				conditions.add(codes.caseBleuNord);
			}
			else
			//CaseRouge(N)
			{
				conditions.add(codes.caseRougeNord);
			}
			//Mur(N)
			if(p.partie().decor()[p.position().getX()][p.position().getY()-1].valeur()==codes.mur)
			{
				conditions.add(codes.murNord);
			}
			nord_accessible = true;
		}
		
		if(p.position().getX()<=X-1)
		{
			//Personnage a l'est
			if (p.partie().occupe(p.position().getX()-1, p.position().getY())!=null){
				
				//Allie
				if(p.partie().occupe(p.position().getX()+1, p.position().getY()).equipe()==p.equipe()){
					conditions.add(codes.allieEst);
				}
				else //Ennemi 
				{
					conditions.add(codes.ennemiEst);
				}
			}
			//CaseBlanche(E)
			if(p.partie().decor()[p.position().getX()+1][p.position().getY()].couleur()==0)
			{
				conditions.add(codes.caseBlancheEst);
			}
			else if(p.partie().decor()[p.position().getX()+1][p.position().getY()].couleur()==1)
			//CaseBleu(E)	
			{
				conditions.add(codes.caseBleuEst);
			}
			else
			//CaseRouge(E)
			{
				conditions.add(codes.caseRougeEst);
			}
			//Mur(E)
			if(p.partie().decor()[p.position().getX()+1][p.position().getY()].valeur()==codes.mur)
			{
				conditions.add(codes.murEst);
			}
			est_accessible = true;
		}
		
		if(p.position().getY()<=Y-1)
		{
			//Personnage au sud
			if (p.partie().occupe(p.position().getX(), p.position().getY()+1)!=null){
				
				//Allie
				if(p.partie().occupe(p.position().getX(), p.position().getY()+1).equipe()==p.equipe()){
					conditions.add(codes.allieSud);
				}
				else //Ennemi 
				{
					conditions.add(codes.ennemiSud);
				}
			}
			//CaseBlanche(S)
			if(p.partie().decor()[p.position().getX()][p.position().getY()+1].couleur()==0)
			{
				conditions.add(codes.caseBlancheSud);
			}
			else if(p.partie().decor()[p.position().getX()][p.position().getY()+1].couleur()==1)
			//CaseBleu(S)
			{
				conditions.add(codes.caseBleuSud);
			}
			else
			//CaseRouge(S)
			{
				conditions.add(codes.caseRougeSud);
			}
			//Mur(S)
			if(p.partie().decor()[p.position().getX()][p.position().getY()+1].valeur()==codes.mur)
			{
				conditions.add(codes.murSud);
			}
			sud_accessible = true;
		}
		
		if(p.position().getX()-1>=0)
		{
			//Personnage a l'ouest
			if (p.partie().occupe(p.position().getX()+1, p.position().getY())!=null){
			
				//Allie
				if(p.partie().occupe(p.position().getX()-1, p.position().getY()).equipe()==p.equipe()){
					conditions.add(codes.allieOuest);
				}	
				else //Ennemi 
				{
					conditions.add(codes.ennemiOuest);
				}
			}
			//CaseBlanche(O)
			if(p.partie().decor()[p.position().getX()-1][p.position().getY()].couleur()==0)
			{
				conditions.add(codes.caseBlancheOuest);
			}
			else if(p.partie().decor()[p.position().getX()-1][p.position().getY()].couleur()==1)
			//CaseBleu(O)
			{
				conditions.add(codes.caseBleuOuest);
			}
			else
			//CaseRouge(O)
			{
				conditions.add(codes.caseRougeOuest);
			}
			//Mur(O)
			if(p.partie().decor()[p.position().getX()-1][p.position().getY()].valeur()==codes.mur)
			{
				conditions.add(codes.murOuest);
			}
			ouest_accessible = true;
		}
		//CaseBlanche(cellule actuelle)
		if(p.partie().decor()[p.position().getX()][p.position().getY()].couleur()==0)
		{
			conditions.add(codes.caseBlancheCentre);
		}
		else if(p.partie().decor()[p.position().getX()][p.position().getY()].couleur()==1)
		//CaseBleu(Cellule actuelle)	
		{
			conditions.add(codes.caseBleuCentre);
		}
		else
		{
			conditions.add(codes.caseRougeCentre);
		}
		
		if(nord_accessible)
		{
			if(ouest_accessible)
			{
				p_NO=p.partie().occupe(p.position().getX()-1,p.position().getY()-1);
				c_NO=p.partie().decor()[p.position().getX()-1][p.position().getY()-1];
			}
			
			if(est_accessible)
			{
				p_NE=p.partie().occupe(p.position().getX()+1,p.position().getY()-1);
				c_NE=p.partie().decor()[p.position().getX()+1][p.position().getY()-1];
			}
			
			p_N=p.partie().occupe(p.position().getX(),p.position().getY()-2);
			c_N =p.partie().decor()[p.position().getX()][p.position().getY()-2];
		}
		
		if(sud_accessible)
		{
			if(est_accessible)
			{
				p_SE=p.partie().occupe(p.position().getX()+1,p.position().getY()+1);
				c_SE=p.partie().decor()[p.position().getX()+1][p.position().getY()+1];
			}
			
			if(ouest_accessible)
			{
				p_SO=p.partie().occupe(p.position().getX()-1,p.position().getY()+1);
				c_SO =p.partie().decor()[p.position().getX()-1][p.position().getY()+1];
			}
			p_S=p.partie().occupe(p.position().getX(),p.position().getY()-2);
			c_S =p.partie().decor()[p.position().getX()][p.position().getY()+2];
		}
		
		if(est_accessible)
		{
			p_E=p.partie().occupe(p.position().getX()+2,p.position().getY());
			c_E =p.partie().decor()[p.position().getX()+2][p.position().getY()];
		}
		
		if(ouest_accessible)
		{
			p_O=p.partie().occupe(p.position().getX()-2,p.position().getY());
			c_O =p.partie().decor()[p.position().getX()-2][p.position().getY()];
		}
		
		
		//quelqun au NW
		if(p_NO!=null)
		{
			//ennemi au NW
			if (p_NO.equipe()!=p.equipe())
			{
				conditions.add(codes.ennemiEloigneNord);
				conditions.add(codes.ennemiEloigneOuest);
				EnnemiN=true;
				EnnemiO=true;
			}
			
		}
		
		if(p_SE!=null)
		{
			if (p_SE.equipe()!=p.equipe())
			{
				conditions.add(codes.ennemiEloigneSud);
				conditions.add(codes.ennemiEloigneEst);
				EnnemiS=true;
				EnnemiE=true;
			}
		}

		if(p_NE!=null)
		{
			if (p_NE.equipe()!=p.equipe())
			{
				if (!EnnemiN){
					conditions.add(codes.ennemiEloigneNord);
					EnnemiN=true;}
				
				if (!EnnemiE){
					conditions.add(codes.ennemiEloigneEst);
					EnnemiE=true;}
			}
		}
		
		if(p_SO!=null)
		{
			if (p_SO.equipe()!=p.equipe())
			{
				if (!EnnemiS){
					conditions.add(codes.ennemiEloigneSud);
					EnnemiS=true;}
				
				if (!EnnemiO){
					conditions.add(codes.ennemiEloigneOuest);
					EnnemiO=true;}
			}
		}
		
		if(!EnnemiN && p_N!=null)
		{
			if(p_N.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneNord);}
		}
		
		if(!EnnemiE && p_E!=null)
		{
			if(p_E.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneEst);}
		}
		
		if(!EnnemiS && p_S!=null)
		{
			if(p_S.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneSud);}
		}
		
		if(!EnnemiO && p_O!=null)
		{
			if(p_O.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneOuest);}
		}
		
		//----------------------------------------------------
		if(c_NO!=null)
		{
			//ennemi au NW
			if (c_NO.couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheEloigneeNord);
				conditions.add(codes.caseBlancheEloigneeOuest);
				BlancEl_N=true;
				BlancEl_O=true;
			}
			else if(c_NO.couleur()==codes.bleu)
			{
				conditions.add(codes.caseBleuEloigneeNord);
				conditions.add(codes.caseBleuEloigneeOuest);
				BleueEl_N=true;
				BleueEl_O=true;
			}
			else
			{
				conditions.add(codes.caseRougeEloigneeNord);
				conditions.add(codes.caseRougeEloigneeOuest);
				RougeEl_N=true;
				RougeEl_O=true;
			}
		}
		
		if(c_SE!=null)
		{
			//ennemi au NW
			if (c_NO.couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheEloigneeSud);
				conditions.add(codes.caseBlancheEloigneeEst);
				BlancEl_S=true;
				BlancEl_E=true;
			}
			else if(c_NO.couleur()==codes.bleu)
			{
				conditions.add(codes.caseBleuEloigneeSud);
				conditions.add(codes.caseBleuEloigneeEst);
				BleueEl_S=true;
				BleueEl_E=true;
			}
			else
			{
				conditions.add(codes.caseRougeEloigneeSud);
				conditions.add(codes.caseRougeEloigneeEst);
				RougeEl_S=true;
				RougeEl_E=true;
			}
		}

		if(c_NE!=null)
		{
			if (c_NE.couleur()==codes.blanc)
			{
				if (!BlancEl_N){
					conditions.add(codes.caseBlancheEloigneeNord);
					BlancEl_N=true;}
				
				if (!BlancEl_E){
					conditions.add(codes.caseBlancheEloigneeEst);
					BlancEl_E=true;}
			}
			else if(c_NE.couleur()==codes.bleu)
			{
				if (!BleueEl_N){
					conditions.add(codes.caseBleuEloigneeNord);
					BleueEl_N=true;}
				
				if (!BleueEl_E){
					conditions.add(codes.caseBleuEloigneeEst);
					BleueEl_E=true;}
			}
			else
			{
				if (!RougeEl_N){
					conditions.add(codes.caseRougeEloigneeNord);
					RougeEl_N=true;}
				
				if (!BleueEl_E){
					conditions.add(codes.caseRougeEloigneeEst);
					RougeEl_E=true;}
			}
		}
		
		if(c_SO!=null)
		{
			if (c_SO.couleur()==codes.blanc)
			{
				if (!BlancEl_S){
					conditions.add(codes.caseBlancheEloigneeSud);
					BlancEl_S=true;}
				
				if (!BlancEl_O){
					conditions.add(codes.caseBlancheEloigneeOuest);
					BlancEl_O=true;}
			}
			else if(c_SO.couleur()==codes.bleu)
			{
				if (!BleueEl_S){
					conditions.add(codes.caseBleuEloigneeSud);
					BleueEl_S=true;}
				
				if (!BleueEl_O){
					conditions.add(codes.caseBleuEloigneeOuest);
					BleueEl_O=true;}
			}
			else
			{
				if (!RougeEl_S){
					conditions.add(codes.caseRougeEloigneeSud);
					RougeEl_S=true;}
				
				if (!BleueEl_O){
					conditions.add(codes.caseRougeEloigneeOuest);
					RougeEl_O=true;}
			}
		}
		
		if(c_N!=null)
		{
			if(c_N.couleur()==codes.blanc && !BlancEl_N){
				conditions.add(codes.caseBlancheEloigneeNord);}
			else if (c_N.couleur()==codes.bleu && !BleueEl_N)
			{
				conditions.add(codes.caseBleuEloigneeNord);}
			else if(c_N.couleur()==codes.rouge && !RougeEl_N)
			{
				conditions.add(codes.caseRougeEloigneeNord);
			}
		}
		
		if(c_E!=null)
		{
			if(c_E.couleur()==codes.blanc && !BlancEl_E){
				conditions.add(codes.caseBlancheEloigneeEst);}
			else if (c_E.couleur()==codes.bleu && !BleueEl_E)
			{
				conditions.add(codes.caseBleuEloigneeNord);}
			else if(c_E.couleur()==codes.rouge && !RougeEl_E)
			{
				conditions.add(codes.caseRougeEloigneeEst);
			}
		}
		
		if(c_S!=null)
		{
			if(c_S.couleur()==codes.blanc && !BlancEl_N){
				conditions.add(codes.caseBlancheEloigneeSud);}
			else if (c_S.couleur()==codes.bleu && !BleueEl_S)
			{
				conditions.add(codes.caseBleuEloigneeSud);}
			else if(c_S.couleur()==codes.rouge && !RougeEl_S)
			{
				conditions.add(codes.caseRougeEloigneeSud);
			}
		}
		
		if(c_O!=null)
		{
			if(c_O.couleur()==codes.blanc && !BlancEl_O){
				conditions.add(codes.caseBlancheEloigneeOuest);}
			else if (c_O.couleur()==codes.bleu && !BleueEl_O)
			{
				conditions.add(codes.caseBleuEloigneeOuest);}
			else if(c_O.couleur()==codes.rouge && !RougeEl_O)
			{
				conditions.add(codes.caseRougeEloigneeOuest);
			}
		}
		
			return conditions;
		}
}

