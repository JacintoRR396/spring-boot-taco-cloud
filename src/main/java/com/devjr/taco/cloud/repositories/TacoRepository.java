package com.devjr.taco.cloud.repositories;

import com.devjr.taco.cloud.entities.Taco;

public interface TacoRepository{

    String S_TABLE_TACO = "Taco";
    String S_TTACO_ID = "id";
    String S_TTACO_NAME = "name";
    String S_TTACO_CREATEDAT = "createdAt";
    int I_TTACO_NAME = 50;
    String S_TABLE_TAING = "Taco_Ingredients";
    String S_TTAING_TACO = "taco";
    String S_TTAING_ING = "ingredient";
    int I_TTAING_ING = 4;

    Taco save(Taco design);

}
