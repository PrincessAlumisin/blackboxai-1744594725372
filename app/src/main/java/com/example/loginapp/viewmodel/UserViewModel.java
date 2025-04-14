package com.example.loginapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.loginapp.data.UserRepository;
import com.example.loginapp.model.User;
import com.example.loginapp.utils.PasswordHasher;
import com.example.loginapp.utils.SharedPrefManager;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private SharedPrefManager sharedPrefManager;
    private MutableLiveData<Boolean> registrationResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        sharedPrefManager = new SharedPrefManager(application);
    }

    public void registerUser(String username, String email, String password) {
        new Thread(() -> {
            try {
                userRepository.registerUser(username, email, password);
                registrationResult.postValue(true);
            } catch (Exception e) {
                registrationResult.postValue(false);
            }
        }).start();
    }

    public void loginUser(String email, String password) {
        new Thread(() -> {
            User user = userRepository.getUserByEmail(email).getValue();
            if (user != null && PasswordHasher.hashPassword(password).equals(user.passwordHash)) {
                sharedPrefManager.setLogin(true, user.username, user.email);
                loginResult.postValue(true);
            } else {
                loginResult.postValue(false);
            }
        }).start();
    }

    public LiveData<Boolean> getRegistrationResult() {
        return registrationResult;
    }

    public LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public boolean isLoggedIn() {
        return sharedPrefManager.isLoggedIn();
    }

    public void logout() {
        sharedPrefManager.clearSession();
    }
}
