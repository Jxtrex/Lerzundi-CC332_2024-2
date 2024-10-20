import java.io.*;


public class WordCount {
private static int N = 256;
private static char ITEM[] = new char[N];
private static long FREQ[] = new long[N];
   //------------------------------------------------
	//public static String ReadFile(String FILENAME) throws Exception {
	public static String ReadFile(String FILENAME) {
	String LINE="",CADENA="";
	   try {
		 File FILE = new File(FILENAME);
		 BufferedReader BR = new BufferedReader(new FileReader(FILE));
		 while ((LINE = BR.readLine()) != null) {
           CADENA = CADENA + LINE;
		 }
	   }
	   catch(IOException e) {
	   }
	   return CADENA;
	}
   //------------------------------------------------
   public static void Inicializar() {
   	  for(int k=0;k<=N-1;k++) {
   	  	  ITEM[k] = (char)0;
   	  	  FREQ[k] = 0;
   	  }
   }
   //-------------------------------------------------
   public static boolean Exist(char E) {
   boolean Sw;
      Sw = false;
      int k = 0;
      while((k <= N-1) && (Sw == false)){
      	if(ITEM[k] == E){
      		Sw = true;	
      	}
      	k++;
      }
      return Sw;
   }
   //-------------------------------------------------
   public static void MostrarFrecuencias() {
   long N;
       N = ITEM.length;
   	   System.out.println("------------------------------");
   	   System.out.println("Elemento\tFrecuencia");
   	   System.out.println("------------------------------");
       for(int k=0;k<=N-1;k++) {
           if(ITEM[k]!=(char)0) {
       	      System.out.println(ITEM[k] + "\t\t" + FREQ[k]);
       	   }
       }
   	   System.out.println("------------------------------");
   }
   //-------------------------------------------------
   public static void ContadorFrecuencias(String CAD) {
   long L,T;
   int P;
   char E;
      L = CAD.length();
      P = -1;
      for(int k=0;k<=L-1;k++) {
      	  E = CAD.charAt(k);
          if(!Exist(E)) {
      	     T = 0;
             for(int i=k;i<=L-1;i++) {
                 if(CAD.charAt(i)==E) {
          	        T++;
                 }
             }
	    	 P++;
	         ITEM[P] = E;
	         FREQ[P] = T;
          }
      }
   }
   //-------------------------------------------------
   public static void main(String args[]) {
   String CAD;
   	   CAD = ReadFile("Datos.TXT");
   	   Inicializar();
   	   ContadorFrecuencias(CAD);
   	   MostrarFrecuencias();
   }
   //-------------------------------------------------
   //-------------------------------------------------
   //-------------------------------------------------
   //-------------------------------------------------
}

