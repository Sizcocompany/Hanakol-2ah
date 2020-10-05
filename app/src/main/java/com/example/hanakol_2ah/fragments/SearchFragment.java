package com.example.hanakol_2ah.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.MealAdapter;
import com.example.hanakol_2ah.models.Meals;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class SearchFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    private MealAdapter adapter;
    private String child = "breakfast";
    private FirestoreRecyclerOptions<Meals> options;

    private View view;
    private SelectedItemFragment selectedItemFragment;


    private EditText meal_search_tv;
    private RecyclerView recyclerView;
    private MealAdapter movieAdapter;
    private List<Meals> movieList;
    //----------------------------------------------------------------------------------------------
    private TextView breackfast_search_Click, lunch_search_Click, dinner_search_Click, juice_search_Click, desserts_search_Click;
    //----------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        this.view = view;
        selectedItemFragment = new SelectedItemFragment();
        meal_search_tv = view.findViewById(R.id.meal_search_tv);

        ImageView ic_close = view.findViewById(R.id.close_click_image);

        setUpRecyclerView(view, child);


        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().remove(SearchFragment.this).commitAllowingStateLoss();
            }
        });


        meal_search_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Query query;

                if (s.toString().isEmpty()) {
                    notebookRef = db.collection(child);
                    query = notebookRef.orderBy("mealName", Query.Direction.ASCENDING);
                    options = new FirestoreRecyclerOptions.Builder<Meals>()
                            .setQuery(query, Meals.class)
                            .build();
                } else {

                    query = db.collection(child)
                            .whereEqualTo("mealName", s.toString())
                            .orderBy("mealName", Query.Direction.ASCENDING);
                }

                options = new FirestoreRecyclerOptions.Builder<Meals>()
                        .setQuery(query, Meals.class)
                        .build();
                adapter = new MealAdapter(getActivity().getApplicationContext(), options);
                RecyclerView recyclerView = view.findViewById(R.id.container_search_recyclerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                adapter.startListening();

                adapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                        Meals meal = documentSnapshot.toObject(Meals.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("MEAL_NAME", meal.getMealName());
                        bundle.putString("MEAL_DESCRIPTION", meal.getDescription());
                        bundle.putString("MEAL_STEP", meal.getSteps());
                        bundle.putString("MEAL_IMAGE_URI", meal.getImageURL());
                        bundle.putString("MEAL_RATE", meal.getMealRate().toString());
                        bundle.putString("MEAL_OWNER_EMAIL", meal.getMealOwner());
                        bundle.putString("MEAL_CREATION_DATE", meal.getMealCreationDate());
                        bundle.putString("CHILD", "breakfast");
                        FragmentTransaction(selectedItemFragment, bundle);

                    }
                });

            }
        });


//        --------------------------------meals_search_Click----------------------------------------
        breackfast_search_Click = view.findViewById(R.id.breackfast_search_Click);
        lunch_search_Click = view.findViewById(R.id.lunch_search_Click);
        dinner_search_Click = view.findViewById(R.id.dinner_search_Click);
        juice_search_Click = view.findViewById(R.id.juice_search_Click);
        desserts_search_Click = view.findViewById(R.id.desserts_search_Click);
//        ------------------------------------------------------------------------------------------

        breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));

        breackfast_search_Click.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                child = "breakfast";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                setUpRecyclerView(view, child);

            }
        });

        lunch_search_Click.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                child = "lunch";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                setUpRecyclerView(view, child);

            }
        });
        dinner_search_Click.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                child = "dinner";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                setUpRecyclerView(view, child);

            }
        });
        juice_search_Click.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                child = "juices";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                setUpRecyclerView(view, child);

            }
        });
        desserts_search_Click.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                child = "desserts";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
                setUpRecyclerView(view, child);

            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    private void setUpRecyclerView(View v, final String child) {

        notebookRef = db.collection(child);
        Query query = notebookRef.orderBy("mealName", Query.Direction.ASCENDING);
        options = new FirestoreRecyclerOptions.Builder<Meals>()
                .setQuery(query, Meals.class)
                .build();
        adapter = new MealAdapter(getActivity().getApplicationContext(), options);
        RecyclerView recyclerView = v.findViewById(R.id.container_search_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        onStart();


        adapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Meals meal = documentSnapshot.toObject(Meals.class);
                Bundle bundle = new Bundle();
                bundle.putString("MEAL_NAME", meal.getMealName());
                bundle.putString("MEAL_DESCRIPTION", meal.getDescription());
                bundle.putString("MEAL_STEP", meal.getSteps());
                bundle.putString("MEAL_IMAGE_URI", meal.getImageURL());
                bundle.putString("MEAL_RATE", meal.getMealRate().toString());
                bundle.putString("MEAL_OWNER_EMAIL", meal.getMealOwner());
                bundle.putString("MEAL_CREATION_DATE", meal.getMealCreationDate());
                bundle.putString("CHILD", child);

                FragmentTransaction(selectedItemFragment, bundle);

            }
        });
    }

    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragment.setArguments(bundle);
    }


}