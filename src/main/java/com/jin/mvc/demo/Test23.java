package com.jin.mvc.demo;

import sun.dc.pr.PRError;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author wu.jinqing
 * @date 2022年04月28日
 */
public class Test23 {

    public static final Set<String> subfix = new HashSet<>(Arrays.asList(
            "rmvb","mov","FLA","fla","wmv","SWF","swf","mkv","mdb","WAV","avi","mex","wav","mp4","mp3"    ));

    public static final ConcurrentMap<String, AtomicInteger> count = new ConcurrentHashMap<>();

    public static void listFile(File file)
    {
        if(null != file)
        {
            if(file.isDirectory())
            {
                if(file.listFiles() != null) {
                    for (File listFile : file.listFiles()) {
                        listFile(listFile);
                    }
                }
            }else {
                String name = file.getName();


                long length = file.length();

                if(length > 100 * 1024 * 1024) {

                    System.out.println(file.toString() + ", " +length);
                }


            }
        }

    }
    public static void main(String[] args) {
        File file = new File("E:\\");

        listFile(file);

        System.out.println("end");

        for (Map.Entry<String, AtomicInteger> entry : count.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

}
