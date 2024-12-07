package com.example.bubble;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Obtener vistas
        ImageView eventImageView = findViewById(R.id.eventImageView);
        TextView eventTitleTextView = findViewById(R.id.eventTitleTextView);
        TextView eventDateTextView = findViewById(R.id.eventDateTextView);
        TextView eventDescriptionTextView = findViewById(R.id.eventDescriptionTextView);
        Button addToCartButton = findViewById(R.id.addToCartButton);
        Button backToCatalogButton = findViewById(R.id.backToCatalogButton);

        // Obtener datos del intent
        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        String eventDate = intent.getStringExtra("eventDate");
        String eventDescription = intent.getStringExtra("eventDescription");
        String eventImageUrl = intent.getStringExtra("eventImageUrl");

        // Configurar vistas con los datos recibidos
        eventTitleTextView.setText(eventName);
        eventDateTextView.setText(eventDate);
        eventDescriptionTextView.setText(eventDescription);

        Glide.with(this).load(eventImageUrl).into(eventImageView);

        // Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Acción del botón "Agregar al Carrito"
        addToCartButton.setOnClickListener(v -> {
            // Crear un objeto CartItem
            CartItem cartItem = new CartItem(eventName, eventDate, eventImageUrl, 1);

            // Agregar a la colección "cart" en Firestore
            db.collection("cart")
                    .add(cartItem)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Evento agregado al carrito", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error al agregar al carrito: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        // Acción del botón "Volver al Catálogo"
        backToCatalogButton.setOnClickListener(v -> {
            // Volver a la actividad del catálogo
            Intent backIntent = new Intent(this, CatalogActivity.class);
            startActivity(backIntent);
            finish(); // Opcional, para que esta actividad no quede en la pila
        });
    }
}
