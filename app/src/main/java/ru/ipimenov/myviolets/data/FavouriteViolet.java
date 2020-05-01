package ru.ipimenov.myviolets.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_violets")
public class FavouriteViolet extends Violet {

    public FavouriteViolet(int violetCounterId, int violetId, String violetName,
                           String violetBreeder, String violetYear, String violetOverview,
                           String violetThumbnailPath, String violetImagePath) {
        super(violetCounterId, violetId, violetName, violetBreeder, violetYear, violetOverview,
                violetThumbnailPath, violetImagePath);
    }

    @Ignore
    public FavouriteViolet(Violet violet) {
        super(violet.getVioletCounterId(), violet.getVioletId(), violet.getVioletName(),
                violet.getVioletBreeder(), violet.getVioletYear(), violet.getVioletOverview(),
                violet.getVioletThumbnailPath(), violet.getVioletImagePath());
    }
}
