package org.example.p1300;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class p1375 {

    public int numTimesAllBlue(int[] flips) {
        int len = flips.length;
        int maxvalue = -1;
        int flag = 0;
        for(int i=1;i<=len;i++){
            maxvalue = Math.max(maxvalue,flips[i-1]);
            if(i == maxvalue){
                flag++;
            }
        }
        return flag;
    }
}
