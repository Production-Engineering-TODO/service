pipeline {
    agent any

    stages {
        stage('Gradle') {
            steps {
                sh 'gradle clean build'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test --info'
            }
        }
        stage('Tag image') {
            steps {
                script {
                    GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
                    MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
                    MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
                    PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
                }
                sh "docker build -t lucianblaga/todo:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
            }
        }
        stage('Deploy image') {
            steps {
                sh 'echo "1a72af1f-3fcf-4ff4-a01f-4e0be88ec47b" | docker login docker.io -u lucianblaga --password-stdin'
                sh "docker push lucianblaga/todo:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION}"
            }
        }
    }
}