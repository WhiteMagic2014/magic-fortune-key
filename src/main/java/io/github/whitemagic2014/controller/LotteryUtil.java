package io.github.whitemagic2014.controller;

import io.github.whitemagic2014.util.MagicMd5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class LotteryUtil {


    public static void main(String[] args) {
        String cfmm = "";
        List<Integer> pool = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            pool.add(i);
        }
        List<Integer> lottery = magicLottery(pool, 6, true, cfmm);
        lottery.forEach(n -> System.out.print(n + " "));
    }


    /**
     * 每日财富密码抽奖， 使用财富密码， 从 奖池中抽取 x 个元素 （适用于彩票选号）
     *
     * @param pool      奖池
     * @param count     抽取几个
     * @param duplicate 抽取元素是否允许重复  (支持重复的一般是有序彩票，比如 排列3，排列5； 不支持重复的一般是无序彩票，比如双色球，大乐透)
     * @param cfmm      财富密码
     * @return
     */
    public static <T> List<T> magicLottery(List<T> pool, int count, boolean duplicate, String cfmm) {
        if (pool == null || pool.isEmpty()) {
            throw new IllegalArgumentException("奖池 pool 不能为空");
        }
        if (count < 0) {
            throw new IllegalArgumentException("抽取数量 count 不能为负数");
        }
        if (!duplicate && count > pool.size()) {
            throw new IllegalArgumentException("不允许重复抽取时，count 不能大于奖池大小: " + count + " > " + pool.size());
        }

        // 格式化日期文本
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 日期文本+口令 = 财富密码
        String todayCfmm = MagicMd5.getMd5String(sdf.format(new Date()) + cfmm);

        int[] tmpResult = new int[count];
        for (int i = 0; i < todayCfmm.length(); i++) {
            char c = todayCfmm.charAt(i);
            // 将第i位 字符 的ascii码 累加存储在 result[i % count] 中
            tmpResult[i % count] += c;
        }
        List<T> lottery = new ArrayList<>();
        // pool  可能是 Arrays.asList 生成的固定长度列表，不支持 remove，这里拷贝一份可变的副本
        List<T> available = new ArrayList<>(pool);
        for (int i = 0; i < count; i++) {
            int r = tmpResult[i] % available.size();
            lottery.add(available.get(r));
            if (!duplicate) {
                available.remove(r);
            }
        }
        return lottery;
    }


}
