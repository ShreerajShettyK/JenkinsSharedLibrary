def call(Map config) {
    pipeline {
        agent any

        environment {
            AWS_ECR_REPOSITORY = config.ecrRepo
            AWS_REGION = config.awsRegion
            DOCKER_IMAGE = config.dockerImage
            IMAGE_TAG = config.imageTag
        }

        stages {
            stage('Login to ECR') {
                steps {
                    script {
                        def ecrLogin = sh(script: "aws ecr get-login-password --region ${env.AWS_REGION} | docker login --username AWS --password-stdin ${env.AWS_ECR_REPOSITORY}", returnStdout: true).trim()
                        echo ecrLogin
                    }
                }
            }

            stage('Push Docker Image to ECR') {
                steps {
                    script {
                        docker.withRegistry("https://${env.AWS_ECR_REPOSITORY}", '') {
                            docker.image("${env.DOCKER_IMAGE}:${env.IMAGE_TAG}").push("${env.IMAGE_TAG}")
                        }
                    }
                }
            }
        }

        post {
            always {
                cleanWs()
            }
        }
    }
}
