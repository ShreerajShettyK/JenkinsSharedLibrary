def call() {
    bat "del /Q \"${env.WORKSPACE}\\*.*\""
}
