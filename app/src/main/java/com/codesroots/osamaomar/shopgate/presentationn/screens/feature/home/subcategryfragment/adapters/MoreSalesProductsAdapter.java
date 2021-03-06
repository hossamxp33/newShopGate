package com.codesroots.osamaomar.shopgate.presentationn.screens.feature.home.subcategryfragment.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.shopgate.R;
import com.codesroots.osamaomar.shopgate.entities.SubCategriesWithProducts;
import com.codesroots.osamaomar.shopgate.helper.PreferenceHelper;
import com.codesroots.osamaomar.shopgate.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment;
import com.codesroots.osamaomar.shopgate.presentationn.screens.feature.home.subcategryfragment.SubCatesViewModel;
import com.codesroots.osamaomar.shopgate.presentationn.screens.feature.rate.RateActivity;
import java.util.List;
import static com.codesroots.osamaomar.shopgate.entities.names.PRODUCT_ID;

public class MoreSalesProductsAdapter extends RecyclerView.Adapter<MoreSalesProductsAdapter.ViewHolder>  {

    private Context context;
    private  List<SubCategriesWithProducts.ProductsbyrateBean> products;
    SubCatesViewModel viewModel;
    int userid = PreferenceHelper.getUserId();
    boolean [] favorite;

    public MoreSalesProductsAdapter(Context mcontext, List<SubCategriesWithProducts.ProductsbyrateBean> productsbyrate,
                                    SubCatesViewModel mViewModel) {
        context = mcontext;
        products = productsbyrate;
        viewModel = mViewModel;
        favorite = new boolean[products.size()];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizental_product_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {

     //   try {
        holder.price.setText(products.get(position).getStart_price()+" "+context.getText(R.string.realcoin));
        if (products.get(position).getProduct()!=null)
        {
            holder.name.setText(products.get(position).getProduct().getName());

            if (products.get(position).getProduct().getProductphotos()!=null) {
                if (products.get(position).getProduct().getProductphotos().size() > 0)
                    Glide.with(context).load(products.get(position).getProduct().getImg())
                            .dontAnimate().placeholder(R.drawable.product).into(holder.Image);
            }
        }
            if (userid>0) {
                if (products.get(position).getProduct().getFavourites().size() > 0) {
                    holder.favorite.setImageResource(R.drawable.favoried);
                    favorite[position]=true;
                }
                else {
                    holder.favorite.setImageResource(R.drawable.like);
                    favorite[position] = false;
                }
            }

            holder.favorite.setOnClickListener(v -> {
                if (userid>0) {
                    if (!favorite[position]) {
                        viewModel.AddToFav(products.get(position).getProduct().getId());
                        viewModel.current_item = position;
                    }
                    ////////////// delete here
                    else {
                        viewModel.DeleteFav(userid,products.get(position).getProduct_id());
                        viewModel.current_item = position;
                    }
                }
                else
                    Snackbar.make(v,context.getText(R.string.loginfirst),Snackbar.LENGTH_LONG).show();
            });
     //   }

//        catch (Exception e)
//        {}



        Fragment fragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle() ;

        bundle.putInt(PRODUCT_ID,products.get(position).getProduct_id());
        fragment.setArguments(bundle);
        holder.mView.setOnClickListener(v -> ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().
                replace(R.id.mainfram,fragment)
                .addToBackStack(null).commit());
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image,favorite;
        private TextView name,price,quntity,ratecount;
        private RatingBar ratingBar;

        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.item_name);
            price = mView.findViewById(R.id.item_price);
            quntity = mView.findViewById(R.id.quentity);
            ratecount = mView.findViewById(R.id.rate_count);
            ratingBar = mView.findViewById(R.id.rates);
            favorite = mView.findViewById(R.id.favorite);
        }
    }
}