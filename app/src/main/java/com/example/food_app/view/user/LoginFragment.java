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
import com.example.food_app.view.splash.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.Executor;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
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
                                    user = mAuth.getCurrentUser();
                                    getUserFromFirebase(new CallBack.OnDataLoad() {
                                        @Override
                                        public void onDataLoad() {
                                            if (currentUser.isBlock()) {
                                                signOut();
                                                Toast.makeText(requireContext(), "Tài khoản bạn không tồn tại", Toast.LENGTH_SHORT).show();
                                            } else {
                                                showAlertDialog();
                                            }
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

        binding.tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), ResetPasswordActivity.class));
        });
    }

    private void showAlertDialog() {
        dialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bạn có muốn lưu mật khẩu?")
                .setPositiveButton("OK", (dialog1, which) -> {
                    SharePreferenceUtils.putString(Constant.USERNAME,email);
                    SharePreferenceUtils.putString(Constant.PASSWORD,password);
                    if (currentUser.getAdmin().equals("0")) {
                        startActivity(new Intent(requireActivity(), HomeActivity.class));
                    } else startActivity(new Intent(requireActivity(), AdminActivity.class));
                    Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();
                })
                .setNegativeButton("Không", (dialog1, which) -> {
                    if (currentUser.getAdmin().equals("0")) {
                        startActivity(new Intent(requireActivity(), HomeActivity.class));
                    } else startActivity(new Intent(requireActivity(), AdminActivity.class));
                    Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void getUserFromFirebase(CallBack.OnDataLoad listener) {
        rf.child("Users").child(user != null ? user.getUid() : "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentUser = snapshot.getValue(User.class);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initLoadingData() {
        dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Vui lòng đợi");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }

    public void signOut() {
        mAuth.signOut();
        // Chuyển hướng người dùng đến màn hình đăng nhập hoặc màn hình chính
        Intent intent = new Intent(requireActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        SharePreferenceUtils.putString(Constant.USERNAME,"");
        SharePreferenceUtils.putString(Constant.PASSWORD,"");
        startActivity(intent);
        requireActivity().finish(); // Đảm bảo người dùng không thể quay lại màn hình này bằng nút back
    }
}
