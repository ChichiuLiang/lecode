import org.example.p1100.p1177_review_overtime;
import org.example.p1300.p1375;
import org.junit.Test;

import java.util.List;

public class test {
    private p1375 p1375 = new p1375();
    private p1177_review_overtime p1177_review_overtime = new p1177_review_overtime();

    @Test
    public void testYourMethod() {
        // 准备测试数据
        // 调用被测试的方法
        // 使用断言来验证结果
//        int[] flips = {3,2,4,1,5};
//        // 例如：
//        int result = p1375.numTimesAllBlue(flips);
//        Assert.assertEquals(2, result);

        int[][] queries = {{3,3,0},{1,2,0},{0,3,1},{0,3,2},{0,4,1}};
//        int[][] queries = {{0,3,2},{0,4,1}};
        List<Boolean> result =  p1177_review_overtime.canMakePaliQueries("abcda",queries);

        System.out.printf(result.toString());
    }
}
