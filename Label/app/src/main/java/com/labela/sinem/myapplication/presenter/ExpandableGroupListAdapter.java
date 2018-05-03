package com.labela.sinem.myapplication.presenter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.labela.sinem.myapplication.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by esineka on 25.04.2018.
 */

public class ExpandableGroupListAdapter implements android.widget.ExpandableListAdapter {

    private List<String> groupList;
    private HashMap<String, List<String>> childListMap = null;
    private FragmentActivity context;

    public ExpandableGroupListAdapter(FragmentActivity context, List<String> groupList, HashMap<String, List<String>> childListMap) {
        this.context = context;
        this.groupList = groupList;
        this.childListMap = childListMap;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        int size = 0;
        if (childListMap != null) {
            if (this.childListMap.get(this.groupList.get(groupPosition)) != null) {
                size = this.childListMap.get(this.groupList.get(groupPosition)).size();
            }
        }
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return
                this.childListMap.get(this.groupList.get(groupPosition))
                        .get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // Create the group view object.
        LinearLayout groupLayoutView = new LinearLayout(context);
        groupLayoutView.setOrientation(LinearLayout.HORIZONTAL);

        // Create and add a textview in returned group view.
        String groupText = groupList.get(groupPosition);
        TextView groupTextView = new TextView(context);
        groupTextView.setText(groupText);
        groupTextView.setTextSize(30);
        groupLayoutView.addView(groupTextView);

        return groupLayoutView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.lblListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
