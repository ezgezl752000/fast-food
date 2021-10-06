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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import Data.Food;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    List<Food> foodList;
    Context _context;
    public AdapterCart(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this._context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterCart.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_recyclerview_cart,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvQuantity.setText(food.getQuantity());
        holder.tvPrice.setText(food.getPrice());
        Picasso.with(this._context).load(food.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvQuantity,tvPrice;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodNameCart);
            tvQuantity = itemView.findViewById(R.id.tvQuantityCart);
            tvPrice = itemView.findViewById(R.id.tvPriceCart);
            imageView = itemView.findViewById(R.id.imgRyclerViewCart);
        }

    }
}
