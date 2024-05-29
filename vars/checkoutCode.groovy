def call(String repoUrl, String branch){
   def workingDir = "${env.WORKSPACE}"
   bat "if exist goAwsSdkProj rmdir /s /q goAwsSdkProj"
   bat "git clone ${repoUrl} ${workingDir}"
   bat "git checkout ${branch}"
   return workingDir
}
