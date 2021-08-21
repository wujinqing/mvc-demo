package com.jin.mvc.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 滑动窗口算法
 *
 * @author wu.jinqing
 * @date 2021年07月05日
 */
public class Test6 {
    public static void main(String[] args) {
        String s = "QWERQASWERFSQWER";
        String t = "QAW";

        System.out.println(min(s, t));
    }

    /**
     * 在s中查找包含t的最小连续区间
     * 
     * @param s
     * @param t
     * @return
     */
    public static String min(String s, String t)
    {
        if(s == null || s.trim().length() == 0)
        {
            return "";
        }

        if(t == null || t.trim().length() == 0)
        {
            return "";
        }
        
        s = s.trim();
        t = t.trim();
        
        
        int left = 0, right = 0, valid = 0, len = Integer.MAX_VALUE, start = 0;

        Map<Character, Integer> needs = new HashMap<>();
        Map<Character, Integer> widow = new HashMap<>();

        for (char c : t.toCharArray()) {
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }

        char[] sa = s.toCharArray();

        while (right < sa.length)
        {
            char c = sa[right];
            right++;
            if(needs.containsKey(c))
            {
                widow.put(c, widow.getOrDefault(c, 0) + 1);
                if(needs.get(c) == widow.get(c))
                {
                    valid++;
                }
            }

            while (valid == needs.size())
            {
                if(right - left < len)
                {
                    start = left;
                    len = right - left;
                }

                char d = sa[left];

                left++;

                if(needs.containsKey(d))
                {
                    if(widow.get(d) == needs.get(d))
                    {
                        valid--;
                    }

                    widow.put(d, widow.get(d) - 1);
                }
            }
        }
        
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }
}
