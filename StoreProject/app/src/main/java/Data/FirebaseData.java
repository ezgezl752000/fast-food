package Data;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FirebaseData {
    FirebaseDatabase database ;
    DatabaseReference databaseReference;
    List<Food> listFood= new ArrayList<>();

    public FirebaseData(String path) {
       database = FirebaseDatabase.getInstance();
       databaseReference = database.getReference(path);
    }

    public interface DataStatus{
        void DataIsLoaded(List<Food> foods, List<String> keys);
        void DataIsInserte();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public void readFoods(final  DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listFood.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()){
                    String name = keyNode.child("name").getValue().toString();
                    String price = keyNode.child("price").getValue().toString();
                    String detail = keyNode.child("detail").getValue().toString();
                    String url = keyNode.child("url").getValue().toString();
                    String rating = keyNode.child("rating").getValue().toString();
                    Food food = new Food(name,price,detail,url,rating);
                    keys.add(keyNode.getKey());
                    listFood.add(food);
                }
                dataStatus.DataIsLoaded(listFood,keys);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
