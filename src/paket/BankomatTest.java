package paket;

import java.util.ArrayList;
import java.util.Scanner;

public class BankomatTest {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		ArrayList<Racun> listaRacuna = new ArrayList<>();
		int opcija = 0;
		int brojRacuna;
		String imeVlasnikaRacuna;
		double iznosNaRacunu;
		int sourceAcc, targetAcc;
		
		do {
			
			System.out.print("\n----------------------MENI----------------------"
					+ "\n1. Kreiranje racuna."
					+ "\n2. Prebacivanje novca sa jednog racuna na drugi"
					+ "\n3. Ispisivanje detalja postojecih racuna."
					+ "\n0. Izlaz."
					+ "\n-------------------------------------------------"
					+ "\nUnesite opciju: ");
			
			//Unos opcije
			while(true) {
				try {
					do {
						opcija = input.nextInt();
						if(opcija < 0 || opcija > 3)
							System.out.println("Pogresan unos pokusajte ponovo!");
					}while((opcija < 0 || opcija > 3));
					break;
				} catch (Exception e) {
					System.out.println("Pogresan unos pokusajte ponovo!");
					input.nextLine();
				}
			}
			
			//opcija 1 - kreiranje racuna--------------------------------------------------------------
			if(opcija == 1) {
				// unos broja novog racuna
				System.out.print("\nUnesite broj novog racuna: ");
				boolean regularan;
				do {
					regularan = false;
					brojRacuna = input.nextInt();
					if(brojRacuna < 0) {
						System.out.println("Greska! Broj racuna mora biti pozitivan broj, pokusajte ponovo.");
						regularan = true;
					}	
					for (int i=0; i < listaRacuna.size(); i++) {
						if(listaRacuna.get(i).brojRacuna == brojRacuna) {
							System.out.println("Greska! Upisani broj racuna vec postoji, pokusajte ponovo.");
							regularan = true;
							break;
						}
					}
				}while(regularan);
				
				// unos imena vlasnika racuna
				System.out.print("Unesite ime vlasnika racuna: ");
				imeVlasnikaRacuna = input.next();
				
				
				// unos iznosa na racunu
				System.out.print("Unesite pocetni iznos na racunu: ");
				do {
					iznosNaRacunu = input.nextDouble();
					if(iznosNaRacunu < 0)
						System.out.println("Greska! Iznos ne smije biti manji od 0, pokusajte ponovo.");
				}while(iznosNaRacunu < 0);
				
				listaRacuna.add(new Racun(brojRacuna, imeVlasnikaRacuna, iznosNaRacunu));
			}
			
			//opcija 2 - prebacivanje novca s jednog racuna na drugi------------------------------
			if(opcija == 2) {
				boolean regularan;
				int i,j;
				// unos racuna source accounta
				System.out.print("\nUnesite broj racun sa kojeg zelite transferisati novac: ");
				do {
					regularan = true;
					sourceAcc = input.nextInt();
					for (i = 0; i < listaRacuna.size(); i++) {
						if(sourceAcc == listaRacuna.get(i).brojRacuna) {
							regularan = false;
							break;
						}
					}
					if(regularan)
						System.out.println("Greska! Uneseni broj racuna ne postoji, pokusajte ponovo.");
				}while(regularan);
				
				// unos racuna target accounta
				System.out.print("Unesite broj racun na koji zelite transferisati novac: ");
				do {
					regularan = true;
					targetAcc = input.nextInt();
					for (j = 0; j < listaRacuna.size(); j++) {
						if(targetAcc == listaRacuna.get(j).brojRacuna) {
							regularan = false;
							break;
						}
					}
					if(regularan)
						System.out.println("Greska! Uneseni broj racuna ne postoji, pokusajte ponovo.");
				}while(regularan);
				
				// unos iznos koji se treba prebaciti
				int iznos;
				System.out.print("Unesite iznos koji zelite transferisati: ");
				do {
					regularan = true;
					iznos = input.nextInt();
					if(iznos > listaRacuna.get(i).iznosNaRacunu)
						System.out.println("Greska! Nema dovoljna sredstava na racunu za ovu transakciju, unesite drugi iznos.");
					else
						regularan = false;
				}while(regularan);
				
				// izvrsavanje transakcije
				listaRacuna.get(i).iznosNaRacunu -= iznos;
				listaRacuna.get(j).iznosNaRacunu += iznos;
			}
			
			
			//opcija 3 - ispisivanje detalja postojecih racuna-------------------------------------
			if(opcija == 3) {
				if(listaRacuna.isEmpty())
					System.out.println("\nNe postoji nijedan kreiran racun.");
				else
					for(int i=0; i < listaRacuna.size(); i++) {
						System.out.println("\nBroj racuna: "+ listaRacuna.get(i).brojRacuna
											+"\n-----------------------------------"
											+"\nVlasnik: "+ listaRacuna.get(i).imeVlasnika
											+"\nIznos: "+ listaRacuna.get(i).iznosNaRacunu);
					}
			}
				
		}while(opcija != 0);
		
		input.close();
	}

}
