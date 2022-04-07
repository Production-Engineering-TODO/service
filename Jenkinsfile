pipeline {
    agent any

     environment {
            DOCKER_PASSWORD = credentials("docker_password")
            GITHUB_TOKEN = credentials("github_token")
    }

    stages {
        stage('Gradle') {
            steps {
                sh 'gradle clean build'
            }
        }
        stage('Unit and Integration Testing') {
            steps {
                sh './gradlew test --info'
            }
        }
        stage('Performance Testing') {
            steps {
                sh 'gradle bootRun &'
                sh 'jmeter -n -t src/test/PerformanceTesting.jmx -l results.jtl'
                sh 'gradle -stop'
                sh 'cat results.jtl'
            }
        }
        stage('Tag image') {
            steps {
                script {
                   sh([script: 'git fetch --tag', returnStdout: true]).trim()
                   env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                   env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                   env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                   env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                }
                sh "docker build -t lucianblaga/todo:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
            }
        }
        stage('Deploy image') {
            steps {
                sh "echo ${env.DOCKER_PASSWORD} | docker login docker.io -u lucianblaga --password-stdin"
                sh "docker push lucianblaga/todo:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION}"

                sh "git tag ${env.IMAGE_TAG}"
                sh "git push https://$GITHUB_TOKEN@github.com/Production-Engineering-TODO/service.git ${env.IMAGE_TAG}"
            }
        }

        stage('Run Application') {
            steps {
                sh "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
            }
        }
    }
}