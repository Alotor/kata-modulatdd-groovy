class UserRegistry {
    def registeredUsers = []

    public boolean registerUser(String userName) {
        if (registeredUsers.find {it->it.nick == userName}) {
            throw new Exception("User already registered")
        }
        registeredUsers << new User(nick: userName)
        return true
    }

    public User getUser(String userName) {
        return registeredUsers.find { it -> it.nick == userName }
    }
}
