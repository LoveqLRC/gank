package rc.loveq.meizhi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.data.entity.MeiZhi;
import rc.loveq.meizhi.event.OnMeizhiTouchListener;
import rc.loveq.meizhi.widget.RatioImageView;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 19:48
 * Email:664215432@qq.com
 */

public class MeiZhiAdapter extends RecyclerView.Adapter<MeiZhiAdapter.ViewHolder> {

    private final List<MeiZhi> mList;
    private final Context mContext;
    private OnMeizhiTouchListener meizhiTouchListener;

    public MeiZhiAdapter(Context context, List<MeiZhi> list) {
        this.mList=list;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MeiZhi meiZhi = mList.get(position);
        int limit=48;
       String s= meiZhi.desc.length()>limit?meiZhi.desc.subSequence(0,limit)+"...":meiZhi.desc;
        holder.titleView.setText(s);
        holder.mMeiZhi=meiZhi;

        Glide.with(mContext)
                .load(meiZhi.url)
                .centerCrop()
                .into(holder.meizhiView)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!holder.card.isShown()) {
                            holder.card.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.meizhi)
        RatioImageView meizhiView;
        @BindView(R.id.title)
        TextView titleView;
        View card;
        MeiZhi mMeiZhi;
        public ViewHolder(View itemView) {
            super(itemView);
            card=itemView;
            ButterKnife.bind(this,itemView);
            meizhiView.setOnClickListener(this);
            card.setOnClickListener(this);
            meizhiView.setOriginalSize(50,50);
        }

        @Override
        public void onClick(View view) {
            if (meizhiTouchListener!=null){
                meizhiTouchListener.onTouch(view,meizhiView,card,mMeiZhi);
            }
        }
    }
    public void setOnMeizhiTouchListener(OnMeizhiTouchListener listener){
        this.meizhiTouchListener=listener;
    }
}
