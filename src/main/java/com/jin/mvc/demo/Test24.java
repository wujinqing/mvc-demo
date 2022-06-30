package com.jin.mvc.demo;

import java.lang.reflect.Method;

/**
 * @author wu.jinqing
 * @date 2022年05月15日
 */
public class Test24 {
    public static final String prefix = "${";
    public static final String subfix = "}";
    private static String setGrabUrlParams(String grabUrl, Object obj)
    {
        if(Assert.isNotEmpty(grabUrl))
        {
            int startIdx = grabUrl.indexOf(prefix);
            int endIdx = grabUrl.indexOf(subfix);

            if(-1 != startIdx && -1 != endIdx)
            {

                startIdx += prefix.length();
                String param = grabUrl.substring(startIdx, endIdx);
                Object value = null;
                try {
                    String getter = "get" + param.substring(0, 1).toUpperCase() + param.substring(1);
                    Method method = obj.getClass().getDeclaredMethod(getter);

                    value = method.invoke(obj);

                } catch (Exception e) {

                }

                if(value != null)
                {
                    grabUrl = grabUrl.replace(prefix+param+subfix, value.toString());
                }

                grabUrl = setGrabUrlParams(grabUrl, obj);

            }
        }

        return grabUrl;
    }

    public static void main(String[] args) {
        String url = "https://www.baidu.com/index.php?id=${id}&age=${age}&name=${name}";

        User user = new User();

        user.setId(1);
        user.setAge(10);
        user.setName("张三");


        url = setGrabUrlParams(url, user);

        System.out.println(url);
    }
}
