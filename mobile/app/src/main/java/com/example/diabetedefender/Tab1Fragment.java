package com.example.diabetedefender;


import android.content.Context;
import android.graphics.Color;

import android.support.v4.app.Fragment;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.Random;




public class Tab1Fragment extends Fragment {




    private final int Y_BASE = 3;
    private final String DATA_SET_LABEL = "Real-time Glucose Level";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1,container, false);
        LineChart chart = (LineChart) view.findViewById(R.id.chart);

        // 弹出的数据点提示框。
        MarkerView mv = new MyMarkerView(getActivity(), R.layout.marker_view);

        chart.setMarkerView(mv);

        // 制作7个数据点（沿x坐标轴）
        LineData mLineData = makeLineData(24);

        // 把X坐标轴放置到底部。默认的是在顶部。
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // X轴坐标线的颜色
        xAxis.setAxisLineColor(Color.LTGRAY);

        // false将不显示图表网格中的x轴标线
        // xAxis.setEnabled(false);

        // 不显示右边的Y坐标轴值
        YAxis rightYAxis = chart.getAxisRight();
        rightYAxis.setDrawLabels(true);

        rightYAxis.setGridColor(Color.LTGRAY);

        // 不显示左边的Y坐标轴值
        YAxis leftYAxis = chart.getAxisLeft();
        leftYAxis.setDrawLabels(false);

        leftYAxis.setGridColor(Color.LTGRAY);

        setChartStyle(chart, mLineData, Color.WHITE);



        // Inflate the layout for this fragment
        return   view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);



    }

    // 设置显示的样式
    private void setChartStyle(LineChart mLineChart, LineData lineData,
                               int color) {
        mLineChart.setDrawBorders(false);

        mLineChart.setDescription(null);

        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mLineChart
                .setNoDataTextDescription("如果传给MPAndroidChart的数据为空，那么你将看到这段文字。@Zhang Phil");

        // 是否绘制背景颜色。
        // 如果mLineChart.setDrawGridBackground(false)，
        // 那么mLineChart.setGridBackgroundColor(Color.CYAN)将失效;
        mLineChart.setDrawGridBackground(false);
        mLineChart.setGridBackgroundColor(0X7363B8FF);

        // 触摸
        mLineChart.setTouchEnabled(true);

        // 拖拽
        mLineChart.setDragEnabled(true);

        // 缩放
        mLineChart.setScaleEnabled(true);

        mLineChart.setPinchZoom(false);

        // 设置背景
        mLineChart.setBackgroundColor(color);

        // 设置x,y轴的数据
        mLineChart.setData(lineData);

        // 比例图标，y的value
        Legend mLegend = mLineChart.getLegend();

        // 如果设置了mLegend.setEnabled(false);
        // 那么下面四行代码将失效。
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setForm(Legend.LegendForm.LINE);
        mLegend.setFormSize(10.0f);
        mLegend.setTextColor(Color.GRAY);
        mLegend.setEnabled(true);

        mLineChart.animateX(2000);
    }

    /**
     * @param count 数据量，多少个数据。
     * @return
     */
    private LineData makeLineData(int count) {
        ArrayList<String> x = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据
            x.add("8:" + i * 5);
        }

        // y轴的数据
        ArrayList<Entry> y = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * 10) + Y_BASE;
            Entry entry = new Entry(num, i);
            y.add(entry);
        }

        // y轴数据集
        LineDataSet mLineDataSet = new LineDataSet(y, DATA_SET_LABEL);

        // 用y轴的集合来设置参数
        mLineDataSet.setLineWidth(4.0f);
        mLineDataSet.setCircleSize(5.0f);
        mLineDataSet.setColor(0XFF4169E1);
        mLineDataSet.setCircleColor(0XFF4169E1);

        // 设置mLineDataSet.setDrawHighlightIndicators(false)后，
        // Highlight的十字交叉的纵横线将不会显示，
        // 同时，mLineDataSet.setHighLightColor(Color.CYAN)失效。
        mLineDataSet.setDrawHighlightIndicators(false);
        mLineDataSet.setHighLightColor(Color.CYAN);

        // 设置这项上显示的数据点的字体大小。
        mLineDataSet.setValueTextSize(10.0f);

        // mLineDataSet.setDrawCircleHole(true);

        // 改变折线样式，用曲线。
        mLineDataSet.setDrawCubic(true);
        // 曲线的平滑度
        mLineDataSet.setCubicIntensity(0.2f);

        // 填充曲线下方的区域，红色，半透明。
        mLineDataSet.setDrawFilled(true);
        mLineDataSet.setFillAlpha(20);
        mLineDataSet.setFillColor(0xFF4169E1);

        // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
        mLineDataSet.setCircleColorHole(Color.WHITE);

        // 设置折线上显示数据的格式。如果不设置，将默认显示float数据格式。
        //mLineDataSet.setValueFormatter(new ValueFormatter() {

        //   @Override
        //   public String getFormattedValue(float value) {
        //       int n = (int) value;
        //       String s = "" + n;
        //      return s;
        //  }
        // });

        ArrayList<LineDataSet> mLineDataSets = new ArrayList<LineDataSet>();
        mLineDataSets.add(mLineDataSet);

        LineData mLineData = new LineData(x, mLineDataSets);

        // 不要在折线上标出数据。
        mLineData.setDrawValues(false);

        return mLineData;
    }

    /**
     * @author Phil
     * <p>
     * 构造一个类似Android Toast的弹出消息提示框。
     */
    private class MyMarkerView extends MarkerView {

        private TextView tvContent;

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            tvContent = (TextView) findViewById(R.id.tvContent);


        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            int n = (int) e.getVal();
            tvContent.setText(n + "");

            // if (e instanceof CandleEntry) {
            // CandleEntry ce = (CandleEntry) e;
            // tvContent.setText(""
            // + Utils.formatNumber(ce.getHigh(), 0, true));
            // } else {
            // tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
            // }
        }

        @Override
        public int getXOffset(float v) {
            return 0;
        }

        @Override
        public int getYOffset(float v) {
            return 0;
        }


    }


}
