package pl.jakubor.filmprojekt;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PremieryFragment extends Fragment implements PremieryObjectsAdapter.PremieraObjectClickedListner {


    public static final String OBJECTS_KEY = "objects";
    @BindView(R.id.objectRecyclerView)
    RecyclerView objectRecycleView;

Toolbar toolbar;
    public PremieryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_premiery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        objectRecycleView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        MovieObject[] objects = (MovieObject[]) getArguments().getSerializable(OBJECTS_KEY);
        PremieryObjectsAdapter adapter = new PremieryObjectsAdapter(objects);
        adapter.setPremieraObjectClickedListner(this);
        objectRecycleView.setAdapter(adapter);

    }

    public static PremieryFragment newInstance(MovieObject[] objects) {

        Bundle args = new Bundle();
        args.putSerializable(OBJECTS_KEY, objects);
        PremieryFragment fragment = new PremieryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void premieryObjectClicked(MovieObject movieObject) {
        Log.d(PremieryFragment.class.getSimpleName(),movieObject.getTitleEng());
        MovieObjectActivity.start(getActivity(),movieObject);
    }
}
