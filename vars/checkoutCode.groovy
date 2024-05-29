def call(String repoUrl, String branch){
   def workingDir = "${env.WORKSPACE}"
   bat "git clone ${repoUrl} ${workingDir}"
   bat "git checkout ${branch}"
   return workingDir
}
