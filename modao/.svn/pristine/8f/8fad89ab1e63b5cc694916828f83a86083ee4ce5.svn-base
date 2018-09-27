package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.bean.CompanyBean;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.TouchTextView;

public class CompanySelectAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private String mCompanyName;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private int mFootviewid = -1;

    private static final int HEAD = 0;

    private static final int NORMAL = 1;

    private static final int FOOT = 2;

    private List<CompanyBean> mDatas = new ArrayList<>();
    private ItemClickListener mItemClickListener;

    public CompanySelectAdapter(Context mContext, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            View view = mInflater.inflate(R.layout.item_company_select, parent, false);
            return new ViewHolderSelect(view);
        } else if (viewType == FOOT) {
            View view = mInflater.inflate(mFootviewid, parent, false);
            return new ViewHolerFoot(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderSelect) {
            ViewHolderSelect viewHolderSelect = (ViewHolderSelect) holder;
            viewHolderSelect.mTvCompanyName.setText(mDatas.get(position).getCompanyname());

            CompanyBean item = mDatas.get(position);
            if (position == 0) {
                viewHolderSelect.mTvFirstChar.setVisibility(View.VISIBLE);
                viewHolderSelect.mTvFirstChar.setText("" + item.getFirstChar());
            } else {
                //如果和上一个item的首字母不同，则认为是新分类的开始
                CompanyBean prevData = mDatas.get(position - 1);
                if (item.getFirstChar() != prevData.getFirstChar()) {
                    viewHolderSelect.mTvFirstChar.setVisibility(View.VISIBLE);
                    viewHolderSelect.mTvFirstChar.setText("" + item.getFirstChar());
                } else {
                    viewHolderSelect.mTvFirstChar.setVisibility(View.GONE);
                }
            }
        }

        if (holder instanceof ViewHolerFoot) {
            ViewHolerFoot holerFoot = (ViewHolerFoot) holder;
            holerFoot.mTvCompanyCount.setText("共计" + mDatas.size() + "个公司");
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + getFootViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mDatas.size()) {
            return FOOT;
        } else {
            return NORMAL;
        }
    }

    public void addFootView(@LayoutRes int layoutid) {
        this.mFootviewid = layoutid;
    }

    public int getFootViewCount() {

        return mFootviewid == -1 ? 0 : 1;

    }

    public void addData(List<CompanyBean> list) {
        if (this.mLayoutManager == null) {
            this.mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        }
        if (list != null && list.size() != 0) {
            int startposition = mDatas.size();
            mDatas.addAll(list);
            Collections.sort(mDatas);
            notifyItemRangeInserted(startposition, mDatas.size());
        }
    }

    public void clear() {
        int size = mDatas.size();
        if (size != 0) {
            mDatas.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    public void refreshData(List list) {
        if (list != null && list.size() != 0) {
            clear();
            addData(list);
        }
    }

    public void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            mRecyclerView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            mRecyclerView.smoothScrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的

        }
    }

    public int getPostition(int sectionIndex) {
        for (int i = 0; i < getItemCount() - getFootViewCount(); i++) {
            char firstChar = mDatas.get(i).getFirstChar();
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;

    }

    class ViewHolderSelect extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_company_name)
        TouchTextView mTvCompanyName;
        @BindView(R.id.tv_first_chart)
        TextView mTvFirstChar;

        public ViewHolderSelect(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_company_name)
        public void onClick() {
            if (null != mItemClickListener) {
                int position = getLayoutPosition();
                SpUtil.saveCompanyId(mDatas.get(position).getCompanyid());
                SpUtil.saveCompanyName(mDatas.get(position).getCompanyname());
                mItemClickListener.onItemClick(mDatas.get(getLayoutPosition()).getCompanyname(), mDatas.get(getLayoutPosition()).getCompanyid(), getLayoutPosition());
            }
        }
    }

    class ViewHolerFoot extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_companycount)
        TextView mTvCompanyCount;

        public ViewHolerFoot(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickListener {
        void onItemClick(String companyName, String companyId, int position);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }
}
