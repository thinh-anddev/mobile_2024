package com.example.food_app.helper;

public class CallBack {
    public interface OnCategoryCallBack {
        void onClick(String category);
    }

    public interface OnDataLoad {
        void onDataLoad();
    }

    public interface OnSearchData {
        void onSearch();
    }

    public interface OnAddToFarvourite {
        void onAdd();
    }

}
