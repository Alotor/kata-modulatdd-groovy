import java.io.*

class CliExecutor {
    def registry = Locator.locateUserRegistry()
    def following = Locator.locateUserFollowing()


    public execute(String userInput) {
        if (userInput.contains("register")) {
            def userToRegister = userInput.split(" ")[1]
            registry.registerUser(userToRegister)
        } else if (userInput.contains("follow")) {
            def inputSplit = userInput.split(" ")
            def userA = inputSplit[1]
            def userB = inputSplit[2]
            following.follow(userA, userB)
        }
    }

    public static main(args) {
        Locator.type = "memory"
        def executor = new CliExecutor()
        def reader = new BufferedReader(new InputStreamReader(System.in));
        def line = reader.readLine()

        while (line != "quit") {
            println "Executing..."
            executor.execute(line)
            println "OK"
            line = reader.readLine()
        }
    }
}
