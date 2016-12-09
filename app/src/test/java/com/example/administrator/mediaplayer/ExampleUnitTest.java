package com.example.administrator.mediaplayer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testFloat(){
        float num=40/100F;
        float degree=num*360;
        System.out.println(num+"  "+degree);
        float currentProgress=42120;
        float totalProgress=257589;
//        BigDecimal bigDecimal=new BigDecimal(currentProgress);
//        float percent=bigDecimal.divide(new BigDecimal(totalProgress),BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).floatValue();
        float percent=currentProgress/totalProgress;
        System.out.println(percent);
    }
}