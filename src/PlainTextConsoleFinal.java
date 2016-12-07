import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class PlainTextConsoleFinal {
    
    public static void option1(List<String> sequences) {
        //Implementar este metodo y los metodos que sean necesarios
    }
    
    /**Imprimir en pantalla para cada paciente la enfermedad que padece, indicando para cada
una cuántas ocurrencias de cada marcador/secuencia de la misma se detectaron en su
muestra de ADN.*/
    
    public static void option2() {
        //Implementar este metodo y los metodos que sean necesarios
    }
    
    /**Imprimir para cada enfermedad sus 3 secuencias codificantes ordenadas de mayor a
menor aparición entre todas las muestras y dicha cantidad*/
    
    public static void option3() {
        //Implementar este metodo y los metodos que sean necesarios
    }

    /**Imprimir en pantalla para cada paciente las secuencias de aminoacidos
    correspondientes a cada una de las secuencias codificantes encontradas de su
    enfermedad en su muestra. Para esto debe traducirse de ADN a Proteínas según la tabla
    (usando la aproximación que el nucleótido U en el ARN es el T en ADN)*/
    
    public static void option4() {
        //Implementar este metodo y los metodos que sean necesarios
    	      	    	
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in); //Scanner para obtener el input del usuario

        sc.useDelimiter(Pattern.compile(System.getProperty("line.separator")));
        
        System.out.println("Bienvenidos al TP Final de EDP Blachman Bond Ems!!"); //Mensaje de bienvenida

        while(true) {
            //Imprimimos el menú ante cada input del usuario
            System.out.println("\tMenú del programa:");
            System.out.println("\tIngrese 1 para ...");
            System.out.println("\tIngrese 2 para ...");
            System.out.println("\tIngrese 3 para ...");
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
        
        System.out.println("Gracias por utilizar este programa!");
        System.out.println("Hasta Luego!");
        
        sc.close();
        
    }
}
