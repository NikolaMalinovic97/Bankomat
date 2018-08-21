package paket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BankomatTest {

	public static void main(String[] args) {

		//Deklarisanje (ponegdje i inicijalizacija)
		Scanner input = new Scanner(System.in);
		ArrayList<Racun> listaRacuna = new ArrayList<>();
		int opcija = 0;
		int brojRacuna;
		String imeVlasnikaRacuna;
		double iznosNaRacunu;
		int sourceAcc, targetAcc;
		String s;
		
		//Deklaracija promjenjljivih potrebnih za input/output
		File fBrojRacuna = new File("Broj racuna.txt");
		File fVlasnikRacuna = new File("Vlasnik racuna.txt");
		File fIznosNaRacunu = new File("Iznos na racunu.txt");
		BufferedReader brBrojRacuna = null;
		BufferedReader brVlasnikRacuna = null;
		BufferedReader brIznosNaRacunu = null;
		PrintWriter pwBrojRacuna = null;
		PrintWriter pwVlasnikRacuna = null;
		PrintWriter pwIznosNaRacunu = null;
		
		//Kreiranje fajlova, ukoliko vec ne postoje
		if(! fBrojRacuna.exists())
			try {
				fBrojRacuna.createNewFile();
			} catch (IOException e1) {
				System.out.println("Greska. File nije kreiran!");
			}
		if(! fVlasnikRacuna.exists())
			try {
				fVlasnikRacuna.createNewFile();
			} catch (IOException e1) {
				System.out.println("Greska. File nije kreiran!");
			}
		if(! fIznosNaRacunu.exists())
			try {
				fIznosNaRacunu.createNewFile();
			} catch (IOException e1) {
				System.out.println("Greska. File nije kreiran!");
			}
		
		//Ucitavanje podataka iz fajlova u array listu
		try {
			brBrojRacuna = new BufferedReader(new FileReader(fBrojRacuna));
			brVlasnikRacuna = new BufferedReader(new FileReader(fVlasnikRacuna));
			brIznosNaRacunu = new BufferedReader(new FileReader(fIznosNaRacunu));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while((s = brVlasnikRacuna.readLine()) != null) {
				listaRacuna.add(new Racun(Integer.parseInt(brBrojRacuna.readLine()), s, Double.parseDouble(brIznosNaRacunu.readLine())));
			}
		} catch (IOException e1) {
			
		}
		try {
			brBrojRacuna.close();
			brVlasnikRacuna.close();
			brIznosNaRacunu.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//Dio programa koji se prikazuje korisniku u konzoli
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
				boolean regularan;
				do {
					regularan = false;
					while(true) {
						try {
							System.out.print("\nUnesite broj novog racuna: ");
							brojRacuna = input.nextInt();
							break;
						} catch (Exception e) {
							System.out.println("Pogresan unos pokusajte ponovo!");
							input.next();
						}
					} 
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
				while(true) {
					try {
						System.out.print("Unesite ime vlasnika racuna: ");
						imeVlasnikaRacuna = input.next();
						break;
					} catch (Exception e) {
						System.out.print("\nUnesite broj novog racuna: ");
						input.nextLine();
					}
				}				
					
				// unos iznosa na racunu
				do {
					while(true) {
						try {
							System.out.print("Unesite pocetni iznos na racunu: ");
							iznosNaRacunu = input.nextDouble();
							break;
						} catch (Exception e) {
							System.out.println("Pogresan unos pokusajte ponovo!");
							input.nextLine();
						}
					}
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
				do {
					regularan = true;
					while(true) {
						try {
							System.out.print("\nUnesite broj racun sa kojeg zelite transferisati novac: ");
							sourceAcc = input.nextInt();
							break;
						} catch (Exception e) {
							System.out.println("Pogresan unos pokusajte ponovo!");
							input.nextLine();
						}
					}
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
				do {
					regularan = true;
					while(true) {
						try {
							System.out.print("Unesite broj racun na koji zelite transferisati novac: ");
							targetAcc = input.nextInt();
							break;
						} catch (Exception e) {
							System.out.println("Pogresan unos pokusajte ponovo!");
							input.nextLine();
						}
					}
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
				do {
					regularan = true;
					while(true) {
						try {
							System.out.print("Unesite iznos koji zelite transferisati: ");
							iznos = input.nextInt();
							break;
						} catch (Exception e) {
							System.out.println("Pogresan unos pokusajte ponovo!");
							input.nextLine();
						}
					}
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
		
		//Upisivanje podataka iz array liste u fajl
		try {
			pwBrojRacuna = new PrintWriter(fBrojRacuna);
			pwVlasnikRacuna = new PrintWriter(fVlasnikRacuna);
			pwIznosNaRacunu = new PrintWriter(fIznosNaRacunu);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 0; i < listaRacuna.size(); i++) {
			pwBrojRacuna.println(listaRacuna.get(i).brojRacuna);
			pwVlasnikRacuna.println(listaRacuna.get(i).imeVlasnika);
			pwIznosNaRacunu.println(listaRacuna.get(i).iznosNaRacunu);
		}
		
		input.close();
		pwBrojRacuna.close();
		pwVlasnikRacuna.close();
		pwIznosNaRacunu.close();
	}

}
