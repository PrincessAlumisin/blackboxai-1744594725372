package com.example.loginapp.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.loginapp.model.User;
import com.example.loginapp.utils.PasswordHasher;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
    private ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void registerUser(String username, String email, String password) {
        executorService.execute(() -> {
            String hashedPassword = PasswordHasher.hashPassword(password);
            User user = new User();
            user.username = username;
            user.email = email;
            user.passwordHash = hashedPassword;
            userDao.insertUser(user);
        });
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
