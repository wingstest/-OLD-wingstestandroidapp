package com.example.blinktasker.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blinktasker.Adapters.ResAdapter;
import com.example.blinktasker.ApiService;
import com.example.blinktasker.ApiServiceBuilder;
import com.example.blinktasker.Objects.RestaurantModel;
import com.example.blinktasker.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {
    public static final String TAG = "loghere";


    private RecyclerView recyclerView;
    private ArrayList<RestaurantModel> restaurantModelArrayList;
    //    private RestaurantAdapter adapter;
    private ResAdapter resAdapter;
    private RestaurantModel[] restaurantModels = new RestaurantModel[]{};

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        restaurantModelArrayList = new ArrayList<RestaurantModel>();

        resAdapter = new ResAdapter(restaurantModelArrayList, this.getActivity());

        recyclerView = getActivity().findViewById(R.id.restaurant_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(resAdapter);

        getRestaurants();

        addSearchFunction();
    }

    private void getRestaurants() {

        ApiService apiService = ApiServiceBuilder.getService();

        Call<RestaurantModel> call = apiService.getResponseRestuarnatList();

        call.enqueue(new Callback<RestaurantModel>() {
            @Override
            public void onResponse(Call<RestaurantModel> call, Response<RestaurantModel> response) {

                for (int i = 0; i < response.body().getRegistrationArrayListModel().size(); i++) {

                    restaurantModelArrayList.add(response.body().getRegistrationArrayListModel().get(i));

                    Log.d(TAG, "onResponse: restaurantModelArrayList" + restaurantModelArrayList.get(i).getName());
                }
                resAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<RestaurantModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    private void addSearchFunction() {

        EditText searchInput = getActivity().findViewById(R.id.res_search);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                Log.d("SEARCH", charSequence.toString());

                restaurantModelArrayList.clear();

                for (RestaurantModel restaurantModel : restaurantModels) {
                    if (restaurantModel.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {

                        restaurantModelArrayList.add(restaurantModel);
                    }
                }
                resAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
