import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FinalBlachmanBondEms {

	static List<String> entrada = new LinkedList<String>();
	static HashMap<String, String> PacientesEnfermedades = new HashMap<String,String>();
	static HashMap<String, HashMap<String, Integer>> PacSecuencias = new HashMap<String, HashMap<String, Integer>>();
	static HashMap<String, List<String>> PacientesGenes = new HashMap<String, List<String>>();

	static Pattern codpac = Pattern.compile("([A-Z0-9]){6}");
	static Pattern codenf = Pattern.compile("(LUPUS|ADDISON|HASHIMOTO)");
	static Pattern codDNA = Pattern.compile("[ATCG]*");

	//Secuencias codificantes Lupus
	static String sL1 = "CTGATACAGTCA";
	static String sL2 = "GTCATATCTACT";
	static String sL3 = "GTCTATCAACTG";

	//Secuencias codificantes Hashimoto
	static String sH1 = "GTCATTGTACGT";
	static String sH2 = "AACGCTGACTCG";
	static String sH3 = "GAACTCGGCTTA";

	//Secuencias codificantes Addison
	static String sA1 = "AAGGTTCGTACT";
	static String sA2 = "AGCCTAGTAGAT";
	static String sA3 = "ACGCTAAGCGCT";

	static Pattern genL = Pattern.compile("ATG([ATCG]{3})*?(" + sL1 + "|" + sL2 + "|" + sL3 + ")([ATCG]{3})*?(TAG|TGA|TAA)");
	static Pattern genA = Pattern.compile("ATG([ATCG]{3})*?(" + sA1 + "|" + sA2 + "|" + sA3 + ")([ATCG]{3})*?(TAG|TGA|TAA)");
	static Pattern genH = Pattern.compile("ATG([ATCG]{3})*?(" + sH1 + "|" + sH2 + "|" + sH3 + ")([ATCG]{3})*?(TAG|TGA|TAA)");

	//Expresiones regulares de Lupus para presencia de secuencias en genes
	static Pattern l1 = Pattern.compile("([ATCG]*(" + sL1 + ")[ATCG]*)*");
	static Pattern l2 = Pattern.compile("([ATCG]*(" + sL2 + ")[ATCG]*)*");
	static Pattern l3 = Pattern.compile("([ATCG]*(" + sL3 + ")[ATCG]*)*");

	//Expresiones regulares de Hashimoto para presencia de secuencias en genes
	static Pattern h1 = Pattern.compile("([ATCG]*(" + sH1 + ")[ATCG]*)*");
	static Pattern h2 = Pattern.compile("([ATCG]*(" + sH2 + ")[ATCG]*)*");
	static Pattern h3 = Pattern.compile("([ATCG]*(" + sH3 + ")[ATCG]*)*");

	//Expresiones regulares de Addison para presencia de secuencias en genes
	static Pattern a1 = Pattern.compile("([ATCG]*(" + sA1 + ")[ATCG]*)*");
	static Pattern a2 = Pattern.compile("([ATCG]*(" + sA2 + ")[ATCG]*)*");
	static Pattern a3 = Pattern.compile("([ATCG]*(" + sA3+ ")[ATCG]*)*");

	//Secuencias aminoácidos Lupus
	static String aminL1 = "Leu Ile Gln Ser";
	static String aminL2 = "Val Ile Ser Thr";
	static String aminL3 = "Val Tyr Gln Leu";

	//Secuencias aminoácidos Addison
	static String aminA1 = "Lys Val Arg Thr";
	static String aminA2 = "Ser Leu Val Asp";
	static String aminA3 = "Thr Leu Ser Ala";

	//Secuencias aminoácidos Hashimoto
	static String aminH1 = "Val Ile Val Arg";
	static String aminH2 = "Asn Ala Asp Ser";
	static String aminH3 = "Glu Leu Gly Leu";

	public static String extraerPaciente(String sequence) {
		String[] parts = sequence.split(" ?- ?");

		if (codpac.matcher(parts[0]).matches()) {
			return parts[0];
		}
		
		throw new RuntimeException("Código de paciente no válido");
	}

	public static String extraerEnfermedad(String sequence) {
		String[] parts = sequence.split(" ?- ?");

		if (codenf.matcher(parts[1]).matches()) {
			return parts[1];
		}

		throw new RuntimeException("Código de enfermedad no válido");
	}

	public static String extraerMuestraDNA(String sequence) {
		String[] parts = sequence.split(" ?- ?");

		if (codDNA.matcher(parts[2]).matches()) {
			return parts[2];
		}

		throw new RuntimeException("Muestra de DNA no válida");
	}

	public static List<String> extraerGenes(String enfermedad, String muestraDNA) {
		Pattern start = Pattern.compile("[ATCG]*ATG");
		Pattern genEnConstr = Pattern.compile("ATG[ATCG]*"); 
		Pattern genL = Pattern.compile("ATG[ATCG]*(CTGATACAGTCA|GTCATATCTACT|GTCTATCAACTG)[ATCG]*(TAG|TGA|TAA)");
		Pattern genA = Pattern.compile("ATG[ATCG]*(AAGGTTCGTACT|AGCCTAGTAGAT|ACGCTAAGCGCT)[ATCG]*(TAG|TGA|TAA)");
		Pattern genH = Pattern.compile("ATG[ATCG]*(GTCATTGTACGT|AACGCTGACTCG|GAACTCGGCTTA)[ATCG]*(TAG|TGA|TAA)");

		List<String> Genes = new LinkedList<String>();

		String temp ="";
		String gen ="";

		Pattern genPattern = null;

		if(enfermedad.equals("LUPUS")) {
			genPattern = genL;
		}

		if(enfermedad.equals("HASHIMOTO")) {
			genPattern = genH;
		}

		if(enfermedad.equals("ADDISON")) {
			genPattern = genA;
		}

		for(int i = 0; i < muestraDNA.length(); i++){

			temp = temp + muestraDNA.charAt(i);

			if(start.matcher(temp).matches() && gen.isEmpty()) {
				gen = "ATG";
			}

			if(genEnConstr.matcher(gen).matches()) {
				gen = gen + muestraDNA.charAt(i);
			}

			if(genPattern.matcher(gen).matches()) {
				Genes.add(gen);
				gen = "";
				temp = "";
			}	
		}
		return Genes;
	}

	public static List<String> extraerGenes(String sequence) {
		return extraerGenes(extraerEnfermedad(sequence), extraerMuestraDNA(sequence));
	}

	public static String[] asignarSecuencias(String enfermedad) {
		String[] secuencias = new String[3];

		if (enfermedad.equals("LUPUS")) {	
			secuencias = new String[]{sL1, sL2, sL3};
		}
		if (enfermedad.equals("HASHIMOTO")) {
			secuencias = new String[]{sH1, sH2, sH3};
		}         
		if (enfermedad.equals("ADDISON")) {	
			secuencias = new String[]{sA1, sA2, sA3};
		}
		return secuencias;
	}

	public static Pattern[] asignarRegExSecuencias(String enfermedad) {
		Pattern[] regExSecuencias = new Pattern[3];

		if (enfermedad.equals("LUPUS")) {	
			regExSecuencias = new Pattern[]{l1, l2, l3};
		}
		if (enfermedad.equals("HASHIMOTO")) {
			regExSecuencias = new Pattern[]{h1, h2, h3};
		}         
		if (enfermedad.equals("ADDISON")) {	
			regExSecuencias = new Pattern[]{a1, a2, a3};
		}
		return regExSecuencias;
	}

	public static HashMap<String, Integer> contabilizarSecuencias(String enfermedad, List<String> Genes) {
		Pattern[] regSec = asignarRegExSecuencias(enfermedad);
		String[] sec = asignarSecuencias(enfermedad);
		int[] cant = new int[] {0,0,0};

		for(int i = 0; i < Genes.size(); i++){	
			for (int j = 0; j < cant.length; j++) {
				if(regSec[j].matcher(Genes.get(i)).matches()) {
					cant[j]++;
				}
			}
		}

		HashMap<String, Integer> Secuencias = new HashMap<String, Integer>();

		for (int i = 0; i < cant.length; i++) {
			if(cant[i]!=0)
				Secuencias.put(sec[i], cant[i]);
		}
		return Secuencias;
	}

	public static HashMap<String, Integer> contabilizarSecuencias(String sequence) {
		return contabilizarSecuencias(extraerEnfermedad(sequence), extraerGenes(sequence));
	}

	public static String[] asignarAminoacidos(String enfermedad) {
		String[] aminoacidos = new String[3];

		if (enfermedad.equals("LUPUS")) {	
			aminoacidos = new String[]{aminL1, aminL2, aminL3};
		}
		if (enfermedad.equals("HASHIMOTO")) {
			aminoacidos = new String[]{aminH1, aminH2, aminH3};
		}         
		if (enfermedad.equals("ADDISON")) {	
			aminoacidos = new String[]{aminA1, aminA2, aminA3};
		}
		return aminoacidos;
	}

	public static int[] contabilizarSecuenciasTotales(String enfermedad, List<String> pacientes) {

		String[] sec = asignarSecuencias(enfermedad);

		int c1 = 0;
		int c2 = 0;
		int c3 = 0;

		for(int i = 0; i < pacientes.size(); i++){	
			HashMap<String, Integer> secCant = PacSecuencias.get(pacientes.get(i));
			c1 += secCant.getOrDefault(sec[0], 0);
			c2 += secCant.getOrDefault(sec[1], 0);
			c3 += secCant.getOrDefault(sec[2], 0);
		}
		int[] resultado= {c1, c2, c3};
		return resultado;
	}

	public static void frecuenciaSort(int[] cantidades, String[] secuencias) { 
		int lenC = cantidades.length;
		int j = 0;
		int tmp = 0;
		String stmp = "";
		for(int i=0;i<lenC;i++) { 
			j = i;
			for(int k = i;k<lenC;k++){ 
				if(cantidades[j]<cantidades[k]){
					j = k; 
				}
			}
			tmp = cantidades[i];
			cantidades[i] = cantidades[j]; 
			cantidades[j] = tmp;

			stmp = secuencias[i];
			secuencias[i] = secuencias[j];
			secuencias[j] = stmp;
		}
	}

	public static void imprimirFrecuencias(String enfermedad, List<String> pacientes){
		int[] cantidades = contabilizarSecuenciasTotales(enfermedad, pacientes);
		String[] secuencias = asignarSecuencias(enfermedad);
		frecuenciaSort(cantidades, secuencias);

		System.out.println(secuencias[0] +" presente " +cantidades[0] +" veces.");
		System.out.println(secuencias[1] +" presente " +cantidades[1] +" veces.");
		System.out.println(secuencias[2] +" presente " +cantidades[2] +" veces.");
	}

	public static void option1(List<String> sequences) {

		for(int i = 0; i < sequences.size(); i++){
			String paciente = extraerPaciente(sequences.get(i));
			String enfermedad = extraerEnfermedad(sequences.get(i));

			PacientesEnfermedades.put(paciente, enfermedad);	

			String muestraDNA = extraerMuestraDNA(sequences.get(i));
			List<String> Genes = extraerGenes(enfermedad, muestraDNA);

			PacientesGenes.put(paciente, Genes);

			PacSecuencias.put(paciente, contabilizarSecuencias(enfermedad, Genes));
		}		
	}

	public static void option2() {
		HashMap<String, String> nombresEnf = new HashMap<String, String>();
		nombresEnf.put("LUPUS", "Lupus Eritematoso Sistémico");
		nombresEnf.put("HASHIMOTO", "Tiroiditis de Hashimoto");
		nombresEnf.put("ADDISON", "Enfermedad de Addison");

		for(int i = 0; i < entrada.size(); i++){
			String paciente = extraerPaciente(entrada.get(i));
			System.out.println("Código de Paciente:" +paciente);

			String enfermedad = PacientesEnfermedades.get(paciente);

			System.out.println("Enfermedad: " +nombresEnf.get(enfermedad));

			HashMap<String, Integer> secCant = PacSecuencias.get(paciente);
			String[] sec = asignarSecuencias(enfermedad);

			for (int j = 0; j < sec.length; j++) {
				if(secCant.containsKey(sec[j])) {
					System.out.println("Secuencia: " +sec[j] +". Cantidad: " +secCant.get(sec[j]));
				}
			}
			System.out.println();
		}
	}

	public static void option3() {
		List<String> pacL = new LinkedList<String>();
		List<String> pacH = new LinkedList<String>();
		List<String> pacA = new LinkedList<String>();

		for (int i = 0; i < entrada.size(); i++) {
			String paciente = extraerPaciente(entrada.get(i));
			if(PacientesEnfermedades.get(paciente).equals("LUPUS")) {
				pacL.add(paciente);
			}
			if(PacientesEnfermedades.get(paciente).equals("HASHIMOTO")) {
				pacH.add(paciente);
			}
			if(PacientesEnfermedades.get(paciente).equals("ADDISON")) {
				pacA.add(paciente);
			}
		}

		System.out.println("Enfermedad: Lupus Eritematoso Sistémico");
		imprimirFrecuencias("LUPUS", pacL);
		System.out.println();

		System.out.println("Enfermedad: Tiroiditis de Hashimoto");
		imprimirFrecuencias("HASHIMOTO", pacH);
		System.out.println();

		System.out.println("Enfermedad: Enfermedad de Addison");
		imprimirFrecuencias("ADDISON", pacA);
	}

	public static void option4() {
		HashMap<String, String> nombresEnf = new HashMap<String, String>();
		nombresEnf.put("LUPUS", "Lupus Eritematoso Sistémico");
		nombresEnf.put("HASHIMOTO", "Tiroiditis de Hashimoto");
		nombresEnf.put("ADDISON", "Enfermedad de Addison");

		for(int i = 0; i < entrada.size(); i++){
			String paciente = extraerPaciente(entrada.get(i));
			System.out.println("Código de Paciente:" +paciente);	
			String enfermedad = PacientesEnfermedades.get(paciente);
			HashMap<String, Integer> secCant = PacSecuencias.get(paciente);

			System.out.println("Enfermedad: " +nombresEnf.get(enfermedad));
			String[] sec = asignarSecuencias(enfermedad);
			String[] amin = asignarAminoacidos(enfermedad);

			for (int j = 0; j < sec.length; j++) {
				if(secCant.containsKey(sec[j])) {
					System.out.println("Secuencia codificante: "+sec[j] +" Secuencia de aminóacidos: "+amin[j]);
				}
			}
			System.out.println();	
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); //Scanner para obtener el input del usuario

		sc.useDelimiter(Pattern.compile(System.getProperty("line.separator")));

		System.out.println("Bienvenidos al TP Final de EDyP!"); //Mensaje de bienvenida
		System.out.println("Autores: Blachman Bond Ems");

		while(true) {
			//Imprimimos el menú ante cada input del usuario
			System.out.println("\tMenú del programa:");
			System.out.println("\tIngrese 1 para ingresar las muestras que desea procesar separadas por enter, al terminar ingrese Q.");
			System.out.println("\tIngrese 2 para ver la enfermedad de cada paciente y las secuencias encontradas en su muestra.");
			System.out.println("\tIngrese 3 para ver las secuencias encontradas para cada enfermedad, ordenadas de mayor a menor frecuencia.");
			System.out.println("\tIngrese 4 para ver las proteínas codificadas por cada paciente por las secuencias de cada enfermedad");
			System.out.println("\tIngrese 5 para salir.");

			String opcion = sc.next(); //Lee una linea de input del usuario

			if (opcion.equalsIgnoreCase("1")) { //eligió la opción 1
				List<String> sequences = new LinkedList<>();
				while (true) {
					String sequence = sc.next(); //Lee una linea de input del usuario
					if (sequence.equalsIgnoreCase("q")) { //si ingresa una q sola, deja de tomar secuencias y procesa
						entrada = sequences;
						option1(sequences);
						break;
					} else {
						sequences.add(sequence); //no era q, asi que agrego la secuencia para procesarla luego
					}
				}   
			} else if (opcion.equalsIgnoreCase("2")) {
				option2();			
			} else if (opcion.equalsIgnoreCase("3")) {
				option3();
			} else if (opcion.equalsIgnoreCase("4")) {
				option4();
			} else if (opcion.equalsIgnoreCase("5")) {
				break;
			} else {
				System.out.println("No ingresó una opción correcta");
			}
		}

		System.out.println("¡Gracias por utilizar este programa!");
		System.out.println("¡Hasta luego!");

		sc.close();

	}
}
