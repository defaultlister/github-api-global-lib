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
                [field: 'SecretFile', environmentVariable: 'secretfile']
              ]
            ]
          ]
    ]) {
        if (body != null) {
            body()
        }
    }
}
