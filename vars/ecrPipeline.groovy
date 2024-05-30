def call(Map config) {
    pipeline {
        agent any

        environment {
            AWS_ECR_REPOSITORY = config.ecrRepo
            AWS_REGION = config.awsRegion
            DOCKER_IMAGE = config.dockerImage
        }

        stages {
            stage('Checkout') {
                steps {
                    git url: config.gitUrl, branch: config.branch
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        docker.build("${env.DOCKER_IMAGE}")
                    }
                }
            }

            stage('Login to ECR') {
                steps {
                    withAWS(credentials: 'team3-aws-credentials', region: env.AWS_REGION) {
                        script {
                            def ecrLogin = sh(script: 'aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ECR_REPOSITORY', returnStdout: true).trim()
                            echo ecrLogin
                        }
                    }
                }
            }

            stage('Push Docker Image to ECR') {
                steps {
                    script {
                        docker.withRegistry("https://${env.AWS_ECR_REPOSITORY}", '') {
                            docker.image("${env.DOCKER_IMAGE}").push("${config.tag}")
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
