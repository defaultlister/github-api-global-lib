def call(Map config = [:], Closure body = null) {
    def prefix = config.prefix ?: "SECRET"

    wrap([$class: 'ServerBuildWrapper',
          secrets: [[
              id: config.id,
              baseUrl: '',
              credentialId: env.SECRET_SERVER_CRED,
              mappings: [
                [field: 'Username', environmentVariable: "${prefix}_username"],
                [field: 'Password', environmentVariable: "${prefix}_password"],
                [field: 'SecretFile', environmentVariable: "${prefix}_secretfile"],
                [field: 'PrivateKey', environmentVariable: "${prefix}_privatekey"],
                [field: 'PublicKey', environmentVariable: "${prefix}_publickey"],
                [field: 'itemValue', environmentVariable: "${prefix}_itemValue"],
                [field: 'PrivateKeyPassphrase', environmentVariable: "${prefix}_passphrase"]
              ]
          ]]
    ]) {
        if (body != null) {
            body()
        }
    }
}
