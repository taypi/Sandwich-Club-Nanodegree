package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException{
        Sandwich sandwich = new Sandwich();

        JSONObject sandwichJson = new JSONObject(json);

        sandwich.setMainName(getSandwichMainName(sandwichJson));
        sandwich.setAlsoKnownAs(getSandwichAka(sandwichJson));
        sandwich.setIngredients(getIngredients(sandwichJson));
        sandwich.setDescription(sandwichJson.optString(Sandwich.KEY_DESCRIPTION));
        sandwich.setImage(sandwichJson.optString(Sandwich.KEY_IMAGE_URL));
        sandwich.setPlaceOfOrigin(sandwichJson.optString(Sandwich.KEY_PLACE_OF_ORIGIN));

        return sandwich;
    }

    private static String getSandwichMainName(JSONObject sandwichJson) throws JSONException {
        return sandwichJson.getJSONObject(Sandwich.KEY_NAME).optString(Sandwich.KEY_MAIN_NAME);
    }

    private static List<String> getSandwichAka(JSONObject sandwichJson) throws JSONException {
        JSONObject nameJson = sandwichJson.getJSONObject(Sandwich.KEY_NAME);
        return getListFromJsonArray(nameJson, Sandwich.KEY_ALSO_KNOWN_AS);
    }

    private static List<String> getIngredients(JSONObject sandwichJson) {
        return getListFromJsonArray(sandwichJson, Sandwich.KEY_INGREDIENTS);
    }

    private static List<String> getListFromJsonArray(JSONObject json, String key) {
        List<String> list = new ArrayList<>();
        JSONArray array = json.optJSONArray(key);
        for (int i = 0; i < array.length(); i++) {
            list.add(array.optString(i));
        }
        return list;
    }
}
