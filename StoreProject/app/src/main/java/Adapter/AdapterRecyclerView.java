package Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Data.Food;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {

    List<Food> foodList;
    List<String> mKeys;
    Context _context;
    private IProductSelectionListener iproductSelectionListener;


    public AdapterRecyclerView(List<Food> foodList, Context context, IProductSelectionListener productSelectionListener, List<String> mKeys) {
        this.foodList = foodList;
        this._context = context;
        this.iproductSelectionListener = productSelectionListener;
        this.mKeys = mKeys;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_recyclerview,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,iproductSelectionListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.ViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.key = mKeys.get(position);
//        this.key = mKeys.get(position);
        holder.tvFood.setText(food.getName());
        Picasso.with(this._context).load(food.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView tvFood;
        IProductSelectionListener selectionListener;
        ImageView imageView;
        String key;
        public ViewHolder(View itemView, IProductSelectionListener selectionListener) {
            super(itemView);
            tvFood = itemView.findViewById(R.id.tvRyclerViewCombo);
            imageView = itemView.findViewById(R.id.imgRyclerViewCombo);
            this.selectionListener = selectionListener;
            this.key = key;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            selectionListener.onProductSelected(getAdapterPosition(), foodList.get(getAdapterPosition()));
        }
    }

    public interface IProductSelectionListener {
        void onProductSelected(int position,Food food);
    }
}
