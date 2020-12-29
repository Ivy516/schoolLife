package com.example.school.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.data.Card;
import com.example.school.data.Goods;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Goods> goodsList;
    private Context context;
    private boolean hasMore=true;
    private boolean feedTips=false;
    private int normalType=0;
    private int footType=1;

    public GoodsAdapter(Context context) {
        this.context = context;
    }

    public GoodsAdapter(Context context,List<Goods> goods,boolean hasMore) {
        this.context = context;
        this.goodsList = goods;
        this.hasMore=hasMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View view;
//        if (viewType==normalType){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item_seondary_market,parent,false);
//            myViewHolder holder = new myViewHolder(view);
//            return holder;
//        }else{
//            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load,parent,false);
//            footHolder footHolder = new footHolder(view);
//            return footHolder;
//        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item_seondary_market,parent,false);
        final myViewHolder holder = new myViewHolder(view);

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Log.d("ddd", "onClick: " +position);
                Goods goods = goodsList.get(position);
                RongIM.getInstance().startPrivateChat(context, goods.getStuNo(), goods.getStuNo());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //if (viewHolder instanceof myViewHolder){
            ((myViewHolder)viewHolder).introduce.setText(goodsList.get(i).getDescription());
            ((myViewHolder)viewHolder).goodsImage.setImageBitmap(goodsList.get(i).getPicture());
            ((myViewHolder)viewHolder).price.setText(String.valueOf(goodsList.get(i).getPrice()));
            ((myViewHolder)viewHolder).time.setText(goodsList.get(i).getTime());
            ((myViewHolder)viewHolder).jg.setText("价格：");


//        }else {
//            ((footHolder)viewHolder).tips.setVisibility(View.VISIBLE);
//            if (hasMore==true){
//                feedTips=false;
//                if (goodsList.size()>0){
//                    ((footHolder)viewHolder).tips.setText("正在加载更多......");
//                }
//            }else {
//                if (goodsList.size()>0){
//                    ((footHolder)viewHolder).tips.setText("没有更多数据了......");
//                }
//            }
//        }
    }

    public void updateList(List<Goods> newDatas, boolean hasMore){
        if (newDatas!=null){
            goodsList.addAll(newDatas);
        }
        this.hasMore=hasMore;
        notifyDataSetChanged();
    }

    public void resetDatas(){
        goodsList=new ArrayList<>();
    }

    public Boolean isFadeTips(){
        return feedTips;
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position==getItemCount()-1){
//            return footType;
//        }else {
//            return normalType;
//        }
//    }

    public void setData(List<Goods> dataList) {
            goodsList.clear();
            goodsList.addAll(dataList);
            notifyDataSetChanged();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        TextView introduce;
        ImageView goodsImage;
        TextView time;
        TextView price;
        TextView jg;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            introduce=itemView.findViewById(R.id.tv_goods_introduce);
            time=itemView.findViewById(R.id.tv_goods_time);
            goodsImage=itemView.findViewById(R.id.im_goods);
            price=itemView.findViewById(R.id.tv_goods_price);
            jg = itemView.findViewById(R.id.jg);
        }
    }

    class footHolder extends RecyclerView.ViewHolder{
        TextView tips;

        public footHolder(@NonNull View itemView) {
            super(itemView);
            tips=itemView.findViewById(R.id.tips);
        }
    }
}
