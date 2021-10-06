package Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.storeproject.R;
import com.example.storeproject.databinding.ActivityCart2Binding;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterCart;
import Data.Food;

public class CartActivity extends AppCompatActivity {

    List<Food> foodList;
    ActivityCart2Binding binding;
    AdapterCart adapterCart;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart2);
        init();
        getButton();
    }

    private void getButton() {
        onBack();
        onOrder();
    }

    private void onOrder() {
        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomePage.listOrderFood.clear();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setMessage("Successed!!!")
                            .setCancelable(false)
                            .setPositiveButton("Back to home page", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getBaseContext(),HomePage.class));
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

            }
        });
    }

    private void onBack() {
        binding.btnCartBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),HomePage.class));
            }
        });
    }

    private  void init(){
        foodList =  new ArrayList<Food>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        foodList = bundle.getParcelableArrayList("orderList1");
        adapterCart = new AdapterCart(foodList,getBaseContext());
        recyclerView = binding.lvRecyclerViewCart;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( CartActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCart);
        getPrice();
    }

    private void getPrice() {
        int price = 0;
        if(foodList != null){
            for (Food food: foodList) {
                price += Integer.parseInt(food.getPrice());
            }
        }
        binding.totalPrice.setText(price +"");
    }


}