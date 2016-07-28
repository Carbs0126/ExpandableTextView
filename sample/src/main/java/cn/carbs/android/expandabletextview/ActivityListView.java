package cn.carbs.android.expandabletextview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;

/**
 * Created by carbs on 2016/7/23.
 */
public class ActivityListView extends AppCompatActivity implements View.OnClickListener{

    private ListView mListView;
    private TheBaseAdapter mAdapter;
    private Button mButton;

    private List<String> mStrings = new ArrayList<>();
    private boolean mFlag = true;
    private CharSequence[] mPoems;
    private CharSequence[] mProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mListView = (ListView)this.findViewById(R.id.listview);
        mButton = (Button)this.findViewById(R.id.button_update_list);

        mPoems = getResources().getStringArray(R.array.poems_2);
        mProses = getResources().getStringArray(R.array.prose);

        mAdapter = new TheBaseAdapter(this, mStrings);
        mListView.setAdapter(mAdapter);
        mButton.setOnClickListener(this);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                inflateListViews();
            }
        }, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_update_list:
                inflateListViews();
                break;
        }
    }

    private void inflateListViews(){
        mListView.smoothScrollByOffset(0);
        mStrings.clear();
        if(mFlag){
            for(CharSequence cs : mPoems){
                mStrings.add(cs.toString());
            }
        }else{
            for(CharSequence cs : mProses){
                mStrings.add(cs.toString());
            }
        }
        mAdapter.notifyDataSetChanged();
        if(mAdapter.getCount() > 0) {
            mListView.setSelection(0);
        }
        mFlag = !mFlag;
    }

    class TheBaseAdapter extends BaseAdapter implements ExpandableTextView.OnExpandListener{

        private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();
        private List<String> mList;
        private LayoutInflater inflater;

        public TheBaseAdapter(Context context, List<String> list){
            mList = list;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
        private int etvWidth;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (null == convertView){
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item, parent, false);
                viewHolder.etv = (ExpandableTextView) convertView.findViewById(R.id.etv);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String content = (String)getItem(position);
            if(etvWidth == 0){
                viewHolder.etv.post(new Runnable() {
                    @Override
                    public void run() {
                        etvWidth = viewHolder.etv.getWidth();
                    }
                });
            }
            viewHolder.etv.setTag(position);
            viewHolder.etv.setExpandListener(this);
            Integer state = mPositionsAndStates.get(position);

            viewHolder.etv.updateForRecyclerView(content.toString(), etvWidth, state== null ? 0 : state);//第一次getview时肯定为etvWidth为0

            return convertView;
        }

        @Override
        public void onExpand(ExpandableTextView view) {
            Object obj = view.getTag();
            if(obj != null && obj instanceof Integer){
                mPositionsAndStates.put((Integer)obj, view.getExpandState());
            }
        }

        @Override
        public void onShrink(ExpandableTextView view) {
            Object obj = view.getTag();
            if(obj != null && obj instanceof Integer){
                mPositionsAndStates.put((Integer)obj, view.getExpandState());
            }
        }
    }

    static class ViewHolder{
        ExpandableTextView etv;
    }
}
