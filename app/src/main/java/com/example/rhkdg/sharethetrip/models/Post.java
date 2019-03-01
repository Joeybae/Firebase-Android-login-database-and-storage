package com.example.rhkdg.sharethetrip.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    // basic
    public String uid;
    public String author;
    public String title;
    public String body;

    //edittext
    public String nation;
    public String date;
    public String price;
    public String address;

    //text
    //public String textnation;
    //public String textdate;
    public String textprice;
    //public String textaddress;
    //image
    public String IMAGE;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String nation, String date, String textprice, String price, String address,String IMAGE) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;

        //edittext
        this.nation = nation;
        this.date = date;
        this.price = price;
        this.address = address;

        //text
        //this.textnation = textnation;
        //this.textdate = textdate;
        this.textprice = textprice;
        //this.textaddress = textaddress;

        this.IMAGE = IMAGE;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //basic
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);

        //edittext
        result.put("nation", nation);
        result.put("date", date);
        result.put("price", price);
        result.put("address", address);

        //star
        result.put("starCount", starCount);
        result.put("stars", stars);

        //text
        //result.put("textnation", textnation);
        //result.put("textdate", textdate);
        result.put("textprice", textprice);
        //result.put("textaddress", textaddress);

        //IMAGE
        result.put("IMAGE", IMAGE);

        return result;
    }

   /* public String getIMAGE() {
        return IMAGE;
    }*/

    /*public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }*/

    // [END post_to_map]

}
// [END post_class]
