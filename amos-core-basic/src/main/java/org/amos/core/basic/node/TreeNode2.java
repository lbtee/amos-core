package org.amos.core.basic.node;

import java.util.List;

public interface TreeNode2<T>{
    Long getId();
    Long getPid();
    void setChildren(List<T> children);
}
