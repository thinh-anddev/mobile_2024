package com.example.food_app.view.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class UserPagerAdapter extends FragmentStateAdapter {
    public UserPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
        }
        return new LoginFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
