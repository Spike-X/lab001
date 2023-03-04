package com.aircraft.codelab.pioneer.pojo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 2022-10-23
 * 遍历树形结构
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    /**
     * id
     */
    public Long id;
    /**
     * 名称
     */
    public String name;
    /**
     * 父id 根节点为0
     */
    public Long parentId;
    /**
     * 子节点信息
     */
    @JSONField(ordinal = 4)
    public List<Menu> childList;

    public Menu(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
