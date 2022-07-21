package com.example.foodrecipe.model.entities

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MealResponse(
    @Expose
    @SerializedName("meals")
    var mealsEntity: List<MealsEntity>
)

@Parcelize
data class MealsEntity(

    @Expose
    @SerializedName("idMeal")
    val idmeal: String?,

    @Expose
    @SerializedName("strMeal")
    val strmeal: String?,

    @Expose
    @SerializedName("strCategory")
    val strcategory: String?,

    @Expose
    @SerializedName("strArea")
    val strarea: String?,

    @Expose
    @SerializedName("strInstructions")
    val strinstructions: String?,

    @Expose
    @SerializedName("strMealThumb")
    val strmealthumb: String?,

    @Expose
    @SerializedName("strTags")
    val strtags: String?,

    @Expose
    @SerializedName("strYoutube")
    val stryoutube: String?,

    @Expose
    @SerializedName("strIngredient1")
    val stringredient1: String?,

    @Expose
    @SerializedName("strIngredient2")
    val stringredient2: String?,

    @Expose
    @SerializedName("strIngredient3")
    val stringredient3: String?,

    @Expose
    @SerializedName("strIngredient4")
    val stringredient4: String?,

    @Expose
    @SerializedName("strIngredient5")
    val stringredient5: String?,

    @Expose
    @SerializedName("strIngredient6")
    val stringredient6: String?,

    @Expose
    @SerializedName("strIngredient7")
    val stringredient7: String?,

    @Expose
    @SerializedName("strIngredient8")
    val stringredient8: String?,

    @Expose
    @SerializedName("strIngredient9")
    val stringredient9: String?,

    @Expose
    @SerializedName("strIngredient10")
    val stringredient10: String?,

    @Expose
    @SerializedName("strIngredient11")
    val stringredient11: String?,

    @Expose
    @SerializedName("strIngredient12")
    val stringredient12: String?,

    @Expose
    @SerializedName("strIngredient13")
    val stringredient13: String?,

    @Expose
    @SerializedName("strIngredient14")
    val stringredient14: String?,

    @Expose
    @SerializedName("strIngredient15")
    val stringredient15: String?,

    @Expose
    @SerializedName("strIngredient16")
    val stringredient16: String?,

    @Expose
    @SerializedName("strIngredient17")
    val stringredient17: String?,

    @Expose
    @SerializedName("strIngredient18")
    val stringredient18: String?,

    @Expose
    @SerializedName("strIngredient19")
    val stringredient19: String?,

    @Expose
    @SerializedName("strIngredient20")
    val stringredient20: String?,

    @Expose
    @SerializedName("strMeasure1")
    val strmeasure1: String?,

    @Expose
    @SerializedName("strMeasure2")
    val strmeasure2: String?,

    @Expose
    @SerializedName("strMeasure3")
    val strmeasure3: String?,

    @Expose
    @SerializedName("strMeasure4")
    val strmeasure4: String?,

    @Expose
    @SerializedName("strMeasure5")
    val strmeasure5: String?,

    @Expose
    @SerializedName("strMeasure6")
    val strmeasure6: String?,

    @Expose
    @SerializedName("strMeasure7")
    val strmeasure7: String?,

    @Expose
    @SerializedName("strMeasure8")
    val strmeasure8: String?,

    @Expose
    @SerializedName("strMeasure9")
    val strmeasure9: String?,

    @Expose
    @SerializedName("strMeasure10")
    val strmeasure10: String?,

    @Expose
    @SerializedName("strMeasure11")
    val strmeasure11: String?,

    @Expose
    @SerializedName("strMeasure12")
    val strmeasure12: String?,

    @Expose
    @SerializedName("strMeasure13")
    val strmeasure13: String?,

    @Expose
    @SerializedName("strMeasure14")
    val strmeasure14: String?,

    @Expose
    @SerializedName("strMeasure15")
    val strmeasure15: String?,

    @Expose
    @SerializedName("strMeasure16")
    val strmeasure16: String?,

    @Expose
    @SerializedName("strMeasure17")
    val strmeasure17: String?,

    @Expose
    @SerializedName("strMeasure18")
    val strmeasure18: String?,

    @Expose
    @SerializedName("strMeasure19")
    val strmeasure19: String?,

    @Expose
    @SerializedName("strMeasure20")
    val strmeasure20: String?,

    @Expose
    @SerializedName("strSource")
    val strsource: String?
) : Parcelable {

    fun toRecipes():Recipes
    {
        return Recipes(
            dishId = idmeal.toString(),
            dishName = strmeal.toString(),
            dishArea = strarea.toString(),
            dishCategory = strcategory.toString(),
            dishImage = strmealthumb.toString(),
            dishYoutubeUrl = stryoutube.toString(),
            dishInstructions = strinstructions.toString(),
            dishSource = strsource.toString(),
            dishTags = strtags.toString(),
            dishIngredient1 = stringredient1.toString(),
            dishIngredient2 = stringredient2.toString(),
            dishIngredient3 = stringredient3.toString(),
            dishIngredient4 = stringredient4.toString(),
            dishIngredient5 = stringredient5.toString(),
            dishIngredient6 = stringredient6.toString(),
            dishIngredient7 = stringredient7.toString(),
            dishIngredient8 = stringredient8.toString(),
            dishIngredient9 = stringredient9.toString(),
            dishIngredient10 = stringredient10.toString(),
            dishIngredient11 = stringredient11.toString(),
            dishIngredient12 = stringredient12.toString(),
            dishIngredient13 = stringredient13.toString(),
            dishIngredient14 = stringredient14.toString(),
            dishIngredient15 = stringredient15.toString(),
            dishIngredient16 = stringredient16.toString(),
            dishIngredient17 = stringredient17.toString(),
            dishIngredient18 = stringredient18.toString(),
            dishIngredient19 = stringredient19.toString(),
            dishIngredient20 = stringredient20.toString(),
            dishMeasure1 = strmeasure1.toString(),
            dishMeasure2 = strmeasure2.toString(),
            dishMeasure3 = strmeasure3.toString(),
            dishMeasure4 = strmeasure4.toString(),
            dishMeasure5 = strmeasure5.toString(),
            dishMeasure6 = strmeasure6.toString(),
            dishMeasure7 = strmeasure7.toString(),
            dishMeasure8 = strmeasure8.toString(),
            dishMeasure9 = strmeasure9.toString(),
            dishMeasure10 = strmeasure10.toString(),
            dishMeasure11 = strmeasure11.toString(),
            dishMeasure12 = strmeasure12.toString(),
            dishMeasure13 = strmeasure13.toString(),
            dishMeasure14 = strmeasure14.toString(),
            dishMeasure15 = strmeasure15.toString(),
            dishMeasure16 = strmeasure16.toString(),
            dishMeasure17 = strmeasure17.toString(),
            dishMeasure18 = strmeasure18.toString(),
            dishMeasure19 = strmeasure19.toString(),
            dishMeasure20 = strmeasure20.toString()
        )
    }
}
