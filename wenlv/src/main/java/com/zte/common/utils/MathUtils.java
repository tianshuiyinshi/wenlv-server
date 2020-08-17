package com.zte.common.utils;

import java.math.BigDecimal;

/**
 * 
 * @author tzh
 * @since 2018-3-14 增加计算公用类，防止减少计算带来精度丢失等问题
 *
 */
public class MathUtils {

    /**
     * 求两数总乘积(字符串) num1*num2
     * 
     * @param num1
     * @param num2
     * @return double类型
     */
    public static Double multiply(String num1, String num2) {
        return multiplyDecimal(num1, num2).doubleValue();
    }

    /**
     * 求两数总乘积(整型) num1*num2
     * 
     * @param num1
     * @param num2
     * @return double类型
     */
    public static Double multiply(Integer num1, Integer num2) {
        return multiplyDecimal(num1.toString(), num2.toString()).doubleValue();
    }

    /**
     * 求两数总乘积(double) num1*num2
     * 
     * @param num1
     * @param num2
     * @return double类型
     */
    public static Double multiply(Double num1, Double num2) {
        return multiplyDecimal(num1.toString(), num2.toString()).doubleValue();
    }

    /**
     * 求两数总乘积(double) num1*num2
     * 
     * @param num1
     * @param num2
     * @return double类型
     */
    public static Double multiply(Integer num1, Double num2) {
        return multiplyDecimal(num1.toString(), num2.toString()).doubleValue();
    }

    /**
     * 求两数总乘积(浮点型) num1*num2
     * 
     * @param num1
     * @param num2
     * @return double类型
     */
    public static Double multiply(Float num1, Float num2) {
        return multiplyDecimal(num1.toString(), num2.toString()).doubleValue();
    }

    /**
     * 求两数总乘积(BigDecimal) num1*num2
     * 
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal multiplyDecimal(String num1, String num2) {
        BigDecimal multNum = new BigDecimal(num1.trim());
        BigDecimal param = new BigDecimal(num2.trim());
        return multNum.multiply(param);
    }

    /**
     * 两数相除(BigDecimal) num1/num2
     * 
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal divideDecimal(String num1, String num2) {
        BigDecimal multNum = new BigDecimal(num1.trim());
        BigDecimal param = new BigDecimal(num2.trim());
        return multNum.divide(param, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 两数相除(BigDecimal),带四舍五入 num1/num2
     * 
     * @param num1
     * @param num2
     * @param mode
     *            1-四舍五入 2-取最大整数，3-取最小整数，4-小数>0.5取最大整数
     *
     * @return
     */
    public static BigDecimal divideDecimal(String num1, String num2, int mode) {
        int scale = 0;
        return divideDecimal(num1, num2, mode, scale);
    }

    public static BigDecimal divideDecimal(String num1, String num2, int mode, int scale) {
        BigDecimal multNum = new BigDecimal(num1.trim());
        BigDecimal param = new BigDecimal(num2.trim());
        if (mode == 1) {
            return multNum.divide(param, scale, BigDecimal.ROUND_UP);
        } else if (mode == 2) {
            return multNum.divide(param, scale, BigDecimal.ROUND_CEILING);
        } else if (mode == 3) {
            return multNum.divide(param, scale, BigDecimal.ROUND_FLOOR);
        } else if (mode == 4) {
            return multNum.divide(param, scale, BigDecimal.ROUND_HALF_DOWN);
        } else if (mode == 5) {
            return multNum.divide(param, scale, BigDecimal.ROUND_HALF_UP);
        } else {
            return multNum.divide(param);
        }
    }

    /**
     * 两数相除(String) 四舍五入
     * 
     * @param num1
     * @param num2
     * @return 整型
     */
    public static double divide(Integer num1, Integer num2, int scale) {
        return divideDecimal(num1.toString(), num2.toString(), 5, scale).doubleValue();
    }

    /**
     * 两数相除(String) 1-四舍五入 2-取最大整数，3-取最小整数，4-小数>0.5取最大整数
     * 
     * @param num1
     * @param num2
     * @param mode
     * @return 返回整型
     */
    public static int divide(String num1, String num2, int mode) {
        return divideDecimal(num1, num2, mode).intValue();
    }

    /**
     * 两数相除(String) 四舍五入
     * 
     * @param num1
     * @param num2
     * @return 整型
     */
    public static int divide(String num1, String num2) {
        return divideDecimal(num1, num2, 5).intValue();
    }

    /**
     * 两数相除(String) 四舍五入
     * 
     * @param num1
     * @param num2
     * @return 整型
     */
    public static int divide(Integer num1, Integer num2) {
        return divideDecimal(num1.toString(), num2.toString(), 5).intValue();
    }

    /**
     * 两数相除(String) 四舍五入
     * 
     * @param num1
     * @param num2
     * @return 整型
     */
    public static int divide(Double num1, Double num2) {
        return divideDecimal(num1.toString(), num2.toString(), 5).intValue();
    }

    /**
     * 两数相除(Double) 四舍五入 ,返回两位小数
     * 
     * @param num1
     * @param num2
     * @return Double
     */
    public static Double divideDouble(Double num1, Double num2) {
        return divideDecimal(num1.toString(), num2.toString()).doubleValue();
    }

    /**
     * 两数相除(String) 四舍五入，,返回两位小数
     * 
     * @param num1
     * @param num2
     * @return Double
     */
    public static Double divideDouble(String num1, String num2) {
        return divideDecimal(num1.toString(), num2.toString()).doubleValue();
    }

    /**
     * 两数相除(int) 四舍五入,返回两位小数
     * 
     * @param num1
     * @param num2
     * @return Double
     */
    public static Double divideDouble(Integer num1, Integer num2) {
        return divideDecimal(num1.toString(), num2.toString()).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(divideDouble("1990", "100"));
        System.out.println(MathUtils.multiply(1990, 0.01));
        System.out.println(MathUtils.divide(22608, 1024, 2));
        System.out.println(getPartitionNum(15462315056l));
    }

    /**
     * 获取分区
     * 
     * @param userindex
     * @return
     */
    public static String getPartitionNum(Long userindex) {
        String s = "P" + String.format("%03d", userindex % 1000);
        return s;
    }

    /**
     * 获取userindex分区
     * 
     * @param userindex
     * @return
     */
    public static String getUserindexParaNum(Long userindex) {
        String s = String.format("%03d", userindex % 1000);
        return s;
    }

    public static String getPartitionStr(Long userindex) {
        String partition = getPartitionNum(userindex);
        return " partition(" + partition + ")";
    }
}
