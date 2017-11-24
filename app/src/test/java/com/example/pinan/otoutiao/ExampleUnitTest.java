package com.example.pinan.otoutiao;

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
        int[] nums = new int[]{1, 7, 9, 3, 15};
        int[] ints = twoSum(nums, 10);
        if (ints != null) {
            System.out.println(ints[0] + " / " + ints[1]);
        } else {
            System.out.println("不存在的");
        }
        assertEquals(4, 2 + 2);
    }
    
    public int[] twoSum(int[] nums, int target) {
        int[] arr = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    arr[0] = i;
                    arr[1] = j;
                    return arr;
                }
            }
        }
        return null;
    }
}