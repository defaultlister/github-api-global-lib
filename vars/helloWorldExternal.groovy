def call(Map config = [:], Closure body = null) {
    wrap([$class: 'ServerBuildWrapper',
          secrets: [
            [
              id: config.id,
              baseUrl: '',
              credentialId: env.SECRET_SERVER_CRED,
              mappings: [
                [field: 'Private Key', environmentVariable: 'privatekey'],
                [field: 'Private Key Passphrase', environmentVariable: 'passphrase']
              ]
            ]
          ]
    ]) {
        if (body != null) {
            body()
        }
    }
}
