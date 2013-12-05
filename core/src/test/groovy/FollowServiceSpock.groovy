import spock.lang.*
import groovy.sql.*

class UserFollowingSpock extends Specification {
    def userFollowing
    def userRegistry

    def setup() {
        Locator.initialize("memory")
        userRegistry = Locator.instance.userStore
        userFollowing = Locator.instance.followService
    }

    void "follow user"() {
        setup:
            userRegistry.registerUser(userA)
            userRegistry.registerUser(userB)

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
            userRegistry.registerUser(userA)
            userRegistry.registerUser(userB)
            userRegistry.registerUser(userC)

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
            userRegistry.registerUser(userA)
            userRegistry.registerUser(userB)

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
