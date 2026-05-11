package senandika.ServiceLayer;

// AuthService.java
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private List<User> users = new ArrayList<>();

    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean register(User user) {
        if (isUserExist((String) user.getEmail())) {
            return false;
        }

        users.add(user);
        return true;
    }

    public void logout() {
        System.out.println("Logout berhasil!");
    }

    public boolean isUserExist(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean validatePassword(String password) {
        return password.length() >= 8;
    }
}