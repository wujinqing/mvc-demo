package com.jin.mvc.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wu.jinqing
 * @date 2022年03月08日
 */
public class DocUtil {
    private static final AtomicBoolean isReq = new AtomicBoolean(true);
    private static final AtomicBoolean isEntryClass = new AtomicBoolean(true);
    private static final Set<String> resovedClazz = new HashSet<>();
    private static final List<Class<?>> classes = new ArrayList<>();
    private static final String sourceBasePath;

    static {
        URL url = DocUtil.class.getClassLoader().getResource(".");
        sourceBasePath = url.getPath().replace("target/classes", "src/main/java");

    }

    public static void main(String[] args) {
        generateDoc(WalletBillListRes.class, WalletBillListRes.class);
    }

    public static void generateDoc(Class<?> reqClazz, Class<?> resClazz)
    {
        isReq.compareAndSet(false, true);
        isEntryClass.compareAndSet(false, true);
        generateDoc(reqClazz);
        isReq.compareAndSet(true, false);
        isEntryClass.compareAndSet(false, true);
        resovedClazz.clear();
        classes.clear();
        generateDoc(reqClazz);
        System.out.println(demoTemplate());
    }

    public static void generateDoc(Class<?> clazz)
    {
        try {
            parseClasses(clazz);

            for (Class<?> c : classes) {

                System.out.println(classTemplate(c));
                resoveDoc(c);

                isEntryClass.compareAndSet(true, false);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void resoveDoc(Class<?> clazz) throws Exception
    {
        String name = clazz.getName();

        if(resovedClazz.contains(name))
        {
            return;
        }

        resovedClazz.add(name);

        String sourcePath = sourceBasePath + name.replace(".", "/") + ".java";

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourcePath)));
        ) {
            String buf = "";
            boolean start = false;
            boolean commentStart = false;
            boolean commentEnd = false;
            StringBuffer comment = new StringBuffer();// 注释
            while ((buf = br.readLine()) != null)
            {
                buf = buf.trim();

                if(buf.contains("public ") && buf.contains("class "))
                {
                    start = true;
                    continue;
                }

                if(start)
                {
                    if(buf.startsWith("/**"))
                    {
                        commentStart = true;
                        comment = new StringBuffer();
                    }

                    if(buf.startsWith("*/"))
                    {
                        commentEnd = true;
                    }


                    if(commentStart && !commentEnd)
                    {
                        comment.append(buf.replace("/**", "").replaceFirst("\\*",""));
                    }

                    if(buf.startsWith("private ") || buf.startsWith("protected ") || buf.startsWith("public "))
                    {
                        String fieldName = resoveFieldName(buf);
                        String fieldType = resoveFieldType(buf, fieldName);

                        String line = isReq.get() ? reqTemplate(fieldName, fieldType, comment.toString()) :
                                resTemplate(fieldName, fieldType, comment.toString());

                        System.out.println(line);
                        commentStart = false;
                        commentEnd = false;
                        comment = new StringBuffer();
                    }
                }
            }
        }
    }

    private static String resoveFieldName(String buf)
    {
        int idx = buf.indexOf(";");
        buf = buf.substring(0, idx).trim();
        idx = buf.lastIndexOf(" ");

        return buf.substring(idx);
    }

    private static String resoveFieldType(String buf, String fieldName)
    {
        int idx = buf.indexOf(fieldName);

        buf = buf.substring(0, idx);

        return buf.replaceFirst("private ", "")
                .replaceFirst("protected ", "")
                .replaceFirst("public ", "")
                .replace("<", "\\<")
                .replace(">", "\\>")
                .trim();
    }

    private static String reqTemplate(String fieldName, String fieldType, String comment)
    {
        return "|"+fieldName.trim()+"|"+fieldType.trim()+"|是否|"+comment.trim()+"||";
    }

    private static String resTemplate(String fieldName, String fieldType, String comment)
    {
        return "|"+fieldName.trim()+"|"+fieldType.trim()+"|"+comment.trim()+"||";
    }

    private static String classTemplate(Class<?> clazz)
    {
        StringBuilder sb = new StringBuilder();

        if(isEntryClass.get()) {
            if (isReq.get()) {
                sb.append("### 入参(").append(clazz.getSimpleName()).append(")").append("\n");
                sb.append("> 接口公共参数请参考:[【接口公共参数】](接口公共参数.md)").append("\n");
                sb.append("|参数名称|类型|必填|说明|备注|").append("\n");
                sb.append("|---|---|---|---|---|");
            }else {
                sb.append("### 返参(").append(clazz.getSimpleName()).append(")").append("\n");
                sb.append("|参数名称|类型|说明|备注|").append("\n");
                sb.append("|---|---|---|---|");
            }
        }else {
            if (isReq.get()) {
                sb.append("\n").append("### ").append(clazz.getSimpleName()).append("\n");
                sb.append("|参数名称|类型|必填|说明|备注|").append("\n");
                sb.append("|---|---|---|---|---|");
            }else {
                sb.append("\n").append("### ").append(clazz.getSimpleName()).append("\n");
                sb.append("|参数名称|类型|说明|备注|").append("\n");
                sb.append("|---|---|---|---|");
            }

        }
        return sb.toString();
    }

    private static String demoTemplate() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("### 示例").append("\n");
        sb.append("\n");
        sb.append("入参：").append("\n");
        sb.append("```").append("\n");
        sb.append("\n");
        sb.append("```").append("\n");
        sb.append("\n");
        sb.append("返参：").append("\n");
        sb.append("```").append("\n");
        sb.append("\n");
        sb.append("```").append("\n");

        return sb.toString();
    }

    private static void parseClasses(Class<?> clazz) {
        classes.add(clazz);

        for (Field field : clazz.getDeclaredFields()) {

            Class<?> type = field.getType();

            if(!ignore(type))
            {
                if(type == List.class)
                {
                    // java.util.List<com.jin.mvc.demo.BillItemDto>
                    String typeName = field.getGenericType().getTypeName().replace("java.util.List<", "").replace(">", "");

                    if(!ignore(typeName))
                    {
                        Class<?> clazz1 = forName(typeName);
                        if(!classes.contains(clazz1))
                        {
                            classes.add(clazz1);
                        }
                    }
                }else if(type == Set.class)
                {
                    // java.util.Set<com.jin.mvc.demo.BillItemDto>
                    String typeName = field.getGenericType().getTypeName().replace("java.util.Set<", "").replace(">", "");

                    if(!ignore(typeName))
                    {
                        Class<?> clazz1 = forName(typeName);
                        if(!classes.contains(clazz1))
                        {
                            classes.add(clazz1);
                        }
                    }
                }else if(type == Map.class)
                {
                    // java.util.Map<java.lang.String, com.jin.mvc.demo.BillItemDto>
                    String typeName = field.getGenericType().getTypeName().replace("java.util.Map<", "").replace(">", "");
                    String[] typeNames = typeName.split(",");

                    if(!ignore(typeNames[0]))
                    {
                        Class<?> clazz1 = forName(typeNames[0]);
                        if(!classes.contains(clazz1))
                        {
                            classes.add(clazz1);
                        }
                    }

                    if(!ignore(typeNames[1]))
                    {
                        Class<?> clazz1 = forName(typeNames[1]);
                        if(!classes.contains(clazz1))
                        {
                            classes.add(clazz1);
                        }
                    }
                }else {
                    String name = type.getName();

                    if(name.startsWith("com.jin"))
                    {
                        if(!ignore(name))
                        {
                            Class<?> clazz1 = forName(name);
                            if(!classes.contains(clazz1))
                            {
                                classes.add(clazz1);
                            }
                        }
                    }
                }
            }
        }
    }

    private static Class<?> forName(String type)
    {
        try {
            return Class.forName(type.trim());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean ignore(String type)
    {
        try {
            return ignore(Class.forName(type.trim()));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;

    }
    private static boolean ignore(Class<?> type)
    {
        return type.isPrimitive() || type == String.class;
    }
}
