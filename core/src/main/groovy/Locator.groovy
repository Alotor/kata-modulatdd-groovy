class Locator {
    static String type = "memory"

    static userRegistry
    static userFollowing

    static locateUserRegistry() {
        if (userRegistry == null) {
            if (type == "memory") {
                userRegistry = new UserRegistryMemory()
            } else {
                userRegistry = new UserRegistryDatabase()
            }
        }
        return userRegistry
    }

    static locateUserFollowing() {
        if (userFollowing == null) {
            userFollowing = new UserFollowing(registry: locateUserRegistry())
        }
        return userFollowing
    }
}
