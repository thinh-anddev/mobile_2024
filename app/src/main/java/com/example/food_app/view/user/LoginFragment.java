package com.example.food_app.view.user;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.food_app.base.BaseFragment;
import com.example.food_app.databinding.FragmentLoginBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.User;
import com.example.food_app.utils.CommonUtils;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.example.food_app.view.admin.AdminActivity;
import com.example.food_app.view.home.HomeActivity;
import com.example.food_app.view.internet.InternetActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    FirebaseAuth mAuth;
    private String email;
    private String password;
    private ProgressDialog dialog;
    private User currentUser;
    @Override
    protected FragmentLoginBinding setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup) {
        return FragmentLoginBinding.inflate(inflater, viewGroup, false);
    }

    @Override
    protected void initView() {
        mAuth = FirebaseAuth.getInstance();
        initLoadingData();
    }

    @Override
    protected void viewListener() {
        binding.btnLogin.setOnClickListener(v -> {
            if (CommonUtils.isNetworkAvailable(requireContext())) {
                dialog.show();
                email = String.valueOf(binding.edtUsername.getText());
                password = String.valueOf(binding.edtPassword.getText());
                if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener( task -> {
                                if (task.isSuccessful()) {
                                    ((UserActivity) getActivity()).getUserFromFirebase(new CallBack.OnDataLoad() {
                                        @Override
                                        public void onDataLoad() {
                                            currentUser = ((UserActivity) getActivity()).currentUser;
                                            if (currentUser.isBlock()) {
                                                ((UserActivity) getActivity()).signOut();
                                                Toast.makeText(requireContext(), "Tài khoản bạn không tồn tại", Toast.LENGTH_SHORT).show();
                                            } else showAlertDialog();
                                        }
                                    });
                                } else {
                                    Toast.makeText(requireContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                }
            } else {
                startActivity(new Intent(requireActivity(), InternetActivity.class));
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bạn có muốn lưu mật khẩu?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        SharePreferenceUtils.putString(Constant.USERNAME,email);
                        SharePreferenceUtils.putString(Constant.PASSWORD,password);
                        if (currentUser.getAdmin().equals("0")) {
                            startActivity(new Intent(requireActivity(), HomeActivity.class));
                        } else startActivity(new Intent(requireActivity(), AdminActivity.class));
                        Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        dialog1.dismiss();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        if (currentUser.getAdmin().equals("0")) {
                            startActivity(new Intent(requireActivity(), HomeActivity.class));
                        } else startActivity(new Intent(requireActivity(), AdminActivity.class));
                        Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        dialog1.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initLoadingData() {
        dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Vui lòng đợi");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }

}
