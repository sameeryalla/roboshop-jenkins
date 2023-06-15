def call() {
    pipeline {
        agent {
            node {
                label 'workstation'
            }
        }
        options {
            ansiColor('xterm')
        }


        stages {
            stage ('Code Compile'){
                steps {
                    sh 'mvn compile'
                }
            }
            stage ('Code Quality Check'){
                steps {
                    //sh 'ls -l'
                    //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.91.157:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
                    sh 'echo Code Quaity'
                }
            }
            stage ('Unit Test Cases'){
                steps {
                    sh 'echo Unit Test cases'
                    sh 'mvn test'
                }
            }
            stage ('Checkmarx SAST Scan'){
                steps {
                    sh 'echo Checkmarx SAST Scan'
                }
            }
            stage ('Checkmarx SCA Scan'){
                steps {
                    sh 'echo Checkmarx SCA Scan'
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