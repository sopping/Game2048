package com.example.game2048.util;

/**
 * Created by Administrator on 14-4-22.
 */
public abstract class Function {
    /**
     * 需要时实现
     * @param args
     * @return
     */
    public abstract Object apply (Object[] args);

    /**
     * 对传递进来的args，调用apply方法
     * @param args
     * @return
     */
    public Object call (Object... args) {
        return apply(args);
    }
}

