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
                [field: 'SecretFile', environmentVariable: "${prefix}_file"],
                [field: 'itemValue', environmentVariable: "${prefix}_item"]
              ]
          ]]
    ]) {
        if (body != null) {
            body()
        }
    }
}
