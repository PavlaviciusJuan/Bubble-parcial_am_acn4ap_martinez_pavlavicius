package com.example.bubble;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        db = FirebaseFirestore.getInstance();
        eventRecyclerView = findViewById(R.id.eventRecyclerView);
        Button goToCartButton = findViewById(R.id.goToCartButton);

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(this, eventList);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventRecyclerView.setAdapter(eventAdapter);

        loadEvents();

        goToCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void loadEvents() {
        db.collection("events").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        eventList.addAll(queryDocumentSnapshots.toObjects(Event.class));
                        eventAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar eventos: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
