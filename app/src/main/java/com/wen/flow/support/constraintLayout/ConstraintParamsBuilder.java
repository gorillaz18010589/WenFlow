package com.wen.flow.support.constraintLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ConstraintParamsBuilder {
    private ConstraintLayout mConstraintLayout;
    private ConstraintLayout.LayoutParams mLayoutParams;

    public ConstraintParamsBuilder(ConstraintParamsBuilder.Builder builder) {
        this.mConstraintLayout = builder.mConstraintLayout;
        this.mLayoutParams = builder.mLayoutParams;
    }

    public static class Builder {
        private ConstraintLayout mConstraintLayout;
        private ConstraintLayout.LayoutParams mLayoutParams;

        public Builder(ConstraintLayout mConstraintLayout) {
            this.mConstraintLayout = mConstraintLayout;
            this.mLayoutParams = (ConstraintLayout.LayoutParams) mConstraintLayout.getLayoutParams();
            this.mConstraintLayout.setLayoutParams(mLayoutParams);
        }

        /**
         * 設定邊距
         *
         * @param left   距離容器的左邊margin
         * @param top    距離容器的頂部margin
         * @param right  距離容器的右邊margin
         * @param bottom 距離容器的底部margin
         * @return
         */
        public Builder setMargin(int left, int top, int right, int bottom) {
            this.mLayoutParams.setMargins(left, top, right, bottom);
            return this;
        }

        /**
         * 設定寬度
         *
         * @param width 寬度
         * @return
         */
        public Builder setWidth(int width) {
            this.mLayoutParams.width = width;
            return this;
        }

        public ConstraintParamsBuilder create() {
            ConstraintParamsBuilder constraintParamsBuilder = new ConstraintParamsBuilder(this);
            return constraintParamsBuilder;
        }
    }
}

