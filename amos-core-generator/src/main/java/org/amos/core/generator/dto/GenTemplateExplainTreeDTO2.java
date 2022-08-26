package org.amos.core.generator.dto;

import lombok.Data;
import lombok.ToString;
import org.amos.core.basic.node.TreeNode2;

import java.util.List;

/**
 * 树形菜单
 *
 * @author bing_huang
 * @since 3.0.0
 */
@Data
@ToString
public class GenTemplateExplainTreeDTO2 extends GenTemplateExplainDTO implements TreeNode2<GenTemplateExplainTreeDTO2> {

    /**
     * 子树
     */
    private List<GenTemplateExplainTreeDTO2> children;

}
