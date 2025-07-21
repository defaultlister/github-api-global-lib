def call(List<Map> secretsList, Closure body) {
    if (!secretsList) {
        error("Expected non-empty list of secrets.")
    }

    def secrets = secretsList.collect { entry ->
        if (!(entry.id instanceof Integer)) {
            error("Secret 'id' must be an Integer. Got: ${entry.id}")
        }

        def credentialId = entry.get('credentialId', 'delinea_jenkins')
        def fields = entry.get('fields', ['Username', 'Password'])
        def prefix = entry.prefix ?: error("Missing 'prefix' for secret with id ${entry.id}")

        def mappings = fields.collect { field ->
            [field: field, environmentVariable: "${prefix}_${field.toUpperCase()}"]
        }

        return [
            $class: 'com.delinea.secrets.jenkins.wrapper.cred.ServerSecret',
            id: entry.id,
            credentialId: credentialId,
            mappings: mappings
        ]
    }

    wrap([$class: 'com.delinea.secrets.jenkins.wrapper.cred.ServerBuildWrapper', secrets: secrets]) {
        body()
    }
}
