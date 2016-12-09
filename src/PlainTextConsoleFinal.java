import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class PlainTextConsoleFinal {

	static HashMap<String, String> PacientesEnfermedades = new HashMap<String,String>();
	static HashMap<String, HashMap<String, Integer>> PacSecuencias = new HashMap<String, HashMap<String, Integer>>();
	static HashMap<String, List<String>> PacientesGenes = new HashMap<String, List<String>>();

	public static String extraerPaciente (String sequence) {
		String codpac = "(A-Z|0-9){6}";
		String codmuestraDNA = "[ATCG]*";
		String paciente = "";

		for(int i=0; i< sequence.length(); i++){
			
			if(sequence.charAt(i)!= '-' || sequence.charAt(i) != ' '){
				paciente = paciente + sequence.charAt(i);

			}else if(sequence.charAt(i) == '-'){

				if(Pattern.compile(codpac).matcher(paciente).matches() && !Pattern.compile(codmuestraDNA).matcher(paciente).matches()) {
					break;
				}
				paciente = "";
			}
		}
		return paciente;
	}

	public static String extraerEnfermedad (String sequence) {

		String codenf = "(LUPUS|ADDISON|HASHIMOTO)";

		String enfermedad = "";

		for(int i=0; i< sequence.length(); i++){

			if(sequence.charAt(i)!= '-' || sequence.charAt(i) != ' '){
				enfermedad = enfermedad + sequence.charAt(i);

			}else if(sequence.charAt(i) == '-'){

				if(Pattern.compile(codenf).matcher(enfermedad).matches()) {
					break;
				}
				enfermedad = "";
			}
		}
		return enfermedad;
	}

	public static String extraerMuestraDNA (String sequence) {

		String muestraDNA = "";

		for(int i=sequence.length(); i>0; i--){

			if(sequence.charAt(i)!= '-' || sequence.charAt(i) != ' '){
				muestraDNA = muestraDNA + sequence.charAt(i);

			}else if(sequence.charAt(i) == '-'){
				break;
			}
		}
		return muestraDNA;
	}

	public static List<String> extraerGenes (String enfermedad, String muestraDNA) {

		String GenL = "ATG[ATCG]*(CTGATACAGTCA|GTCATATCTACT|GTCTATCAACTG)[ATCG]*(TAG|TGA|TAA)";
		String GenA = "ATG[ATCG]*(AAGGTTCGTACT|AGCCTAGTAGAT|ACGCTAAGCGCT)[ATCG]*(TAG|TGA|TAA)";
		String GenH = "ATG[ATCG]*(GTCATTGTACGT|AACGCTGACTCG|GAACTCGGCTTA)[ATCG]*(TAG|TGA|TAA)";

		List<String> Genes = new LinkedList<>();

		for(int h = 0; h < muestraDNA.length(); h++){

			String temp ="";
			temp = temp + muestraDNA.charAt(h);

			if(enfermedad == "LUPUS" && Pattern.compile(GenL).matcher(temp).matches()) {
				Genes.add(temp);
			}

			if(enfermedad == "HASHIMOTO" && Pattern.compile(GenH).matcher(temp).matches()) {
				Genes.add(temp);
			}

			if(enfermedad == "ADDISON" && Pattern.compile(GenA).matcher(temp).matches()) {
				Genes.add(temp);
			}

			temp="";
		}
		return Genes;
	}

	public static List<String> extraerGenes (String sequence) {
		return extraerGenes(extraerEnfermedad(sequence), extraerMuestraDNA(sequence));
	}

	public static HashMap<String, Integer> contabilizarSecuencias (String enfermedad, List<String> Genes) {

		String L1 = "([ATCG]*(CTGATACAGTCA)[ATCG]*)*";
		String L2 = "([ATCG]*(GTCATATCTACT)[ATCG]*)*";
		String L3 = "([ATCG]*(GTCTATCAACTG)[ATCG]*)*";

		String H1 = "([ATCG]*(GTCATTGTACGT)[ATCG]*)*";
		String H2 = "([ATCG]*(AACGCTGACTCG)[ATCG]*)*";
		String H3 = "([ATCG]*(GAACTCGGCTTA)[ATCG]*)*";

		String A1 = "([ATCG]*(AAGGTTCGTACT)[ATCG]*)*";
		String A2 = "([ATCG]*(AGCCTAGTAGAT)[ATCG]*)*";
		String A3 = "([ATCG]*(ACGCTAAGCGCT)[ATCG]*)*";

		String sL1 = "CTGATACAGTCA";
		String sL2 = "GTCATATCTACT";
		String sL3 = "GTCTATCAACTG";

		String sH1 = "GTCATTGTACGT";
		String sH2 = "AACGCTGACTCG";
		String sH3 = "GAACTCGGCTTA";

		String sA1 = "AAGGTTCGTACT";
		String sA2 = "AGCCTAGTAGAT";
		String sA3 = "ACGCTAAGCGCT";

		HashMap<String, Integer> Secuencias = new HashMap<String, Integer>();

		int c1 = 0;
		int c2 = 0;
		int c3 = 0;

		for(int i = 0; i < Genes.size(); i++){	
			if(enfermedad == "LUPUS") {		
				if(Pattern.compile(L1).matcher(Genes.get(i)).matches()) {
					c1++;
				}
				if(Pattern.compile(L2).matcher(Genes.get(i)).matches()) {
					c2++;
				}
				if(Pattern.compile(L3).matcher(Genes.get(i)).matches()) {
					c3++;
				}
			}
			if(enfermedad == "HASHIMOTO") {	
				if(Pattern.compile(H1).matcher(Genes.get(i)).matches()) {
					c1++;
				}
				if(Pattern.compile(H2).matcher(Genes.get(i)).matches()) {
					c2++;
				}
				if(Pattern.compile(H3).matcher(Genes.get(i)).matches()) {
					c3++;
				}
			}
			if(enfermedad == "ADDISON") {		
				if(Pattern.compile(A1).matcher(Genes.get(i)).matches()) {
					c1++;
				}
				if(Pattern.compile(A2).matcher(Genes.get(i)).matches()) {
					c2++;
				}
				if(Pattern.compile(A3).matcher(Genes.get(i)).matches()) {
					c3++;
				}
			}	
		}

		if(enfermedad == "LUPUS") {
			if(c1!=0){
				Secuencias.put(sL1, c1);
			}
			if(c2!=0){
				Secuencias.put(sL2, c2);
			}
			if(c3!=0){
				Secuencias.put(sL3, c3);
			}
		}

		if(enfermedad == "HASHIMOTO") {
			if(c1!=0){
				Secuencias.put(sH1, c1);
			}
			if(c2!=0){
				Secuencias.put(sH2, c2);
			}
			if(c3!=0){
				Secuencias.put(sH3, c3);
			}
		}

		if(enfermedad == "ADDISON") {
			if(c1!=0){
				Secuencias.put(sA1, c1);
			}
			if(c2!=0){
				Secuencias.put(sA2, c2);
			}
			if(c3!=0){
				Secuencias.put(sA3, c3);
			}
		}
		return Secuencias;
	}

	public static HashMap<String, Integer> contabilizarSecuencias (String sequence) {
		return contabilizarSecuencias(extraerEnfermedad(sequence), extraerGenes(sequence));
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

	/**Imprimir en pantalla para cada paciente la enfermedad que padece, indicando para cada
una cuántas ocurrencias de cada marcador/secuencia de la misma se detectaron en su
muestra de ADN.*/

	public static void option2(List<String> sequences) {

		for(int i = 0; i < sequences.size(); i++){
			System.out.println("Código de Paciente:" +extraerPaciente(sequences.get(i)));
			String enfermedad = extraerEnfermedad(sequences.get(i));

			HashMap<String, Integer> M = contabilizarSecuencias(sequences.get(i));
			String sL1 = "CTGATACAGTCA";
			String sL2 = "GTCATATCTACT";
			String sL3 = "GTCTATCAACTG";

			String sH1 = "GTCATTGTACGT";
			String sH2 = "AACGCTGACTCG";
			String sH3 = "GAACTCGGCTTA";

			String sA1 = "AAGGTTCGTACT";
			String sA2 = "AGCCTAGTAGAT";
			String sA3 = "ACGCTAAGCGCT";

			if(enfermedad == "LUPUS") {
				System.out.println("Enfermedad: Lupus Eritematoso Sistémico");

				if(M.containsKey(sL1)) {
					System.out.println("Secuencia: " +sL1 +". Cantidad: " +M.get(sL1));
				}
				if(M.containsKey(sL2)) {
					System.out.println("Secuencia: " +sL2 +". Cantidad: " +M.get(sL2));
				}
				if(M.containsKey(sL3)) {
					System.out.println("Secuencia: " +sL3 +". Cantidad: " +M.get(sL3));
				}
			}

			if(enfermedad == "HASHIMOTO") {
				System.out.println("Enfermedad: Tiroiditis de Hashimoto");

				if(M.containsKey(sH1)) {
					System.out.println("Secuencia: " +sH1 +". Cantidad: " +M.get(sH1));
				}
				if(M.containsKey(sH2)) {
					System.out.println("Secuencia: " +sH2 +". Cantidad: " +M.get(sH2));
				}
				if(M.containsKey(sH3)) {
					System.out.println("Secuencia: " +sH2 +". Cantidad: " +M.get(sH2));
				}
			}

			if(enfermedad == "ADDISON") {
				System.out.println("Enfermedad: Enfermedad de Addison");

				if(M.containsKey(sA1)) {
					System.out.println("Secuencia: " +sA1 +". Cantidad: " +M.get(sA1));
				}
				if(M.containsKey(sA2)) {
					System.out.println("Secuencia: " +sA2 +". Cantidad: " +M.get(sA2));
				}
				if(M.containsKey(sA3)) {
					System.out.println("Secuencia: " +sA3 +". Cantidad: " +M.get(sA3));
				}
			}
		}
	}

	/**Imprimir para cada enfermedad sus 3 secuencias codificantes ordenadas de mayor a
menor aparición entre TODAS las muestras y dicha cantidad*/

	public static void option3(List<String> sequences) {
		// SORTING!

		System.out.println("Enfermedad: Lupus Eritematoso Sistémico");
		System.out.println("Enfermedad: Tiroiditis de Hashimoto");
		System.out.println("Enfermedad: Enfermedad de Addison");
		
	}

	/**Imprimir en pantalla para cada paciente las secuencias de aminoacidos
    correspondientes a cada una de las secuencias codificantes encontradas de su
    enfermedad en su muestra. Para esto debe traducirse de ADN a Proteínas según la tabla
    (usando la aproximación que el nucleótido U en el ARN es el T en ADN)*/

	public static void option4(List<String> sequences) {

		String sL1 = "CTGATACAGTCA";
		String sL2 = "GTCATATCTACT";
		String sL3 = "GTCTATCAACTG";

		String sH1 = "GTCATTGTACGT";
		String sH2 = "AACGCTGACTCG";
		String sH3 = "GAACTCGGCTTA";

		String sA1 = "AAGGTTCGTACT";
		String sA2 = "AGCCTAGTAGAT";
		String sA3 = "ACGCTAAGCGCT";

		String AminL1 = "Leu Ile Gln Ser";
		String AminL2 = "Val Ile Ser Thr";
		String AminL3 = "Val Tyr Gln Leu";

		String AminA1 = "Lys Val Arg Thr";
		String AminA2 = "Ser Leu Val Asp";
		String AminA3 = "Thr Leu Ser Ala";

		String AminH1 = "Val Ile Val Arg";
		String AminH2 = "Asn Ala Asp Ser";
		String AminH3 = "Glu Leu Gly Leu";

		for(int i = 0; i < sequences.size(); i++){

			String enfermedad = extraerEnfermedad(sequences.get(i));
			HashMap<String, Integer> M = contabilizarSecuencias(sequences.get(i));

			if(enfermedad == "LUPUS") {
				System.out.println("Enfermedad: Lupus Eritematoso Sistémico");

				if(M.containsKey(sL1)) {
					System.out.println(AminL1);
				}
				if(M.containsKey(sL2)) {
					System.out.println(AminL2);
				}
				if(M.containsKey(sL3)) {
					System.out.println(AminL3);
				}
			}

			if(enfermedad == "HASHIMOTO") {
				System.out.println("Enfermedad: Tiroiditis de Hashimoto");

				if(M.containsKey(sH1)) {
					System.out.println(AminH1);
				}
				if(M.containsKey(sH2)) {
					System.out.println(AminH2);
				}
				if(M.containsKey(sH3)) {
					System.out.println(AminH3);
				}
			}

			if(enfermedad == "ADDISON") {
				System.out.println("Enfermedad: Enfermedad de Addison");

				if(M.containsKey(sA1)) {
					System.out.println(AminA1);
				}
				if(M.containsKey(sA2)) {
					System.out.println(AminA2);
				}
				if(M.containsKey(sA3)) {
					System.out.println(AminA3);
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in); //Scanner para obtener el input del usuario

		sc.useDelimiter(Pattern.compile(System.getProperty("line.separator")));

		System.out.println("Bienvenidos al TP Final de EDP Blachman Bond Ems!!"); //Mensaje de bienvenida

		while(true) {
			//Imprimimos el menú ante cada input del usuario
			System.out.println("\tMenú del programa:");
			System.out.println("\tIngrese 1 para procesar las muestras deseadas.");
			System.out.println("\tIngrese 2 para ver la enfermedad de cada paciente y las secuencias encontradas en su muestra.");
			System.out.println("\tIngrese 3 para ver las secuencias encontradas para cada enfermedad, ordenadas de mayor a menor frecuencia.");
			System.out.println("\tIngrese 4 para ver las proteínas codificadas por cada paciente por las secuencias de cada enfermedad");
			System.out.println("\tIngrese Q para salir.");

			String opcion = sc.next(); //Lee una linea de input del usuario

			List<String> sequences = new LinkedList<>();
			if (opcion.equalsIgnoreCase("1")) { //elijió la opción 1

				while (true) {
					String sequence = sc.next(); //Lee una linea de input del usuario
					if (sequence.equalsIgnoreCase("q")) { //si ingresa una q sola, deja de tomar secuencias y procesa
						option1(sequences);
						break;
					} else {
						sequences.add(sequence); //no era q, asi que agrego la secuencia para procesarla luego
					}
				}   
			} else if (opcion.equalsIgnoreCase("2")) {
				option2(sequences);			
			} else if (opcion.equalsIgnoreCase("3")) {
				option3(sequences);
			} else if (opcion.equalsIgnoreCase("4")) {
				option4(sequences);
			} else if (opcion.equalsIgnoreCase("5")) {
				break;
			} else {
				System.out.println("No ingresó una opción correcta");
			}
		}

		System.out.println("Gracias por utilizar este programa!");
		System.out.println("Hasta Luego!");

		sc.close();

	}
}
