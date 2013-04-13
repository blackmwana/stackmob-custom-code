import java.lang.String;
import java.util.Arrays;
public class Hi {
   
    int[] intsx =new int[2];
    public static void main(String[] args) {
        String joe=null;
        String[] s= {"2","3","1","0"};//new String[4];
        String[] t= {"2","3","1","0"};//new String[4];
        //s[0]="love";
        joe="love";
        System.out.println("Hello, World: "+Arrays.equals(s,t)+" "+joe);
    }

}
