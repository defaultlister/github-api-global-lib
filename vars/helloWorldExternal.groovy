def call(Map config = [:], Closure body = null) {
    if (!env.SECRET_SERVER_CRED) {
        error "❌ SECRET_SERVER_CRED is not defined in environment!"
    }
    if (!config.id) {
        error "❌ Secret ID (config.id) must be provided!"
    }

    wrap([$class: 'ServerBuildWrapper',
          secrets: [
            [
              id: config.id,
              baseUrl: '',
              credentialId: env.SECRET_SERVER_CRED,
              mappings: [
                [field: 'Username', environmentVariable: 'username'],
                [field: 'Password', environmentVariable: 'password'],
                [field: 'SecretFile', environmentVariable: 'secretfile'],
                [field: 'PrivateKey', environmentVariable: 'privatekey'],
                [field: 'PublicKey', environmentVariable: 'publickey'],
                [field: 'PrivateKeyPassphrase', environmentVariable: 'passphrase']
              ]
            ]
          ]
    ]) {
        // Optional debug print
        echo "🔍 [DEBUG] username: ${env.username ?: 'null'}"
        echo "🔍 [DEBUG] privatekey (trimmed): ${env.privatekey ? env.privatekey.take(20) + '...' : 'null'}"
        echo "🔍 [DEBUG] secretfile (base64?): ${env.secretfile ? env.secretfile.take(20) + '...' : 'null'}"

        if (body != null) {
            body()
        }
    }
}
