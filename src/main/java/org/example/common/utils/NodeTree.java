package org.example.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.example.common.utils.lambda.LambdaTypeInfo;
import org.example.common.utils.lambda.SFunction;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NodeTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前ID
     */
    private String value;

    /**
     * 父级ID
     **/
    private String parentId;

    /**
     * 前端展示
     */
    private String label;

    private Map<String, Object> params = new HashMap<>();

    /**
     * 是否已选
     */
    private boolean selected;

    /**
     * 是否可选
     */
    private boolean checked;

    /**
     * 子级
     */
    private List<NodeTree> children = new ArrayList<>();

    public NodeTree() {
    }

    public NodeTree(String value, String label) {
        this.value = value;
        this.label = label;
        this.children = new ArrayList<>();
        this.params = new HashMap<>();
    }

    public NodeTree(String value, String label, Map<String, Object> params) {
        this.value = value;
        this.label = label;
        this.children = new ArrayList<>();
        this.params = params;
    }

    public NodeTree(String value, String label, String parentId) {
        this.value = value;
        this.label = label;
        this.parentId = parentId;
        this.children = new ArrayList<>();
        this.params = new HashMap<>();
    }

    public NodeTree(String value, String label, String parentId, Map<String, Object> params) {
        this.value = value;
        this.label = label;
        this.parentId = parentId;
        this.children = new ArrayList<>();
        this.params = params;
    }

    /**
     * 构造树形结构
     *
     * @param sourceList {@link List<NodeTree>}
     * @return {@link List<NodeTree>}
     * @author XJH
     * @since 2023/5/24
     **/
    public static List<NodeTree> createTree(List<NodeTree> sourceList) {
        List<NodeTree> list = new ArrayList<>();
        Set<String> valueSet = sourceList.stream().map(NodeTree::getValue).filter(StrUtil::isNotBlank).collect(Collectors.toSet());
        Set<String> parentIdSet = sourceList.stream().map(NodeTree::getParentId).filter(StrUtil::isNotBlank).collect(Collectors.toSet());
        Iterator<NodeTree> iterator = sourceList.iterator();
        while (iterator.hasNext()) {
            NodeTree box = iterator.next();
            // 找到根节点进行处理，找下一级节点
            if (StrUtil.isBlank(box.getParentId()) || !valueSet.contains(box.getParentId())) {
                iterator.remove();
                list.add(findChildren(box, sourceList, parentIdSet));
            }
        }
        return list;
    }

    /**
     * 找子级
     *
     * @param rootNodeTree {@link NodeTree}
     * @param list         {@link List<NodeTree>}
     * @param parentIdSet  {@link Set<String>}
     * @return {@link NodeTree}
     * @author XJH
     * @since 2023/5/24
     **/
    private static NodeTree findChildren(NodeTree rootNodeTree, List<NodeTree> list, Set<String> parentIdSet) {
        for (NodeTree box : list) {
            if (rootNodeTree.getValue().equals(box.getParentId())) {
                if (parentIdSet.contains(box.getValue())) {
                    rootNodeTree.getChildren().add(findChildren(box, list, parentIdSet));
                } else {
                    rootNodeTree.getChildren().add(box);
                }
            }
        }
        return rootNodeTree;
    }

    /**
     * 匹配关键词返回节点以及所有父节点
     * 例：NodeTree.matchingKeywordByParams(tree, "keyword", String::contains, "name");
     *
     * @param tree        {@link List<NodeTree>}
     * @param keyword     {@link String}
     * @param biPredicate {@link BiPredicate<String, String>}
     * @param sFunctions  {@link SFunction<S, R>}
     * @return {@link List<NodeTree>}
     * @author XJH
     * @since 2023/5/25
     **/
    @SuppressWarnings("unchecked")
    public static <T, S, R> List<NodeTree> matchingKeywordByParams(List<NodeTree> tree, T keyword, BiPredicate<T, T> biPredicate, SFunction<S, R>... sFunctions) {
        if (Objects.isNull(tree)) {
            return Collections.emptyList();
        }
        Iterator<NodeTree> iterator = tree.iterator();
        while (iterator.hasNext()) {
            NodeTree next = iterator.next();
            boolean dqFlag = false;
            for (SFunction<S, ?> sFunction : sFunctions) {
                String paramName = LambdaTypeInfo.getFieldName(sFunction);
                dqFlag = dqFlag || biPredicate.test((T) next.getParams().get(paramName), keyword);
            }
            boolean childrenFlag = CollUtil.isNotEmpty(matchingKeywordByParams(next.getChildren(), keyword, biPredicate, sFunctions));
            if (!childrenFlag) {
                next.setChildren(Collections.emptyList());
            }
            if (!dqFlag && !childrenFlag) {
                iterator.remove();
            }
        }
        return tree;
    }

    /**
     * 递归过滤
     * 例：NodeTree.filteringByParams(list, new BigDecimal("2"), BigDecimal::equals, "age");
     *
     * @param list {@link List<NodeTree>}
     * @return {@link List<NodeTree>}
     * @author XJH
     * @since 2023/5/18
     **/
    @SuppressWarnings("unchecked")
    public static <T, F> List<NodeTree> filteringByParams(List<NodeTree> list, T keyword, BiPredicate<T, T> biPredicate, SFunction<F, ?>... sFunctions) {
        if (Objects.isNull(list)) {
            return Collections.emptyList();
        }
        Iterator<NodeTree> iterator = list.iterator();
        while (iterator.hasNext()) {
            NodeTree next = iterator.next();
            for (SFunction<F, ?> sFunction : sFunctions) {
                String paramName = LambdaTypeInfo.getFieldName(sFunction);
                if (biPredicate.test((T) next.getParams().get(paramName), keyword)) {
                    iterator.remove();
                }
            }
            next.setChildren(filteringByParams(next.getChildren(), keyword, biPredicate, sFunctions));
        }
        return list;
    }


}
