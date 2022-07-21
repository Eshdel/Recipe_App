package com.example.foodrecipe.model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
@Entity(tableName = "Recipes")
data class Recipes(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,

    @ColumnInfo(name = "dish_id")
    val dishId: String,

    @ColumnInfo(name = "dish_name")
    val dishName:String,

    @ColumnInfo(name = "dish_category")
    val dishCategory: String,

    @ColumnInfo(name = "dish_area")
    val dishArea: String,

    @ColumnInfo(name = "dish_instructions")
    val dishInstructions: String,

    @ColumnInfo(name = "dish_image")
    val dishImage: String,

    @ColumnInfo(name = "dish_tags")
    val dishTags: String,

    @ColumnInfo(name = "dish_youtube_url")
    val dishYoutubeUrl: String,

    @ColumnInfo(name = "dish_ingredient_1")
    val dishIngredient1: String,

    @ColumnInfo(name = "dish_ingredient_2")
    val dishIngredient2: String,

    @ColumnInfo(name = "dish_ingredient_3")
    val dishIngredient3: String,

    @ColumnInfo(name = "dish_ingredient_4")
    val dishIngredient4: String,

    @ColumnInfo(name = "dish_ingredient_5")
    val dishIngredient5: String,

    @ColumnInfo(name = "dish_ingredient_6")
    val dishIngredient6: String,

    @ColumnInfo(name = "dish_ingredient_7")
    val dishIngredient7: String,

    @ColumnInfo(name = "dish_ingredient_8")
    val dishIngredient8: String,

    @ColumnInfo(name = "dish_ingredient_9")
    val dishIngredient9: String,

    @ColumnInfo(name = "dish_ingredient_10")
    val dishIngredient10: String,

    @ColumnInfo(name = "dish_ingredient_11")
    val dishIngredient11: String,

    @ColumnInfo(name = "dish_ingredient_12")
    val dishIngredient12: String,

    @ColumnInfo(name = "dish_ingredient_13")
    val dishIngredient13: String,

    @ColumnInfo(name = "dish_ingredient_14")
    val dishIngredient14: String,

    @ColumnInfo(name = "dish_ingredient_15")
    val dishIngredient15: String,

    @ColumnInfo(name = "dish_ingredient_16")
    val dishIngredient16: String,

    @ColumnInfo(name = "dish_ingredient_17")
    val dishIngredient17: String,

    @ColumnInfo(name = "dish_ingredient_18")
    val dishIngredient18: String,

    @ColumnInfo(name = "dish_ingredient_19")
    val dishIngredient19: String,

    @ColumnInfo(name = "dish_ingredient_20")
    val dishIngredient20: String,

    @ColumnInfo(name = "dish_measure_1")
    val dishMeasure1: String,

    @ColumnInfo(name = "dish_measure_2")
    val dishMeasure2: String,

    @ColumnInfo(name = "dish_measure_3")
    val dishMeasure3: String,

    @ColumnInfo(name = "dish_measure_4")
    val dishMeasure4: String,

    @ColumnInfo(name = "dish_measure_5")
    val dishMeasure5: String,

    @ColumnInfo(name = "dish_measure_6")
    val dishMeasure6: String,

    @ColumnInfo(name = "dish_measure_7")
    val dishMeasure7: String,

    @ColumnInfo(name = "dish_measure_8")
    val dishMeasure8: String,

    @ColumnInfo(name = "dish_measure_9")
    val dishMeasure9: String,

    @ColumnInfo(name = "dish_measure_10")
    val dishMeasure10: String,

    @ColumnInfo(name = "dish_measure_11")
    val dishMeasure11: String,

    @ColumnInfo(name = "dish_measure_12")
    val dishMeasure12: String,

    @ColumnInfo(name = "dish_measure_13")
    val dishMeasure13: String,

    @ColumnInfo(name = "dish_measure_14")
    val dishMeasure14: String,

    @ColumnInfo(name = "dish_measure_15")
    val dishMeasure15: String,

    @ColumnInfo(name = "dish_measure_16")
    val dishMeasure16: String,

    @ColumnInfo(name = "dish_measure_17")
    val dishMeasure17: String,

    @ColumnInfo(name = "dish_measure_18")
    val dishMeasure18: String,

    @ColumnInfo(name = "dish_measure_19")
    val dishMeasure19: String,

    @ColumnInfo(name = "dish_measure_20")
    val dishMeasure20: String,

    @ColumnInfo(name = "dish_source")
    val dishSource: String
) : Parcelable {
    fun toMealEntity():MealsEntity {
        return MealsEntity(
            idmeal = dishId,
            strmeal = dishName,
            strarea = dishArea,
            strcategory = dishCategory,
            strmealthumb = dishImage,
            stryoutube = dishYoutubeUrl,
            strinstructions = dishInstructions,
            strsource = dishSource,
            strtags = dishTags,
            stringredient1 = dishIngredient1,
            stringredient2 = dishIngredient2,
            stringredient3 = dishIngredient3,
            stringredient4 = dishIngredient4,
            stringredient5 = dishIngredient5,
            stringredient6 = dishIngredient6,
            stringredient7 = dishIngredient7,
            stringredient8 = dishIngredient8,
            stringredient9 = dishIngredient9,
            stringredient10 = dishIngredient10,
            stringredient11 = dishIngredient11,
            stringredient12 = dishIngredient12,
            stringredient13 = dishIngredient13,
            stringredient14 = dishIngredient14,
            stringredient15 = dishIngredient15,
            stringredient16 = dishIngredient16 ,
            stringredient17 = dishIngredient17,
            stringredient18 = dishIngredient18,
            stringredient19 = dishIngredient19,
            stringredient20 = dishIngredient20,
            strmeasure1 = dishMeasure1,
            strmeasure2 = dishMeasure2,
            strmeasure3 = dishMeasure3,
            strmeasure4 = dishMeasure4,
            strmeasure5 = dishMeasure5,
            strmeasure6 = dishMeasure6,
            strmeasure7 = dishMeasure7,
            strmeasure8 = dishMeasure8,
            strmeasure9 = dishMeasure9,
            strmeasure10 = dishMeasure10,
            strmeasure11 = dishMeasure11,
            strmeasure12 = dishMeasure12,
            strmeasure13 = dishMeasure13,
            strmeasure14 = dishMeasure14,
            strmeasure15 = dishMeasure15,
            strmeasure16 = dishMeasure16,
            strmeasure17 = dishMeasure17,
            strmeasure18 = dishMeasure18,
            strmeasure19 = dishMeasure19 ,
            strmeasure20 = dishMeasure20
        )
    }
}
