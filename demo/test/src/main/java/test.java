import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by wq on 2017/3/2.
 */
public class test {
//    private final String  ss[] ={"123"};
//    public  void run(){
////        String ass ="";
////        Integer [] integer_arr ={88, 459, 5262, 88, -17, 677, 88,667, -17, 459, 5262};
////        Arrays.sort(integer_arr);
////        System.out.println(Arrays.toString(integer_arr));
////        String tmp = Arrays.toString(integer_arr).replaceAll("\\,", ",,").replaceAll("[\\[\\]]", ",").replaceAll("\\s", "");
////        System.out.println(tmp);
////        int n = 3;
////        String reg = ".*(\\,(\\d+)\\,)\\1{" + (n - 1) + "}.*";
////        String r = tmp.replaceAll(reg, "$2");
////        System.out.println("重复" + n + "遍的数字是：" + r);
////        BigDecimal asd1123 = 12312312777;
////        BigInteger asdasd = 12312312777;
//        Boolean ss1 =true;
//        char[] asd ={1,2,3};
//        Byte ss;
//        Short shrot;
//        Integer int1=123;
//        Long asdasdasfsa = (long)1123;
////        Float asdf = new String("0.0");
//        Double dasd = 123.0;
////        System.out.println(ss[0]);
//        this.ss[0]="12312";
////        System.out.println(ss[0]);
//        char[] vale ={'1','1'};
//        String abc = new String(vale);
//        System.out.println();
//        int [] asd ={1,2,3,4};
//        int[] ary ={65,66,67,68,W97,97};
//        String str = new String(ary, 2, 3);
//        System.out.println(str);
//    }
//    public static  void main(String[]args){
//        new test().run();
//    };

    /**
     * map的最快获取方式
     * */
    public void test1(){
        JSONObject jsonObject = new JSONObject();
        //解析Map类型数据最快
        for (Map.Entry entry:jsonObject.entrySet()){
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
        }

        //
        for(String key :jsonObject.keySet()){
            //慢
        }
    }

//    public <T> T test2(){
//        return null;
//    }
}
