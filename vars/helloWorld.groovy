def call(Map config = [:], Closure body = null) {
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
                [field: 'PrivateKey Passphrase', environmentVariable: 'passphrase']
              ]
            ]
          ]
    ]) {
        if (body != null) {
            body()
        }
    }
}
