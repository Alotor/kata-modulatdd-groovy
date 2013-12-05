class Locator {
    static _instance

    static initialize(String type="memory", Map config=[:]) {
        _instance = new Locator(type:type, config:config)
    }

    static getInstance() {
        return _instance
    }

    def type
    def userStore
    def followService
    def config

    def getUserStore() {
        if (userStore == null) {
            if (type == "memory") {
                userStore = new UserStoreInMemory()
            } else {
                userStore = new UserStoreSqlite(config.database?:"sample")
            }
        }
        return userStore
    }

    def getFollowService() {
        if (followService == null) {
            followService = new FollowService(userStore: this.userStore)
        }
        return followService
    }
}
