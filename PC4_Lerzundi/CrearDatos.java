import java.io.IOException;

public class CrearDatos {
   //---------------------------------------------------------
   //---------------------------------------------------------
   public static void main(String[] args) throws IOException, InterruptedException {
   PP_Tools OBJ = new PP_Tools();
   String CADENA;
   int T = 12;
   int SIZE = 10;
   int M = 5;
   int N = 3;
   int A[] = {10,0,100}; //N
   int B[] = {99,1,999}; //N
   int S[] = {4,2,5};    //N

      OBJ.CreateDataFileVariable("Datos1.TXT",M,N,'H',A,B,S);
      OBJ.CreateDataFileVariable("Datos2.TXT",M,N,'V',A,B,S);



   }
   //---------------------------------------------------------
}