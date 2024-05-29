def call() {
    bat "if exist \"${env.WORKSPACE}\" rmdir /s /q \"${env.WORKSPACE}\""
}