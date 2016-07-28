package cn.carbs.android.expandabletextview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;

/**
 * Created by carbs on 2016/7/23.
 */
public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    private TextView mTVComparison;
    private Button mBtnUpdateText;
    private Button mBtnToListView;

    private ExpandableTextView mETV;
    private CharSequence[] mPoems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPoems = getResources().getStringArray(R.array.poems);

        mTVComparison = (TextView)this.findViewById(R.id.tv_comparison);
        mBtnUpdateText = (Button)this.findViewById(R.id.button_update_text);
        mBtnToListView = (Button)this.findViewById(R.id.button_to_list_view);
        mETV = (ExpandableTextView)this.findViewById(R.id.etv);

        mBtnUpdateText.setOnClickListener(this);
        mBtnToListView.setOnClickListener(this);

        // 测试添加OnClickListener的情况，功能正常。添加外部的onClick事件后，原来的点击toggle功能自动屏蔽，
        // 点击尾部的ClickableSpan仍然有效
        /*mETV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (mETV.getExpandState()){
                    case ExpandableTextView.STATE_SHRINK:
                        Toast.makeText(getApplicationContext(),"ExpandableTextView clicked, STATE_SHRINK",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case ExpandableTextView.STATE_EXPAND:
                        Toast.makeText(getApplicationContext(),"ExpandableTextView clicked, STATE_EXPAND",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });*/
//        mETV.setText(mPoems[0]);//在ExpandableTextView在创建完成之前改变文字，功能正常
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_to_list_view:
                gotoCheckInListView();
                break;
            case R.id.button_update_text:
                updateText();
                break;
        }
    }

    private void gotoCheckInListView(){
        Intent intent = new Intent(ActivityMain.this, ActivityListView.class);
        startActivity(intent);
    }

    private Random mRandom = new Random();
    private int prevRandomInt = -1;
    private int currRandomInt = -1;

    private void updateText(){
        currRandomInt = mRandom.nextInt(mPoems.length);
        while (prevRandomInt == currRandomInt){
            currRandomInt = mRandom.nextInt(mPoems.length);
        }
        prevRandomInt = currRandomInt;
        CharSequence newCS = mPoems[currRandomInt];

        mTVComparison.setText(newCS);//作为对比示例
        mETV.setText(newCS);//效果显示
    }
}