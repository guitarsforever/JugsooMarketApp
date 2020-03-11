package com.example.jugsoomarket.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugsoomarket.Adapter.CartListAdapter;
import com.example.jugsoomarket.R;
import com.example.jugsoomarket.ViewModel.ItemViewModel;
import com.example.jugsoomarket.ViewModel.item;

import java.util.List;
import java.util.Map;


public class BaseFragment extends Fragment {
    private static final String ITEM_DELETED = "Item deleted";
    private static final String ITEM_TOTAL = "Total = $";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button addBtn;
    private Button scanQR;
    private TextView textTotal;
    private ItemViewModel itemViewModel;
    private CartListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_fragment, container, false);
        setViews(rootView);
        clickListeners(rootView);
        initRecyclerView();
        initViewModel();
        swipeToDelete();
        return rootView;
    }

    private void setViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        addBtn = (Button) rootView.findViewById(R.id.add);
        scanQR = (Button) rootView.findViewById(R.id.scanQR);
        textTotal = (TextView) rootView.findViewById(R.id.total);
    }


    private void clickListeners(View rootView) {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTotalString = ITEM_TOTAL + String.format("%d",(adapter.getTotalPrice()));
                textTotal.setText(textTotalString);

            }
        });
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragment();

            }
        });
    }

    private void swapFragment(){
        ManualQRCodeFragment newGamefragment = new ManualQRCodeFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.base_fragment, newGamefragment);
        fragmentTransaction.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
        fragmentTransaction.commit();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new CartListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<item>>() {
            @Override
            public void onChanged(@Nullable List<item> items) {
                adapter.setItemList(items);
            }
        });
    }

    private void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), ITEM_DELETED, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
