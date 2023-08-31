package com.wen.flow.support.custom.titlebar;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

@SuppressWarnings("unused")
public final class SelectorDrawable extends StateListDrawable {

    public final static class Builder {

        /** 默認狀態 */
        private Drawable mDefault;
        /** 焦點狀態 */
        private Drawable mFocused;
        /** 按下狀態 */
        private Drawable mPressed;
        /** 選中狀態 */
        private Drawable mChecked;
        /** 啟用狀態 */
        private Drawable mEnabled;
        /** 選擇狀態 */
        private Drawable mSelected;
        /** 光標懸浮狀態（4.0新特性） */
        private Drawable mHovered;

        public Builder setDefault(Drawable drawable) {
            mDefault = drawable;
            return this;
        }

        public Builder setFocused(Drawable drawable) {
            mFocused = drawable;
            return this;
        }

        public Builder setPressed(Drawable drawable) {
            mPressed = drawable;
            return this;
        }

        public Builder setChecked(Drawable drawable) {
            mChecked = drawable;
            return this;
        }

        public Builder setEnabled(Drawable drawable) {
            mEnabled = drawable;
            return this;
        }

        public Builder setSelected(Drawable drawable) {
            mSelected = drawable;
            return this;
        }

        public Builder setHovered(Drawable drawable) {
            mHovered = drawable;
            return this;
        }

        public SelectorDrawable build() {
            SelectorDrawable selector = new SelectorDrawable();
            if (mPressed != null) {
                selector.addState(new int[]{android.R.attr.state_pressed}, mPressed);
            }
            if (mFocused != null) {
                selector.addState(new int[]{android.R.attr.state_focused}, mFocused);
            }
            if (mChecked != null) {
                selector.addState(new int[]{android.R.attr.state_checked}, mChecked);
            }
            if (mEnabled != null) {
                selector.addState(new int[]{android.R.attr.state_enabled}, mEnabled);
            }
            if (mSelected != null) {
                selector.addState(new int[]{android.R.attr.state_selected}, mSelected);
            }
            if (mHovered != null) {
                selector.addState(new int[]{android.R.attr.state_hovered}, mHovered);
            }
            if (mDefault != null) {
                selector.addState(new int[]{}, mDefault);
            }
            return selector;
        }
    }
}
