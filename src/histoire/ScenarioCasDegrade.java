package histoire;
import villagegaulois.*;
import personnages.*;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Gaulois gauloisVide = null;
		
		village.ajouterHabitant(bonemine);
		village.installerVendeur(bonemine, "fleurs", 20);
		Etal etalFleur = village.rechercherEtal(bonemine);
		Etal etalVide = new Etal();
		etalFleur.acheterProduit(10, gauloisVide);
		
	}
}
