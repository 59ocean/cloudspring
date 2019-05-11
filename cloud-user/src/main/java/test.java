import java.util.HashMap;
import java.util.Map;

public class test extends Thread{
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        map.put("test",Integer.valueOf("123"));
        Integer var = (Integer)map.get("test");
       // System.out.println(var);
        Object obj = map.get("test");
        //System.out.println(obj);
        //String str = (String)map.get("test");
        Object obj2 = (Object)map.get("test");
        System.out.println(obj2);
        Object object = new Object();
        System.out.println("objeckk"+object);

    }

    @Override
    public void run() {
        super.run();
    }
}
