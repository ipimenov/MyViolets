package ru.ipimenov.myviolets.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VioletDao {

    @Query("SELECT * FROM violets")
    LiveData<List<Violet>> getAllViolets();

    @Query("SELECT * FROM favourite_violets")
    LiveData<List<FavouriteViolet>> getAllFavouriteViolets();

    @Query("SELECT * FROM violets WHERE violetCounterId == :id")
    Violet getVioletById(int id);

    @Query("SELECT * FROM violets WHERE violetName == :violetName")
    Violet getVioletByVioletName(String violetName);

    @Query("SELECT * FROM favourite_violets WHERE violetName == :violetName")
    FavouriteViolet getFavouriteVioletByVioletName(String violetName);

    @Query("DELETE FROM violets")
    void deleteAllViolets();

    @Insert
    void insertViolet(Violet violet);

    @Delete
    void deleteViolet(Violet violet);

    @Insert
    void insertFavouriteViolet(FavouriteViolet violet);

    @Delete
    void deleteFavouriteViolet(FavouriteViolet violet);
}
