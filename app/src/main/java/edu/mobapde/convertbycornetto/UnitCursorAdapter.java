package edu.mobapde.convertbycornetto;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ngoc on 3/2/2016.
 */
public class UnitCursorAdapter extends CursorRecyclerViewAdapter<UnitCursorAdapter.UnitViewHolder>{

    private OnItemClickListener mOnItemClickListener;
    View lastSelectedView = null;
    int lastSelectedPosition = -1;

    public UnitCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    public void setmOnItemClickListener(OnItemClickListener m){
        this.mOnItemClickListener = m;
    }

    @Override
    public UnitViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_item, viewGroup, false);
        return new UnitViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return getCursor().getCount();
    }

    @Override
    public void onBindViewHolder(UnitViewHolder viewHolder, Cursor cursor) {
        viewHolder.tvUnit.setText(cursor.getString(cursor.getColumnIndex(Unit.COLUMN_UNITNAME)));
        viewHolder.container.setTag(R.id.db_id, cursor.getInt(cursor.getColumnIndex(Unit.COLUMN_ID)));
        viewHolder.container.setTag(R.id.adapter_position, viewHolder.getAdapterPosition());
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(Integer.parseInt(v.getTag(R.id.db_id).toString()));
                if(v!=lastSelectedView) {
                    if (lastSelectedView != null) {
                        lastSelectedView.setBackgroundColor(Color.parseColor("#ffffff"));
                        notifyItemChanged(lastSelectedPosition);
                    }
                    v.setBackgroundColor(Color.parseColor("#ff89e85d"));
                    lastSelectedView = v;
                    lastSelectedPosition = Integer.parseInt(v.getTag(R.id.adapter_position).toString());
                    // notifyItemChanged(lastSelectedPosition); // now the new one
                }
            }
        });
        viewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int i = 0;
                mOnItemClickListener.onItemLongClick(Integer.parseInt(v.getTag(R.id.db_id).toString()));
                return false;
            }
        });
    }

    public class UnitViewHolder extends RecyclerView.ViewHolder{
        TextView tvUnit;
        View container;

        public UnitViewHolder(View itemView) {
            super(itemView);
            tvUnit = (TextView) itemView.findViewById(R.id.tv_unit);
            container = itemView.findViewById(R.id.container);
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(int id);
        public void onItemLongClick(int id);
    }

}
