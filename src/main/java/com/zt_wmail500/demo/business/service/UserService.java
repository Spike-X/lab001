/*
 * Copyright (c) 2020, Tao Zhang (zt_wmail500@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zt_wmail500.demo.business.service;

import com.zt_wmail500.demo.business.pojo.User;

/**
 * @description: y
 * @author: spikeX
 * @create: 2020-10-31
 **/
public interface UserService {
    /**
     *
     * @param user 用户对象
     * @return 成功则返回"success"，失败则返回错误信息
     */
    String addUser(User user);
}
