package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.example.storeproject.R;
import com.example.storeproject.databinding.ActivityAddProductBinding;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Data.Food;

public class AddProduct extends AppCompatActivity implements Serializable {

    ActivityAddProductBinding binding;
    int quantity, price, money;
    String name, detail, rating, url;
    Food food;
    List<Food> listOrderFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);
        init();
        getView();
        getButton();
    }

    private void init() {
        listOrderFood = new ArrayList<>();
        quantity = 1;
        name = getIntent().getStringExtra("name");
        price = Integer.parseInt(getIntent().getStringExtra("price"));
        detail = getIntent().getStringExtra("detail");
        rating = getIntent().getStringExtra("rating");
        url = getIntent().getStringExtra("url");
    }

    private void getView() {
        binding.detailQuantity.setText(quantity + "");
        binding.detailFoodName.setText(name);
        binding.detailPrice.setText(price + "");
        binding.detailDetail.setText(detail);
        Picasso.with(getBaseContext()).load(url).into(binding.detailImage);
    }

    private void getButton() {
        onAdd();
        onSub();
        onAddToCart();
    }

    private void onAddToCart() {
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food food = new Food(name, money + "", detail, url, rating, quantity + "");
                Intent intent = new Intent(getBaseContext(), HomePage.class);
//        intent.putExtra("name", food.getName());
//        intent.putExtra("price", food.getPrice());
//        intent.putExtra("detail", food.getDetail());
//        intent.putExtra("url", food.getUrl());
//        intent.putExtra("rating",food.getRating());
//        intent.putExtra("quantity",food.getQuantity());

//        Bundle bundle = new Bundle();
//        bundle.putParcelable("food", (Parcelable) food);
//        intent.putExtras(bundle);
//                listOrderFood.add(food);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("orderList", (ArrayList<? extends Parcelable>) listOrderFood);
//                intent.putExtras(bundle);
                HomePage.listOrderFood.add(food);
                startActivity(intent);
            }
        });
    }

    private void onSub() {
        binding.btnDetailSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity <= 1) return;
                quantity--;
                money = quantity * price;
                binding.detailPrice.setText(money + "");
                binding.detailQuantity.setText(quantity + "");
            }
        });
    }

    private void onAdd() {
        binding.btnDetailAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                money = quantity * price;
                binding.detailPrice.setText(money + "");
                binding.detailQuantity.setText(quantity + "");
            }
        });
    }
}