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
        parameters {
            choice(name: 'env', choices: ['dev','prod'], description: 'pick Environment')
        }

        stages {
            stage ('Terraform INIT'){
                steps {
                    sh 'terraform init -backend-config=env-${env}/state.vfvars'
                }
            }
            stage ('Terraform APPLY'){
                steps {
                    sh 'terraform apply -backend-config=env-${env}/state.vfvars'
                }
            }
        }
        post {
            always {
                clearWs()
            }
        }

    }
}