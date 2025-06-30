def call(Map config = [:], Closure body = null) {
    wrap([$class: 'ServerBuildWrapper',
          secrets: [
            [
              id: config.id,
              baseUrl: '',
              credentialId: env.SECRET_SERVER_CRED,
              mappings: [
                [field: 'SecretFile', environmentVariable: 'SecretFile'],
              ]
            ]
          ]
    ]) {
        if (body != null) {
            body()
        }
    }
}
