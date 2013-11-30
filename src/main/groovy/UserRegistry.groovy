class UserRegistry {
    def registeredUsers = []

    public boolean registerUser(String username) {
        if (registeredUsers.contains(username)) {
            throw new Exception("User already registered")
        }

        registeredUsers << username
        return true
    }
}
