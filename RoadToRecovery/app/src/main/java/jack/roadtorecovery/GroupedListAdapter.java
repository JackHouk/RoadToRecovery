package jack.roadtorecovery;

import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Base code from http://javatechig.com/android/listview-with-section-header-in-android
 * Created by Jack on 5/21/2016.
 */
class GroupedListAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public GroupedListAdapter(Context context) {
        addSectionHeaderItem("Group: Misc", 0);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //TODO: Check for "Group: " at beginning of item vales and remove it to avoid conflict with group names
    public void addItem(final String item, String sectionHeader) {

        if(sectionHeader.compareTo("") == 0)
            sectionHeader = "Group: Misc";
        else
            sectionHeader = "Group: " + sectionHeader;
        String toCompare;
        for (int i = 0; i < mData.size(); i++) {
            toCompare = mData.get(i);
            if (toCompare.compareTo(sectionHeader) == 0) {
                mData.add(i + 1, item);
                notifyDataSetChanged();
                return;
            }
        }
        for (int i = 0; i < mData.size(); i++) {
            toCompare = "Group: Misc";
            if (toCompare.compareTo(mData.get(i)) == 0) {
                addSectionHeaderItem(sectionHeader, i);
                mData.add(i+1, item);
                return;
            }
        }
    }

    public void addSectionHeaderItem(final String item, int location) {
        mData.add(location, item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        String groupLabel = "Group: ";
        if(mData.get(position).length() > 6 && groupLabel.compareTo(mData.get(position).substring(0, 7)) == 0)
                return TYPE_SEPARATOR;
        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.network_fragment_contact_entry, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.contact);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.grouped_list_section, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.contactHeader);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

}