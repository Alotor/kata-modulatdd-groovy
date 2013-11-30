class UserFollowing {
    def registry = new UserRegistry()

    public boolean follow(String nickA, String nickB) {
        def userA = registry.getUser(nickA)
        return userA.addFollowing(nickB)
    }

    public List getFollowings(String user) {
        return registry.getUser(user).followings
    }
}
