package rc.loveq.meizhi.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.data.entity.Gank;
import rc.loveq.meizhi.ui.WebActivity;
import rc.loveq.meizhi.util.StringStyles;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/2/2 21:21
 * Email:664215432@qq.com
 */

public class GankListAdapter extends AnimRecyclerViewAdapter<GankListAdapter.ViewHolder> {
    public List<Gank> mGankList;


    public GankListAdapter(List<Gank> gankList) {
        mGankList = gankList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category)
        TextView mCategory;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.gank_layout)
        LinearLayout mGankLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        @OnClick(R.id.gank_layout)
        public void onGank(View view){
            Gank gank = mGankList.get(getLayoutPosition());
            Intent intent = WebActivity.newIntent(view.getContext(), gank.url, gank.desc);
            view.getContext().startActivity(intent);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gank gank = mGankList.get(position);
        if (position==0){
            showCategory(holder);
        }else {
            if (!mGankList.get(position-1).type.equals(mGankList.get(position).type)) {
                showCategory(holder);
            }else {
                hideCategory(holder);
            }
        }
        holder.mCategory.setText(gank.type);

        SpannableStringBuilder builder=new SpannableStringBuilder(gank.desc)
                    .append(StringStyles.format(holder.mTitle.getContext(), " (via. " +
                            gank.who +
                            ")",R.style.ViaTextAppearance));
        CharSequence subSequence = builder.subSequence(0, builder.length());
        holder.mTitle.setText(subSequence);
        showItemAnim(holder.mTitle,position);
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    private void showCategory(ViewHolder viewHolder) {
        if (!isVisibleOf(viewHolder.mCategory)) {
            viewHolder.mCategory.setVisibility(View.VISIBLE);
        }
    }
    private void hideCategory(ViewHolder viewHolder){
        if (isVisibleOf(viewHolder.mCategory)) {
            viewHolder.mCategory.setVisibility(View.GONE);
        }
    }

    public boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}
