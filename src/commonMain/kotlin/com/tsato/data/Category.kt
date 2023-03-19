package com.tsato.data

import kotlinx.serialization.Serializable

sealed interface Food
sealed interface Handiwork

@Serializable
sealed class Category(val name: String) {
    object Meat: Category("Meat"), Food
    object SeaFood: Category("Sea Food"), Food
    object Rice: Category("Rice"), Food
    object Bread: Category("Bread"), Food
    object Vegetables: Category("Vegetables"), Food
    object Fruits: Category("Fruits"), Food
    object Diary: Category("Diary"), Food

    object Pottery: Category("Pottery"), Handiwork
    object Clothes: Category("Clothes"), Handiwork

    companion object {
        fun getAllFoodCategories(): List<Category> = listOf(
            Meat, SeaFood, Rice, Bread, Vegetables, Fruits, Diary
        )
        fun getAllHandiworkCategories(): List<Category> = listOf(
            Pottery, Clothes
        )
    }

    sealed class SubCategory(val name: String) {
        object KomekoPan: SubCategory("Komeko Pan")
        object komekoNoodle: SubCategory("Komeko Noodle")
    }

}

@Serializable
sealed class FoodState : Food {
    object ReadyMade
    object Ingredient
    object Drink
}
