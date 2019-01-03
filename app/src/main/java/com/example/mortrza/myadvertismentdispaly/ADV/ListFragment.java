package com.example.mortrza.myadvertismentdispaly.ADV;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mortrza.myadvertismentdispaly.R;
import com.example.mortrza.myadvertismentdispaly.dbHandler;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    dbHandler dbh;
    RecyclerView rv;
    List<Agahi> items;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rv = (RecyclerView) inflater.inflate(R.layout.list_rcycler_in_fragment,container,false);

        items = new ArrayList<>();
        setupRecycler(rv);
        return rv;
    }

    private void setupRecycler(RecyclerView recyclerView) {


        dbh = new dbHandler(getContext());
        dbh.open();


        for(int i = 1 ; i<dbh.ADV_count() ; i++){
            int m= i+1;

            if(getArguments().get("FRG").equals("1") && dbh.isLike(m+"")){
               items.add(dbh.display2(m+""));
            }

            if(getArguments().get("FRG").equals(dbh.get_Cat_Cat(m))){
                items.add(dbh.display2(m+""));
            }
        }

/*
        if(getArguments().get("FRG").equals("HOME")){
            items.add(dbh.display2("2"));
        }
*/
        dbh.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        AgahiAdapter agahiAdapter = new AgahiAdapter(getContext(),items);
        recyclerView.setAdapter(agahiAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
