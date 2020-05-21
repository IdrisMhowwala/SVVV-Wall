
package com.example.idris_000.wall;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class MessageAdapter extends ArrayAdapter<Adddata> {

        private Activity activity;
        private List<Adddata> messages;
    FirebaseDatabase fd = FirebaseDatabase.getInstance();


        public MessageAdapter(Activity context, int resource, List<Adddata> objects) {
            super(context, resource, objects);
            this.activity = context;
            this.messages = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            int layoutResource = 0; // determined by view type
            Adddata Adddata = getItem(position);
            int viewType = getItemViewType(position);



            if (Adddata.getName().equalsIgnoreCase(Wallhome.n)) {
                layoutResource = R.layout.activity_chat_right;
            } else {
                layoutResource = R.layout.activity_chat_left;
            }

            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = inflater.inflate(layoutResource, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            //set message content
            holder.msg.setText(Adddata.getEnroll()+"("+Adddata.getName() + "):\n"+Adddata.getMsg());

            return convertView;
        }

//        @Override
//        public int getViewTypeCount() {
//            // return the total number of view types. this value should never change
//            // at runtime. Value 2 is returned because of left and right views.
//            return 2;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            // return a value between 0 and (getViewTypeCount - 1)
//            return position % 2;
//        }

        private class ViewHolder {
            private TextView msg;

            public ViewHolder(View v) {
                msg = (TextView) v.findViewById(R.id.txt_msg);
            }
        }
    }
