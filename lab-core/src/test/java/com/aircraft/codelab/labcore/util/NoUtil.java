package com.aircraft.codelab.labcore.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        Date date = new Date();
        Long endTime = getEndTime(date);
        System.out.println(endTime);
        Long dayRemainingTime = getDayRemainingTime();
        System.out.println(dayRemainingTime);
        //数据key
        String redisKey = REDIS_KEY_PRE_ORDER_NO;
        //incr会将null自动设置成0
        Long increment = stringRedisTemplate.opsForValue().increment(redisKey, listSize);
        stringRedisTemplate.expire(redisKey, getEndTime(date), TimeUnit.MILLISECONDS);

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
     * 获取一天中剩余的时间（毫秒）
     */
    public Long getDayRemainingTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间:  " + now);
        //当天的零点
        System.out.println("当天的零点:  " + LocalDateTime.of(now.toLocalDate(), LocalTime.MIN));
        //当天的最后时间
        LocalDateTime today_end = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
        System.out.println("当天的最后时间:  " + today_end);
        return ChronoUnit.MILLIS.between(now, today_end);
    }

    /**
     * 获取当前时间与今天最后一个时间差 单位：毫秒
     *
     * @return
     */
    private Long getEndTime(Date currentDate) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 0);
        todayEnd.set(Calendar.MINUTE, 0);
        todayEnd.set(Calendar.SECOND, 0);
        todayEnd.add(Calendar.DAY_OF_MONTH, 1);
        todayEnd.add(Calendar.SECOND, -1);
        Date endDate = todayEnd.getTime();
        System.out.println(endDate);
        return endDate.getTime() - currentDate.getTime();
    }

    @Test
    public void orderNo() {
        /** 生成18位订单编号:8位日期+2位订单来源+2位支付方式+6位以上自增id */
        try {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String key = "mall:oms:orderId" + date;
            Long increment = stringRedisTemplate.opsForValue().increment(key, 1);
            StringBuffer buf = new StringBuffer();
            buf.append(date);
            buf.append(String.format("%02d", 0)); // 订单来源：0->PC订单；1->app订单
            buf.append(String.format("%02d", 0)); // 支付方式：0->未支付；1->支付宝；2->微信
            String incrementStr = increment.toString();
            if (incrementStr.length() <= 6) {
                buf.append(String.format("%06d", increment));
            } else {
                buf.append(incrementStr);
            }
            // 202108070000000001
            System.out.println(buf.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
