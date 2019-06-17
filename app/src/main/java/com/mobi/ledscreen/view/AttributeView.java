package com.mobi.ledscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobi.ledscreen.R;
import com.mobi.ledscreen.base.Constant;
import com.mobi.ledscreen.mode.ColorMode;
import com.mobi.ledscreen.mode.SpeedMode;
import com.mobi.ledscreen.mode.WordSizeMode;
import com.mobi.ledscreen.recycler.BaseRecyclerViewAdapter;
import com.mobi.ledscreen.recycler.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by waiarl on 2019/6/12.
 */
public class AttributeView extends LinearLayout implements Constant {
    private final Context mContext;
    private RecyclerView recyclerView_speed;
    private TextView txt_size;
    private RecyclerView recyclerView_size;
    private RecyclerView recyclerView_color;
    private SpeedAdapter speedAdapter;
    private SizeAdapter sizeAdapter;
    private ColorAdapter colorAdapter;
    private OnAttributChoosedListener<SpeedMode> speedChoosedListener;
    private OnAttributChoosedListener<WordSizeMode> sizeChoosedListener;
    private OnAttributChoosedListener<ColorMode> colorChoosedListener;

    private List<SpeedMode> speedModeList = new ArrayList<>();
    private List<WordSizeMode> sizeModeList = new ArrayList<>();
    private List<ColorMode> colorModeList = new ArrayList<>();

    public AttributeView(Context context) {
        this(context, null);
    }

    public AttributeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttributeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        init();
        setListener();
    }

    private void initData() {
        speedModeList.clear();
        for (SpeedMode mode : SPEED_LIST) {
            SpeedMode m = mode.clone();
            speedModeList.add(m);
        }

        sizeModeList.clear();
        for (WordSizeMode mode : WORD_SIZE_LIST) {
            WordSizeMode m = mode.clone();
            sizeModeList.add(m);
        }

        colorModeList.clear();
        for (ColorMode mode : COLOR_LIST) {
            ColorMode m = mode.clone();
            colorModeList.add(m);
        }

        speedModeList.get(0).selected = true;
        sizeModeList.get(0).selected = true;
        colorModeList.get(0).selected = true;

    }

    private void init() {
        inflate(mContext, R.layout.view_type, this);

        recyclerView_speed = findViewById(R.id.recyclerView_speed);
        txt_size = findViewById(R.id.txt_size);
        recyclerView_size = findViewById(R.id.recyclerView_size);
        recyclerView_color = findViewById(R.id.recyclerView_color);

        speedAdapter = new SpeedAdapter(speedModeList);
        sizeAdapter = new SizeAdapter(sizeModeList);
        colorAdapter = new ColorAdapter(colorModeList);
        setAdapter(recyclerView_speed, speedAdapter);
        setAdapter(recyclerView_size, sizeAdapter);
        setAdapter(recyclerView_color, colorAdapter);

    }

    private void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        final LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    private void setListener() {
        speedAdapter.setOnRecyclerViewItemClickListener((v, position) -> onSpeedChoosed(v, position));
        sizeAdapter.setOnRecyclerViewItemClickListener((v, position) -> onSizeChoosed(v, position));
        colorAdapter.setOnRecyclerViewItemClickListener((v, position) -> onColorChoosed(v, position));
    }

    private void onSpeedChoosed(View v, int position) {
        final SpeedMode mode = speedAdapter.getItem(position);
        for (SpeedMode m : speedAdapter.getData()) {
            m.selected = m == mode;
        }
        speedAdapter.notifyDataSetChanged();
        if (speedChoosedListener != null) {
            speedChoosedListener.onItemChoosed(mode);
        }
    }

    private void onSizeChoosed(View v, int position) {
        final WordSizeMode mode = sizeAdapter.getItem(position);
        for (WordSizeMode m : sizeAdapter.getData()) {
            m.selected = m == mode;
        }
        sizeAdapter.notifyDataSetChanged();
        if (sizeChoosedListener != null) {
            sizeChoosedListener.onItemChoosed(mode);
        }
    }

    private void onColorChoosed(View v, int position) {
        final ColorMode mode = colorAdapter.getItem(position);
        for (ColorMode m : colorAdapter.getData()) {
            m.selected = m == mode;
        }
        colorAdapter.notifyDataSetChanged();
        if (colorChoosedListener != null) {
            colorChoosedListener.onItemChoosed(mode);
        }
    }


    public AttributeView setOnSpeedChoosedListener(OnAttributChoosedListener<SpeedMode> speedChoosedListener) {
        this.speedChoosedListener = speedChoosedListener;
        return this;
    }

    public AttributeView setOnWordSizeChoosedListener(OnAttributChoosedListener<WordSizeMode> sizeChoosedListener) {
        this.sizeChoosedListener = sizeChoosedListener;
        return this;
    }

    public AttributeView setOnColorChoosedListener(OnAttributChoosedListener<ColorMode> colorChoosedListener) {
        this.colorChoosedListener = colorChoosedListener;
        return this;
    }

    public interface OnAttributChoosedListener<T> {
        void onItemChoosed(T t);
    }

    private class SpeedAdapter extends BaseRecyclerViewAdapter<SpeedMode> {
        public SpeedAdapter(List<SpeedMode> speedList) {
            super(R.layout.adapter_speed, speedList);
        }

        @Override
        protected void convert(BaseViewHolder holder, SpeedMode item) {

            holder.getView(R.id.rel_bg).setSelected(item.selected);
            holder.setText(R.id.txt, item.text);
        }
    }

    private class SizeAdapter extends BaseRecyclerViewAdapter<WordSizeMode> {
        public SizeAdapter(List<WordSizeMode> wordSizeList) {
            super(R.layout.adapter_speed, wordSizeList);
        }

        @Override
        protected void convert(BaseViewHolder holder, WordSizeMode item) {
            holder.getView(R.id.rel_bg).setSelected(item.selected);
            holder.setText(R.id.txt, item.text);
        }
    }

    private class ColorAdapter extends BaseRecyclerViewAdapter<ColorMode> {
        public ColorAdapter(List<ColorMode> colorList) {
            super(R.layout.adapter_speed, colorList);

        }

        @Override
        protected void convert(BaseViewHolder holder, ColorMode item) {
            holder.getView(R.id.rel_bg).setSelected(item.selected);
            holder.getView(R.id.txt).setBackgroundResource(item.drawable);

        }
    }


}
