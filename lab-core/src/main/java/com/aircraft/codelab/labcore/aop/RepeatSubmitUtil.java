package com.aircraft.codelab.labcore.aop;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * 2022-04-02
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class RepeatSubmitUtil {
    /**
     * @param reqJSON     请求的参数，这里通常是JSON
     * @param excludeKeys 请求参数里面要去除哪些字段再求摘要
     * @return 去除参数的MD5摘要
     */
    public static String dedupParamMD5(final String reqJSON, String... excludeKeys) {
        TreeMap paramTreeMap = JSON.parseObject(reqJSON, TreeMap.class);
        if (excludeKeys != null) {
            List<String> dedupExcludeKeys = Arrays.asList(excludeKeys);
            if (!dedupExcludeKeys.isEmpty()) {
                for (String dedupExcludeKey : dedupExcludeKeys) {
                    paramTreeMap.remove(dedupExcludeKey);
                }
            }
        }
        String paramTreeMapJSON = JSON.toJSONString(paramTreeMap);
        String md5deDupParam = DigestUtil.md5Hex(paramTreeMapJSON);
        log.debug("md5deDupParam = {}, excludeKeys = {} {}", md5deDupParam, Arrays.deepToString(excludeKeys), paramTreeMapJSON);
        return md5deDupParam;
    }
}
