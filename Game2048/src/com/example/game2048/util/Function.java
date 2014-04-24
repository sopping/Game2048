package com.example.game2048.util;

/**
 * 函数式编程
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

