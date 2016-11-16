package com.example.administrator.exceptablelistview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private List<String> group_list;
    private List<String> item_list1;
    private List<String> item_list2;
    private List<String> item_list3;
    private List<List<String>> item_listAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //随便一堆测试数据  父条目
        group_list = new ArrayList<String>();
        group_list.add("支付问题");
        group_list.add("订单问题");
        group_list.add("物流问题");
        //子条目1
        item_list1 = new ArrayList<String>();
        item_list1.add("钱不够");
        item_list1.add("密码错了");
        item_list1.add("账号呢");
        //子条目2
        item_list2 = new ArrayList<String>();
        item_list2.add("编号错了");
        //子条目3
        item_list3 = new ArrayList<String>();
        item_list3.add("在路上");
        item_list3.add("快到了");
        item_listAll = new ArrayList<List<String>>();
        item_listAll.add(item_list1);
        item_listAll.add(item_list2);
        item_listAll.add(item_list3);
        expandableListView = (ExpandableListView) findViewById(R.id.expendlist);
        expandableListView.setAdapter(new MyExpandableListViewAdapter(this));
    }
    //用过ListView的人一定很熟悉，只不过这里是BaseExpandableListAdapter
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public MyExpandableListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return group_list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            //子条目的个数
            return item_listAll.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group_list.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return item_listAll.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().from(context).inflate(
                        R.layout.expendlist_group, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
                groupHolder.arrow = (ImageView)convertView.findViewById(R.id.arrow);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.txt.setText(group_list.get(groupPosition));
            //判断isExpanded就可以控制是按下还是关闭，同时更换图片
            if(isExpanded){
                groupHolder.arrow.setBackgroundResource(R.drawable.my_setting_help_up);
            }else{
                groupHolder.arrow.setBackgroundResource(R.drawable.item_my_setting_help_down);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().from(context).inflate(
                        R.layout.expendlist_item, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView) convertView.findViewById(R.id.txt);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.txt.setText(item_listAll.get(groupPosition).get(childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    class GroupHolder {
        public TextView txt;
        public ImageView arrow;
    }

    class ItemHolder {
        public TextView txt;
    }
}
