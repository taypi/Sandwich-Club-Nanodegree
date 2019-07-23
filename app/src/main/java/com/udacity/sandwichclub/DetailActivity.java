package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String NO_INGREDIENTS = "No ingredients";
    public static final String NO_ALIASES = "No aliases";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            // Sandwich data unavailable
            e.printStackTrace();
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mDescriptionTextView.setText(sandwich.getDescription());
        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mAlsoKnownTextView.setText(getAka(sandwich.getAlsoKnownAs()));
        mIngredientsTextView.setText(getIngredients(sandwich.getIngredients()));
    }

    private String getIngredients(List<String> ingredientsList) {
        String ingredients = getStringFromList(ingredientsList, "\n\n");
        return ingredients.isEmpty() ? NO_INGREDIENTS : ingredients;
    }

    private String getAka(List<String> akaList) {
        String aka = getStringFromList(akaList, ", ");
        return aka.isEmpty() ? NO_ALIASES : aka.substring(0, aka.length() - 2);
    }

    private String getStringFromList(List<String> list, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : list) {
            stringBuilder.append(item).append(separator);
        }
        return stringBuilder.toString();
    }
}
