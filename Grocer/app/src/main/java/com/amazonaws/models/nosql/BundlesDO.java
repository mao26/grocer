package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "grocer-mobilehub-822272901-Bundles")

public class BundlesDO {
    private String _userId;
    private String _name;
    private String _recipeDescription;
    private List<String> _recipeItems;
    private Map<String, String> _recipes;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "recipe_description")
    public String getRecipeDescription() {
        return _recipeDescription;
    }

    public void setRecipeDescription(final String _recipeDescription) {
        this._recipeDescription = _recipeDescription;
    }
    @DynamoDBAttribute(attributeName = "recipe_items")
    public List<String> getRecipeItems() {
        return _recipeItems;
    }

    public void setRecipeItems(final List<String> _recipeItems) {
        this._recipeItems = _recipeItems;
    }
    @DynamoDBAttribute(attributeName = "recipes")
    public Map<String, String> getRecipes() {
        return _recipes;
    }

    public void setRecipes(final Map<String, String> _recipes) {
        this._recipes = _recipes;
    }

}
