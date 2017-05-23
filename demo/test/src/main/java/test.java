import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.*;
import java.util.*;

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
     */
    public void test1() {
        JSONObject jsonObject = new JSONObject();
        //解析Map类型数据最快
        for (Map.Entry entry : jsonObject.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
        }

        //
        for (String key : jsonObject.keySet()) {
            //慢
        }
    }

    //    public <T> T test2(){
//        return null;
//
    public static void main(String[] args) {
        int size_int = 10000000;
        Date date1 = new Date();
        String str = "";
        List<String> list = new ArrayList<String>();
        for (int i = 0, a = size_int; i < a; i++) {
            str = i + "";
            list.add(str);
        }
        Date date2 = new Date();
        System.out.println(list.size() + ":" + (date2.getTime() - date1.getTime()));

        Date date3 = new Date();
        List<String> list1 = new ArrayList<String>();
        for (int i = 0, a = size_int; i < a; i++) {
            String str1 = i + "";
            list1.add(str1);
        }
        Date date4 = new Date();
        System.out.println(list1.size() + ":" + (date4.getTime() - date3.getTime()));
    }

    //    public static void main(String[]args){
//        int size_int = 10000000;
//        Date date1 = new Date();
//        List<String> list = new ArrayList<String>();
//        for(int i=0,a=size_int;i<a;i++){
//            String str = i+"";
//            list.add(str);
//        }
//        Date date2 = new Date();
//        System.out.println(list.size()+":"+(date2.getTime()-date1.getTime()));
//
//        Date date3 = new Date();
//        List<String> list1 = new ArrayList<String>();
//        for(int i=0 ;i<size_int;i++){
//            String str1 = i+"";
//            list1.add(str1);
//        }
//        Date date4 = new Date();
//        System.out.println(list1.size()+":"+ (date4.getTime()-date3.getTime()));
//    }
    /**
     * world 导出
     * */
    @Test
    public void exportSimpleWord () throws Exception {
        // 要填充的数据, 注意map的key要和word中${xxx}的xxx一致
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("content", "wq");
        //Configuration用于读取ftl文件

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");

  /*以下是两种指定ftl文件所在目录路径的方式, 注意这两种方式都是
   * 指定ftl文件所在目录的路径,而不是ftl文件的路径
   */
        //指定路径的第一种方式(根据某个类的相对路径指定)
        //configuration.setClassForTemplateLoading(this.getClass(),"");

        //指定路径的第二种方式,我的路径是C:/a.ftl
        File file = new File("/Users/wengqian/study/");
        configuration.setDirectoryForTemplateLoading(file);


        // 输出文档路径及名称
        File outFile = new File("/Users/wengqian/study/test.doc");

        //以utf-8的编码读取ftl文件
        Template t = configuration.getTemplate("multiple_message_board_moulde.ftl", "utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
        t.process(dataMap, out);
        out.close();
    }

}
