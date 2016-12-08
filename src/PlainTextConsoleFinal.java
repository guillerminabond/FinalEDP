import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class PlainTextConsoleFinal {

	public static void option1(List<String> sequences){

		String codpac = "(A-Z|0-9){6}";
		String codenf = "(LUPUS|ADDISON|HASHIMOTO)";
		String muestraDNA = "[ATCG]*";

		String GenL = "ATG[ATCG]*(CTGATACAGTCA|GTCATATCTACT|GTCTATCAACTG)[ATCG]*(TAG|TGA|TAA)";
		String GenA = "ATG[ATCG]*(AAGGTTCGTACT|AGCCTAGTAGAT|ACGCTAAGCGCT)[ATCG]*(TAG|TGA|TAA)";
		String GenH = "ATG[ATCG]*(GTCATTGTACGT|AACGCTGACTCG|GAACTCGGCTTA)[ATCG]*(TAG|TGA|TAA)";

		String L1 = "([ATCG]*(CTGATACAGTCA)[ATCG]*)*";
		String L2 = "([ATCG]*(GTCATATCTACT)[ATCG]*)*";
		String L3 = "([ATCG]*(GTCTATCAACTG)[ATCG]*)*";

		String H1 = "([ATCG]*(GTCATTGTACGT)[ATCG]*)*";
		String H2 = "([ATCG]*(AACGCTGACTCG)[ATCG]*)*";
		String H3 = "([ATCG]*(GAACTCGGCTTA)[ATCG]*)*";

		String A1 = "([ATCG]*(AAGGTTCGTACT)[ATCG]*)*";
		String A2 = "([ATCG]*(AGCCTAGTAGAT)[ATCG]*)*";
		String A3 = "([ATCG]*(ACGCTAAGCGCT)[ATCG]*)*";

		String temp = "";
		List<String> Genes = new LinkedList<>();

		HashMap<String, String> PacientesEnfermedades = new HashMap<String,String>();
		HashMap<String, HashMap<String, Integer>> PacSecuencias = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, List<String>> PacientesGenes = new HashMap<String, List<String>>();

		for(int i = 0; i < sequences.size(); i++){
			String paciente = "";
			String enfermedad = "";

			
			for(int j=0; j< sequences.get(i).length(); j++){

				if(sequences.get(i).charAt(j)!= '-' || sequences.get(i).charAt(j) != ' '){
					temp = temp + sequences.get(i).charAt(j);

				}else if(sequences.get(i).charAt(j) == '-'){

					if(Pattern.compile(codpac).matcher(temp).matches() && !Pattern.compile(muestraDNA).matcher(temp).matches()) {
						paciente = temp;
						temp = "";
					}

					if(Pattern.compile(codenf).matcher(temp).matches()) {
						enfermedad = temp;
						temp = "";
					}
									
					PacientesEnfermedades.put(paciente, enfermedad);

					HashMap<String, Integer> Secuencias = new HashMap<String, Integer>();				
					
					if (j == sequences.get(i).length() -1) {
						String cadena = "";
						int cantidad = 0;

						for(int h = 0; h < temp.length(); h++){
							cadena = cadena + temp.charAt(h);
							String secuencia = "";

							if(enfermedad == "LUPUS" && Pattern.compile(GenL).matcher(cadena).matches()) {
								Genes.add(cadena);

								if(Pattern.compile(L1).matcher(cadena).matches()) {
									secuencia = "CTGATACAGTCA";
									cadena = "";
									cantidad++;
								}
								if(Pattern.compile(L2).matcher(cadena).matches()) {
									secuencia = "GTCATATCTACT";
									cadena = "";
									cantidad++;
								}
								if(Pattern.compile(L3).matcher(cadena).matches()) {
									secuencia = "GTCTATCAACTG";
									cadena = "";
									cantidad++;
								}
							}

							if(enfermedad == "HASHIMOTO" && Pattern.compile(GenH).matcher(cadena).matches()) {
								Genes.add(cadena);

								if(Pattern.compile(H1).matcher(cadena).matches()) {
									secuencia = "AAGGTTCGTACT";
									cadena = "";
									cantidad++;
								}
								if(Pattern.compile(H2).matcher(cadena).matches()) {
									secuencia = "AACGCTGACTCG";
									cadena = "";
									cantidad++;
								}
								if(Pattern.compile(H3).matcher(cadena).matches()) {
									secuencia = "GAACTCGGCTTA";
									cadena = "";
									cantidad++;
								}
							}

							if(enfermedad == "ADDISON" && Pattern.compile(GenA).matcher(cadena).matches()) {
								Genes.add(cadena);

								if(Pattern.compile(A1).matcher(cadena).matches()) {
									secuencia = "CTGATACAGTCA";
									cadena = "";
									cantidad++;
								}
								if(Pattern.compile(A2).matcher(cadena).matches()) {
									secuencia = "AGCCTAGTAGAT";
									cadena = "";
									cantidad++;
								}
								if(Pattern.compile(A3).matcher(cadena).matches()) {
									secuencia = "ACGCTAAGCGCT";
									cadena = "";
									cantidad++;
								}
							}
						
							PacientesGenes.put(paciente, Genes);
							Secuencias.put(secuencia, cantidad);
							PacSecuencias.put(paciente, Secuencias);
							temp = "";
							
						}		
					}
				}
			}
		}
	}

	/**Imprimir en pantalla para cada paciente la enfermedad que padece, indicando para cada
una cuántas ocurrencias de cada marcador/secuencia de la misma se detectaron en su
muestra de ADN.*/

	public static void option2(List<String> sequences) {

	}

	/**Imprimir para cada enfermedad sus 3 secuencias codificantes ordenadas de mayor a
menor aparición entre todas las muestras y dicha cantidad*/

	public static void option3(List<String> sequences) {
	}

	/**Imprimir en pantalla para cada paciente las secuencias de aminoacidos
    correspondientes a cada una de las secuencias codificantes encontradas de su
    enfermedad en su muestra. Para esto debe traducirse de ADN a Proteínas según la tabla
    (usando la aproximación que el nucleótido U en el ARN es el T en ADN)*/

	public static void option4(List<String> sequences) {
		String enfermedad = "";
		String codenf = "(LUPUS|ADDISON|HASHIMOTO)";
		String temp = "";

		String L1 = "([ATCG]*(CTGATACAGTCA)[ATCG]*)*";
		String L2 = "([ATCG]*(GTCATATCTACT)[ATCG]*)*";
		String L3 = "([ATCG]*(GTCTATCAACTG)[ATCG]*)*";

		String AminL1 = "Leu Ile Gln Ser";
		String AminL2 = "Val Ile Ser Thr";
		String AminL3 = "Val Tyr Gln Leu";

		String A1 = "([ATCG]*(AAGGTTCGTACT)[ATCG]*)*";
		String A2 = "([ATCG]*(AGCCTAGTAGAT)[ATCG]*)*";
		String A3 = "([ATCG]*(ACGCTAAGCGCT)[ATCG]*)*";

		String AminA1 = "Lys Val Arg Thr";
		String AminA2 = "Ser Leu Val Asp";
		String AminA3 = "Thr Leu Ser Ala";

		String H1 = "([ATCG]*(GTCATTGTACGT)[ATCG]*)*";
		String H2 = "([ATCG]*(AACGCTGACTCG)[ATCG]*)*";
		String H3 = "([ATCG]*(GAACTCGGCTTA)[ATCG]*)*";

		String AminH1 = "Val Ile Val Arg";
		String AminH2 = "Asn Ala Asp Ser";
		String AminH3 = "Glu Leu Gly Leu";

		for(int i = 0; i < sequences.size(); i++){

			for(int j=0; j< sequences.get(i).length(); j++){

				if(sequences.get(i).charAt(j)!= '-' || sequences.get(i).charAt(j) != ' '){
					temp = temp + sequences.get(i).charAt(j);

				}else if(sequences.get(i).charAt(j) == '-'){

					if(Pattern.compile(codenf).matcher(temp).matches()) {
						enfermedad = temp;
						temp = "";
					}

					if (j == sequences.get(i).length() -1) {
						String cadena = "";

						for(int h = 0; h < temp.length(); h++){
							cadena = cadena + temp.charAt(h);

							if(enfermedad == "LUPUS") {
								System.out.println("Enfermedad: Lupus Eritematoso Sistémico");

								if(Pattern.compile(L1).matcher(cadena).matches()) {
									System.out.println(AminL1);
								}
								if(Pattern.compile(L2).matcher(cadena).matches()) {
									System.out.println(AminL2);
								}
								if(Pattern.compile(L3).matcher(cadena).matches()) {
									System.out.println(AminL3);
								}
							}

							if(enfermedad == "HASHIMOTO") {
								System.out.println("Enfermedad: Tiroiditis de Hashimoto");

								if(Pattern.compile(H1).matcher(cadena).matches()) {
									System.out.println(AminH1);
								}
								if(Pattern.compile(H2).matcher(cadena).matches()) {
									System.out.println(AminH2);
								}
								if(Pattern.compile(H3).matcher(cadena).matches()) {
									System.out.println(AminH3);
								}
							}

							if(enfermedad == "ADDISON") {
								System.out.println("Enfermedad: Enfermedad de Addison");

								if(Pattern.compile(A1).matcher(cadena).matches()) {
									System.out.println(AminA1);
								}
								if(Pattern.compile(A2).matcher(cadena).matches()) {
									System.out.println(AminA2);
								}
								if(Pattern.compile(A3).matcher(cadena).matches()) {
									System.out.println(AminA3);
								}
							}

						}

					}
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
			System.out.println("\tIngrese 1 para organizar la información entregada.");
			System.out.println("\tIngrese 2 para ver la enfermedad de cada paciente y las secuencias encontradas en su muestra.");
			System.out.println("\tIngrese 3 para ver las secuencias encontradas para cada enfermedad ordenadas de mayor a menor frecuencia.");
			System.out.println("\tIngrese 4 para ver las proteínas codificadas de cada para paciente por las secuencias de cada enfermedad");
			System.out.println("\tIngrese Q para salir.");

			String opcion = sc.next(); //Lee una linea de input del usuario

			if (opcion.equalsIgnoreCase("1")) { //elijió la opción 1
				List<String> sequences = new LinkedList<>();
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
				List<String> sequences = new LinkedList<>();
				while (true) {
					String sequence = sc.next(); //Lee una linea de input del usuario
					if (sequence.equalsIgnoreCase("q")) { //si ingresa una q sola, deja de tomar secuencias y procesa
						option2(sequences);
						break;
					} else {
						sequences.add(sequence); //no era q, asi que agrego la secuencia para procesarla luego
					}
				}

			} else if (opcion.equalsIgnoreCase("3")) {
				List<String> sequences = new LinkedList<>();
				while (true) {
					String sequence = sc.next(); //Lee una linea de input del usuario
					if (sequence.equalsIgnoreCase("q")) { //si ingresa una q sola, deja de tomar secuencias y procesa
						option3(sequences);
						break;
					} else {
						sequences.add(sequence); //no era q, asi que agrego la secuencia para procesarla luego
					}
				}
			} else if (opcion.equalsIgnoreCase("4")) {
				List<String> sequences = new LinkedList<>();
				while (true) {
					String sequence = sc.next(); //Lee una linea de input del usuario
					if (sequence.equalsIgnoreCase("q")) { //si ingresa una q sola, deja de tomar secuencias y procesa
						option4(sequences);
						break;
					} else {
						sequences.add(sequence); //no era q, asi que agrego la secuencia para procesarla luego
					}
				}
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
