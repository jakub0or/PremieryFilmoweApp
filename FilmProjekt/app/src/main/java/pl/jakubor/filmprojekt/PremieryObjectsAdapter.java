package pl.jakubor.filmprojekt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kuba on 03.06.2016.
 */

public class PremieryObjectsAdapter extends RecyclerView.Adapter<PremieryObjectsAdapter.PremieryObjectsViewHolder> {

    private final MovieObject[] movieObjects;
    @BindView(R.id.itemImageView)
    ImageView itemImageView;
    @BindView(R.id.itemTextView)
    TextView itemTextView;

    private PremieraObjectClickedListner premieraObjectClickedListner;

    public void setPremieraObjectClickedListner(PremieraObjectClickedListner premieraObjectClickedListner) {
        this.premieraObjectClickedListner = premieraObjectClickedListner;
    }

    public PremieryObjectsAdapter(MovieObject[] movieObjects) {
        this.movieObjects = movieObjects;
    }

    @Override
    public PremieryObjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_premiery_object, parent, false);
        return new PremieryObjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PremieryObjectsViewHolder holder, int position) {
        //wyciaganie elementu który chcemy wyswietlac
        MovieObject movieObject = movieObjects[position];
        holder.setSolarObject(movieObject); //ustawienie na view holderze obektu by wiedział co ma wyswietlic

    }

    @Override
    public int getItemCount() {
        return movieObjects.length;
    }

    private void itemClicked(MovieObject movieObject) {
        if (premieraObjectClickedListner != null) {
            premieraObjectClickedListner.premieryObjectClicked(movieObject);
        }
    }

    class PremieryObjectsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemImageView)
        ImageView itemImageView;
        @BindView(R.id.itemTextView)
        TextView itemTextView;

        public MovieObject getSolarObject() {
            return movieObject;
        }

        private MovieObject movieObject;

        public PremieryObjectsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setSolarObject(MovieObject movieObject) {
            this.movieObject = movieObject;
            itemTextView.setText(movieObject.getTitlePL());
            Glide.with(itemImageView.getContext())
                    .load(movieObject.getPosterUrl())
                    .placeholder(R.mipmap.placeholder)
                    .into(itemImageView)
            ;
        }

        @Override
        public void onClick(View v) {
            itemClicked(movieObject);
        }
    }

    public interface PremieraObjectClickedListner {
        void premieryObjectClicked(MovieObject movieObject);
    }
}


