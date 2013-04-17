import java.lang.String;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
public class Hi {
   
    int[] intsx =new int[2];
    static void pol(String v){
        System.out.println(v);
    }
    public static void main(String[] args) {
        String joe=null;
        List<String> me=null;
        me= new ArrayList<String>();
        me.add("sd");
        String[] s= {"2","3","1","0"};//new String[4];
        String[] t= {"2","3","4","0"};//new String[4];
        String[] u= null;
        String[] w= new String[t.length];
        System.arraycopy(t,0,w,0,w.length);
        w[1]=":P";
        List<String> b=Arrays.asList();
         me.addAll(Arrays.asList(t));
         t[0]="bu";
        u= new String[0]; 
        //s[0]="love";
        joe="love";
        pol(new String("pol"));
        System.out.println("Hello, World: "+Arrays.equals(s,t)+" "+joe);
        System.out.println(u.length+" "+b+me);
    }

}
