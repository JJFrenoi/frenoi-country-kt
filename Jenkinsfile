pipeline {
    agent {
        kubernetes {
            inheritFrom 'jenkins-gradle-docker-agent'
            defaultContainer 'gradle'
            yamlFile 'template/build.yml'
        }
    }
    environment {
        GRADLE_USER_HOME = '/workspace/.gradle'
        IMAGE_NAME = 'frenoi/frenoi-country-kt'
        MONGO_URI = credentials('MONGO_URI')
        MONGO_DATABASE = credentials('frenoi-country-database')
    }
    parameters {
        booleanParam(name: 'DEPLOY_BOOL', defaultValue: false, description: 'Deploy to production')
        booleanParam(name: 'TAG_VERSION', defaultValue: false, description: 'Tag version')
        string(name: 'APP_VERSION', defaultValue: '0.0.0', description: 'Version of the app to be deploy')
    }
    stages {
        stage('Build') {
            when {
                expression {
                    !params.TAG_VERSION
                }
            }
            steps {
                container('gradle') {
                    sh 'gradle clean build --no-daemon'
                }
            }
        }
        stage('Docker Build') {
            when {
                expression {
                    params.TAG_VERSION
                }
            }
            steps {
                container('docker') {
                    script {
                        def localImageVersion = "${env.LOCAL_REGISTRY}/${IMAGE_NAME}:${APP_VERSION}"
                        sh "docker build -t ${localImageVersion} . --build-arg MONGO_URI=${MONGO_URI} --build-arg MONGO_DATABASE=${MONGO_DATABASE}"
                        sh "docker push ${localImageVersion}"
                    }
                }
            }
        }
    }
    post {
        cleanup {
            cleanWs()
        }
    }
}
