package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();

		
		etal.occuperEtal(new Gaulois("bonemine", 2), "fleur", 10);
		etal.acheterProduit(5, null);
		
		
		Gaulois abraracoursix = new Gaulois("Abraracoursix",10);
		try {
			etal.acheterProduit(0, abraracoursix);
		}catch(java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		etal.libererEtal();
		try {
			etal.acheterProduit(2, abraracoursix);
		}catch(IllegalStateException e) {
			e.printStackTrace();
		}
		
		System.out.println("fin du test");
	}

}
