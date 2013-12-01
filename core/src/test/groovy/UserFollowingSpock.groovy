import spock.lang.*
import groovy.sql.*

class UserFollowingSpock extends Specification {
    def setup() {
        def sql = Sql.newInstance('jdbc:sqlite:sample.db','org.sqlite.JDBC' )
        sql.execute("drop table if exists users")
        sql.execute("drop table if exists user_following")
        sql.execute("create table users( username varchar primary_key )")
        sql.execute("create table user_following( user varchar, following varchar, primary key(user, following))")
    }

    void "follow user"() {
        setup:
            def userRegistry = new UserRegistryDatabase()
            userRegistry.registerUser(userA)
            userRegistry.registerUser(userB)

            def userFollowing = new UserFollowing(registry: userRegistry)

        when:
            def result = userFollowing.follow(userA, userB)

        then:
            result == true

        where:
            userA = "@alonso"
            userB = "@algohace"
    }

    void "user a follows b and then we check that is following"() {
        setup:
            def userRegistry = new UserRegistryDatabase()
            userRegistry.registerUser(userA)
            userRegistry.registerUser(userB)
            userRegistry.registerUser(userC)

            def userFollowing = new UserFollowing(registry: userRegistry)

            userFollowing.follow(userA, userB)
            userFollowing.follow(userA, userC)

        when:
            def result = userFollowing.getFollowings(userA)

        then:
            result != null
            result.size() == 2
            result.contains(userB)
            result.contains(userC)

        where:
            userA = "@alotor"
            userB = "@algohace"
            userC = "@adelatorrefos"
    }

    void "When #userA follow #userB twice, you only got one following"() {
        setup:
            def userRegistry = new UserRegistryDatabase()
            userRegistry.registerUser(userA)
            userRegistry.registerUser(userB)

            def userFollowing = new UserFollowing(registry: userRegistry)

            userFollowing.follow(userA, userB)
            userFollowing.follow(userA, userB)

        when:
            def result = userFollowing.getFollowings(userA)

        then:
            result != null
            result.size() == 1
            result.contains(userB)

        where:
            userA = "@alotor"
            userB = "@algohace"
    }
}
