package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum, int nbetals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche  = new Marche(nbetals);
	}

	private static class Marche{
		private Etal[] etal;
		
		private Marche(int nbetals) {
			etal = new Etal[nbetals];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etal[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int i = 0;
			while(i < etal.length) {
				if(etal[i] != null && etal[i].isEtalOccupe()) {
					return i;
				}
				i = i+1;
			}
			
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int i = 0;
			int nbproduit = 0;
			while(i< etal.length ) {
				if(etal[i] != null && etal[i].contientProduit(produit)) {
					nbproduit ++;
				}
				i ++;
			}
			Etal[] nbpro = new Etal[nbproduit];
			int i2 = 0;
			i = 0;
			while (i<etal.length) {
				if(etal[i] != null && etal[i].contientProduit(produit)) {
					nbpro[i2] = etal[i];
					i2++;
				}
				i++;
			}
			return nbpro;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i =0; i< etal.length ; i++) {
				if(etal[i] != null && gaulois.getNom().equals(etal[i].getVendeur().getNom())) {
					return etal[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int i = 0;
			while(i< etal.length) {
				if(etal[i].isEtalOccupe()) {
					etal[i].afficherEtal();
				}
				i = i+1;
			}
			if(i!= etal.length){
				i = etal.length - i + 1;
				chaine.append( "Il reste " + i + " �tals non utilis�s dans le march�.\n" );
			}
			return chaine.toString();
		}
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
		if(this.chef == null) {
			throw new VillageSansChefException("pas de chef dans ce village !");
		}
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
	
	public String installerVendeur(Gaulois gaulois, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(gaulois + "cherche un endroit pour vendre" + nbProduit + produit + "\n");
		int i = marche.trouverEtalLibre();
		if(i!= -1) {
			marche.utiliserEtal(i, gaulois, produit, nbProduit);
			chaine.append("Le" + gaulois.getNom() + "vend des" + produit + "� l'�tal n�" + i +"\n");
		}
		else {
			chaine.append("Plus d'etals disponible :<\n");
		}
		return chaine.toString();
		
	}
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etal = this.marche.trouverEtals(produit);
		int i = etal.length;
		if (i ==1) {
			chaine.append("Seul le vendeur " + etal[0].getVendeur() + "propose des " + produit + "au marche\n");
		}
		if(i == 0) {
			chaine.append("Il n'y a pas le produit demand� \n");
		}
		else {
			chaine.append("Les vendeurs qui proposent des" + produit + "sont :\n");
			for(int v = 0; v < i; v++) {
				chaine.append("-" + etal[i].getVendeur() + "\n");
			}
		}
		return chaine.toString();
		
		
	}
	
	public String partirVendeur(Gaulois gaulois) {
		Etal etal = marche.trouverVendeur(gaulois);
		return etal.libererEtal();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
}