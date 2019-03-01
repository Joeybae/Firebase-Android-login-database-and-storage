package com.example.rhkdg.sharethetrip.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhkdg.sharethetrip.R;
import com.example.rhkdg.sharethetrip.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    //public TextView bodyView;

    //edittext
    public TextView nationView;
    public TextView dateView;
    public TextView priceView;
    public TextView addressView;

    //text
    //public TextView nationtextView;
    //public TextView datetextView;
    public TextView pricetextView;
    //public TextView addresstextView;

    //image
    View mView;
    FirebaseAuth mAuth;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.postTitle);
        authorView = itemView.findViewById(R.id.postAuthor);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.postNumStars);
        //bodyView = itemView.findViewById(R.id.postBody);

        //edittext
        nationView = itemView.findViewById(R.id.postNation);
        dateView = itemView.findViewById(R.id.postDate);
        priceView = itemView.findViewById(R.id.postPrice);
        addressView = itemView.findViewById(R.id.postAddress);

        //text
        //nationtextView = itemView.findViewById(R.id.textNation);
        //datetextView = itemView.findViewById(R.id.textDate);
        pricetextView = itemView.findViewById(R.id.textPrice);
        //addresstextView = itemView.findViewById(R.id.textAddress);

        mView = itemView;
        mAuth = FirebaseAuth.getInstance();
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        //bodyView.setText(post.body);

        //edittext
        nationView.setText(post.nation);
        dateView.setText(post.date);
        priceView.setText(post.price);
        addressView.setText(post.address);

        //text
        //nationtextView.setText(post.textnation);
        //datetextView.setText(post.textdate);
        pricetextView.setText(post.textprice);
        //addresstextView.setText(post.textaddress);

        starView.setOnClickListener(starClickListener);
    }

    public void setImage(final Context ctx, final String IMAGE) {
        final ImageView post_image = mView.findViewById(R.id.postImage);
        Picasso.with(ctx)
                .load(IMAGE)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(post_image, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        //if error occured try again by getting the image from online
                        Picasso.with(ctx)
                                .load(IMAGE)
                                .error(R.drawable.ic_toggle_star_24)
                                .into(post_image, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError() {
                                        Toast.makeText(ctx, "failed to load image !", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }
}
