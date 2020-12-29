package com.example.school.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.data.Card;
import com.example.school.fragment.Fragment1;

import java.util.ArrayList;
import java.util.List;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Card> cards;
    Fragment mContext;
    RecyclerView recyclerView;

    private int normalType = 0;
    private int footType = 1;

    //当前加载状态
    private int loadstate = 2;
    //正在加载
    public final int LOADING = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;

    private boolean hasMore = true;
    private boolean feedTips = false;

    public CardRecyclerViewAdapter(Fragment1 mContext, List<Card> cards) {
        this.mContext = mContext;
        this.cards = cards;
        //this.hasMore=hasMore;
    }

    public void setList(List<Card> cardList){
        this.cards = cardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == normalType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item_card, parent, false);
            return new myViewHolder(view);
        } else if (viewType == footType){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load, parent, false);
            return new footHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof myViewHolder) {
            ((myViewHolder) holder).avator.setImageResource(cards.get(position).getAvaId());
            ((myViewHolder) holder).user.setText(cards.get(position).getName());
            ((myViewHolder) holder).time.setText(cards.get(position).getTime());
            ((myViewHolder) holder).content.setText(cards.get(position).getContent());
            ((myViewHolder) holder).like.setImageResource(R.drawable.ic_like);
            ((myViewHolder) holder).comments.setImageResource(R.drawable.ic_saying);
            ((myViewHolder) holder).location.setText(cards.get(position).getLocation());

            ((myViewHolder) holder).like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cards.get(position).getLike()) {
                        cards.get(position).setLike(false);
                        ((myViewHolder) holder).like.setImageResource(R.drawable.ic_like);
                    } else {
                        cards.get(position).setLike(true);
                        ((myViewHolder) holder).like.setImageResource(R.drawable.ic_like1);
                    }

                }
            });
        } else if (holder instanceof footHolder){
            switch(loadstate){
                case LOADING:
                    ((footHolder) holder).tips.setVisibility(View.VISIBLE);
                    ((footHolder) holder).tips.setText("正在加载更多......");
                    break;
                case LOADING_COMPLETE:
                    ((footHolder) holder).tips.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    ((footHolder) holder).tips.setVisibility(View.VISIBLE);
                    ((footHolder) holder).tips.setText("没有更多数据了......");
                    break;
                default:
                    break;
            }
//            ((footHolder) holder).tips.setVisibility(View.VISIBLE);
//            ((footHolder) holder).tips.setText("正在加载更多......");
//            if (hasMore==true){
//                feedTips=false;
//                if (cards.size()>0){
//                    ((footHolder)holder).tips.setText("正在加载更多......");
//                }
//            }else {
//                if (cards.size()>0){
//                    ((footHolder)holder).tips.setText("没有更多数据了......");
//                }
//            }
        }


    }

    //设置上拉状态
    public void setLoadstate(int loadstate){
        this.loadstate = loadstate;
        notifyDataSetChanged();
    }

    public Boolean isFadeTips() {
        return feedTips;
    }

    public void resetDatas(List<Card> cards) {
        this.cards = cards;
    }

    public void updateList(List<Card> newDatas) {
        if (newDatas != null) {
            cards.addAll(newDatas);
        }
        //notifyDataSetChanged();
    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        this.recyclerView = recyclerView;
//    }
//
//    @Override
//    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onDetachedFromRecyclerView(recyclerView);
//        this.recyclerView = null;
//    }

    @Override
    public int getItemCount() {
        return cards.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView avator;
        TextView user;
        TextView time;
        TextView content;
        ImageView like, comments;
        TextView location;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            avator = itemView.findViewById(R.id.iv_Avatar);
            user = itemView.findViewById(R.id.tv_user);
            time = itemView.findViewById(R.id.tv_time);
            content = itemView.findViewById(R.id.tv_content);
            like = itemView.findViewById(R.id.iv_like);
            comments = itemView.findViewById(R.id.saying);
            location = itemView.findViewById(R.id.tv_location);
        }
    }

    class footHolder extends RecyclerView.ViewHolder {
        TextView tips;

        public footHolder(@NonNull View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.tips);
        }
    }
}
