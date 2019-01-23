package com.antiy.asset.util;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.antiy.common.base.BaseConverter;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;

/**
 * @author: 张兵
 * @date: 2019/1/8 14:36
 * @description: 列表转换为树形结构
 */
public class NodeUtilsConverter<S, T> extends BaseConverter<S, T> {
    private static final String PARTNER_COLUMN       = "parentId";
    private static final String ID_COLUMN            = "stringId";
    private static final String CHILDREN_NODE_COLUMN = "childrenNode";
    private static final String PARENT_ID_VALUE      = "0";
    private static final Logger logger               = LogUtils.get();

    /**
     * 转换为树形结构
     * @param sourceList 原列表树形
     * @param clazz 转换为树形的结构class
     * @return
     */
    public List<T> columnToNode(List<S> sourceList, Class<T> clazz) throws BusinessException {
        return this.columnToNode(sourceList, clazz, ID_COLUMN, PARTNER_COLUMN, CHILDREN_NODE_COLUMN, null);
    }

    /**
     * 转换为树形结构
     * @param sourceList 原列表树形
     * @param clazz 转换为树形的结构class
     * @param rootNode 自定义一个root节点
     * @return
     * @throws BusinessException
     */
    public List<T> columnToNode(List<S> sourceList, Class<T> clazz, List<T> rootNode) throws BusinessException {
        return this.columnToNode(sourceList, clazz, ID_COLUMN, PARTNER_COLUMN, CHILDREN_NODE_COLUMN, rootNode);
    }

    /**
     * 列表转换为树形接口
     * @param sourceList 需要转换为树形结构的数组,对应数据库的行
     * @param clazz 转换为树形结构对象的class
     * @param parentColumn 需要转换为树形结构的父Id字段名,默认为parent_id，对应数据库的父Id
     * @param childrenNodeColumn 转换为树形结构的子节点字段名,默认为childrenNode
     * @param idColumn 对象的Id 字段名
     * @return
     */
    public List<T> columnToNode(List<S> sourceList, Class<T> clazz, String idColumn, String parentColumn,
                                String childrenNodeColumn, List<T> rootNode) throws BusinessException {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        try {

            // 1.保存原始数据到Map中
            Map<String, S> sourceNodeMap = new HashMap<>();
            for (S node : sourceList) {
                sourceNodeMap.put(objToString(ReflectionUtils.invokeGetterMethod(node, idColumn)), node);
            }

            // 2.初始化rootNode
            if (CollectionUtils.isEmpty(rootNode)) {
                rootNode = new ArrayList<T>();
            }
            for (S node : sourceList) {
                String parentId = objToString(ReflectionUtils.invokeGetterMethod(node, parentColumn));
                if (StringUtils.isEmpty(parentId) || PARENT_ID_VALUE.equals(parentId)) {
                    // 初始化rootNode并且转换信息
                    rootNode.add(this.convert(node, clazz));
                }
            }

            // 3.递归转换为rootNode
            rootNode = convertNode(sourceList, clazz, rootNode, parentColumn, idColumn, childrenNodeColumn);
        } catch (Exception e) {
            logger.error("转换失败", e);
            throw new BusinessException("转换失败", e);
        }

        return rootNode;
    }

    private String objToString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 递归转换
     * @param sourceList
     * @param clazz
     * @param rootNode
     * @param parentColumn
     * @param idColumn
     * @throws Exception
     */
    private List<T> convertNode(List<S> sourceList, Class<T> clazz, List<T> rootNode, String parentColumn,
                                String idColumn, String childrenNodeColumn) throws Exception {
        if (CollectionUtils.isNotEmpty(sourceList)) {
            Iterator<S> it = sourceList.iterator();
            while (it.hasNext()) {
                recursionNode(it, it.next(), clazz, rootNode, parentColumn, idColumn, childrenNodeColumn);
            }
        }
        return rootNode;
    }

    /**
     * 递归
     * @param iterator
     * @param s
     * @param clazz
     * @param rootNode
     * @param parentColumn
     * @param idColumn
     * @param childrenNodeColumn
     * @throws Exception
     */
    private void recursionNode(Iterator<S> iterator, S s, Class<T> clazz, List<T> rootNode, String parentColumn,
                               String idColumn, String childrenNodeColumn) throws Exception {
        if (CollectionUtils.isNotEmpty(rootNode)) {
            for (T t : rootNode) {
                String id = objToString(ReflectionUtils.invokeGetterMethod(t, idColumn));
                String partnerId = objToString(ReflectionUtils.invokeGetterMethod(s, parentColumn));

                // 反射调用并且设置值。
                List<T> source = (List<T>) ReflectionUtils.invokeGetterMethod(t, childrenNodeColumn);
                if (StringUtils.isNotBlank(id) && id.equals(partnerId)) {
                    // 判断T的chlidrenList是否为null如果为空，则初始化
                    if (source == null) {
                        ReflectionUtils.setFieldValue(t, childrenNodeColumn, new ArrayList<>());
                        source = (List<T>) ReflectionUtils.invokeGetterMethod(t, childrenNodeColumn);
                    }
                    source.add(this.convert(s, clazz));
                    ReflectionUtils.setFieldValue(t, childrenNodeColumn, source);
                    iterator.remove();
                } else if (CollectionUtils.isNotEmpty(source)) {
                    // 递归调用
                    recursionNode(iterator, s, clazz, source, parentColumn, idColumn, childrenNodeColumn);
                }
            }
        }
    }

}
