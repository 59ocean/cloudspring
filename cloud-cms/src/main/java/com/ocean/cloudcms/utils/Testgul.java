package com.ocean.cloudcms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author: chenhy
 * @Date: 2019/3/4 15:17
 * @Version 1.0
 */
public class Testgul {

    public static String filePath = "D:\\dictionary.txt";//敏感词库文件路径
    public static Set<String> words;
    public static Map<String,String> wordMap;
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则
    public static  List<String> lists = new ArrayList<>();
    public static Set<String> readTxtByLine(String path){
        Set<String> keyWordSet = new HashSet<String>();
        File file=new File(path);
        if(!file.exists()){      //文件流是否存在
            return keyWordSet;
        }
        BufferedReader reader=null;
        String temp=null;
        //int line=1;
        try{
            //reader=new BufferedReader(new FileReader(file));这样在web运行的时候，读取会乱码
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            while((temp=reader.readLine())!=null){
                //System.out.println("line"+line+":"+temp);
                keyWordSet.add(temp);
                lists.add(temp);
                //line++;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }

    public static void main(String[] args) throws InterruptedException {
//        words = readTxtByLine(filePath);
//        SensitiveBean.GetInstance().setSensitiveWords(lists);
//        SensitiveWordInit sensitiveWordInit = new SensitiveWordInit();
//        SensitivewordEngine.sensitiveWordMap = sensitiveWordInit.initKeyWord(SensitiveBean.GetInstance().getSensitiveWords());
//        String text = "草拟吗 你妈的 看片加微";
//        long a = System.currentTimeMillis();
//        System.out.println(a);
//        Set<String> result = SensitivewordEngine.getSensitiveWord(text,SensitivewordEngine.maxMatchType);
//        System.out.println(System.currentTimeMillis()-a);
//        System.out.println(result);
    }

}
