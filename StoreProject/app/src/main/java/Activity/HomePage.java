package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.storeproject.R;
import com.example.storeproject.databinding.ActivityHomePageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterRecyclerView;
import Adapter.AdapterRecyclerView2;
import Adapter.AdapterRecyclerViewSpecialCombo;
import Data.FirebaseData;
import Data.Food;
import Data.User;


import static com.example.storeproject.R.layout.*;

public class HomePage extends AppCompatActivity implements AdapterRecyclerView2.IProductSelectionListener, NavigationView.OnNavigationItemSelectedListener {
    ActivityHomePageBinding binding;
    List<Food> foodList;
    RecyclerView recyclerView, recyclerView2, recyclerViewDrink, recyclerViewCombo;
    AdapterRecyclerView adapterRecyclerView;
    AdapterRecyclerView2 adapterRecyclerView2, adapterRecyclerViewDrink;
    AdapterRecyclerViewSpecialCombo adapterRecyclerViewSpecialCombo;
    public static ArrayList<Food> listOrderFood;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String currentUserId;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, activity_home_page);
        init();
        getProfile();
        initRecyclerView();
        onCart();
        setNavigationView();
    }

    private void getProfile() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ImageView img = findViewById(R.id.avatar);
                User user = snapshot.child("users").child(currentUserId).getValue(User.class);
                String URL = user.getUri();
                if(!URL.equals("")) Picasso.with(HomePage.this).load(URL).into(img);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void setNavigationView() {
        drawerLayout = binding.drawLayout;
        navigationView = binding.navView;
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void onCart() {
        binding.Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), CartActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("orderList1", (ArrayList<? extends Parcelable>) listOrderFood);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }

    private void init() {
        if (listOrderFood == null) listOrderFood = new ArrayList<>();
        foodList = new ArrayList<>();
        recyclerView = binding.lvRecyclerViewCombo;
        recyclerView2 = binding.lvRecyclerView2;
        recyclerViewDrink = binding.lvRecyclerDrink;
        recyclerViewCombo = binding.lvRecyclerViewSpecialCombo;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManagerCombo = new LinearLayoutManager(HomePage.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(HomePage.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerDrink = new LinearLayoutManager(HomePage.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerSpecialCombo = new LinearLayoutManager(HomePage.this, LinearLayoutManager.VERTICAL, false);
        db();
        recyclerView.setLayoutManager(linearLayoutManagerCombo);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDrink.setLayoutManager(linearLayoutManagerDrink);
        recyclerViewDrink.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCombo.setLayoutManager(linearLayoutManagerSpecialCombo);
        recyclerViewCombo.setItemAnimator(new DefaultItemAnimator());
    }

    private void db() {
        new FirebaseData("combo").readFoods(new FirebaseData.DataStatus() {
            @Override
            public void DataIsLoaded(List<Food> foods, List<String> keys) {
                foodList = foods;
                adapterRecyclerViewSpecialCombo = new AdapterRecyclerViewSpecialCombo(foods, getBaseContext(), HomePage.this::onProductSelected, keys);
                recyclerViewCombo.setAdapter(adapterRecyclerViewSpecialCombo);
            }

            @Override
            public void DataIsInserte() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        new FirebaseData("Food").readFoods(new FirebaseData.DataStatus() {
            @Override
            public void DataIsLoaded(List<Food> foods, List<String> keys) {
                foodList = foods;
                adapterRecyclerView2 = new AdapterRecyclerView2(foods, getBaseContext(), HomePage.this, keys);
                recyclerView2.setAdapter(adapterRecyclerView2);
            }

            @Override
            public void DataIsInserte() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        new FirebaseData("favorite").readFoods(new FirebaseData.DataStatus() {
            @Override
            public void DataIsLoaded(List<Food> foods, List<String> keys) {
                foodList = foods;
                adapterRecyclerView = new AdapterRecyclerView(foods, getBaseContext(), HomePage.this::onProductSelected, keys);
                recyclerView.setAdapter(adapterRecyclerView);
            }

            @Override
            public void DataIsInserte() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        new FirebaseData("drink").readFoods(new FirebaseData.DataStatus() {
            @Override
            public void DataIsLoaded(List<Food> foods, List<String> keys) {
                foodList = foods;
                adapterRecyclerViewDrink = new AdapterRecyclerView2(foods, getBaseContext(), HomePage.this, keys);
                recyclerViewDrink.setAdapter(adapterRecyclerViewDrink);
            }

            @Override
            public void DataIsInserte() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public void onProductSelected(int position, Food food) {
        Intent intent = new Intent(getBaseContext(), AddProduct.class);
        intent.putExtra("name", food.getName());
        intent.putExtra("price", food.getPrice());
        intent.putExtra("detail", food.getDetail());
        intent.putExtra("url", food.getUrl());
        intent.putExtra("rating", food.getRating());
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuHome:
                break;
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intentLogout = new Intent(this,LoginActivity.class);
                startActivity(intentLogout);
                break;
            case R.id.menuProfile:
                Intent intentProfile = new Intent(this,ChangeProfile.class);
                startActivity(intentProfile);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else super.onBackPressed();
    }

}