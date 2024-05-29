// vars/cleanWorkspace.groovy
def call() {
    echo 'Cleaning workspace...'
    if (isUnix()) {
        sh 'rm -rf *'
    } else {
        bat 'del /s /q *.*'
        bat 'for /d %i in (*) do rmdir /s /q %i'
    }
    echo 'Workspace cleaned.'
}
