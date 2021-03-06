import groovy.sql.Sql

class UserStoreInMemory {
    def registeredUsers = []

    public UserStoreInMemory() {
    }

    public boolean registerUser(String userName) {
        if (getUser(userName) != null) {
            throw new Exception("User already registered")
        }
        registeredUsers << new User(nick: userName)
        return true
    }

    public void updateUser(User user) {
    }

    public User getUser(String userName) {
        return registeredUsers.find { it -> it.nick == userName }
    }

    public List getUsers() {
        return registeredUsers
    }
}
