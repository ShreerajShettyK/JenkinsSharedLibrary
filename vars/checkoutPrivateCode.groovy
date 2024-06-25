def call(String repoUrl, String branch, String credentialsId) {
    def workingDir = "${env.WORKSPACE}"
    if (isUnix()) {
        sh "git clone ${repoUrl} ${workingDir}"
        sh "git checkout ${branch}"
    } else {
        bat "git clone ${repoUrl} ${workingDir}"
        bat "git checkout ${branch}"
    }
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh "git config credential.helper 'store --file=.git-credentials'"
        sh "echo -e \"${repoUrl}\nusername=${USERNAME}\npassword=${PASSWORD}\" > .git-credentials"
    }
    return workingDir
}
