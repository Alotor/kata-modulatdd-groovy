import groovy.sql.Sql

class UserStoreSqlite {
    def sql

    public UserStoreSqlite(String database) {
        sql = Sql.newInstance("jdbc:sqlite:${database}.db","org.sqlite.JDBC" )
    }

    public boolean registerUser(String userName) {
        if (getUser(userName) != null) {
            throw new Exception("User already registered")
        }
        sql.executeInsert("insert into users(username) values(?)", [userName])
        return true
    }

    public void updateUser(User user) {
        sql.execute("delete from user_following where user = ?", user.nick)
        user.followings.each { following->
            sql.executeInsert("insert into user_following values (?,?)", [user.nick, following])
        }
    }

    public User getUser(String userName) {
        def row = sql.firstRow("select * from users where username = $userName")
        if (row == null) {
            return null
        }
        def result = new User(nick: row.username)
        sql.eachRow("select * from user_following where user = $userName"){ followingRow->
            result.followings << followingRow.following
        }
        return result
    }

    public List getUsers() {
        def result = []

        sql.eachRow("select * from users") { row->
            result << getUser(row.username)
        }

        return result
    }
}
