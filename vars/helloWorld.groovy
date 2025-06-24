def call(Map config = [:]) {
    wrap([$class: 'ServerBuildWrapper',
          secrets: [
            [
              id: ${config.id}. ,
              baseUrl: '',
              credentialId: env.SECRET_SERVER_CRED,
              mappings: [
                [field: 'Username', environmentVariable: 'username'],
                [field: 'Password', environmentVariable: 'password']
              ]
            ]
          ]
        ])
}
