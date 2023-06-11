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
                    sh 'terraform init -backend-config=env-${env}/state.tfvars'
                }
            }
            stage ('Terraform APPLY'){
                steps {
                    sh 'terraform apply -auto-approve -var-file=env-${env}/main.tfvars'
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