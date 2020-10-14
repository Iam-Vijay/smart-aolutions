package in.smartglobalsolutions.mygenerator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.smartglobalsolutions.mygenerator.Pojo.SeachPasser;


public class CustomSeachAdapter extends RecyclerView.Adapter<CustomSeachAdapter.MyViewHolder> implements Filterable {

    private ArrayList<SeachPasser> moviesList;
    private RecyclerViewClickListener itemListener;
        ArrayList<SeachPasser> orig;
    public CustomSeachAdapter(RecyclerViewClickListener itemListener) {
        this.moviesList = new ArrayList<>();
        this.itemListener=itemListener;
    }

    public CustomSeachAdapter() {
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SeachPasser> results = new ArrayList<SeachPasser>();
                if (orig == null)
                    orig = moviesList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final SeachPasser g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                moviesList = (ArrayList<SeachPasser>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
            int posti;
            int orginalpos;
        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.searchtext);

        }




    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SeachPasser movie = moviesList.get(position);
        holder.title.setText(movie.getName());
        holder.posti=movie.getId();
        holder.orginalpos=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.recyclerViewListClicked(view, movie.getId(),holder.title.getText().toString(), String.valueOf(position),movie.getAddress());
            }
        });

    }
    public void setdata(ArrayList<SeachPasser> arrayList){
        this.moviesList.clear();
        this.moviesList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}

