import java.io.*

class CliExecutor {
    def registry = Locator.locateUserRegistry()
    def following = Locator.locateUserFollowing()


    public execute(String userInput) {
        if (userInput.startsWith("register")) {
            def userToRegister = userInput.split(" ")[1]
            registry.registerUser(userToRegister)
        } else if (userInput.startsWith("follow")) {
            def inputSplit = userInput.split(" ")
            def userA = inputSplit[1]
            def userB = inputSplit[2]
            following.follow(userA, userB)
        } else if (userInput.startsWith("list registered")) {
            println "=" * 10
            registry.getUsers().each {
                println it.nick
            }
            println "=" * 10
        } else if (userInput.startsWith("list follow")) {
            def inputSplit = userInput.split(" ")
            def userName = inputSplit[2]
            def user = registry.getUser(userName)
            println "=" * 10
            user.followings.each {
                println it
            }
            println "=" * 10
        } else if (userInput.startsWith("help")) {
            println '''\
Available commands:
    * help - print this help
    * register <user> - register user
    * follow <userA> <userB> - follow user
    * list registered - show list of registered users
    * list follow <user> - show list of following for user
    * quit - exit the cli
            '''
        }
    }

    public static main(args) {
        Locator.type = "memory"
        def executor = new CliExecutor()
        def reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(">> ")
        def line = reader.readLine()

        while (line != "quit") {
            println "Executing..."
            try {
                executor.execute(line)
                println "OK"
            } catch (Exception e) {
                println e.message
            }
            System.out.print(">> ")
            line = reader.readLine()
        }
    }
}
