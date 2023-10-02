package com.wen.flow.ui.dash.product.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wen.flow.R;
import com.wen.flow.ui.dash.product.Transport;

import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

public class TransportViewHolder extends BaseViewHolder<Transport> {
    private TextView title1;

    public TransportViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_transport);
        title1 = getView(R.id.title1);
    }

    @Override
    public void setData(Transport data) {
        super.setData(data);
        title1.setText(data.getTitle());
    }
}
