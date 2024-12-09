package com.example.bubble;

import com.example.bubble.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        Button backToCatalogButton = findViewById(R.id.backToCatalogButton2);

        db = FirebaseFirestore.getInstance();

        cartItems = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartItems, new CartAdapter.OnCartItemChangeListener() {
            @Override
            public void onItemRemoved(int position, String itemId) {
                db.collection("cart").document(itemId).delete()
                        .addOnSuccessListener(aVoid -> {
                            cartItems.remove(position);
                            cartAdapter.notifyItemRemoved(position);
                            Toast.makeText(CartActivity.this, "Evento eliminado del carrito", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(CartActivity.this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

            @Override
            public void onQuantityChanged(int position, int newQuantity) {
            }
        });

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        backToCatalogButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(this, CatalogActivity.class);
            startActivity(backIntent);
            finish();
        });

        loadCartItems();
    }

    private void loadCartItems() {
        db.collection("cart").get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        cartItems.clear();
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            CartItem item = document.toObject(CartItem.class);
                            item.setId(document.getId());
                            cartItems.add(item);
                        }
                        cartAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar el carrito: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }


}
