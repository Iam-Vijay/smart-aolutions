package in.smartglobalsolutions.mygenerator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.smartglobalsolutions.mygenerator.Pojo.RecylerPasser;


public class RecylerAdapter  extends RecyclerView.Adapter<RecylerAdapter.MyViewHolder> {

    private int selectedPosition = -1;
    private List<RecylerPasser> moviesList;
    Context context;
    ArrayList<String> colorarray=new ArrayList<>();
    Random randomizer;
    public RecylerAdapter(Context context) {
        this.moviesList = new ArrayList<>();
        this.context = context;
        colorarray.add("#F49678");
        colorarray.add("#9A9AE6");
        colorarray.add("#28C76F");
        colorarray.add("#EA5455");
        colorarray.add("#FF9F43");
        colorarray.add("#F891DB");

        randomizer = new Random();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView stockname;
        TextView stockbal;
        CardView cardView;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            stockname = view.findViewById(R.id.label);
            cardView = view.findViewById(R.id.cardview);
            imageView=view.findViewById(R.id.centerimg);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylerview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RecylerPasser movie = moviesList.get(position);
        holder.stockname.setText(movie.getName());
        holder.stockname.setTextColor(Color.parseColor("#FFFFFF"));
        holder.cardView.setCardBackgroundColor(Color.parseColor(colorarray.get(randomizer.nextInt(colorarray.size()))));
        if (movie.isIsimage()){
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load("https://www.chitsoft.com/wapp/api/mobile/image/"+movie.getImage()).into(holder.imageView);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }



    }

    public void setdata(List<RecylerPasser> data) {
        this.moviesList.clear();
        this.moviesList.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}