package com.wen.flow.support.custom.titlebar;

public interface OnTitleBarListener {

    /**
     * 左邊的標題被點擊
     *
     * @param titleBar      標題欄對象（非空）
     */
    default void onLeftClick(TitleBar titleBar) {}

    /**
     * 中間的標題被點擊
     *
     * @param titleBar      標題欄對象（非空）
     */
    default void onTitleClick(TitleBar titleBar) {}

    /**
     * 右邊的標題被點擊
     *
     * @param titleBar      標題欄對象（非空）
     */
    default void onRightClick(TitleBar titleBar) {}
}
