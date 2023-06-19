package org.example.p1177;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class p1177_review_overtime {
//    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
//        List<Boolean> result = new ArrayList<>();
//        //去除相同的字符（偶数去掉），
//        //奇数剩下一个可以变回文
//        //偶数剩下0个可以变回文
//
//        //可变项k数
//        // 奇数 2k >= 剩下数量-1
//        // 偶数 2k >= 剩下数量
//
//        for(int[] queriesArr: queries){
//            String str  = getString(s,queriesArr[0],queriesArr[1]);
//            boolean isOdd = str.length() % 2 != 0;
//            int k = queriesArr[2];
//            int unChangeNum = getUnRe(str);
//            if (isOdd && ( unChangeNum== 1  || k * 2 >= unChangeNum -1) ){
//                result.add(true);
//                continue;
//            }
//            if (!isOdd && (k * 2>= unChangeNum) ){
//                result.add(true);
//                continue;
//            }
//            result.add(false);
//        }
//        return result;
//    }
//
//    public String getString(String s, int start, int end){
//        return s.substring(start,end+1);
//    }
//
//
//    public int getUnRe(String str){
//        Map<Character,Integer> map = new HashMap<>();
//        int unRe =0;
//        for (char s:str.toCharArray()){
//            if(map.containsKey(s)){
//                map.put(s,map.get(s)+1);
//            }
//            else {
//                map.put(s,1);
//            }
//        }
//        for(Integer nums:map.values()){
//            if(nums % 2!=0){
//                unRe++;
//            }
//        }
//        return unRe;
//    }


    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
         int len = s.length();
        List<Boolean> res = new ArrayList<>();
         //记录前缀
         int[] prefix = new int[len+1];
         for (int i=0;i<len;i++){
             //计算每个位置的前缀异或值   每个字母进行异或操作
             prefix[i+1] = prefix[i]^(1 << (s.charAt(i)-'a'));
         }

         for(int q[] :queries){
             int count = Integer.bitCount(prefix[q[1]+1] ^ prefix[q[0]]);
             res.add(count/2<=q[2]);

         }
         return res;
    }
}
