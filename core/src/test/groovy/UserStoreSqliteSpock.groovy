import spock.lang.*
import groovy.sql.*

class UserStoreSqliteSpock extends Specification {
    def userStore

    def setup() {
        def sql = Sql.newInstance('jdbc:sqlite:test.db','org.sqlite.JDBC' )
        sql.execute("drop table if exists users")
        sql.execute("drop table if exists user_following")
        sql.execute("create table users( username varchar primary_key )")
        sql.execute("create table user_following( user varchar, following varchar, primary key(user, following))")

        userStore = new UserStoreSqlite("test")
    }

    void "user registration"() {
        when:
            def result = userStore.registerUser(userName)

        then:
            result == true

        where:
            userName = "@alonso"

    }

    void "user cannot register twice"() {
        setup:
            userStore.registerUser(userName)

        when:
            def result = userStore.registerUser(userName)

        then:
            thrown Exception

        where:
            userName = "@alonso"
    }

    void "when i register a user i can return it"() {
        setup:
            userStore.registerUser(userName)

        when:
            def result = userStore.getUser(userName)

        then:
            result != null
            result.nick == userName

        where:
            userName = "@alonso"
    }

    void "when you are not registered return null"() {
        when:
            def result = userStore.getUser(userName)

        then:
            result == null

        where:
            userName = "@alonso"
    }

    void "when i register a user with followings returns the same"() {
        setup:
            userStore.registerUser(userName)
            def user = userStore.getUser(userName)
            user.followings = [following1, following2]

        when:
            userStore.updateUser(user)
            def result = userStore.getUser(userName)

        then:
            result.followings != null
            result.followings.size() == 2
            result.followings.contains(following1)
            result.followings.contains(following2)

        where:
            userName = "@alotor"
            following1 = "@algohace"
            following2 = "@adelatorrefoss"
    }

    void "return list of users"() {
        setup:
            userStore.registerUser(userA)
            userStore.registerUser(userB)
            userStore.registerUser(userC)

        when:
            def result = userStore.users

        then:
            result != null
            result.size() == 3
            result*.nick.contains(userA)
            result*.nick.contains(userB)
            result*.nick.contains(userC)

        where:
            userA = "@alotor"
            userB = "@algohace"
            userC = "@adelatorrefoss"
    }
}
