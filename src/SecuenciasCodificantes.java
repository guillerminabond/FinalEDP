
public class SecuenciasCodificantes {
//	CTGATACAGTCA o GTCATATCTACT o GTCTATCAACTG para Lupus Eritematoso Sistémico

//	AAGGTTCGTACT o AGCCTAGTAGAT o ACGCTAAGCGCT para la Enfermedad de Addison

//	GTCATTGTACGT o AACGCTGACTCG o GAACTCGGCTTA para la tiroiditis de Hashimoto.

/**/	
	
/*Para probar: (TODO lo que tiene que matchear
	
(A-Z0-9){6} - (LUPUS|ADDISON|HASHIMOTO) - [ACTG]*
	
*/
	
/**Para cada enfermedad: (M1)
 
  (A-Z0-9){6} - LUPUS - [ACTG]*
 
 -> Paciente: (A-Z0-9){6}
 -> Enfermedad: Lupus Eritematoso Sistémico
 
 (A-Z0-9){6} - ADDISON - [ACTG]*
 
 -> Paciente: (A-Z0-9){6}
 -> Enfermedad: Enfermedad de Addison
 
 (A-Z0-9){6} - HASHIMOTO - [ACTG]*
 
 -> Paciente: (A-Z0-9){6}
 -> Enfermedad: Tiroiditis de Hashimoto
  
 */	
	
/**Captar solo secuencias (M2):
 
L:
[ACGT]*CTGATACAGTCA[ACGT]* 
[ACGT]*GTCATATCTACT[ACGT]*
[ACGT]*GTCTATCAACTG[ACGT]*

A:
[ACGT]*AAGGTTCGTACT[ACGT]*
[ACGT]*AGCCTAGTAGAT[ACGT]*
[ACGT]*ACGCTAAGCGCT[ACGT]*

H:
[ACGT]*GTCATTGTACGT[ACGT]*
[ACGT]*AACGCTGACTCG[ACGT]*
[ACGT]*GAACTCGGCTTA[ACGT]*

Para captar genes con secuencias (M3):

ATG[ACGT]*(CTGATACAGTCA|GTCATATCTACT|GTCTATCAACTG)[ACGT]*(TAG|TGA|TAA)

ATG[ACGT]*(AAGGTTCGTACT|AGCCTAGTAGAT|ACGCTAAGCGCT)[ACGT]*(TAG|TGA|TAA)

ATG[ACGT]*(GTCATTGTACGT|AACGCTGACTCG|GAACTCGGCTTA)[ACGT]*(TAG|TGA|TAA)

*/
	
	/**Proteínas
Lupus
CTG ATA CAG TCA: Leu Ile Gln Ser
GTC ATA TCT ACT: Val Ile Ser Thr
GTC TAT CAA CTG: Val Tyr Gln Leu

Addison
AAG GTT CGT ACT: Lys Val Arg Thr
AGC CTA GTA GAT: Ser Leu Val Asp
ACG CTA AGC GCT: Thr Leu Ser Ala

Hashimoto
GTC ATT GTA CGT: Val Ile Val Arg
AAC GCT GAC TCG: Asn Ala Asp Ser
GAA CTC GGC TTA: Glu Leu Gly Leu
 
	 * 
	 * */
	
	/*Para probar: (TODO lo que tiene que matchear
	
	(A-Z0-9){6} - (LUPUS|ADDISON|HASHIMOTO) - [ACTG]*
	
	*/
	
	
}
