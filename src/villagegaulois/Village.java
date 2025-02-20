package villagegaulois;

import java.lang.reflect.Constructor;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtalMarche);
	}
	
	private static class Marche{
		private Etal[] etals;
		private int nbEtal;
		
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			this.nbEtal =nbEtal;
			for(int i=0;i<this.nbEtal;i++) {
				this.etals[i] = new Etal();
			}
		}		
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i=0;i<nbEtal;i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsOccupe = new Etal[nbEtal];
			Etal[] etalsOccupeR;
			int nbEtalOccupe=0;
			for(int i=0;i<nbEtal;i++) {
				if(etals[i].contientProduit(produit)) {
					etalsOccupe[nbEtalOccupe] = etals[i];
					nbEtalOccupe++;
				}
			}
			etalsOccupeR = new Etal[nbEtalOccupe];
			for(int i=0;i<nbEtalOccupe;i++) {
				etalsOccupeR[i] = etalsOccupe[i];
			}
			return etalsOccupeR;
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nbEtal;i++) {
				if(etals[i].isEtalOccupe()) {
					if(etals[i].getVendeur() == gaulois) {
						return etals[i];
					}
				}
			}
			return null;
		}
		private String afficherMarche() {
			StringBuilder s = new StringBuilder();
			int nbEtalLibre=0;
			for(int i=0;i<nbEtal;i++) {
				if(etals[i].isEtalOccupe()) {
					s.append(etals[i].afficherEtal());
				}else {
					nbEtalLibre++;
				}
			}
			s.append("Il reste " + nbEtalLibre + "2tals non utilisés dans le marché.\n");
			return s.toString();
		}
	}
	
	public String installerVendeur(Gaulois vendeur,String produit, int nbProduit) {
		int nEtalLibre = marche.trouverEtalLibre();
		StringBuilder s = new StringBuilder();
		s.append(vendeur.getNom());
		s.append(" cherche un endroit pour vendre ");
		s.append(nbProduit);
		s.append(" ");
		s.append(produit);
		s.append(".\n");
		if(nEtalLibre != -1) {
			marche.utiliserEtal(nEtalLibre, vendeur, produit, nbProduit);
			s.append("Le vendeur ");
			s.append(vendeur.getNom());
			s.append(" vend des fleurs à l'étal n°");
			s.append(nEtalLibre+1);
			s.append(".\n");
		}
		return s.toString();
	}
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalVendProduit = marche.trouverEtals(produit);
		int nbEtalVendProduit = etalVendProduit.length;
		StringBuilder s = new StringBuilder();
		switch (nbEtalVendProduit) {
		case 0: {
			
			s.append("Il n'y a pas de vendeur qui propose des fleurs au marché.\n");
			break;
		}
		case 1: {
			s.append("Seul le vendeur ");
			s.append(etalVendProduit[0].getVendeur().getNom());
			s.append(" propose des ");
			s.append(produit);
			s.append(" au marché.\n");
			break;
		}
		default:
			s.append("Les vendeurs qui proposent des ");
			s.append(produit);
			s.append(" sont :\n");
			for(int i=0;i<nbEtalVendProduit;i++) {
				s.append("- ");
				s.append(etalVendProduit[i].getVendeur().getNom());
				s.append("\n");
			}
		}
		return s.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder s = new StringBuilder();
		Etal e = rechercherEtal(vendeur);
		for(int i=0;i<marche.nbEtal;i++) {
			if(marche.etals[i] == e) {
				marche.etals[i].libererEtal();
			}
		}
		
		return s.toString();
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}