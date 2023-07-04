package com.wen.flow.enums;

import com.wen.flow.R;

public enum IconEnum {
    ICON_CLOSE(0, R.drawable.click_icon_close, R.id.btnClose);

    IconEnum(int code, int iconDrawable, int iconId) {
        this.code = code;
        this.iconDrawable = iconDrawable;
        this.iconId = iconId;
    }

    private int code;
    private int iconDrawable;
    private int iconId;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public static IconEnum getIcon(int code) {
        IconEnum[] iconEnums = IconEnum.values();
        for (IconEnum iconEnum : iconEnums) {
            if (iconEnum.getCode() == code) {
                return iconEnum;
            }
        }
        return getIcon(0);
    }

}
