package Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Food implements Parcelable {
    String name;
    String price;
    String detail;
    String url;
    String rating;
    String quantity;

    protected Food(Parcel in) {
        name = in.readString();
        price = in.readString();
        detail = in.readString();
        url = in.readString();
        rating = in.readString();
        quantity = in.readString();
    }

    public Food(String name, String price, String detail, String url, String rating, String quantity) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.url = url;
        this.rating = rating;
        this.quantity = quantity;
    }

    public Food( ) {
    }


    public Food(String name, String price, String detail, String url, String rating) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.url = url;
        this.rating = rating;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        parcel.writeString(getPrice());
        parcel.writeString(getDetail());
        parcel.writeString(getUrl());
        parcel.writeString(getRating());
        parcel.writeString(getQuantity());
    }
}
