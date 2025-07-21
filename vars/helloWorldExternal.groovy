def call(List<Map> secretsList = [], Closure body) {
    if (!secretsList || !(secretsList instanceof List)) {
        error("Expected a list of secrets with 'id' (Integer) and 'prefix'.")
    }

    def secrets = secretsList.collect { entry ->
        if (!(entry.id instanceof Integer)) {
            error("Secret 'id' must be an Integer. Got: ${entry.id}")
        }

        if (!entry.prefix) {
            error("Missing 'prefix' for secret with id ${entry.id}")
        }

        def credentialId = entry.get('credentialId', 'delinea_jenkins')
        def fields = entry.get('fields', ['Username', 'Password'])

        def mappings = fields.collect { field ->
            [
                field              : field,
                environmentVariable: "${entry.prefix}_${field.toUpperCase()}"
            ]
        }

        return [
            $class       : 'com.delinea.secrets.jenkins.wrapper.cred.ServerSecret',
            id           : entry.id,
            credentialId : credentialId,
            mappings     : mappings
        ]
    }

    wrap([$class: 'com.delinea.secrets.jenkins.wrapper.cred.ServerBuildWrapper', secrets: secrets]) {
        body()
    }
}
