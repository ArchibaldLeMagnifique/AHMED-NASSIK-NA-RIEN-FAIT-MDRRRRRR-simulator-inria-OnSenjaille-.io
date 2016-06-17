package Parser;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

import moteur.*;

import org.jdom2.filter.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ParserXML {
	//static org.jdom2.Document document;
	//static Element racine;// pas laisser ici

/*	public void test(String[] args) {
		// On crée une instaXxRomUalD720N0Sc0P3xXnce de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// ajouter scanf nom fichier
			document = sxb.build(new File("modele.xml"));
		} catch (Exception e) {
		}
		// On initialise un nouvel élément racine avec l'élément racine du
		// document.
		racine = document.getRootElement();// racine : <automates>
		// Méthode définie plus loin
		afficheALL();
		Partie partieOfficial = buildGame("modeleJ1", "modeleJ2");
		partieOfficial.affichageText();
	}*/

	
	 

	public ParserXML(){
		
	}
	
	
	
	
	
	
	
	void afficheALL(String nomFichier) {
		org.jdom2.Document document = null;
		Element racine;
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// ajouter scanf nom fichier
			document = sxb.build(new File(nomFichier));
		} catch (Exception e) {
		}
		
		racine = document.getRootElement();
		
		System.out.println("Nombre d'automates : " + racine.getChild("nb_aut").getText());
		List listAutomates = racine.getChildren("au");
		Iterator it_automates = listAutomates.iterator();// iterateur automates
		Element automate_courant;
		while (it_automates.hasNext()) // parcours automates
		{
			automate_courant = (Element) it_automates.next();
			System.out.println("Type du personnage : " + automate_courant.getAttributeValue("personnage"));
			Element action = automate_courant.getChild("action");
			System.out.println("Nombre de lignes automate actions : " + action.getAttributeValue("nb_l")
					+ "\nNombre de colonnes automate action : " + action.getAttributeValue("nb_c"));
			List listLignesAction = action.getChildren("l");
			Iterator it_lignes = listLignesAction.iterator(); // iterateur
																// lignes
																// automate
																// action
			Element ligne_courante;
			System.out.println("Automate des actions :");
			while (it_lignes.hasNext()) {
				ligne_courante = (Element) it_lignes.next();
				List listColonnesActions = ligne_courante.getChildren("c");
				Iterator it_colonnes = listColonnesActions.iterator();// iterateur
																		// colonnes
				Element colonne_courante;
				while (it_colonnes.hasNext()) {
					colonne_courante = (Element) it_colonnes.next();
					System.out.print(colonne_courante.getText());
				}
				System.out.println("");
			}

			Element transition = automate_courant.getChild("transition");
			System.out.println("Nombre de lignes automate transition : " + transition.getAttributeValue("nb_l")
					+ "\nNombre de colonnes transition action : " + transition.getAttributeValue("nb_c"));
			List listLignesTransi = transition.getChildren("l");
			it_lignes = listLignesTransi.iterator();
			System.out.println("Automate des Transitions :");
			while (it_lignes.hasNext()) {
				ligne_courante = (Element) it_lignes.next();
				List listColonnesTransitions = ligne_courante.getChildren("c");
				Iterator it_colonnes = listColonnesTransitions.iterator();
				Element colonne_courante;
				while (it_colonnes.hasNext()) {
					colonne_courante = (Element) it_colonnes.next();
					System.out.print(colonne_courante.getText());
				}
				System.out.println("");

			}

		}

	}

	static int nbAuto(String nomFichier)// renvoie le nombre d'automates
	{
		org.jdom2.Document document = null;
		Element racine;
		// On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;)
			document = sxb.build(new File(nomFichier));
		} catch (Exception e) {
		}
		// On initialise un nouvel élément racine avec l'élément racine du
		// document.
		racine = document.getRootElement();// <automates>
		int nbAuto;
		nbAuto = Integer.parseInt(racine.getChild("nb_aut").getValue());
		return nbAuto;

	}

	public Personnage createPersonnage(Element element, Partie partie, int indicePlacement) {
		int i = 0, j = 0;

		System.out.println("1Type du personnage : " + element.getAttributeValue("personnage"));

		Element action = element.getChild("action");
		System.out.println("Nombre de lignes automate actions : " + action.getAttributeValue("nb_l")
				+ "\nNombre de colonnes automate action : " + action.getAttributeValue("nb_c"));

		int actions[][] = new int[Integer.parseInt(action.getAttributeValue("nb_l"))][Integer.parseInt(action.getAttributeValue("nb_c"))];

		List listLignesAction = action.getChildren("l");
		Iterator it_lignes = listLignesAction.iterator(); // iterateur lignes
															// automate action
		Element ligne_courante;
		System.out.println("Automate des actions :");
		while (it_lignes.hasNext()) {
			ligne_courante = (Element) it_lignes.next();
			List listColonnesActions = ligne_courante.getChildren("c");
			Iterator it_colonnes = listColonnesActions.iterator();// iterateur
																	// colonnes
			Element colonne_courante;
			while (it_colonnes.hasNext()) {
				colonne_courante = (Element) it_colonnes.next();

				System.out.println(colonne_courante.getText());
				actions[i][j] = Integer.parseInt(colonne_courante.getText());
				System.out.println("actions "+i+" "+j+" "+actions[i][j]);
				j++;
			}

			i++;
			j = 0;

			System.out.println("");
		}
		Position automatePosition = partie.placerActions(actions, indicePlacement,Integer.parseInt(action.getAttributeValue("nb_c")),15);
		System.out.println("Positionnement en "+i);
		Element transition = element.getChild("transition");

		// TRANSITIONS
		int transi[][] = new int[Integer.parseInt(transition.getAttributeValue("nb_l"))][Integer.parseInt(transition.getAttributeValue("nb_c"))];
		i = 0;
		j = 0;

		System.out.println("Nombre de lignes automate transition : " + transition.getAttributeValue("nb_l")
				+ "\nNombre de colonnes transition action : " + transition.getAttributeValue("nb_c"));
		List listLignesTransi = transition.getChildren("l");
		it_lignes = listLignesTransi.iterator();
		System.out.println("Automate des Transitions :");
		while (it_lignes.hasNext()) {
			ligne_courante = (Element) it_lignes.next();
			List listColonnesTransitions = ligne_courante.getChildren("c");
			Iterator it_colonnes = listColonnesTransitions.iterator();
			Element colonne_courante;
			while (it_colonnes.hasNext()) {
				colonne_courante = (Element) it_colonnes.next();
				transi[i][j] = Integer.parseInt(colonne_courante.getText());
				System.out.print("transitions "+"j="+j+" i="+i+" "+colonne_courante.getText()+" ");
				j++;
			}

			j = 0;
			i++;
			System.out.println("");

		}
		Automate auto;
		// Position newPos=new Position(i*6,j);
		auto = new Automate(transi, automatePosition, partie);

		Personnage persoCourant = null;
		
		/*if (element.getAttributeValue("personnage") == "guerrier") {
			persoCourant = new Guerrier(0, auto, automatePosition);
		} else {
			if (element.getAttributeValue("personnage") == "peintre") {
				persoCourant = new Peintre(0, auto, automatePosition);
			}
		}*/
		System.out.println("X: "+automatePosition.getX()+" Y : "+automatePosition.getY());
	//	persoCourant = new Guerrier(0, auto, automatePosition);
		persoCourant = new Guerrier(0, auto, new Position(automatePosition.getX(),automatePosition.getY()));
		System.out.println("X: "+persoCourant.position().getX()+" Y : "+persoCourant.position().getY());

		//persoCourant = new Guerrier(0, auto, new Position(1,2));
		partie.ajouterPersonnage(persoCourant);
		return persoCourant;

	}

	
	public Joueur createPlayer(Partie partie, String nomFichier, int posInitiale) {
		// Joueur joueur=new Joueur();
		// Personnage persoCourant=null;
		
		
		org.jdom2.Document document = null;
		Element racine;
		SAXBuilder sxb = new SAXBuilder();
		//try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;)
			try {
				document = sxb.build(new File(nomFichier));
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//	} catch (Exception e) 
		
		
		racine = document.getRootElement();

		int i = 0;
		List<Element> listAutomates = racine.getChildren("au");
		Iterator<Element> it_automates = listAutomates.iterator();// iterateur automates
		List<Personnage> personnageListe = new ArrayList<Personnage>();
		Element automate_courant;
		while (it_automates.hasNext()) // parcours automates
		{
			automate_courant = (Element) it_automates.next();
			System.out.println("Type du personnage : " + automate_courant.getAttributeValue("personnage"));
			personnageListe.add(createPersonnage(automate_courant, partie, posInitiale + i));
			System.out.println("ooooo");
			i++;

		}

		return new Joueur(personnageListe);

	}
		
	

	public Partie buildGame(String fichierJoueur1, String fichierJoueur2) {
		int nbTotalAutomates = nbAuto(fichierJoueur1) + nbAuto(fichierJoueur2);
		// hauteur max : 40
		// longueur max : 5

		// taille map : chaque perso peut prendre la taille max

		Partie partieInitiale = new Partie(6 * nbTotalAutomates, 15);// peut
																		// etre
																		// modif
																		// 40
																		// trop
																		// faible//g
																		// mis
																		// 60 et
																		// ça
																		// spawn
																		// a un
																		// endroit
																		// random
																		// sur
																		// la
																		// hauteur
		Joueur joueur1 = createPlayer(partieInitiale, fichierJoueur1, 0);
		Joueur joueur2 = createPlayer(partieInitiale, fichierJoueur2, nbAuto(fichierJoueur1));

		// initialisation JOUEUR 1

		partieInitiale.ajouterListe(joueur1.getPersonnages());
		partieInitiale.ajouterListe(joueur2.getPersonnages());

		return partieInitiale;
	}

	/*
	 * static void instancierJoueur(joueur joueur,String nomFichier) { //On crée
	 * une instance de SAXBuilder SAXBuilder sxb = new SAXBuilder(); Element
	 * racine;//care y a deja un racine debut class try { //On crée un nouveau
	 * document JDOM avec en argument le fichier XML //Le parsing est terminé ;)
	 * document = sxb.build(new File(nomFichier)); } catch(Exception e){} //UN
	 * JOUEUR A UNE LISTE DE PERSONNAGES personnage persoCourant;//personnage
	 * courant que l'on va ajouter a la liste des persos du joueur List
	 * listAutomates = racine.getChildren("au"); Iterator it_automates =
	 * listAutomates.iterator();//iterateur automates Element automate_courant;
	 * automate_courant=(Element) it_automates.next();
	 * //System.out.println("ee");
	 * persoCourant.type=automate_courant.getAttributeValue("personnage");
	 * Element action=automate_courant.getChild("action"); int nbl,nbc;
	 * nbl=Integer.parseInt(action.getAttributeValue("nb_l"));
	 * nbc=Integer.parseInt(action.getAttributeValue("nb_c")); //int tg[][];
	 * //int[nbl][nbc] actions;//tableau actions System.out.println(
	 * "Nombre de lignes automate actions : "+action.getAttributeValue("nb_l")+
	 * "\nNombre de colonnes automate action : "
	 * +action.getAttributeValue("nb_c")); persoCourant.actions List
	 * listLignesAction=action.getChildren("l"); Iterator
	 * it_lignes=listLignesAction.iterator(); Element ligne_courante;
	 * System.out.println("Automate des actions :"); while(it_lignes.hasNext())
	 * { ligne_courante=(Element)it_lignes.next(); List
	 * listColonnesActions=ligne_courante.getChildren("c"); Iterator
	 * it_colonnes=listColonnesActions.iterator(); Element colonne_courante;
	 * while(it_colonnes.hasNext()) { colonne_courante=(Element)
	 * it_colonnes.next(); System.out.print(colonne_courante.getText()); }
	 * System.out.println(""); }
	 * 
	 * Element transition=automate_courant.getChild("transition");
	 * System.out.println("Nombre de lignes automate transition : "
	 * +transition.getAttributeValue("nb_l")+
	 * "\nNombre de colonnes transition action : "
	 * +transition.getAttributeValue("nb_c")); List
	 * listLignesTransi=transition.getChildren("l");
	 * it_lignes=listLignesTransi.iterator(); System.out.println(
	 * "Automate des Transitions :"); while(it_lignes.hasNext()) {
	 * ligne_courante=(Element)it_lignes.next(); List
	 * listColonnesTransitions=ligne_courante.getChildren("c"); Iterator
	 * it_colonnes=listColonnesTransitions.iterator(); Element colonne_courante;
	 * while(it_colonnes.hasNext()) { colonne_courante=(Element)
	 * it_colonnes.next(); System.out.print(colonne_courante.getText()); }
	 * System.out.println("");
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * ParserXML
	 * 
	 */
}