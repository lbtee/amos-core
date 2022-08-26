package org.amos.core.basic.utils.crud;

import lombok.Data;
/**
 * @desc: sql between 条件封装
 * @author: liubt
 * @date: 2022-08-08 13:21
 **/
@Data
public class Between<T> {
    private T bLeft;
    private T bRight;

    public Between(T bLeft, T bRight) {
        this.bLeft = bLeft;
        this.bRight = bRight;
    }
}
