def call(Map config = [:], Closure body = null) {
    def prefix = config.prefix ?: "SECRET"

    wrap([$class: 'ServerBuildWrapper',
          secrets: [[
              id: config.id,
              baseUrl: '',
              credentialId: env.SECRET_SERVER_CRED,
              mappings: [
                [field: 'Username', environmentVariable: "${prefix}_USERNAME"],
                [field: 'Password', environmentVariable: "${prefix}_PASSWORD"],
                [field: 'SecretFile', environmentVariable: "${prefix}_FILE"],
                [field: 'itemValue', environmentVariable: "${prefix}_ITEM"]
              ]
          ]]
    ]) {
        if (body != null) {
            body()
        }
    }
}
