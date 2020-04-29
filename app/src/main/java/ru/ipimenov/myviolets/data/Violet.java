package ru.ipimenov.myviolets.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "violets")
public class Violet {

    @PrimaryKey
    private int violetCounterId; // id по порядку загрузки
    private int violetId; // id если есть картинка, если нет 0
    private String violetName; // название сорта
    private String violetBreeder; // селекционер
    private String violetYear; // год релиза
    private String violetOverview; // описание сорта
    private String violetThumbnailPath; // путь к маленькому изображению
    private String violetImagePath; // путь к большому изображению

    public Violet(int violetCounterId, int violetId, String violetName, String violetBreeder, String violetYear, String violetOverview, String violetThumbnailPath, String violetImagePath) {
        this.violetCounterId = violetCounterId;
        this.violetId = violetId;
        this.violetName = violetName;
        this.violetBreeder = violetBreeder;
        this.violetYear = violetYear;
        this.violetOverview = violetOverview;
        this.violetThumbnailPath = violetThumbnailPath;
        this.violetImagePath = violetImagePath;
    }

    public int getVioletCounterId() {
        return violetCounterId;
    }

    public void setVioletCounterId(int violetCounterId) {
        this.violetCounterId = violetCounterId;
    }

    public int getVioletId() {
        return violetId;
    }

    public void setVioletId(int violetId) {
        this.violetId = violetId;
    }

    public String getVioletName() {
        return violetName;
    }

    public void setVioletName(String violetName) {
        this.violetName = violetName;
    }

    public String getVioletBreeder() {
        return violetBreeder;
    }

    public void setVioletBreeder(String violetBreeder) {
        this.violetBreeder = violetBreeder;
    }

    public String getVioletYear() {
        return violetYear;
    }

    public void setVioletYear(String violetYear) {
        this.violetYear = violetYear;
    }

    public String getVioletOverview() {
        return violetOverview;
    }

    public void setVioletOverview(String violetOverview) {
        this.violetOverview = violetOverview;
    }

    public String getVioletThumbnailPath() {
        return violetThumbnailPath;
    }

    public void setVioletThumbnailPath(String violetThumbnailPath) {
        this.violetThumbnailPath = violetThumbnailPath;
    }

    public String getVioletImagePath() {
        return violetImagePath;
    }

    public void setVioletImagePath(String violetImagePath) {
        this.violetImagePath = violetImagePath;
    }
}
