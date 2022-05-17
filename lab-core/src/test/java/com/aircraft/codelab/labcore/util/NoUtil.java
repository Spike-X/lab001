package com.aircraft.codelab.labcore.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 2022-05-10
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class NoUtil {
    /**
     * 订单号生成
     **/
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    private static final AtomicInteger SEQ = new AtomicInteger(10000);
    //    private static final DateTimeFormatter DF_FMT_PREFIX = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter DF_FMT_PREFIX = DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN);
    private volatile static String IP_SUFFIX;

    private static final String REDIS_KEY_PRE_ORDER_NO = "REDIS_KEY_WALLET_ORDER_NO";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static String generateOrderNo() {
        LocalDateTime dataTime = LocalDateTime.now(ZONE_ID);
        if (SEQ.intValue() > 99990) {
            SEQ.getAndSet(10000);
        }
        String localIpSuffix = getLocalIpSuffix();
        return dataTime.format(DF_FMT_PREFIX) + localIpSuffix + SEQ.getAndIncrement();
    }

    public List<String> getBatchOrderNo(Integer listSize) {
        //数据key
        String redisKey = REDIS_KEY_PRE_ORDER_NO;
        //incr会将null自动设置成0
        Long increment = stringRedisTemplate.opsForValue().increment(redisKey, listSize);
        stringRedisTemplate.expire(redisKey, getEndTime(), TimeUnit.MILLISECONDS);

        List<String> result = new ArrayList<>();

        long index = increment - listSize + 1;
        for (long i = index; i <= increment; i++) {
            StringBuilder no = new StringBuilder("WD").append(DateUtil.format(new Date(), "yyyyMMdd"));
            //一天最多生成99999999条数据
            no.append(String.format("%08d", (i)));
            result.add(no.toString());
        }
        return result;
    }

    /**
     * 获取当前时间与今天最后一个时间差 单位：秒
     *
     * @return
     */
    private Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 0);
        todayEnd.set(Calendar.MINUTE, 0);
        todayEnd.set(Calendar.SECOND, 0);
        todayEnd.add(Calendar.DAY_OF_MONTH, 1);
        todayEnd.add(Calendar.SECOND, -1);
        Date endDate = todayEnd.getTime();
        return endDate.getTime() - (new Date()).getTime();
    }

    /*public static String generateOrderNo() {
        LocalDateTime dataTime = LocalDateTime.now(ZONE_ID);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有多是负数
            hashCodeV = -hashCodeV;
        }
        // 0 表明前面补充0
        // 4 表明长度为4
        // d 表明参数为正数型
        return dataTime.format(DF_FMT_PREFIX) + String.format("%04d", hashCodeV);
    }*/

    private static String getLocalIpSuffix() {
        if (null != IP_SUFFIX) {
            return IP_SUFFIX;
        }
        try {
            synchronized (NoUtil.class) {
                if (null != IP_SUFFIX) {
                    return IP_SUFFIX;
                }
                InetAddress addr = InetAddress.getLocalHost();
                //  172.17.0.4  172.17.0.199 ,
                String hostAddress = addr.getHostAddress();
                if (null != hostAddress && hostAddress.length() > 4) {
                    String ipSuffix = hostAddress.trim().split("\\.")[3];
                    if (ipSuffix.length() == 2) {
                        IP_SUFFIX = ipSuffix;
                        return IP_SUFFIX;
                    }
                    ipSuffix = "0" + ipSuffix;
                    IP_SUFFIX = ipSuffix.substring(ipSuffix.length() - 2);
                    return IP_SUFFIX;
                }
                IP_SUFFIX = RandomUtils.nextInt(10, 20) + "";
                return IP_SUFFIX;
            }
        } catch (Exception e) {
            System.out.println("获取IP失败:" + e.getMessage());
            IP_SUFFIX = RandomUtils.nextInt(10, 20) + "";
            return IP_SUFFIX;
        }
    }

    @Test
    void BatchOrderNo() {
            List<String> batchOrderNo = getBatchOrderNo(10000);
//        List<String> orderNos = Collections.synchronizedList(new ArrayList<>());
//        IntStream.range(0, 8000).parallel().forEach(i -> {
////            orderNos.add(generateOrderNo());
//            orderNos.addAll(batchOrderNo);
//        });

        List<String> filterOrderNos = batchOrderNo.stream().distinct().collect(Collectors.toList());

        System.out.println("订单样例：" + batchOrderNo.get(2000));
        System.out.println("订单样例：" + batchOrderNo.get(3000));
        System.out.println("生成订单数：" + batchOrderNo.size());
        System.out.println("过滤重复后订单数：" + filterOrderNos.size());
        System.out.println("重复订单数：" + (batchOrderNo.size() - filterOrderNos.size()));
    }


    /*public static void main(String[] args) {
        final String merchId = "12334";
        List<String> orderNos = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0, 1000).parallel().forEach(i -> {
            orderNos.add(getOrderNo(merchId));
        });

        List<String> filterOrderNos = orderNos.stream().distinct().collect(Collectors.toList());

        System.out.println("生成订单数：" + orderNos.size());
        System.out.println("过滤重复后订单数：" + filterOrderNos.size());
        System.out.println("重复订单数：" + (orderNos.size() - filterOrderNos.size()));
    }*/

    /**
     * 获取订单号 //有问题的代码示例
     * 订单号命名规则：下单渠道2位+时间信息8位+下单时间的Unix时间戳后4位+随机码4（或是这4位数字加上随机码和随机规则进行随机后的数字）
     *
     * @return
     */
    public static String getOrderNo(String channelNo) {
        StringBuilder orderNo = new StringBuilder();
        orderNo.append(channelNo != null ? channelNo : 99);
        orderNo.append(DateUtil.format(new Date(), "yyyyMMdd"));
        String unixTime = String.valueOf(System.currentTimeMillis());
        orderNo.append(unixTime.substring(unixTime.length() - 4));
        int ran = (int) (Math.random() * 9000) + 1000;
        orderNo.append(ran);
        return orderNo.toString();
    }
}
