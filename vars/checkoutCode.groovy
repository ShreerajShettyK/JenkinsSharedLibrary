// def call(String repoUrl, String branch){
//    def workingDir = "${env.WORKSPACE}"
//    bat "git clone ${repoUrl} ${workingDir}"
//    bat "git checkout ${branch}"
//    return workingDir
// }


def call(String repoUrl, String branch) {
    def workingDir = "${env.WORKSPACE}"
    if (isUnix()) {
        sh "git clone ${repoUrl} ${workingDir}"
        sh "git checkout ${branch}"
    } else {
        bat "git clone ${repoUrl} ${workingDir}"
        bat "git checkout ${branch}"
    }
    return workingDir
}
