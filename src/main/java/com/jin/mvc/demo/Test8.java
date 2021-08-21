package com.jin.mvc.demo;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wu.jinqing
 * @date 2021年07月27日
 */
@Data
public class Test8   {
    public static void main(String[] args)throws Exception {
        generate(Test9.class);
    }

    public static void generate(Class<?> cls) throws Exception
    {
        String path = cls.getResource(".").getPath();
        path = path.replace("target/classes", "src/main/java");
        String name = cls.getSimpleName() + ".java";

        List<MyField> fields = fields(cls);
        StringBuilder sb = new StringBuilder();

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + name)));
        ) {
            String buf;
            while ((buf = br.readLine()) != null)
            {
                for (MyField field : fields) {
                    String name1 = field.getName();
                    String type = field.getType();
                    String modifier = field.getModifier();

                    String s = "^.*(modifier)\\s+(type)\\s+(name)\\s*;.*$";
                    String reg = s.replace("modifier", modifier).replace("type", type).replace("name", name1);

                    if(buf.matches(reg))
                    {
                        buf = buf.replaceFirst(reg, "(" + name1 + ";)");
                    }
                }
                sb.append(buf);
            }
        }

        System.out.println(sb.toString());

        final String rootDoc = sb.toString();

fields.forEach(f -> doc(f, rootDoc));

        System.out.println("end");
    }

    public static void doc(MyField myField, final String rootDoc)
    {
        String name = myField.getName();
        String id = "(" + name + ";)";


    }
    public static List<MyField> fields(Class<?> cls) throws Exception
    {
        List<MyField> list = new ArrayList<>();
        if(cls == Object.class)
        {

        }else {
            fields(cls.getSuperclass());

            for (Field declaredField : cls.getDeclaredFields()) {
                String name = declaredField.getName();
                String type = declaredField.getType().getSimpleName();
                String modifier = Modifier.isPrivate(declaredField.getModifiers()) ? "private" : "public";

                list.add(new MyField(modifier, type, name));
            }

        }

        return list;
    }

    @Data
    static class MyField
    {
        /**
         * 修饰符
         */
        private String modifier;

        /**
         * 修饰符
         */
        private String type;

        /**
         * 字段名称
         */
        private String name;

        public MyField(String modifier, String type, String name) {
            this.modifier = modifier;
            this.type = type;
            this.name = name;
        }
    }
}
