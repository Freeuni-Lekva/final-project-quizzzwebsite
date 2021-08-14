package com.example.quizzzwebsite;

import java.sql.Timestamp;

public interface quiz {
    public String getName();

    public int getCreatorID();

    public String getDescription();

    public Timestamp getCreationTime();
    public boolean isPractice();

    public boolean isCorrect();

    public boolean isPages();

    public boolean isRandom();
}
